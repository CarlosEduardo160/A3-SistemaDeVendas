package com.marketplace.service;

import com.marketplace.dto.ProdutoRequest;
import com.marketplace.dto.ProdutoResponse;
import com.marketplace.dto.ProdutoUpdateRequest;
import com.marketplace.exception.RecursoNaoEncontradoException;
import com.marketplace.model.Produto;
import com.marketplace.model.Usuario;
import com.marketplace.repository.ProdutoRepository;
import com.marketplace.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    public ProdutoService(ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository) {
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public ProdutoResponse cadastrar(ProdutoRequest req) {
        // Regra: produto precisa de um usuário cadastrado
        Usuario vendedor = usuarioRepository.findById(req.vendedorId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Vendedor não encontrado: " + req.vendedorId()));

        Produto produto = new Produto(req.nome().trim(),
                req.valor().setScale(2, RoundingMode.HALF_UP),
                req.quantidade(), vendedor);
        return ProdutoResponse.de(produtoRepository.save(produto));
    }

    @Transactional
    public ProdutoResponse atualizar(Long id, ProdutoUpdateRequest req) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + id));

        produto.setNome(req.nome().trim());
        produto.setValor(req.valor().setScale(2, RoundingMode.HALF_UP));
        produto.setQuantidade(req.quantidade());
        return ProdutoResponse.de(produto); // o JPA salva sozinho ao fim da transação (entidade "gerenciada")
    }

    @Transactional(readOnly = true)
    public ProdutoResponse buscarPorId(Long id) {
        return ProdutoResponse.de(produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado: " + id)));
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponse> listar(Long vendedorId, boolean apenasDisponiveis) {
        List<Produto> produtos;
        if (vendedorId != null) {
            produtos = produtoRepository.findByVendedorId(vendedorId);
        } else if (apenasDisponiveis) {
            produtos = produtoRepository.findByQuantidadeGreaterThan(0);
        } else {
            produtos = produtoRepository.findAll();
        }
        return produtos.stream().map(ProdutoResponse::de).toList();
    }
}
