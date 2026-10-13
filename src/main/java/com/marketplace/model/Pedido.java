package com.marketplace.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comprador_id", nullable = false)
    private Usuario comprador;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Usuario vendedor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private int quantidade;

    /** Preço do produto NO MOMENTO da compra (o vendedor pode alterar o preço depois). */
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorUnitario;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorBruto;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal desconto;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorFinal;

    @Column(nullable = false)
    private long pontosUtilizados;

    @Column(nullable = false)
    private long pontosGanhos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status = StatusPedido.PENDENTE;

    @Column(nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    protected Pedido() {
    }

    public Pedido(Usuario comprador, Usuario vendedor, Produto produto, int quantidade,
                  BigDecimal valorUnitario, BigDecimal valorBruto, BigDecimal desconto,
                  BigDecimal valorFinal, long pontosUtilizados, long pontosGanhos) {
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorBruto = valorBruto;
        this.desconto = desconto;
        this.valorFinal = valorFinal;
        this.pontosUtilizados = pontosUtilizados;
        this.pontosGanhos = pontosGanhos;
    }

    public Long getId() { return id; }
    public Usuario getComprador() { return comprador; }
    public Usuario getVendedor() { return vendedor; }
    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public BigDecimal getValorUnitario() { return valorUnitario; }
    public BigDecimal getValorBruto() { return valorBruto; }
    public BigDecimal getDesconto() { return desconto; }
    public BigDecimal getValorFinal() { return valorFinal; }
    public long getPontosUtilizados() { return pontosUtilizados; }
    public long getPontosGanhos() { return pontosGanhos; }
    public StatusPedido getStatus() { return status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }

    public void setStatus(StatusPedido status) { this.status = status; }
}
