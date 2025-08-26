package com.estoqueBelissima.controle_estoque.controller;

import com.estoqueBelissima.controle_estoque.model.ItemVenda;
import com.estoqueBelissima.controle_estoque.service.ItemVendaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itens-venda")
public class ItemVendaController {

    private final ItemVendaService itemVendaService;

    public ItemVendaController(ItemVendaService itemVendaService) {
        this.itemVendaService = itemVendaService;
    }

    @PostMapping("/venda/{vendaId}")
    public ResponseEntity<ItemVenda> adicionarItem(@PathVariable Long vendaId, @Valid @RequestBody ItemVenda itemVenda) {
        return ResponseEntity.ok(itemVendaService.adicionarItem(vendaId, itemVenda));
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemVenda> atualizarItem(@PathVariable Long itemId, @Valid @RequestBody ItemVenda itemAtualizado) {
        try {
            return ResponseEntity.ok(itemVendaService.atualizarItem(itemId, itemAtualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removerItem(@PathVariable Long itemId) {
        try {
            itemVendaService.removerItem(itemId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemVenda> buscarItem(@PathVariable Long itemId) {
        return itemVendaService.buscarPorId(itemId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
