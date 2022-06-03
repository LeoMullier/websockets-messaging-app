// ========== DECLARATION DES PACKAGES ========== //
package fr.utc.sr03.chat.dao;


// ========== IMPORTATION DES BIBLIOTHEQUES ========== //
import fr.utc.sr03.chat.model.Invites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


// ========== CLASSE INVITESREPOSITORY ========== //
@Repository
public interface InvitesRepository extends JpaRepository<Invites, Long> {
    // ========== METHODES DE RECHERCHES DANS LA BDD ========== //
    List<Invites> findByIduser(long iduser);
}
