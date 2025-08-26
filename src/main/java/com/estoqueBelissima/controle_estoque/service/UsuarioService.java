package com.estoqueBelissima.controle_estoque.service;


import com.estoqueBelissima.controle_estoque.model.Usuario;
import com.estoqueBelissima.controle_estoque.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {



    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario buscarPorName(String name) {
        return usuarioRepository.findByName(name).orElse(null);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    public Optional<Usuario> buscarPorId(Long id){
        return usuarioRepository.findById(id);
    }
    public Usuario atualizar(Long id, Usuario usuarioAtualizado){
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    usuarioExistente.setName(usuarioAtualizado.getName());
                    usuarioExistente.setEmail(usuarioAtualizado.getEmail());
                    usuarioExistente.setSenha(usuarioAtualizado.getSenha());
                    return usuarioRepository.save(usuarioExistente);
                })
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: "+ id));
    }
    public void deletar(Long id){
        usuarioRepository.deleteById(id);
    }

    public Usuario validarLogin(String name, String senha) {
        return usuarioRepository.findByName(name)
                .filter(u -> u.getSenha().equals(senha))
                .orElse(null);
    }


}