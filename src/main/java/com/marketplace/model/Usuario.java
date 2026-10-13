package com.marketplace.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false, unique = true)
    private String email;

    /** Guarda o HASH (BCrypt) da senha, nunca a senha em texto puro. */
    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private long pontos = 0;

    protected Usuario() {
        // exigido pelo JPA
    }

    public Usuario(String nome, String cpf, String endereco, String email, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getEndereco() { return endereco; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public long getPontos() { return pontos; }

    public void setPontos(long pontos) { this.pontos = pontos; }
}
