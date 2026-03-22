package com.example.reservas.controller;

import com.example.reservas.entity.DetalhesEstadia;
import com.example.reservas.entity.Reserva;
import com.example.reservas.entity.Status;
import com.example.reservas.entity.TipoQuarto;
import com.example.reservas.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva criar(@Valid @RequestBody Reserva reserva) {
        return service.criar(reserva);
    }

    @GetMapping
    public List<Reserva> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public Reserva buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Reserva atualizar(@PathVariable Long id, @Valid @RequestBody Reserva reserva) {
        return service.atualizar(id, reserva);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }

    @PostMapping("/detalhes/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public DetalhesEstadia criarDetalhes(@PathVariable Long id, @Valid @RequestBody DetalhesEstadia detalhes) {
        return service.criarVinculoDetalhes(id, detalhes);
    }

    @GetMapping("/detalhes/{id}")
    public DetalhesEstadia buscarDetalhes(@PathVariable Long id) {
        return service.buscarDetalhes(id);
    }

    @PutMapping("/detalhes/{id}")
    public DetalhesEstadia atualizarDetalhes(@PathVariable Long id, @Valid @RequestBody DetalhesEstadia novosDetalhes) {
        Reserva reserva = service.buscarPorId(id);
        reserva.setDetalhesEstadia(novosDetalhes);
        return service.atualizar(id, reserva).getDetalhesEstadia();
    }

    @DeleteMapping("/detalhes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerDetalhes(@PathVariable Long id) {
        service.removerDetalhes(id);
    }

    @GetMapping("/status/{status}")
    public List<Reserva> listarPorStatus(@PathVariable Status status) {
        return service.listarPorStatus(status);
    }

    @GetMapping("/quarto/{tipoQuarto}")
    public List<Reserva> listarPorTipoQuarto(@PathVariable TipoQuarto tipoQuarto) {
        return service.listarPorTipoQuarto(tipoQuarto);
    }

    @GetMapping("/hoje")
    public List<Reserva> listarHoje() {
        return service.listarQuemChegaHoje();
    }

    @GetMapping("/proximas")
    public List<Reserva> listarProximas(@RequestParam(defaultValue = "7") int dias) {
        return service.listarProximosCheckins(dias);
    }

    @GetMapping("/listarNomeEmail")
    public List<Reserva> listarNomeEmail(@RequestParam String termo) {
        return service.listarNomeEmail(termo);
    }

    @GetMapping("/hospedados")
    public List<Reserva> listarHospedados() {
        return service.listarEmHospedagem();
    }

    @GetMapping("/com-detalhes")
    public List<Reserva> listarComDetalhes() {
        return service.listarComDetalhes();
    }

    @GetMapping("/sem-detalhes")
    public List<Reserva> listarSemDetalhes() {
        return service.listarSemDetalhes();
    }

    @PatchMapping("/{id}/confirmar")
    public Reserva confirmar(@PathVariable Long id) {
        return service.confirmar(id);
    }

    @PatchMapping("/{id}/checkin")
    public Reserva checkin(@PathVariable Long id) {
        return service.realizarCheckin(id);
    }

    @PatchMapping("/{id}/checkout")
    public Reserva checkout(@PathVariable Long id) {
        return service.realizarCheckout(id);
    }

    @PatchMapping("/{id}/cancelar")
    public Reserva cancelar(@PathVariable Long id) {
        return service.cancelar(id);
    }

    @PatchMapping("/{id}/quarto/{tipoQuarto}")
    public Reserva alterarTipo(@PathVariable Long id, @PathVariable TipoQuarto tipoQuarto) {
        return service.alterarTipoQuarto(id, tipoQuarto);
    }

}