package com.example.reservas.repository;

import com.example.reservas.entity.Reserva;
import com.example.reservas.entity.Status;
import com.example.reservas.entity.TipoQuarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long>{

    List<Reserva> findByStatus(Status status);

    List<Reserva> findByTipoQuarto(TipoQuarto tipoQuarto);

    List<Reserva> findByDataEntrada(LocalDate dataEntrada);

    List<Reserva> findByDataEntradaBetween(LocalDate inicio, LocalDate fim);

    @Query("SELECT r FROM Reserva r WHERE " +
            "LOWER(r.nomeHospede) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "LOWER(r.emailHospede) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Reserva> buscarPorNomeOuEmail(@Param("termo") String termo);

    //ou
    //List<Reserva> findByNomeHospedeContainingIgnoreCaseOrEmailHospedeContainingIgnoreCase(String nome, String email);

    @Query("SELECT r FROM Reserva r WHERE r.detalhesEstadia IS NOT NULL")
    List<Reserva> findReservasComDetalhes();

    @Query("SELECT r FROM Reserva r LEFT JOIN r.detalhesEstadia d WHERE d.id IS NULL")
    List<Reserva> findReservasSemDetalhes();

}
