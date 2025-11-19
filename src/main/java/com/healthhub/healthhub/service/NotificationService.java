package com.healthhub.healthhub.service;

import com.healthhub.healthhub.model.Notification;
import com.healthhub.healthhub.model.Utilisateur;

import java.util.List;

public interface NotificationService {
    Notification getNotificationById(Long id);
    List<Notification> getAllNotifications();
    String AjouterNotification(Notification notification);
    String ModifierNotificationById(Long id,Notification notification);
    String SupprimerNotificationById(Long id);

    Notification envoyerNotification(Utilisateur utilisateur, String message);

    List<Notification> getNotificationsByUtilisateur(Utilisateur utilisateur);

    Notification marquerCommeLue(Long notificationId);
}