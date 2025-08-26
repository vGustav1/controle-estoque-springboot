package com.estoqueBelissima.controle_estoque.service;

import com.estoqueBelissima.controle_estoque.model.Categoria;
import com.estoqueBelissima.controle_estoque.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    public Optional<Categoria> buscarPorNome(String nome){
        return categoriaRepository.findByNome(nome);
    }


    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository){
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria salvar(Categoria categoria){
        if (categoriaRepository.existsByNome(categoria.getNome())) {
            throw new IllegalArgumentException("Já existe uma categoria com este nome: " + categoria.getNome());
        }
        return categoriaRepository.save(categoria);
    }

    public Optional<Categoria> buscarPorId(Long id){
        return categoriaRepository.findById(id);
    }

    public List<Categoria> listarTodos(){
        return categoriaRepository.findAll();
    }

    public Categoria atualizar(Long id, Categoria categoriaAtualizada){
        return categoriaRepository.findById(id)
                .map(categoriaExistente -> {
                    if (!categoriaExistente.getNome().equals(categoriaAtualizada.getNome())
                            && categoriaRepository.existsByNome(categoriaAtualizada.getNome())) {
                        throw new IllegalArgumentException("Já existe uma categoria com este nome: " + categoriaAtualizada.getNome());
                    }
                    categoriaExistente.setNome(categoriaAtualizada.getNome());
                    return categoriaRepository.save(categoriaExistente);
                })
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com o ID: " + id));
    }

    public void deletar(Long id){
        if (!categoriaRepository.existsById(id)) {
            throw new IllegalArgumentException("Categoria não encontrada com o ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
