package com.healthhub.healthhub.controller;


import com.healthhub.healthhub.model.RendezVous;
import com.healthhub.healthhub.service.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173/")
public class RendezVousController {
    @Autowired
    private RendezVousService rendezVousService;

    public RendezVousController(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @GetMapping("/rendezvous/{id}")
    public RendezVous getRendezVousById(@PathVariable("id") Long id){
        return rendezVousService.getRendesVousById(id);
    }

    @GetMapping("/rendezvous")
    public List<RendezVous> getAllRendezVous(){
        return rendezVousService.getAllRendezVous();
    }

    @PostMapping("/rendezvous")
    public  String createRendezVous(@RequestBody RendezVous rendezVous){
        rendezVousService.AjouterRendezVous(rendezVous);
        return "RendezVous créé avec succès";
    }
    @PutMapping("/rendezvous/{id}")
    public String updateRendezVous(@PathVariable("id") Long id,@RequestBody RendezVous rendezVous){
        rendezVousService.ModifierRendezVousById(id,rendezVous);
        return "RendezVous mis à jour avec succès";
    }
    @DeleteMapping("/rendezvous/{id}")
    public String deleteRendezVous(@PathVariable("id") Long id){
        rendezVousService.SupprimerRendezVousById(id);
        return "RendezVous supprimé avec succès";
    }
}
