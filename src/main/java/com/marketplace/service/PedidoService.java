package com.marketplace.service;

import com.marketplace.dto.PedidoRequest;
import com.marketplace.dto.PedidoResponse;
import com.marketplace.exception.NegocioException;
import com.marketplace.exception.RecursoNaoEncontradoException;
import com.marketplace.model.Pedido;
import com.marketplace.model.Produto;
import com.marketplace.model.StatusPedido;
import com.marketplace.model.Usuario;
import com.marketplace.repository.PedidoRepository;
import com.marketplace.repository.ProdutoRepository;
import com.marketplace.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ProdutoRepository produtoRepository,
                         UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Cria o pedido. Tudo acontece numa única transação: ou TUDO é salvo (pedido, estoque, pontos)
     * ou NADA é (se qualquer regra falhar, o banco volta ao estado anterior).
     */
    @Transactional
    public PedidoResponse criar(PedidoRequest req) {
        // Ordem fixa de locks (produto -> usuário) para evitar deadlock entre transações.
        Produto produto = produtoRepository.buscarComLock(req.produtoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto não encontrado: " + req.produtoId()));
        Usuario comprador = usuarioRepository.buscarComLock(req.compradorId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Comprador não encontrado: " + req.compradorId()));

        // O vendedor NÃO vem do front: ele é o dono do produto (o front não consegue forjar isso).
        Usuario vendedor = produto.getVendedor();

        if (vendedor.getId().equals(comprador.getId())) {
            throw new NegocioException("Você não pode comprar o seu próprio produto.");
        }
        int quantidade = req.quantidade();
        if (produto.getQuantidade() < quantidade) {
            throw new NegocioException("Estoque insuficiente. Disponível: " + produto.getQuantidade());
        }

        BigDecimal valorUnitario = produto.getValor();
        BigDecimal valorBruto = valorUnitario.multiply(BigDecimal.valueOf(quantidade));
        long totalEmCentavos = valorBruto.setScale(2, RoundingMode.HALF_UP).movePointRight(2).longValueExact();

        // 100 pontos = R$ 1,00  =>  1 ponto = 1 centavo. Por isso, "pontos usados" == "centavos de desconto".
        long pontosUtilizados = 0;
        if (req.usarPontos()) {
            pontosUtilizados = Math.min(comprador.getPontos(), totalEmCentavos); // nunca desconta mais que o total
        }
        BigDecimal desconto = BigDecimal.valueOf(pontosUtilizados, 2); // (100, escala 2) = 1.00
        BigDecimal valorFinal = valorBruto.subtract(desconto);

        // R$ 1,00 gasto = 1 ponto (só a parte inteira; calculado sobre o que foi realmente pago)
        long pontosGanhos = valorFinal.setScale(0, RoundingMode.DOWN).longValue();

        produto.setQuantidade(produto.getQuantidade() - quantidade);
        comprador.setPontos(comprador.getPontos() - pontosUtilizados + pontosGanhos);

        Pedido pedido = new Pedido(comprador, vendedor, produto, quantidade, valorUnitario,
                valorBruto, desconto, valorFinal, pontosUtilizados, pontosGanhos);
        return PedidoResponse.de(pedidoRepository.save(pedido));
    }

    @Transactional
    public PedidoResponse atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado: " + id));

        if (!pedido.getStatus().podeIrPara(novoStatus)) {
            throw new NegocioException("Transição inválida: " + pedido.getStatus() + " -> " + novoStatus);
        }

        if (novoStatus == StatusPedido.CANCELADO) {
            desfazerEfeitos(pedido);
        }
        pedido.setStatus(novoStatus);
        return PedidoResponse.de(pedido);
    }

    /** Cancelamento: devolve o estoque, devolve os pontos usados e retira os pontos que haviam sido ganhos. */
    private void desfazerEfeitos(Pedido pedido) {
        Produto produto = produtoRepository.buscarComLock(pedido.getProduto().getId()).orElseThrow();
        Usuario comprador = usuarioRepository.buscarComLock(pedido.getComprador().getId()).orElseThrow();

        produto.setQuantidade(produto.getQuantidade() + pedido.getQuantidade());

        long pontos = comprador.getPontos() + pedido.getPontosUtilizados() - pedido.getPontosGanhos();
        comprador.setPontos(Math.max(0, pontos)); // se já gastou os pontos ganhos, o saldo para em 0
    }

    @Transactional(readOnly = true)
    public PedidoResponse buscarPorId(Long id) {
        return PedidoResponse.de(pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado: " + id)));
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> listar(Long compradorId, Long vendedorId) {
        List<Pedido> pedidos;
        if (compradorId != null) {
            pedidos = pedidoRepository.findByCompradorIdOrderByCriadoEmDesc(compradorId);
        } else if (vendedorId != null) {
            pedidos = pedidoRepository.findByVendedorIdOrderByCriadoEmDesc(vendedorId);
        } else {
            pedidos = pedidoRepository.findAll();
        }
        return pedidos.stream().map(PedidoResponse::de).toList();
    }
}
