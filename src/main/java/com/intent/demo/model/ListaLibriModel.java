package com.intent.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lista_libri")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ListaLibriModel {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Getter @Setter
    @Column(name = "titolo", nullable = false, unique = true)
    private String titolo;

    @Getter @Setter
    @ManyToMany (cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UtenteModel> utenti;

    @Getter @Setter
    @Column(name = "libri")
    @ManyToMany(/*mappedBy = "lista",*/ cascade = CascadeType.ALL)
    @JoinTable(name = "libro_lista",
            joinColumns = { @JoinColumn(name = "libri_id") },
            inverseJoinColumns = { @JoinColumn(name = "lista_id") })
    private List<LibroModel> libri;

}
