package com.estoqueBelissima.controle_estoque.model;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private LocalDateTime vendaFeitaEm;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        CONCLUIDA,
        CANCELADA,
        PENDENTE
    }

    private Double valorTotal;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private List<ItemVenda> itens;

    @PrePersist
    public void prePersiste(){
        this.vendaFeitaEm = LocalDateTime.now();
    }

    public void calcularValores() {
        double total = 0.0;
        if (itens != null) {
            for (ItemVenda item : itens) {
                item.calcularSubTotal();
                total += item.getSubTotal();
            }
        }
        this.valorTotal = total;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDateTime getVendaFeitaEm() { return vendaFeitaEm; }
    public void setVendaFeitaEm(LocalDateTime vendaFeitaEm) { this.vendaFeitaEm = vendaFeitaEm; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }

    public List<ItemVenda> getItens() { return itens; }
    public void setItens(List<ItemVenda> itens) { this.itens = itens; }
}
