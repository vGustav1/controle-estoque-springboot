package com.estoqueBelissima.controle_estoque.controller;

import com.estoqueBelissima.controle_estoque.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EstoqueController {

    private final ProdutoService produtoService;

    public EstoqueController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/estoque")
    public String mostrarEstoque(Model model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        return "estoque";
    }
}
