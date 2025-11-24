package com.healthhub.healthhub.controller;


import com.healthhub.healthhub.model.Notification;
import com.healthhub.healthhub.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Notification getNotificationById(@PathVariable("id") Long id){
        return notificationService.getNotificationById(id);
    }
    @GetMapping("/notifications")
    public List<Notification> getAllNotification(){
        return notificationService.getAllNotifications();
    }

    @PostMapping("/notifications")
    public  String createNotification(@RequestBody Notification notification){
        notificationService.AjouterNotification(notification);
        return "Notification créé avec succès";
    }
    @PutMapping("/notifications/{id}")
    public String updateNotification(@PathVariable("id") Long id,@RequestBody Notification notification){
        notificationService.ModifierNotificationById(id,notification);
        return "Notification mis à jour avec succès";
    }
    @DeleteMapping("/notifications/{id}")
    public String deleteNotification(@PathVariable("id") Long id){
        notificationService.SupprimerNotificationById(id);
        return "Notification supprimé avec succès";
    }
}
