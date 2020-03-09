package com.intent.demo.resource;

import com.intent.demo.model.ListaLibriModel;
import com.intent.demo.model.UtenteModel;
import com.intent.demo.repository.ListaLibriRepository;
import com.intent.demo.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController("/utente")
@CrossOrigin(origins = "*")
public class UtenteController {

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    ListaLibriRepository listaLibriRepository;

    @GetMapping(value = "/utente")
    public List<UtenteModel> getAllUtenti (){
        return utenteRepository.findAll();
    }

    @GetMapping("/utente/{id}")
    public UtenteModel getUtenteById (@PathVariable int id){
        return utenteRepository.findById(id).get();
    }

    @PostMapping(value = "/signup")
    public String addUtente (@RequestBody UtenteModel utenteDaLoggare) {
        List<UtenteModel> lista = utenteRepository.findAll();
        List<UtenteModel> esistente = lista.stream().filter(utenteModel -> (utenteDaLoggare.getUsername().equals(utenteModel.getUsername())) ||
                utenteDaLoggare.getEmail().equals(utenteModel.getEmail())).collect(Collectors.toList());
        if(esistente.isEmpty()) {
            try {
                utenteRepository.save(utenteDaLoggare);
                return utenteDaLoggare.toString() + " Iscrizione eseguita.";
            } catch (Exception ex) {
                ex.printStackTrace();
                return " Errore: Impossibile iscriversi. Controllare i parametri inseriti";
            }
        }
        return " Utente già esistente";
    }

    @PostMapping(value = "/login")


    @PutMapping(value = "/utente/{id}")
    public Optional<UtenteModel> editUtente (@RequestBody UtenteModel utente, @PathVariable int id){
        try {
            Optional<UtenteModel> utente2 = utenteRepository.findById(id);
            UtenteModel utenteToUpdate = utente2.get();
            utenteToUpdate.setNome(utente.getNome());
            utenteToUpdate.setCognome(utente.getCognome());
            utenteToUpdate.setEmail(utente.getEmail());
            utenteToUpdate.setUsername(utente.getUsername());
            utenteToUpdate.setPassword(utente.getPassword());
            utenteRepository.save(utenteToUpdate);
            return utenteRepository.findById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return  utenteRepository.findById(id);
        }
    }

    @DeleteMapping(value = "/utente/{id}")
    public String deleteUtente (@PathVariable int id) {
        try {
            utenteRepository.deleteById(id);
            return " Utente eliminato correttamente.";
        } catch (Exception ex) {
            ex.printStackTrace();
            return " Errore nell'eliminazione dell'utente.";
        }
    }

    //è impossibile associare la stessa lista più di una volta
    @PutMapping(value = "/utente/{idUtente}/lista-libri/{idLista}")
    public Optional<UtenteModel> associaLista (@PathVariable int idUtente, @PathVariable int idLista){
        try {
            boolean found = false;
            Optional<UtenteModel> utente = utenteRepository.findById(idUtente);
            UtenteModel utenteDaModificare = utente.get();

            Optional<ListaLibriModel> lista = listaLibriRepository.findById(idLista);
            ListaLibriModel listaDaAggiungere = lista.get();
            for (ListaLibriModel listaLibri : utenteDaModificare.getListaLibri()) {
                if (listaLibri.getId() == listaDaAggiungere.getId()){
                    found = true;
                }
            }
            if (!found){
                utenteDaModificare.getListaLibri().add(listaDaAggiungere);
                utenteRepository.save(utenteDaModificare);
            }
            return utenteRepository.findById(idUtente);
        } catch (Exception ex) {
            ex.printStackTrace();
            return utenteRepository.findById(idUtente);
        }
    }

    @DeleteMapping(value = "/utente/{idUtente}/lista-libri/{idLista}")
    public Optional<UtenteModel> dissociaLista (@PathVariable int idUtente, @PathVariable int idLista) {
        try {
            Optional<UtenteModel> utente = utenteRepository.findById(idUtente);
            UtenteModel utenteToUpdate = utente.get();
            for (ListaLibriModel listaLibri : utenteToUpdate.getListaLibri()) {
                if (listaLibri.getId() == idLista) {
                    utenteToUpdate.getListaLibri().remove(listaLibri);
                    break;
                }
            }
            utenteRepository.save(utenteToUpdate);
            return utenteRepository.findById(idUtente);
        } catch (Exception ex) {
            ex.printStackTrace();
            return utenteRepository.findById(idUtente);
        }
    }
}
