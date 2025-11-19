package com.healthhub.healthhub.repository;

import com.healthhub.healthhub.model.Role;
import com.healthhub.healthhub.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur , Long> {
    Optional<Utilisateur> findByEmail(String email);
    List<Utilisateur> findByRole(Role role);
    boolean existsByEmail(String email);
}
