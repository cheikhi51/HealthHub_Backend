package com.healthhub.healthhub.service.impl;

import com.healthhub.healthhub.exception.ResourceNotFoundException;
import com.healthhub.healthhub.model.Historique;
import com.healthhub.healthhub.model.Utilisateur;
import com.healthhub.healthhub.repository.HistoriqueRepository;
import com.healthhub.healthhub.repository.UtilisateurRepository;
import com.healthhub.healthhub.service.HistoriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class HistoriqueServiceImpl implements HistoriqueService {

    @Autowired
    private HistoriqueRepository historiqueRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public Historique getHistoriqueById(Long id) {
        return historiqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Historique avec l'ID " + id + " non trouvé"
                ));
    }

    @Override
    public List<Historique> getAllHistorique() {
        return historiqueRepository.findAll();
    }

    @Override
    @Transactional
    public Historique AjouterHistorique(Historique historique) {
        if (historique.getDateAction() == null) {
            historique.setDateAction(Instant.now());
        }
        return historiqueRepository.save(historique);
    }

    @Override
    @Transactional
    public Historique ModifierHistoriqueById(Long id, Historique historique) {
        Historique existingHistorique = getHistoriqueById(id);

        if (historique.getActionType() != null) {
            existingHistorique.setActionType(historique.getActionType());
        }
        if (historique.getDetails() != null) {
            existingHistorique.setDetails(historique.getDetails());
        }

        return historiqueRepository.save(existingHistorique);
    }

    @Override
    @Transactional
    public void SupprimerHistoriqueById(Long id) {
        if (!historiqueRepository.existsById(id)) {
            throw new ResourceNotFoundException("Historique avec l'ID " + id + " non trouvé");
        }
        historiqueRepository.deleteById(id);
    }

    @Override
    public List<Historique> getHistoriqueUtilisateur(Long utilisateurId) {
        return historiqueRepository.findByUtilisateur_IdOrderByDateActionDesc(utilisateurId);
    }

    @Override
    public List<Historique> getHistoriqueParAction(Long utilisateurId, String actionType) {
        return historiqueRepository.findByUtilisateur_IdAndActionTypeOrderByDateActionDesc(
                utilisateurId,
                actionType
        );
    }

    @Override
    public List<Historique> getHistoriqueParPeriode(Long utilisateurId, Instant dateDebut, Instant dateFin) {
        return historiqueRepository.findByUtilisateur_IdAndDateActionBetweenOrderByDateActionDesc(
                utilisateurId,
                dateDebut,
                dateFin
        );
    }

    @Override
    public List<Historique> getDernieresActions(Long utilisateurId, int limite) {
        if (limite == 10) {
            return historiqueRepository.findTop10ByUtilisateur_IdOrderByDateActionDesc(utilisateurId);
        }
        // Pour d'autres limites, on récupère tout et on filtre
        return historiqueRepository.findByUtilisateur_IdOrderByDateActionDesc(utilisateurId)
                .stream()
                .limit(limite)
                .toList();
    }

    @Override
    public long compterActionsUtilisateur(Long utilisateurId) {
        return historiqueRepository.countByUtilisateur_Id(utilisateurId);
    }

    @Override
    @Transactional
    public void enregistrerAction(Utilisateur utilisateur, String actionType, String details) {
        Historique historique = new Historique();
        historique.setUtilisateur(utilisateur);
        historique.setActionType(actionType);
        historique.setDetails(details);
        historique.setDateAction(Instant.now());

        historiqueRepository.save(historique);
    }

    @Override
    @Transactional
    public void enregistrerAction(Long utilisateurId, String actionType, String details) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Utilisateur avec l'ID " + utilisateurId + " non trouvé"
                ));

        enregistrerAction(utilisateur, actionType, details);
    }
}