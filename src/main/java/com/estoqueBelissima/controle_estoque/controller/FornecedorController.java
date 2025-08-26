package com.estoqueBelissima.controle_estoque.controller;

import com.estoqueBelissima.controle_estoque.model.Fornecedor;
import com.estoqueBelissima.controle_estoque.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedores")

public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping
    public List<Fornecedor> listar(){
        return fornecedorService.listTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> buscar (@PathVariable Long id){
        return fornecedorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> atualizar(@PathVariable Long id, @RequestBody Fornecedor fornecedor){
        try {
            Fornecedor atualizado = fornecedorService.atualizar(id, fornecedor);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deletar (@PathVariable Long id){
        fornecedorService.deletar(id);
    }

}
