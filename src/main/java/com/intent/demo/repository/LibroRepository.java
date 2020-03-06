package com.intent.demo.repository;

import com.intent.demo.model.LibroModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<LibroModel, Integer> {
}
