package com.estoqueBelissima.controle_estoque.service;

import com.estoqueBelissima.controle_estoque.model.Fornecedor;
import com.estoqueBelissima.controle_estoque.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public Fornecedor salvar(Fornecedor fornecedor){

        if(fornecedor.getNome() == null || fornecedor.getNome().isBlank()){
            throw new IllegalArgumentException("O nome é obrigatório");
        }
        if (fornecedor.getCnpj() == null || fornecedor.getCnpj().isBlank()){
            throw new IllegalArgumentException("O CNPJ é obrigatório");
        }
        return fornecedorRepository.save(fornecedor);
    }

    public List<Fornecedor> listTodos(){
        return fornecedorRepository.findAll();
    }

    public Optional<Fornecedor> buscarPorId(Long id){
        return fornecedorRepository.findById(id);
    }

    public Fornecedor atualizar(Long id, Fornecedor fornecedorAtualizado){
        return fornecedorRepository.findById(id)
                .map(fornecedorExistente -> {
                    fornecedorExistente.setNome(fornecedorAtualizado.getNome());
                    fornecedorExistente.setCnpj(fornecedorAtualizado.getCnpj());
                    fornecedorExistente.setTelefone(fornecedorAtualizado.getTelefone());
                    fornecedorExistente.setEmail(fornecedorAtualizado.getEmail());
                    return fornecedorRepository.save(fornecedorExistente);
                })
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado com ID: "+id));
    }

    public void deletar(Long id){
        fornecedorRepository.deleteById(id);
    }
}
