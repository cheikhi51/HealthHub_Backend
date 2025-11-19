package com.healthhub.healthhub.service.impl;

import com.healthhub.healthhub.exception.ResourceNotFoundException;
import com.healthhub.healthhub.model.Notification;
import com.healthhub.healthhub.model.Utilisateur;
import com.healthhub.healthhub.repository.NotificationRepository;
import com.healthhub.healthhub.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification avec l'ID " + id + " non trouvée"));
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public String AjouterNotification(Notification notification) {
        if (notification.getDateEnvoi() == null) {
            notification.setDateEnvoi(LocalDateTime.now());
        }
        if (notification.getLu() == null) {
            notification.setLu(false);
        }
        notificationRepository.save(notification);
        return "Notification créée avec succès";
    }

    @Override
    public String ModifierNotificationById(Long id, Notification notification) {
        Notification existingNotif = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification avec l'ID " + id + " non trouvée"));

        if (notification.getMessage() != null) {
            existingNotif.setMessage(notification.getMessage());
        }
        if (notification.getDateEnvoi() != null) {
            existingNotif.setDateEnvoi(notification.getDateEnvoi());
        }
        if (notification.getDateLecture() != null) {
            existingNotif.setDateLecture(notification.getDateLecture());
        }
        if (notification.getLu() != null) {
            existingNotif.setLu(notification.getLu());
        }

        notificationRepository.save(existingNotif);
        return "Notification mise à jour avec succès";
    }

    @Override
    public String SupprimerNotificationById(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notification avec l'ID " + id + " non trouvée");
        }
        notificationRepository.deleteById(id);
        return "Notification supprimée avec succès";
    }

    @Override
    public Notification envoyerNotification(Utilisateur utilisateur, String message) {
        Notification notification = new Notification();
        notification.setUtilisateurId(utilisateur.getId());
        notification.setMessage(message);
        notification.setDateEnvoi(LocalDateTime.now());
        notification.setLu(false);

        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationsByUtilisateur(Utilisateur utilisateur) {
        return notificationRepository.findByUtilisateurIdOrderByDateEnvoiDesc(utilisateur.getId());
    }


    @Override
    public Notification marquerCommeLue(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification avec l'ID " + notificationId + " non trouvée"));

        notification.setLu(true);
        notification.setDateLecture(LocalDateTime.now());

        return notificationRepository.save(notification);
    }
}