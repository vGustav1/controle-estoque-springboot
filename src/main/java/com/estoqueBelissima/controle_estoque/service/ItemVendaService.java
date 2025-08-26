package com.estoqueBelissima.controle_estoque.service;

import com.estoqueBelissima.controle_estoque.model.ItemVenda;
import com.estoqueBelissima.controle_estoque.model.Venda;
import com.estoqueBelissima.controle_estoque.repository.ItemVendaRepository;
import com.estoqueBelissima.controle_estoque.repository.VendaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemVendaService {

    private final ItemVendaRepository itemVendaRepository;
    private final VendaRepository vendaRepository;

    public ItemVendaService(ItemVendaRepository itemVendaRepository, VendaRepository vendaRepository) {
        this.itemVendaRepository = itemVendaRepository;
        this.vendaRepository = vendaRepository;
    }

    public ItemVenda adicionarItem(Long vendaId, ItemVenda itemVenda) {
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada com o ID: " + vendaId));

        itemVenda.setVenda(venda);
        ItemVenda salvo = itemVendaRepository.save(itemVenda);

        venda.getItens().add(salvo);
        venda.calcularValores();
        vendaRepository.save(venda);

        return salvo;
    }

    public ItemVenda atualizarItem(Long itemId, ItemVenda itemAtualizado) {
        return itemVendaRepository.findById(itemId)
                .map(itemExistente -> {
                    itemExistente.setProduto(itemAtualizado.getProduto());
                    itemExistente.setQtd(itemAtualizado.getQtd());
                    itemExistente.calcularSubTotal();

                    // Atualiza o valor total da venda
                    Venda venda = itemExistente.getVenda();
                    venda.calcularValores();
                    vendaRepository.save(venda);

                    return itemVendaRepository.save(itemExistente);
                })
                .orElseThrow(() -> new IllegalArgumentException("ItemVenda não encontrado com o ID: " + itemId));
    }

    public void removerItem(Long itemId) {
        ItemVenda item = itemVendaRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("ItemVenda não encontrado com o ID: " + itemId));

        Venda venda = item.getVenda();
        itemVendaRepository.delete(item);

        venda.getItens().remove(item);
        venda.calcularValores();
        vendaRepository.save(venda);
    }

    public Optional<ItemVenda> buscarPorId(Long itemId) {
        return itemVendaRepository.findById(itemId);
    }
}
