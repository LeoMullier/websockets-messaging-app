// ========== DECLARATION DES PACKAGES ========== //
package fr.utc.sr03.chat.dao;


// ========== IMPORTATION DES BIBLIOTHEQUES ========== //
import fr.utc.sr03.chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


// ========== CLASSE MESSAGEREPOSITORY ========== //
public interface MessageRepository extends JpaRepository<User, Long> {
    // ========== METHODES DE RECHERCHES DANS LA BDD ========== //
    List<User> findByLoginAndMdp(String login, String mdp);
}
