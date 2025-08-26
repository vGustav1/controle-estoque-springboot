package com.estoqueBelissima.controle_estoque.controller;


import com.estoqueBelissima.controle_estoque.model.Venda;
import com.estoqueBelissima.controle_estoque.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @GetMapping
    public List<Venda> listar(){
        return vendaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscar(@PathVariable Long id){
        return vendaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venda> atualizar(@PathVariable Long id, @RequestBody Venda venda){
        try{
            Venda atualizado = vendaService.atualizar(id, venda);
            return ResponseEntity.ok(atualizado);
        }catch (IllegalArgumentException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Venda> criar (@RequestBody Venda venda){
        Venda novo = vendaService.salvar(venda);
        return ResponseEntity.ok(novo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        vendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
