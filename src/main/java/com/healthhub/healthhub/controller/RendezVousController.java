package com.healthhub.healthhub.controller;


import com.healthhub.healthhub.model.RendezVous;
import com.healthhub.healthhub.service.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RendezVousController {
    @Autowired
    private RendezVousService rendezVousService;

    public RendezVousController(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @GetMapping("/rendezvous/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public RendezVous getRendezVousById(@PathVariable("id") Long id){
        return rendezVousService.getRendesVousById(id);
    }

    @GetMapping("/rendezvous")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public List<RendezVous> getAllRendezVous(){
        return rendezVousService.getAllRendezVous();
    }

    @PostMapping("/rendezvous")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public  String createRendezVous(@RequestBody RendezVous rendezVous){
        rendezVousService.AjouterRendezVous(rendezVous);
        return "RendezVous créé avec succès";
    }
    @PutMapping("/rendezvous/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public String updateRendezVous(@PathVariable("id") Long id,@RequestBody RendezVous rendezVous){
        rendezVousService.ModifierRendezVousById(id,rendezVous);
        return "RendezVous mis à jour avec succès";
    }
    @DeleteMapping("/rendezvous/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public String deleteRendezVous(@PathVariable("id") Long id){
        rendezVousService.SupprimerRendezVousById(id);
        return "RendezVous supprimé avec succès";
    }
}
