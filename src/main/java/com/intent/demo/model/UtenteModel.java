package com.intent.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "utenti")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UtenteModel {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name="nome")
    @Getter @Setter
    private String nome;

    @Column(name="cognome")
    @Getter @Setter
    private String cognome;

    @Column(name="username", nullable = false, unique = true)
    @Getter @Setter
    private String username;

    @Column(name="password",  nullable = false)
    @Getter @Setter
    private String password;

    @Column(name="email", nullable = false, unique = true)
    @Getter @Setter
    private String email;

    @Getter @Setter
    @ManyToMany (/*mappedBy = "utenti",*/ cascade = CascadeType.ALL)
    @JoinTable(name = "lista_libri_utenti",
            joinColumns = { @JoinColumn(name = "lista_libri_model_id") },
            inverseJoinColumns = { @JoinColumn(name = "utenti_id") })
    private List<ListaLibriModel> listaLibri;
}
