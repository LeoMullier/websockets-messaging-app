// ========== DECLARATION DES PACKAGES ========== //
package fr.utc.sr03.chat.controller;


// ========== IMPORTATION DES RESSOURCES ========== //
import fr.utc.sr03.chat.dao.CanalRepository;
import fr.utc.sr03.chat.dao.InvitesRepository;
import fr.utc.sr03.chat.dao.UserRepository;
import fr.utc.sr03.chat.model.Canal;
import fr.utc.sr03.chat.model.Invites;
import fr.utc.sr03.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.Random;


// ========== CONTROLLEUR PRINCIPAL ========== //
@Controller
@RequestMapping("/admin")
public class mainController {
    // Ajout des repository
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CanalRepository canalRepository;
    @Autowired
    private InvitesRepository invitesRepository;

    // Initialisation des variables globales
    private User utilisateur;
    private int authValidee = 0;
    private int authRefusee = 0;
    private int page = 0;


    // ========== MAPPER LES REQUETES PAR GET SUR /admin ========== //
    @GetMapping
    public RedirectView adminGET(Model model) {
        // Initialisations
        System.out.println(" ");
        System.out.println("(!) adminROOT - NULL - Nouvelle requête GET sur la page /admin.");

        // Retour de la méthode
        System.out.println("(!) adminROOT - NULL - Redirection vers /admin/bienvenue");
        return new RedirectView("admin/bienvenue");
    }


    // ========== MAPPER LES REQUETES PAR GET SUR /admin/bienvenue ========== //
    @GetMapping("/bienvenue")
    public String bienvenueGET(Model model) {
        // Initialisations
        System.out.println(" ");
        System.out.println("(!) adminBIENVENUE - NULL - Nouvelle requête GET sur la page /admin/bienvenue.");

        // Mise en place des données du modèle
        model.addAttribute("user", new User());
        model.addAttribute("authRefusee", authRefusee);

        // Retour de la méthode
        System.out.println("(!) adminBIENVENUE - NULL - Affichage de la page admin_login.");
        return "admin_login";
    }


    // ========== MAPPER LES REQUETES PAR GET SUR /admin/bienvenue ========== //
    @GetMapping("/deconnexion")
    public String deconnexionGET(Model model) {
        // Initialisations
        System.out.println(" ");
        System.out.println("(!) adminDECONNEXION - " + utilisateur.getLogin() + " - Nouvelle requête GET sur la page /admin/deconnexion.");

        // Suppression de la sauvegarde de l'utilisateur actuel dans le contrôleur
        String prenom = utilisateur.getPrenom();
        utilisateur = null;
        authValidee = 0;
        authRefusee = 0;

        // Mise en place des données du modèle
        model.addAttribute("prenom", prenom);

        // Retour de la méthode
        System.out.println("(!) adminDECONNEXION - NULL - Affichage de la page admin_deconnexion");
        return "admin_deconnexion";
    }


    // ========== MAPPER LES REQUETES PAR POST SUR /admin/accueil ========== //
    @PostMapping("/accueil")
    public RedirectView accueilPOST(@ModelAttribute User userTemp, Model model, ModelAndView mv) {
        // Initialisations
        System.out.println(" ");
        System.out.println("(!) adminACCUEIL - NULL - Nouvelle requête POST sur la page /admin/accueil.");

        // Préparation des informations d'authentification
        String tmp_login = userTemp.getLogin();
        String tmp_mdp = userTemp.getMdp();
        System.out.println("(!) adminACCUEIL - NULL - Login demandé: " + tmp_login);
        System.out.println("(!) adminACCUEIL - NULL - Mot de passe demandé:" + tmp_mdp);

        // Vérification du login et mot de passe fournis
        List<User> utilisateursTrouves = userRepository.findByLoginAndMdpAndDesactive(tmp_login, tmp_mdp, 0);
        if (utilisateursTrouves.isEmpty() == true || utilisateursTrouves.size() > 1 || utilisateursTrouves.get(0).getAdmin() == 0) {

            // Dans le cas d'un problème d'authentification de l'utilisateur
            System.out.println("(!) adminACCUEIL - NULL - Authentification refusée pour l'utilisateur " + tmp_login +".");
            authValidee = 0;
            authRefusee = authRefusee + 1;

            // Retour de la méthode
            System.out.println("(!) adminACCUEIL - NULL - Redirection vers /admin/bienvenue");
            return new RedirectView("bienvenue");
        } else {

            // Dans le cas d'une authentification valide de l'utilisateur
            System.out.println("(!) adminACCUEIL - NULL - Authentification réussie pour l'utilisateur " + tmp_login +".");
            authValidee = 1;
            authRefusee = 0;
            utilisateur = utilisateursTrouves.get(0);

            // Retour de la méthode
            System.out.println("(!) adminACCUEIL - " + utilisateur.getLogin() + " - Redirection vers /admin/accueil");
            return new RedirectView("accueil");
        }
    }


    // ========== MAPPER LES REQUETES PAR POST SUR /admin/accueil ========== //
    @GetMapping("/accueil")
    public RedirectView accueilGET(Model model) {
        // Initialisations
        System.out.println(" ");
        System.out.println("(!) adminACCUEIL - " + utilisateur.getLogin() + " - Nouvelle requête GET sur la page /admin/accueil.");

        // Sélection de la bonne page à afficher (ListeUtilisateurs ou ListeConversations)
        if (authValidee == 1 && authRefusee == 0) {
            switch (page) {
                case 0:
                    // Retour de la méthode (par défaut)
                    System.out.println("(!) adminACCUEIL - " + utilisateur.getLogin() + " - Redirection vers /admin/accueil/liste-utilisateurs");
                    return new RedirectView("accueil/liste-utilisateurs");
                case 1:
                    // Retour de la méthode
                    System.out.println("(!) adminACCUEIL - " + utilisateur.getLogin() + " - Redirection vers /admin/accueil/liste-conversations");
                    return new RedirectView("accueil/liste-conversations");
                default:
                    // Retour de la méthode (incohérent)
                    System.out.println("(!) adminACCUEIL - " + utilisateur.getLogin() + " - Un problème incohérent est survenu.");
                    System.out.println("(!) adminACCUEIL - " + utilisateur.getLogin() + " - Redirection vers /admin/bienvenue");
                    return new RedirectView("bienvenue");
            }
        } else {
            // Retour de la méthode (incohérent)
            System.out.println("(!) adminACCUEIL - " + utilisateur.getLogin() + " - Un problème incohérent est survenu.");
            System.out.println("(!) adminACCUEIL - " + utilisateur.getLogin() + " - Redirection vers /admin/bienvenue");
            return new RedirectView("bienvenue");
        }
    }


    // ========== MAPPER LES REQUETES PAR GET SUR /admin/accueil/liste-utilisateurs/actualisation ========== //
    @GetMapping("/accueil/liste-utilisateurs/actualisation")
    public RedirectView accueilListeUtilisateursActualisationGET(Model model) {
        // Initialisations
        System.out.println(" ");
        System.out.println("(!) adminLISTE-UTILISATEURS-ACTUALISATION - " + utilisateur.getLogin() + " - Nouvelle requête GET sur la page /admin/accueil/liste-utilisateurs/actualisation.");

        // Changement de la page souhaitée
        page = 0;

        // Retour de la méthode
        System.out.println("(!) adminLISTE-UTILISATEURS-ACTUALISATION - " + utilisateur.getLogin() + " - Redirection vers /admin/accueil");
        return new RedirectView("../../accueil");
    }


    // ========== MAPPER LES REQUETES PAR GET SUR /admin/accueil/liste-conversation/actualisation ========== //
    @GetMapping("/accueil/liste-conversations/actualisation")
    public RedirectView accueilListeConversationActualisationGET(Model model) {
        // Initialisations
        System.out.println(" ");
        System.out.println("(!) adminLISTE-CONVERSATION-ACTUALISATION - Nouvelle requête GET sur la page /admin/accueil/liste-conversations/actualisation.");

        // Changement de la page souhaitée
        page = 1;

        // Retour de la méthode
        System.out.println("(!) adminLISTE-CONVERSATION-ACTUALISATION - " + utilisateur.getLogin() + " - Redirection vers /admin/accueil");
        return new RedirectView("../../accueil");
    }

    // ========== MAPPER LES REQUETES PAR POST SUR /admin/accueil/liste-utilisateurs ========== //
    @GetMapping("/accueil/liste-utilisateurs")
    public String accueilListeUtilisateursGET(Model model) {
        // Initialisations
        System.out.println(" ");
        System.out.println("(!) adminLISTE-UTILISATEURS - " + utilisateur.getLogin() + " - Nouvelle requête GET sur la page /admin/accueil/liste-utilisateurs.");

        // Calcul du nombre de canaux pour lesquels l'utilisateur est inscrit
        List<Invites> canauxInvitesTrouves = invitesRepository.findByIduser(utilisateur.getId());
        List<Canal> canauxProprioTrouves = canalRepository.findByUserproprio(utilisateur.getId());
        int nbCanauxInscrits = canauxProprioTrouves.size() + canauxInvitesTrouves.size();

        // Récupération de tous les utilisateurs existant
        List<User> tousLesUtilisateurs = userRepository.findAll();

        // Mise en place des données du modèle
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("nbCanauxInscrits", nbCanauxInscrits);
        model.addAttribute("tousLesUtilisateurs", tousLesUtilisateurs);

        // Retour de la méthode
        System.out.println("(!) adminLISTE-UTILISATEURS - " + utilisateur.getLogin() + " - Affichage de la page admin_liste-utilisateurs");
        return "admin_liste-utilisateurs";
    }


    // ========== MAPPER LES REQUETES PAR GET SUR /admin/accueil/liste-utilisateurs/ajouter ========== //
    @GetMapping(value="/accueil/liste-utilisateurs/ajouter")
    public String accueilListeUtilisateursAjouterGET(Model model) {
        // Initialisations
        System.out.println(" ");
        System.out.println("(!) adminLISTE-UTILISATEUR-AJOUTER - " + utilisateur.getLogin() + " - Nouvelle requête GET sur la page /admin/accueil/liste-utilisateur/ajouter.");

        // Calcul du nombre de canaux pour lesquels l'utilisateur est inscrit
        List<Invites> canauxInvitesTrouves = invitesRepository.findByIduser(utilisateur.getId());
        List<Canal> canauxProprioTrouves = canalRepository.findByUserproprio(utilisateur.getId());
        int nbCanauxInscrits = canauxProprioTrouves.size() + canauxInvitesTrouves.size();

        // Préparation des données du modèles
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("nbCanauxInscrits", nbCanauxInscrits);

        // Retour de la méthode
        System.out.println("(!) adminLISTE-UTILISATEUR-AJOUTER - " + utilisateur.getLogin() + " - Affichage de la page admin_ajouter-utilisateur");
        return "admin_ajouter-utilisateur";
    }


    // ========== MAPPER LES REQUETES PAR POST SUR /admin/accueil/liste-utilisateurs/ajouter ========== //
    @PostMapping("/accueil/liste-utilisateurs/ajouter")
    public RedirectView accueilListeUtilisateurAjouterPOST(@ModelAttribute User userTemp, Model model, ModelAndView mv) {
        // Initialisations
        System.out.println(" ");
        System.out.println("(!) adminLISTE-UTILISATEUR-AJOUTER - " + utilisateur.getLogin() + " - Nouvelle requête POST sur la page /admin/accueil/liste-utilisateurs/ajouter.");

        // Préparation des informations du nouvel utilisateur
        String tmp_login = userTemp.getLogin();
        String tmp_nom = userTemp.getNom();
        String tmp_prenom = userTemp.getPrenom();
        int tmp_admin = userTemp.getAdmin();

        // Génération d'un mot de passe aléatoire
        byte[] array = new byte[6];
        new Random().nextBytes(array);
        String tmp_mdp = new String(array, Charset.forName("US-ASCII"));

        System.out.println("(!) adminLISTE-UTILISATEUR-AJOUTER - " + utilisateur.getLogin() + " - Login demandé: " + tmp_login);
        System.out.println("(!) adminLISTE-UTILISATEUR-AJOUTER - " + utilisateur.getLogin() + " - Mot de passe demandé:" + tmp_mdp);
        System.out.println("(!) adminLISTE-UTILISATEUR-AJOUTER - " + utilisateur.getLogin() + " - Nom demandé:" + tmp_nom);
        System.out.println("(!) adminLISTE-UTILISATEUR-AJOUTER - " + utilisateur.getLogin() + " - Prénom demandé:" + tmp_prenom);
        System.out.println("(!) adminLISTE-UTILISATEUR-AJOUTER - " + utilisateur.getLogin() + " - Admin demandé:" + tmp_admin);


        // Vérification du login et mot de passe fournis
        List<User> utilisateursTrouves = userRepository.findByLogin(tmp_login);
        if (utilisateursTrouves.isEmpty() == false) {

            // Dans le cas d'un conflit avec la base des utilisateurs existant
            System.out.println("(!) adminLISTE-UTILISATEUR-AJOUTER - " + utilisateur.getLogin() + " - Login refusé car déjà existant parmis les utilisateurs enregistrés.");

            // Retour de la méthode
            System.out.println("(!) adminLISTE-UTILISATEUR-AJOUTER - " + utilisateur.getLogin() + " - Redirection vers /admin/accueil/liste-utilisateurs/ajouter");
            return new RedirectView("accueil/liste-utilisateurs/ajouter");
        } else {

            // Dans le cas de la validation du nouvel utilisateur
            System.out.println("(!) adminLISTE-UTILISATEUR-AJOUTER - " + utilisateur.getLogin() + " - Aucun problème détecté sur les informations transmises.");
            User utilisateur_a_ajouter = new User();
            utilisateur_a_ajouter.setLogin(tmp_login);
            utilisateur_a_ajouter.setMdp(tmp_mdp);
            utilisateur_a_ajouter.setNom(tmp_nom);
            utilisateur_a_ajouter.setPrenom(tmp_prenom);
            utilisateur_a_ajouter.setAdmin(tmp_admin);
            utilisateur_a_ajouter.setEnligne(0);
            utilisateur_a_ajouter.setDesactive(0);
            userRepository.saveAndFlush(utilisateur_a_ajouter);
            System.out.println("(!) adminLISTE-UTILISATEUR-AJOUTER - " + utilisateur.getLogin() + " - Ajout effectif du nouvel utilisateur " + tmp_login + ".");

            // Retour de la méthode
            System.out.println("(!) adminLISTE-UTILISATEUR-AJOUTER - " + utilisateur.getLogin() + " - Redirection vers /admin/accueil/liste-utilisateurs");
            return new RedirectView("../liste-utilisateurs");
        }
    }


    // ========== MAPPER LES REQUETES PAR GET SUR /admin/accueil/liste-utilisateurs/activer ========== //
    @GetMapping(value="/accueil/liste-utilisateurs/activer/{id}")
    public RedirectView accueilListeUtilisateursActiverGET(@PathVariable int id, Model model) {
        // Initialisations
        System.out.println(" ");
        System.out.println("(!) adminLISTE-UTILISATEUR-ACTIVER - " + utilisateur.getLogin() + " - Nouvelle requête GET sur la page /admin/accueil/liste-utilisateur/activer.");

        // Désactivation de l'utilisateur souhaité
        Optional<User> utilisateurs_a_activer = userRepository.findById(Long.valueOf(id));
        User utilisateur_a_activer = utilisateurs_a_activer.get();
        utilisateur_a_activer.setDesactive(0);

        // Mise à jour de la base de données
        userRepository.saveAndFlush(utilisateur_a_activer);
        System.out.println("(!) adminLISTE-UTILISATEUR-ACTIVER - " + utilisateur.getLogin() + " - Activation effective pour l'utilisateur " + utilisateur_a_activer.getLogin() + ".");

        // Retour de la méthode
        System.out.println("(!) adminLISTE-UTILISATEUR-ACTIVER - " + utilisateur.getLogin() + " - Redirection vers /admin/accueil/liste-utilisateurs");
        return new RedirectView("../../liste-utilisateurs");
    }


    // ========== MAPPER LES REQUETES PAR GET SUR /admin/accueil/liste-utilisateurs/desactiver ========== //
    @GetMapping(value="/accueil/liste-utilisateurs/desactiver/{id}")
    public RedirectView accueilListeUtilisateursDesactiverGET(@PathVariable int id, Model model) {
        // Initialisations
        System.out.println(" ");
        System.out.println("(!) adminLISTE-UTILISATEUR-DESACTIVER - " + utilisateur.getLogin() + " - Nouvelle requête GET sur la page /admin/accueil/liste-utilisateur/desactiver.");

        // Désactivation de l'utilisateur souhaité
        Optional<User> utilisateurs_a_desactiver = userRepository.findById(Long.valueOf(id));
        User utilisateur_a_desactiver = utilisateurs_a_desactiver.get();
        utilisateur_a_desactiver.setDesactive(1);

        // Mise à jour de la base de données
        userRepository.saveAndFlush(utilisateur_a_desactiver);
        System.out.println("(!) adminLISTE-UTILISATEUR-DESACTIVER - " + utilisateur.getLogin() + " - Désactivation effective pour l'utilisateur " + utilisateur_a_desactiver.getLogin() + ".");

        // Retour de la méthode
        System.out.println("(!) adminLISTE-UTILISATEUR-DESACTIVER - " + utilisateur.getLogin() + " - Redirection vers /admin/accueil/liste-utilisateurs");
        return new RedirectView("../../liste-utilisateurs");
    }


    // ========== MAPPER LES REQUETES PAR GET SUR /admin/accueil/liste-utilisateurs/supprimer ========== //
    @GetMapping(value="/accueil/liste-utilisateurs/supprimer/{id}")
    public RedirectView accueilListeUtilisateursSupprimerGET(@PathVariable int id, Model model) {
        // Initialisations
        System.out.println(" ");
        System.out.println("(!) adminLISTE-UTILISATEUR-SUPPRIMER - " + utilisateur.getLogin() + " - Nouvelle requête GET sur la page /admin/accueil/liste-utilisateur/supprimer.");

        // Désactivation de l'utilisateur souhaité
        Optional<User> utilisateurs_a_supprimer = userRepository.findById(Long.valueOf(id));
        User utilisateur_a_supprimer = utilisateurs_a_supprimer.get();
        utilisateur_a_supprimer.setDesactive(1);

        // Mise à jour de la base de données
        userRepository.delete(utilisateur_a_supprimer);
        System.out.println("(!) adminLISTE-UTILISATEUR-SUPPRIMER - " + utilisateur.getLogin() + " - Suppression effective pour l'utilisateur " + utilisateur_a_supprimer.getLogin() + ".");

        // Retour de la méthode
        System.out.println("(!) adminLISTE-UTILISATEUR-SUPPRIMER - " + utilisateur.getLogin() + " - Redirection vers /admin/accueil/liste-utilisateurs");
        return new RedirectView("../../liste-utilisateurs");
    }


    // ========== MAPPER LES REQUETES PAR POST SUR /admin/accueil/liste-conversations ========== //
    @GetMapping("/accueil/liste-conversations")
    public String accueilListeConversationsGET(Model model) {
        // Initialisations
        System.out.println(" ");
        System.out.println("(!) adminLISTE-CONVERSATIONS - " + utilisateur.getLogin() + " - Nouvelle requête GET sur la page /admin/accueil/liste-conversation.");

        // Calcul du nombre de canaux pour lesquels l'utilisateur est inscrit
        List<Invites> canauxInvitesTrouves = invitesRepository.findByIduser(utilisateur.getId());
        List<Canal> canauxProprioTrouves = canalRepository.findByUserproprio(utilisateur.getId());
        int nbCanauxInscrits = canauxProprioTrouves.size() + canauxInvitesTrouves.size();

        // Récupération de tous les utilisateurs existant
        List<Canal> tousLesCanaux = canalRepository.findAll();

        // Mise en place des données du modèle
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("nbCanauxInscrits", nbCanauxInscrits);
        model.addAttribute("tousLesCanaux", tousLesCanaux);

        // Retour de la méthode
        System.out.println("(!) adminLISTE-CONVERSATIONS - " + utilisateur.getLogin() + " - Affichage de la page admin_liste-conversations");
        return "admin_liste-conversations";
    }

    // ========== MAPPER LES REQUETES PAR GET SUR /admin/accueil/liste-conversations/supprimer ========== //
    @GetMapping(value="/accueil/liste-conversations/supprimer/{id}")
    public RedirectView accueilListeConversationsSupprimerGET(@PathVariable int id, Model model) {
        // Initialisations
        System.out.println(" ");
        System.out.println("(!) adminLISTE-CONVERSATIONS-SUPPRIMER - " + utilisateur.getLogin() + " - Nouvelle requête GET sur la page /admin/accueil/liste-conversations/supprimer.");

        // Suppression du canal souhaité
        Optional<Canal> canaux_a_supprimer = canalRepository.findById(Long.valueOf(id));
        Canal canal_a_supprimer = canaux_a_supprimer.get();

        // Mise à jour de la base de données
        canalRepository.delete(canal_a_supprimer);
        System.out.println("(!) adminLISTE-CONVERSATIONS-SUPPRIMER - " + utilisateur.getLogin() + " - Suppression effective pour l'utilisateur " + canal_a_supprimer.getTitre() + ".");

        // Retour de la méthode
        System.out.println("(!) adminLISTE-CONVERSATIONS-SUPPRIMER - " + utilisateur.getLogin() + " - Redirection vers /admin/accueil/liste-conversations");
        return new RedirectView("../../liste-conversations");
    }
}

