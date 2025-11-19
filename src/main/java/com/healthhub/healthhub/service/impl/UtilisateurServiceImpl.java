package com.healthhub.healthhub.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthhub.healthhub.dto.JwtResponse;
import com.healthhub.healthhub.dto.LoginDTO;
import com.healthhub.healthhub.dto.RegisterDTO;
import com.healthhub.healthhub.model.Administrateur;
import com.healthhub.healthhub.model.Medecin;
import com.healthhub.healthhub.model.Patient;
import com.healthhub.healthhub.model.Utilisateur;
import com.healthhub.healthhub.repository.AdministrateurRepository;
import com.healthhub.healthhub.repository.MedecinRepository;
import com.healthhub.healthhub.repository.PatientRepository;
import com.healthhub.healthhub.repository.UtilisateurRepository;
import com.healthhub.healthhub.util.JwtUtil;
import com.healthhub.healthhub.service.UtilisateurService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    @Autowired
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private AdministrateurRepository administrateurRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;


    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil, ObjectMapper objectMapper) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id).get();

    }

    @Override
    public List<Utilisateur> getAllUtilisateur() {
        return utilisateurRepository.findAll();
    }

    @Override
    public String AjouterUtilisateur(Utilisateur utilisateur) {
        String hashedPassword = passwordEncoder.encode(utilisateur.getMotDePasse());
        utilisateur.setMotDePasse(hashedPassword);
        utilisateurRepository.save(utilisateur);
        return "User created sucessufully";
    }

    @Override
    public String ModifierUtilisateurById(Long id, Utilisateur utilisateur) {
        Utilisateur existingUser = utilisateurRepository.findById(id).orElse(null);
        if(existingUser != null && existingUser.getId() == id){
            existingUser.setNom(utilisateur.getNom());
            existingUser.setPrenom(utilisateur.getPrenom());
            existingUser.setEmail(utilisateur.getEmail());
            existingUser.setRole(utilisateur.getRole());
            existingUser.setMotDePasse(utilisateur.getMotDePasse());
            utilisateurRepository.save(existingUser);
            return "Utilisateur mis à jour avec succèss";
        }
        return "Utilisateur non trouvé";
    }

    @Override
    public String SupprimerUtilisateurById(Long id) {
        utilisateurRepository.deleteById(id);
        return "Utilisateur supprimé avec succèss";
    }

    @Override
    @Transactional
    public String register(RegisterDTO registerDto) {
        try {
            // Vérifier si l'utilisateur existe déjà
            if (utilisateurRepository.findByEmail(registerDto.getEmail()).isPresent()) {
                return "{\"error\": \"Un utilisateur avec cet email existe déjà\"}";
            }

            // Encoder le mot de passe
            String encodedPassword = passwordEncoder.encode(registerDto.getMotDePasse());

            Utilisateur utilisateur;

            // Créer l'entité appropriée selon le rôle
            switch (registerDto.getRole()) {
                case PATIENT:
                    Patient patient = new Patient();
                    patient.setNom(registerDto.getNom());
                    patient.setPrenom(registerDto.getPrenom());
                    patient.setEmail(registerDto.getEmail());
                    patient.setMotDePasse(encodedPassword);
                    patient.setDateNaissance(registerDto.getDateNaissance());
                    patient.setAdresse(registerDto.getAdresse());
                    patient.setTelephone(registerDto.getTelephone());
                    utilisateur = patientRepository.save(patient);
                    break;

                case MEDCIN:
                    Medecin medecin = new Medecin();
                    medecin.setNom(registerDto.getNom());
                    medecin.setPrenom(registerDto.getPrenom());
                    medecin.setEmail(registerDto.getEmail());
                    medecin.setMotDePasse(encodedPassword);
                    medecin.setSpecialite(registerDto.getSpecialite());
                    medecin.setDisponibilite(registerDto.getDisponibilite() != null ? registerDto.getDisponibilite() : true);
                    medecin.setTelephone(registerDto.getTelephone());
                    utilisateur = medecinRepository.save(medecin);
                    break;

                case ADMIN:
                    Administrateur admin = new Administrateur();
                    admin.setNom(registerDto.getNom());
                    admin.setPrenom(registerDto.getPrenom());
                    admin.setEmail(registerDto.getEmail());
                    admin.setMotDePasse(encodedPassword);
                    utilisateur = administrateurRepository.save(admin);
                    break;

                default:
                    return "{\"error\": \"Rôle non valide: " + registerDto.getRole() + "\"}";
            }

            // Générer le token JWT
            String token = jwtUtil.generateToken(utilisateur.getEmail(), utilisateur.getRole().name());

            JwtResponse response = new JwtResponse(
                    token,
                    utilisateur.getRole().name(),
                    utilisateur.getEmail(),
                    "Utilisateur créé avec succès"
            );

            return objectMapper.writeValueAsString(response);

        } catch (Exception e) {
            return "{\"error\": \"Erreur lors de la création: " + e.getMessage() + "\"}";
        }
    }

    @Override
    public String login(LoginDTO loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getMotDePasse())
            );

            Utilisateur utilisateur = findByEmail(loginDto.getEmail());
            String token = jwtUtil.generateToken(utilisateur.getEmail(), utilisateur.getRole().name());

            JwtResponse response = new JwtResponse(
                    token,
                    utilisateur.getRole().name(),
                    utilisateur.getEmail(),
                    "Connexion réussie"
            );

            return objectMapper.writeValueAsString(response);

        } catch (AuthenticationException e) {
            return "{\"error\": \"Identifiants invalides\"}";
        } catch (Exception e) {
            return "{\"error\": \"Erreur lors de la connexion: " + e.getMessage() + "\"}";
        }
    }

    @Override
    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}
