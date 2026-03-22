package com.example.reservas.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Objects;

@Entity
@Table(name = "detalhes_estadia")
public class DetalhesEstadia {

    @Id
    private Long id;

    @Column
    @NotBlank
    private String numeroQuarto;

    @Column
    @NotNull
    @Min(1)
    private Integer andar;

    @Column
    @NotNull
    private Boolean possuiFrigobar = false;

    @Column
    @NotNull
    private Boolean possuiVaranda = false;

    @Column
    @NotNull
    private Boolean acessibilidade = false;

    @Column
    @Size(max=300)
    private String observacoesQuarto;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonBackReference
    private Reserva reserva;

    public DetalhesEstadia() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroQuarto() {
        return numeroQuarto;
    }

    public void setNumeroQuarto(String numeroQuarto) {
        this.numeroQuarto = numeroQuarto;
    }

    public Integer getAndar() {
        return andar;
    }

    public void setAndar(Integer andar) {
        this.andar = andar;
    }

    public Boolean getPossuiFrigobar() {
        return possuiFrigobar;
    }

    public void setPossuiFrigobar(Boolean possuiFrigobar) {
        this.possuiFrigobar = possuiFrigobar;
    }

    public Boolean getPossuiVaranda() {
        return possuiVaranda;
    }

    public void setPossuiVaranda(Boolean possuiVaranda) {
        this.possuiVaranda = possuiVaranda;
    }

    public Boolean getAcessibilidade() {
        return acessibilidade;
    }

    public void setAcessibilidade(Boolean acessibilidade) {
        this.acessibilidade = acessibilidade;
    }

    public String getObservacoesQuarto() {
        return observacoesQuarto;
    }

    public void setObservacoesQuarto(String observacoesQuarto) {
        this.observacoesQuarto = observacoesQuarto;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalhesEstadia that = (DetalhesEstadia) o;
        return Objects.equals(id, that.id) && Objects.equals(numeroQuarto, that.numeroQuarto) && Objects.equals(andar, that.andar) && Objects.equals(possuiFrigobar, that.possuiFrigobar) && Objects.equals(possuiVaranda, that.possuiVaranda) && Objects.equals(acessibilidade, that.acessibilidade) && Objects.equals(observacoesQuarto, that.observacoesQuarto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroQuarto, andar, possuiFrigobar, possuiVaranda, acessibilidade, observacoesQuarto);
    }

    @Override
    public String toString() {
        return "DetalhesEstadia{" +
                "id=" + id +
                ", numeroQuarto='" + numeroQuarto + '\'' +
                ", andar=" + andar +
                ", possuiFrigobar=" + possuiFrigobar +
                ", possuiVaranda=" + possuiVaranda +
                ", acessibilidade=" + acessibilidade +
                ", observacoesQuarto='" + observacoesQuarto + '\'' +
                '}';
    }

}
