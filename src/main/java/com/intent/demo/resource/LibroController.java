package com.intent.demo.resource;

import com.intent.demo.model.LibroModel;
import com.intent.demo.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/libro")
@CrossOrigin(origins = "*")
public class LibroController {

    @Autowired
    LibroRepository libroRepository;

    @GetMapping(value = "/libro")
    public List<LibroModel> getAllLibri (){
        return libroRepository.findAll();
    }

    @GetMapping("/libro/{id}")
    public LibroModel getLibroById (@PathVariable int id){
        return libroRepository.findById(id).get();
    }

    @PostMapping(value = "/libro")
    public String addLibro (@RequestBody LibroModel libro) {
        try {
            libroRepository.save(libro);
            return libro.toString() + " Aggiunto correttamente.";
        } catch (Exception ex) {
            ex.printStackTrace();
            return " Errore: Impossibile aggiungere il libro. Controllare i parametri inseriti";
        }
    }

    @PutMapping(value = "/libro/{id}")
    public Optional<LibroModel> editLibro (@RequestBody LibroModel libro, @PathVariable int id){
        try {
            Optional<LibroModel> libro2 = libroRepository.findById(id);
            LibroModel libroToUpdate = libro2.get();
            libroToUpdate.setTitolo(libro.getTitolo());
            libroToUpdate.setAutore(libro.getAutore());
            libroToUpdate.setPrimaEdizione(libro.getPrimaEdizione());
            libroToUpdate.setTrama(libro.getTrama());

            libroRepository.save(libroToUpdate);
            return libroRepository.findById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return  libroRepository.findById(id);
        }
    }

    @DeleteMapping(value = "/libro/{id}")
    public String deleteLibro (@PathVariable int id) {
        try {
            libroRepository.deleteById(id);
            return " Libro eliminato correttamente.";
        } catch (Exception ex) {
            ex.printStackTrace();
            return " Errore nell'eliminazione del libro.";
        }
    }
}
