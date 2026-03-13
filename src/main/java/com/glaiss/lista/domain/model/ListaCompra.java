package com.glaiss.lista.domain.model;

import com.glaiss.core.common.builder.IBuilder;
import com.glaiss.core.domain.model.EntityAbstract;
import com.glaiss.core.utils.anotacao.ValorBigDecimal;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Table(name = "listas_compra")
@Entity
public class ListaCompra extends EntityAbstract {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;

    private String nome;

    @Column(name = "valor_total")
    @ValorBigDecimal
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Column(name = "total_itens")
    private short totalItens;

    @OneToMany(mappedBy = "listaCompra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemLista> itensLista = new LinkedList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_lista_codigo", nullable = false)
    private StatusLista statusLista;

    // Construtor protegido para JPA
    protected ListaCompra() {
    }

    // Construtor privado para o Builder
    private ListaCompra(ListaCompraBuilder builder) {
        this.id = builder.id;
        this.usuarioId = builder.usuarioId;
        this.nome = builder.nome;
        this.valorTotal = builder.valorTotal;
        this.totalItens = builder.totalItens;
        this.itensLista = builder.itensLista;
        this.statusLista = builder.statusLista;
        this.setCreatedDate(builder.createdDate);
        this.setCreatedBy(builder.createdBy);
        this.setModifiedDate(builder.modifiedDate);
        this.setModifiedBy(builder.modifiedBy);
        this.setVersion(builder.version);
    }

    // Getters e Setters manuais
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public short getTotalItens() {
        return totalItens;
    }

    public void setTotalItens(short totalItens) {
        this.totalItens = totalItens;
    }

    public List<ItemLista> getItensLista() {
        return itensLista;
    }

    public void setItensLista(List<ItemLista> itensLista) {
        this.itensLista = itensLista;
    }

    public void recalcularTotais() {
        if (this.itensLista == null || this.itensLista.isEmpty()) {
            this.valorTotal = BigDecimal.ZERO;
            this.totalItens = 0;
            return;
        }

        this.totalItens = (short) this.itensLista.size();

        boolean isFinalizada = this.statusLista != null &&
                               EStatusLista.FINALIZADA.equals(this.statusLista.getCodigo());

        this.valorTotal = this.itensLista.stream()
                .map(item -> {
                    BigDecimal preco = isFinalizada ? item.getPrecoUnitario() :
                                     (item.getItemOferta() != null ? item.getItemOferta().getPreco() : BigDecimal.ZERO);
                    return (preco != null ? preco : BigDecimal.ZERO).multiply(BigDecimal.valueOf(item.getQuantidade()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public StatusLista getStatusLista() {
        return statusLista;
    }

    public void setStatusLista(StatusLista statusLista) {
        this.statusLista = statusLista;
    }

    // Método estático para iniciar o builder
    public static ListaCompraBuilder builder() {
        return new ListaCompraBuilder();
    }

    // Implementação manual do Builder
    public static final class ListaCompraBuilder implements IBuilder<ListaCompra> {
        private UUID id;
        private UUID usuarioId;
        private String nome;
        private BigDecimal valorTotal = BigDecimal.ZERO;
        private short totalItens;
        private List<ItemLista> itensLista = new LinkedList<>();
        private StatusLista statusLista;
        private LocalDateTime createdDate;
        private String createdBy;
        private LocalDateTime modifiedDate;
        private String modifiedBy;
        private Long version;

        private ListaCompraBuilder() {
        }

        public ListaCompraBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public ListaCompraBuilder usuarioId(UUID usuarioId) {
            this.usuarioId = usuarioId;
            return this;
        }

        public ListaCompraBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public ListaCompraBuilder valorTotal(BigDecimal valorTotal) {
            this.valorTotal = valorTotal;
            return this;
        }

        public ListaCompraBuilder totalItens(short totalItens) {
            this.totalItens = totalItens;
            return this;
        }

        public ListaCompraBuilder itensLista(List<ItemLista> itensLista) {
            this.itensLista = itensLista;
            return this;
        }

        public ListaCompraBuilder statusLista(StatusLista statusLista) {
            this.statusLista = statusLista;
            return this;
        }

        public ListaCompraBuilder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public ListaCompraBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public ListaCompraBuilder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public ListaCompraBuilder modifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
            return this;
        }

        public ListaCompraBuilder version(Long version) {
            this.version = version;
            return this;
        }

        @Override
        public ListaCompra build() {
            return new ListaCompra(this);
        }
    }
}
