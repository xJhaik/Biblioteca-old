package com.intent.demo.repository;

import com.intent.demo.model.UtenteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<UtenteModel, Integer> {
}
