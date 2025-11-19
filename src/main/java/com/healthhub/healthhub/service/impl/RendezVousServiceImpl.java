package com.healthhub.healthhub.service.impl;

import com.healthhub.healthhub.model.RendezVous;
import com.healthhub.healthhub.repository.RendezVousRepository;
import com.healthhub.healthhub.service.RendezVousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RendezVousServiceImpl implements RendezVousService {
    @Autowired
    private final RendezVousRepository rendezVousRepository;

    public RendezVousServiceImpl(RendezVousRepository rendezVousRepository) {
        this.rendezVousRepository = rendezVousRepository;
    }

    @Override
    public RendezVous getRendesVousById(Long id) {
        return rendezVousRepository.findById(id).get();
    }

    @Override
    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }

    @Override
    public String AjouterRendezVous(RendezVous rendezVous) {
        rendezVousRepository.save(rendezVous);
        return "Rendez-vous created successfully";
    }

    @Override
    public String ModifierRendezVousById(Long id, RendezVous rendezVous) {
        RendezVous existingRV = rendezVousRepository.findById(id).orElse(null);
        if(existingRV != null && existingRV.getId() == id){
            existingRV.setDateDebut(rendezVous.getDateDebut());
            existingRV.setDateFin(rendezVous.getDateFin());
            existingRV.setMotif(rendezVous.getMotif());
            existingRV.setStatut(rendezVous.getStatut());
            rendezVousRepository.save(existingRV);
        }
        return "Rendez-vous updated successfully";
    }

    @Override
    public String SupprimerRendezVousById(Long id) {
        rendezVousRepository.deleteById(id);
        return "Rendez-vous deleted successfully";
    }
}
