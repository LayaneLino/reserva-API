package com.example.reservas.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column
    private String nomeHospede;

    @NotBlank
    @Email
    @Column
    private String emailHospede;

    @NotBlank
    @Size(max = 20)
    @Column(length = 20)
    private String telefoneHospede;

    @Column
    @NotNull
    @FutureOrPresent
    private LocalDate dataEntrada;

    @Column
    @NotNull
    private LocalDate dataSaida;

    @Column
    private LocalDateTime dataCheckin;

    @Column
    private LocalDateTime dataCheckout;

    @Column(updatable = false)
    private LocalDateTime dataCriacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private TipoQuarto tipoQuarto;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status;

    @Size(max = 500)
    @Column
    private String observacoes;

    @OneToOne(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private DetalhesEstadia detalhesEstadia;

    public Reserva() {
    }

    public Reserva(Long id, String nomeHospede, String emailHospede, String telefoneHospede, LocalDate dataEntrada, LocalDate dataSaida, LocalDateTime dataCheckin, LocalDateTime dataCheckout, LocalDateTime dataCriacao, TipoQuarto tipoQuarto, Status status, String observacoes, DetalhesEstadia detalhesEstadia) {
        this.id = id;
        this.nomeHospede = nomeHospede;
        this.emailHospede = emailHospede;
        this.telefoneHospede = telefoneHospede;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.dataCheckin = dataCheckin;
        this.dataCheckout = dataCheckout;
        this.dataCriacao = dataCriacao;
        this.tipoQuarto = tipoQuarto;
        this.status = status;
        this.observacoes = observacoes;
        this.detalhesEstadia = detalhesEstadia;
    }

    @PrePersist
    protected void dataCriacao() {
        this.dataCriacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeHospede() {
        return nomeHospede;
    }

    public void setNomeHospede(String nomeHospede) {
        this.nomeHospede = nomeHospede;
    }

    public String getEmailHospede() {
        return emailHospede;
    }

    public void setEmailHospede(String emailHospede) {
        this.emailHospede = emailHospede;
    }

    public String getTelefoneHospede() {
        return telefoneHospede;
    }

    public void setTelefoneHospede(String telefoneHospede) {
        this.telefoneHospede = telefoneHospede;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public LocalDateTime getDataCheckin() {
        return dataCheckin;
    }

    public void setDataCheckin(LocalDateTime dataCheckin) {
        this.dataCheckin = dataCheckin;
    }

    public LocalDateTime getDataCheckout() {
        return dataCheckout;
    }

    public void setDataCheckout(LocalDateTime dataCheckout) {
        this.dataCheckout = dataCheckout;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public TipoQuarto getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(TipoQuarto tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public DetalhesEstadia getDetalhesEstadia() {
        return detalhesEstadia;
    }

    public void setDetalhesEstadia(DetalhesEstadia detalhesEstadia) {
        this.detalhesEstadia = detalhesEstadia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id) && Objects.equals(nomeHospede, reserva.nomeHospede) && Objects.equals(emailHospede, reserva.emailHospede) && Objects.equals(telefoneHospede, reserva.telefoneHospede) && Objects.equals(dataEntrada, reserva.dataEntrada) && Objects.equals(dataSaida, reserva.dataSaida) && Objects.equals(dataCheckin, reserva.dataCheckin) && Objects.equals(dataCheckout, reserva.dataCheckout) && Objects.equals(dataCriacao, reserva.dataCriacao) && tipoQuarto == reserva.tipoQuarto && status == reserva.status && Objects.equals(observacoes, reserva.observacoes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeHospede, emailHospede, telefoneHospede, dataEntrada, dataSaida, dataCheckin, dataCheckout, dataCriacao, tipoQuarto, status, observacoes);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", nomeHospede='" + nomeHospede + '\'' +
                ", emailHospede='" + emailHospede + '\'' +
                ", telefoneHospede='" + telefoneHospede + '\'' +
                ", dataEntrada=" + dataEntrada +
                ", dataSaida=" + dataSaida +
                ", dataCheckin=" + dataCheckin +
                ", dataCheckout=" + dataCheckout +
                ", dataCriacao=" + dataCriacao +
                ", tipoQuarto=" + tipoQuarto +
                ", status=" + status +
                ", observacoes='" + observacoes + '\'' +
                '}';
    }

}
