package fr.utc.sr03.chat.controller;

import fr.utc.sr03.chat.dao.UserRepository;
import fr.utc.sr03.chat.dao.InvitesRepository;
import fr.utc.sr03.chat.dao.CanalRepository;
import fr.utc.sr03.chat.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.stream.Collectors;
//import java.util.Math;
import javax.servlet.http.HttpServletRequest;

//@RequestMapping(value = "/NewCustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class restController
{
    // Ajout des repository
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InvitesRepository invitesRepository;
    @Autowired
    private CanalRepository canalRepository;


    // Gestion des utilisateurs authentifiés
    public Hashtable<String, String> utilisateursAuthentifies = new Hashtable<String, String>();
    //               ip      token


    // BandeauLatLogin
    @PostMapping(value = "/test2", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ValidationAuthentification afficherPOST2(@RequestBody UserTmp utilisateurTmp, HttpServletRequest request)
    {
        String adresseIpClient = request.getRemoteAddr();
        System.out.println("(blaip) "+adresseIpClient);

        System.out.println("(!) userLOGIN - NULL - Requête POST entrante pour authentification de l'utilisateur.");
        String loginTmp = utilisateurTmp.getLogin();
        String mdpTmp = utilisateurTmp.getMdp();
        System.out.println("(!) userLOGIN - NULL - login demandé : " + loginTmp);
        System.out.println("(!) userLOGIN - NULL - mdp demandé : " + mdpTmp);
        List<User> utilisateursTrouves = userRepository.findByLoginAndMdpAndDesactive(loginTmp, mdpTmp, 0);

        if (utilisateursTrouves.isEmpty() == true || utilisateursTrouves.size() > 1 || utilisateursTrouves.get(0).getAdmin() == 0)
        {
            // Dans le cas d'un problème d'authentification de l'utilisateur
            System.out.println("(*) Authentification refusée pour l'utilisateur ");

            // Retour de la méthode
            return null;
        } else {

            // Dans le cas d'une authentification valide de l'utilisateur

            String alphabetsInUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String alphabetsInLowerCase = "abcdefghijklmnopqrstuvwxyz";
            String numbers = "0123456789";
            // create a super set of all characters
            String allCharacters = alphabetsInLowerCase + alphabetsInUpperCase + numbers;
            // initialize a string to hold result
            StringBuffer randomString = new StringBuffer();
            // loop for 10 times
            for (int i = 0; i < 64; i++) {
                // generate a random number between 0 and length of all characters
                int randomIndex = (int)(Math.random() * allCharacters.length());
                // retrieve character at index and add it to result
                randomString.append(allCharacters.charAt(randomIndex));
            }
            String tokenClient = randomString.toString();

            utilisateursAuthentifies.put(adresseIpClient, tokenClient);

            ValidationAuthentification retourAuthentification = new ValidationAuthentification(utilisateursTrouves.get(0).getId(), tokenClient);
            System.out.println("(*) Authentification réussie pour l'utilisateur .");

            return retourAuthentification;
        }

    }


    // BandeauLatAccueil - Transmettre les informations sur l'utilisateur actuel pour affichage
    @PostMapping(value = "/bandeau_lat_accueil", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Donnees BandeauLatAccueil(@RequestBody Token tokenEntrant, HttpServletRequest request)
    {
        // Initialisations
        String ipClient = request.getRemoteAddr();
        System.out.println("(!) USER-BandeauLatAccueil - " + ipClient +" - Requête POST entrante.");

        // Vérification de l'authentification avec ip et token
        System.out.println("(!) USER-BandeauLatAccueil - " + ipClient +" - Vérification de l'authentification de l'utilisateur d'identifiant " + tokenEntrant.getIdClient() + ".");
        if (!((utilisateursAuthentifies.get(ipClient)).equals(tokenEntrant.getTokenClient())))
        {
            // Echec dans le cas d'un changement d'ip ou d'un mauvais token
            System.out.println("(!) USER-BandeauLatAccueil - " + ipClient + " - L'utilisateur n'a pas pu être correctement authentifié.");
            System.out.println("(!) USER-BandeauLatAccueil - " + ipClient + " - Retour de la fonction : packet de données vide.");
            Donnees donneesRetour = new Donnees(
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "echec");
            return donneesRetour;
        } else {
            // Succès dans le cas où l'authentification est confirmée
            System.out.println("(!) USER-BandeauLatAccueil - " + ipClient + " - L'utilisateur a bien été authentifié.");

            // Préparation des données à envoyer
            System.out.println("(!) USER-BandeauLatAccueil - " + ipClient + " - Récupération, calcul et préparation des données à envoyer.");
            List<User> utilisateursTrouves = userRepository.findByIdAndDesactive((Long) tokenEntrant.getIdClient(), 0);
            List<Invites> canauxInvitesTrouves = invitesRepository.findByIduser(tokenEntrant.getIdClient());
            List<Canal> canauxProprioTrouves = canalRepository.findByUserproprio(tokenEntrant.getIdClient());
            int nbCanauxInscrits = canauxProprioTrouves.size() + canauxInvitesTrouves.size();

            // Envoi des données au React
            System.out.println("(!) USER-BandeauLatAccueil - " + ipClient + " - Retour de la fonction : packet de données contenant prenom/nom/login/admin/enligne/nbcanaux.");
            Donnees donneesRetour = new Donnees(
                    utilisateursTrouves.get(0).getPrenom(),
                    utilisateursTrouves.get(0).getNom(),
                    utilisateursTrouves.get(0).getLogin(),
                    String.valueOf(utilisateursTrouves.get(0).getAdmin()),
                    String.valueOf(utilisateursTrouves.get(0).getEnligne()),
                    String.valueOf(nbCanauxInscrits),
                    "",
                    "",
                    "",
                    "",
                    "valide");
            return donneesRetour;
        }
    }
}