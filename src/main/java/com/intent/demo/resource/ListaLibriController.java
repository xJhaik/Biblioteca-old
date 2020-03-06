package com.intent.demo.resource;

import com.intent.demo.model.LibroModel;
import com.intent.demo.model.ListaLibriModel;
import com.intent.demo.model.UtenteModel;
import com.intent.demo.repository.LibroRepository;
import com.intent.demo.repository.ListaLibriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/lista-libri")
@CrossOrigin(origins = "*")
public class ListaLibriController {

    @Autowired
    ListaLibriRepository listaLibriRepository;

    @Autowired
    LibroRepository libroRepository;

    @GetMapping(value = "/lista-libri")
    public List<ListaLibriModel> getAllListe (){
        return listaLibriRepository.findAll();
    }

    @GetMapping("/lista-libri/{id}")
    public ListaLibriModel getListaById (@PathVariable int id){
        return listaLibriRepository.findById(id).get();
    }

    @PostMapping(value = "/lista-libri")
    public String addLista (@RequestBody ListaLibriModel lista) {
        try {
            listaLibriRepository.save(lista);
            return lista.toString() + " Aggiunta correttamente.";
        } catch (Exception ex) {
            ex.printStackTrace();
            return " Errore: Impossibile aggiungere la lista. Controllare i parametri inseriti";
        }
    }

    @PutMapping(value = "/lista-libri/{id}")
    public Optional<ListaLibriModel> editLista (@RequestBody ListaLibriModel lista, @PathVariable int id){
        try {
            Optional<ListaLibriModel> lista2 = listaLibriRepository.findById(id);
            ListaLibriModel listaToUpdate = lista2.get();
            listaToUpdate.setTitolo(lista.getTitolo());

            listaLibriRepository.save(listaToUpdate);
            return listaLibriRepository.findById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return  listaLibriRepository.findById(id);
        }
    }

    @DeleteMapping(value = "/lista-libri/{id}")
    public String deleteLista (@PathVariable int id) {
        try {
            listaLibriRepository.deleteById(id);
            return " Lista eliminata correttamente.";
        } catch (Exception ex) {
            ex.printStackTrace();
            return " Errore nell'eliminazione della lista";
        }
    }

    @PutMapping(value = "/lista-libri/{idLista}/libro/{idLibro}")
    public Optional<ListaLibriModel> associaLibro (@PathVariable int idLista, @PathVariable int idLibro) {
        try {
            boolean found = false;
            Optional<ListaLibriModel> lista = listaLibriRepository.findById(idLista);
            ListaLibriModel listaDaModificare = lista.get();

            Optional<LibroModel> libro = libroRepository.findById(idLibro);
            LibroModel libroDaAggiungere = libro.get();
            for (LibroModel libroCiclo : listaDaModificare.getLibri()) {
                if (libroCiclo.getId() == libroDaAggiungere.getId()){
                    found = true;
                }
            }
            if (!found){
                listaDaModificare.getLibri().add(libroDaAggiungere);
                listaLibriRepository.save(listaDaModificare);
            }
            return listaLibriRepository.findById(idLista);
        } catch (Exception ex) {
            ex.printStackTrace();
            return listaLibriRepository.findById(idLista);
        }
    }

    @DeleteMapping(value = "/lista-libri/{idLista}/libro/{idLibro}")
    public Optional<ListaLibriModel> dissociaLibro (@PathVariable int idLista, @PathVariable int idLibro) {
        try {
            Optional<ListaLibriModel> lista = listaLibriRepository.findById(idLista);
            ListaLibriModel listaToUpdate = lista.get();
            for (LibroModel libro : listaToUpdate.getLibri()) {
                if (libro.getId() == idLibro) {
                    listaToUpdate.getLibri().remove(libro);
                    break;
                }
            }
            listaLibriRepository.save(listaToUpdate);
            return listaLibriRepository.findById(idLista);
        } catch (Exception ex) {
            ex.printStackTrace();
            return listaLibriRepository.findById(idLista);
        }
    }

}
