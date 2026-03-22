package com.example.reservas.service;

import com.example.reservas.entity.DetalhesEstadia;
import com.example.reservas.entity.Reserva;
import com.example.reservas.entity.Status;
import com.example.reservas.entity.TipoQuarto;
import com.example.reservas.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    @Transactional
    public Reserva criar(Reserva reserva) {
        LocalDate hoje = LocalDate.now();

        if (reserva.getDataEntrada().isBefore(hoje)) {
            throw new RuntimeException("A data de entrada deve ser a partir de hoje!");
        }

        if (!reserva.getDataSaida().isAfter(reserva.getDataEntrada())) {
            throw new RuntimeException("A data de saída deve ser posterior à data de entrada!");
        }

        reserva.setId(null);
        reserva.setStatus(Status.PENDENTE);
        reserva.setDataCheckin(null);
        reserva.setDataCheckout(null);
        reserva.setDetalhesEstadia(null);

        return repository.save(reserva);
    }

    public List<Reserva> listarTodas() {
        return repository.findAll();
    }

    public Reserva buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID de reserva não encontrado!"));
    }

    @Transactional
    public Reserva atualizar(Long id, Reserva dadosNovos) {
        Reserva reservaExistente = buscarPorId(id);

        if (reservaExistente.getStatus() == Status.CONCLUIDA || reservaExistente.getStatus() == Status.CANCELADA) {
            throw new RuntimeException("Não é possível editar reservas com status finalizado!");
        }

        if (!dadosNovos.getDataSaida().isAfter(dadosNovos.getDataEntrada())) {
            throw new RuntimeException("A nova data de saída deve ser posterior à data de entrada!");
        }

        reservaExistente.setNomeHospede(dadosNovos.getNomeHospede());
        reservaExistente.setEmailHospede(dadosNovos.getEmailHospede());
        reservaExistente.setTelefoneHospede(dadosNovos.getTelefoneHospede());
        reservaExistente.setDataEntrada(dadosNovos.getDataEntrada());
        reservaExistente.setDataSaida(dadosNovos.getDataSaida());
        reservaExistente.setTipoQuarto(dadosNovos.getTipoQuarto());
        reservaExistente.setObservacoes(dadosNovos.getObservacoes());

        if (dadosNovos.getDetalhesEstadia() != null && reservaExistente.getDetalhesEstadia() != null) {
            DetalhesEstadia detalhesExistentes = reservaExistente.getDetalhesEstadia();
            DetalhesEstadia detalhesNovos = dadosNovos.getDetalhesEstadia();

            detalhesExistentes.setNumeroQuarto(detalhesNovos.getNumeroQuarto());
            detalhesExistentes.setAndar(detalhesNovos.getAndar());
            detalhesExistentes.setPossuiFrigobar(detalhesNovos.getPossuiFrigobar());
            detalhesExistentes.setPossuiVaranda(detalhesNovos.getPossuiVaranda());
            detalhesExistentes.setAcessibilidade(detalhesNovos.getAcessibilidade());
            detalhesExistentes.setObservacoesQuarto(detalhesNovos.getObservacoesQuarto());
        } else if (dadosNovos.getDetalhesEstadia() != null) {
            if (reservaExistente.getStatus() != Status.CANCELADA && reservaExistente.getStatus() != Status.CONCLUIDA) {
                dadosNovos.getDetalhesEstadia().setReserva(reservaExistente);
                reservaExistente.setDetalhesEstadia(dadosNovos.getDetalhesEstadia());
            }
        }

        return repository.save(reservaExistente);
    }

    @Transactional
    public void excluir(Long id) {
        Reserva reserva = buscarPorId(id);
        if (reserva.getStatus() == Status.EM_HOSPEDAGEM) {
            throw new RuntimeException("Não é possível excluir uma reserva de um hóspede que já está em hospedagem!");
        }
        repository.delete(reserva);
    }

    @Transactional
    public DetalhesEstadia criarVinculoDetalhes(Long id, DetalhesEstadia novosDetalhes) {
        Reserva reserva = buscarPorId(id);

        if (reserva.getStatus() != Status.PENDENTE && reserva.getStatus() != Status.CONFIRMADA) {
            throw new IllegalStateException("Só é pérmitido adicionar detalhes a uma reserva pendente ou confirmada");
        }

        if (reserva.getDetalhesEstadia() != null) {
            throw new RuntimeException("Esta reserva já possui detalhes vinculados!");
        }

        novosDetalhes.setReserva(reserva);
        reserva.setDetalhesEstadia(novosDetalhes);

        repository.save(reserva);
        return reserva.getDetalhesEstadia();
    }

    public DetalhesEstadia buscarDetalhes(Long id) {
        Reserva reserva = buscarPorId(id);
        if (reserva.getDetalhesEstadia() == null) {
            throw new RuntimeException("Detalhe de estadia não encontrado para esta reserva!");
        }
        return reserva.getDetalhesEstadia();
    }

    @Transactional
    public void removerDetalhes(Long id) {
        Reserva reserva = buscarPorId(id);
        if (reserva.getDetalhesEstadia() != null) {
            reserva.setDetalhesEstadia(null);
            repository.save(reserva);
        }
    }

    public List<Reserva> listarPorStatus(Status status) {
        return repository.findByStatus(status);
    }

    public List<Reserva> listarPorTipoQuarto(TipoQuarto tipo) {
        return repository.findByTipoQuarto(tipo);
    }

    public List<Reserva> listarQuemChegaHoje() {
        return repository.findByDataEntrada(LocalDate.now());
    }

    public List<Reserva> listarProximosCheckins(int dias) {
        LocalDate hoje = LocalDate.now();
        LocalDate dataLimite = hoje.plusDays(dias);
        return repository.findByDataEntradaBetween(hoje, dataLimite);
    }

    public List<Reserva> listarNomeEmail(String termo) {
        return repository.buscarPorNomeOuEmail(termo);
    }

    public List<Reserva> listarEmHospedagem() {
        return repository.findByStatus(Status.EM_HOSPEDAGEM);
    }

    public List<Reserva> listarComDetalhes() {
        return repository.findReservasComDetalhes();
    }

    public List<Reserva> listarSemDetalhes() {
        return repository.findReservasSemDetalhes();
    }

    @Transactional
    public Reserva confirmar(Long id) {
        Reserva reserva = buscarPorId(id);
        if (reserva.getStatus() != Status.PENDENTE) {
            throw new RuntimeException("A confirmação só é permitido em reservas pendentes!");
        }
        reserva.setStatus(Status.CONFIRMADA);
        return repository.save(reserva);
    }

    @Transactional
    public Reserva realizarCheckin(Long id) {
        Reserva reserva = buscarPorId(id);

        if (reserva.getStatus() != Status.CONFIRMADA) {
            throw new RuntimeException("O check-in só é permitido em reservas CONFIRMADAS!");
        }

        LocalDate hoje = LocalDate.now();
        if (hoje.isBefore(reserva.getDataEntrada())) {
            throw new RuntimeException("O check-in só é permitido antes da data de entrada prevista!");
        }

        reserva.setStatus(Status.EM_HOSPEDAGEM);
        reserva.setDataCheckin(LocalDateTime.now());

        return repository.save(reserva);
    }

    @Transactional
    public Reserva realizarCheckout(Long id) {
        Reserva reserva = buscarPorId(id);
        if (reserva.getStatus() != Status.EM_HOSPEDAGEM) {
            throw new RuntimeException("O check-out só é permitido se o status for EM_HOSPEDAGEM!");
        }
        reserva.setStatus(Status.CONCLUIDA);
        reserva.setDataCheckout(LocalDateTime.now());
        return repository.save(reserva);
    }

    @Transactional
    public Reserva cancelar(Long id) {
        Reserva reserva = buscarPorId(id);
        Status atual = reserva.getStatus();

        if (atual != Status.PENDENTE && atual != Status.CONFIRMADA) {
            throw new RuntimeException("Não é possível cancelar uma reserva em andamento ou concluída!");
        }

        reserva.setStatus(Status.CANCELADA);
        return repository.save(reserva);
    }

    @Transactional
    public Reserva alterarTipoQuarto(Long id, TipoQuarto novoTipo) {
        Reserva reserva = buscarPorId(id);
        Status atual = reserva.getStatus();

        if (atual == Status.CANCELADA || atual == Status.CONCLUIDA) {
            throw new RuntimeException("Não é possível trocar o quarto de uma reserva cancelada!");
        }

        reserva.setTipoQuarto(novoTipo);
        return repository.save(reserva);
    }

}
