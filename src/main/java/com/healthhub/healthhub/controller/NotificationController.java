package com.healthhub.healthhub.controller;


import com.healthhub.healthhub.model.Notification;
import com.healthhub.healthhub.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173/")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @GetMapping("/notifications/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public Notification getNotificationById(@PathVariable("id") Long id){
        return notificationService.getNotificationById(id);
    }
    @GetMapping("/notifications")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public List<Notification> getAllNotification(){
        return notificationService.getAllNotifications();
    }

    @PostMapping("/notifications")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public  String createNotification(@RequestBody Notification notification){
        notificationService.AjouterNotification(notification);
        return "Notification créé avec succès";
    }
    @PutMapping("/notifications/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public String updateNotification(@PathVariable("id") Long id,@RequestBody Notification notification){
        notificationService.ModifierNotificationById(id,notification);
        return "Notification mis à jour avec succès";
    }
    @DeleteMapping("/notifications/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PATIENT') or hasRole('MEDCIN')")
    public String deleteNotification(@PathVariable("id") Long id){
        notificationService.SupprimerNotificationById(id);
        return "Notification supprimé avec succès";
    }
}
