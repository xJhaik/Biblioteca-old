package com.intent.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "libro")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LibroModel {

    @Id
    @Getter @Setter
    @Column(name = "id" , updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Getter @Setter
    @Column(name = "titolo", nullable = false, unique = true)
    private String titolo;

    @Getter @Setter
    @Column(name = "autore")
    private String autore;


    @Getter @Setter
    @Column(name = "trama")
    @Lob
    private String trama;

    @Getter @Setter
    @Column(name = "prima_edizione")
    private int primaEdizione;

    @Getter @Setter
    @Column(name = "creatore")
    private String creatore;

    @Getter @Setter
    @ManyToMany (cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "lista_libri_utenti",
            joinColumns = { @JoinColumn(name = "lista_libri_model_id") },
            inverseJoinColumns = { @JoinColumn(name = "utenti_id") })
    private List<ListaLibriModel> lista;
}
