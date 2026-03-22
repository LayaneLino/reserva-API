package com.example.reservas.repository;

import com.example.reservas.entity.DetalhesEstadia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalhesEstadiaRepository extends JpaRepository<DetalhesEstadia, Long> {
}
