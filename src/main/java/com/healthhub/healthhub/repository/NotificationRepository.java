package com.healthhub.healthhub.repository;

import com.healthhub.healthhub.model.Notification;
import com.healthhub.healthhub.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByUtilisateurIdOrderByDateEnvoiDesc(Long utilisateurId);

    List<Notification> findByUtilisateurIdAndLuFalse(Long utilisateurId);

    Long countByUtilisateurIdAndLuFalse(Long utilisateurId);
}
