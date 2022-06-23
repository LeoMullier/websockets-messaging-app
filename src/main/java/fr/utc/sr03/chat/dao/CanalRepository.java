// ========== DECLARATION DES PACKAGES ========== //
package fr.utc.sr03.chat.dao;


// ========== IMPORTATION DES BIBLIOTHEQUES ========== //
import fr.utc.sr03.chat.model.Canal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


// ========== CLASSE USERREPOSITORY ========== //
@Repository
public interface CanalRepository extends JpaRepository<Canal, Long> {
    // ========== METHODES DE RECHERCHES DANS LA BDD ========== //
    List<Canal> findByUserproprio(long userproprio);
    List<Canal> findByIdAndUserproprio(long id, long userproprio);
}
