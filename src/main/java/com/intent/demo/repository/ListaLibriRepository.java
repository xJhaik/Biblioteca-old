package com.intent.demo.repository;

import com.intent.demo.model.ListaLibriModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListaLibriRepository extends JpaRepository<ListaLibriModel, Integer> {
}
