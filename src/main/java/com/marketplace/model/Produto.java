package com.marketplace.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private int quantidade;

    /** Regra de negócio: todo produto pertence a um usuário (o vendedor). */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Usuario vendedor;

    protected Produto() {
    }

    public Produto(String nome, BigDecimal valor, int quantidade, Usuario vendedor) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.vendedor = vendedor;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getValor() { return valor; }
    public int getQuantidade() { return quantidade; }
    public Usuario getVendedor() { return vendedor; }

    public void setNome(String nome) { this.nome = nome; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}
