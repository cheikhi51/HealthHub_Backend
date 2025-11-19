package com.healthhub.healthhub.repository;

import com.healthhub.healthhub.model.Historique;
import com.healthhub.healthhub.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface HistoriqueRepository extends JpaRepository<Historique, Long> {

    // Trouver l'historique d'un utilisateur spécifique
    List<Historique> findByUtilisateurOrderByDateActionDesc(Utilisateur utilisateur);

    // Trouver par ID utilisateur
    List<Historique> findByUtilisateur_IdOrderByDateActionDesc(Long utilisateurId);

    // Trouver par type d'action
    List<Historique> findByUtilisateur_IdAndActionTypeOrderByDateActionDesc(Long utilisateurId, String actionType);

    // Trouver par période
    List<Historique> findByUtilisateur_IdAndDateActionBetweenOrderByDateActionDesc(
            Long utilisateurId,
            Instant dateDebut,
            Instant dateFin
    );

    // Compter les actions d'un utilisateur
    long countByUtilisateur_Id(Long utilisateurId);

    // Trouver les N dernières actions
    List<Historique> findTop10ByUtilisateur_IdOrderByDateActionDesc(Long utilisateurId);
}