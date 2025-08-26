package com.estoqueBelissima.controle_estoque.service;


import com.estoqueBelissima.controle_estoque.model.Venda;
import com.estoqueBelissima.controle_estoque.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    private VendaRepository vendaRepository;

    public VendaService(VendaRepository vendaRepository){
        this.vendaRepository = vendaRepository;
    }

    public Venda salvar(Venda venda){
        if (venda.getItens() != null){
            venda.getItens().forEach(item -> item.setVenda(venda));
            venda.calcularValores();
        }
        return vendaRepository.save(venda);
    }

    public List<Venda> listarTodos(){
        return vendaRepository.findAll();
    }

    public Optional<Venda> buscarPorId(Long id){
        return vendaRepository.findById(id);
    }

    public Venda atualizar(Long id, Venda vendaAtualizada){
        return vendaRepository.findById(id)
                .map(vendaExistente -> {
                    vendaExistente.setUsuario(vendaAtualizada.getUsuario());
                    vendaExistente.setStatus(vendaAtualizada.getStatus());
                    vendaExistente.setItens(vendaAtualizada.getItens());
                    if (vendaAtualizada.getItens() != null){
                        vendaAtualizada.getItens().forEach(item -> item.setVenda(vendaExistente));
                        vendaExistente.calcularValores();
                    }
                    return vendaRepository.save(vendaExistente);
                })
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada com o ID: "+id));
    }

    public void deletar(Long id){
        if (!vendaRepository.existsById(id)){
            throw new IllegalArgumentException("Venda não encontrada com o ID: "+id);
        }
        vendaRepository.deleteById(id);
    }

}
