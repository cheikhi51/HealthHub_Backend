package com.healthhub.healthhub.service;

import com.healthhub.healthhub.dto.LoginDTO;
import com.healthhub.healthhub.dto.RegisterDTO;
import com.healthhub.healthhub.model.Utilisateur;

import java.util.List;

public interface UtilisateurService {
    String register(RegisterDTO registerDTO);
    String login(LoginDTO loginDTO);
    Utilisateur getUtilisateurById(Long id);
    List<Utilisateur> getAllUtilisateur();
    String AjouterUtilisateur(Utilisateur utilisateur);
    String ModifierUtilisateurById(Long id , Utilisateur utilisateur);
    String SupprimerUtilisateurById(Long id);

    Utilisateur findByEmail(String email);
}
