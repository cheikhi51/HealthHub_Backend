package com.healthhub.healthhub.service;

import com.healthhub.healthhub.model.RendezVous;

import java.util.List;

public interface RendezVousService {
    RendezVous getRendesVousById(Long id);
    List<RendezVous> getAllRendezVous();
    String AjouterRendezVous(RendezVous rendezVous);
    String ModifierRendezVousById(Long id,RendezVous rendezVous);
    String SupprimerRendezVousById(Long id);
}
