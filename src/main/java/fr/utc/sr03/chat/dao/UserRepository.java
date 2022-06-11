// ========== DECLARATION DES PACKAGES ========== //
package fr.utc.sr03.chat.dao;


// ========== IMPORTATION DES BIBLIOTHEQUES ========== //
import fr.utc.sr03.chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


// ========== CLASSE USERREPOSITORY ========== //
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // ========== METHODES DE RECHERCHES DANS LA BDD ========== //
    List<User> findByLoginAndMdpAndDesactive(String login, String mdp, int desactive);
    List<User> findByIdAndDesactive(Long id, int desactive);
    List<User> findByLogin(String login);
}
