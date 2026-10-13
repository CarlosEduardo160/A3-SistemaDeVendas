package com.marketplace.service;

import com.marketplace.dto.LoginRequest;
import com.marketplace.dto.UsuarioRequest;
import com.marketplace.dto.UsuarioResponse;
import com.marketplace.exception.ConflitoException;
import com.marketplace.exception.CredenciaisInvalidasException;
import com.marketplace.exception.RecursoNaoEncontradoException;
import com.marketplace.model.Usuario;
import com.marketplace.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UsuarioResponse cadastrar(UsuarioRequest req) {
        String cpf = req.cpf().replaceAll("\\D", ""); // guarda só os dígitos
        String email = req.email().trim().toLowerCase();

        if (usuarioRepository.existsByCpf(cpf)) {
            throw new ConflitoException("Já existe um usuário com este CPF.");
        }
        if (usuarioRepository.existsByEmail(email)) {
            throw new ConflitoException("Já existe um usuário com este e-mail.");
        }

        Usuario usuario = new Usuario(req.nome().trim(), cpf, req.endereco().trim(),
                email, passwordEncoder.encode(req.senha()));
        return UsuarioResponse.de(usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {
        return UsuarioResponse.de(usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado: " + id)));
    }

    @Transactional(readOnly = true)
    public UsuarioResponse login(LoginRequest req) {
        Usuario usuario = usuarioRepository.findByEmail(req.email().trim().toLowerCase())
                .orElseThrow(CredenciaisInvalidasException::new);

        if (!passwordEncoder.matches(req.senha(), usuario.getSenha())) {
            throw new CredenciaisInvalidasException(); // mesma mensagem: não revela se o e-mail existe
        }
        return UsuarioResponse.de(usuario);
    }
}
