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
        System.out.println(" ");
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


    // ContenuAccueilTous - Transmettre le code HTML du tableau affichant tous les canaux
    @PostMapping(value = "/contenu_accueil/canauxtous", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Donnees ContenuAccueilTous(@RequestBody Token tokenEntrant, HttpServletRequest request)
    {
        // Initialisations
        String ipClient = request.getRemoteAddr();
        System.out.println(" ");
        System.out.println("(!) USER-ContenuAccueilTous - " + ipClient +" - Requête POST entrante sur le chemin /canauxtous.");

        // Vérification de l'authentification avec ip et token
        System.out.println("(!) USER-ContenuAccueilTous - " + ipClient +" - Vérification de l'authentification de l'utilisateur d'identifiant " + tokenEntrant.getIdClient() + ".");
        if (!((utilisateursAuthentifies.get(ipClient)).equals(tokenEntrant.getTokenClient())))
        {
            // Echec dans le cas d'un changement d'ip ou d'un mauvais token
            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - L'utilisateur n'a pas pu être correctement authentifié.");
            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Retour de la fonction : packet de données vide.");
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
            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - L'utilisateur a bien été authentifié.");

            // Préparation des données à envoyer
            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Récupération, calcul et préparation des données à envoyer.");
            List<Canal> canauxProprioTrouves = canalRepository.findByUserproprio(tokenEntrant.getIdClient());
            List<Invites> canauxInvitesTrouves = invitesRepository.findByIduser(tokenEntrant.getIdClient());
            int nbCanauxProprio = canauxProprioTrouves.size();
            int nbCanauxInvites = canauxInvitesTrouves.size();
            int nbCanauxTous = nbCanauxProprio + nbCanauxInvites;
            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - " + nbCanauxProprio + " canaux propriétaires trouvés.");
            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - " + nbCanauxInvites + " canaux invités trouvés.");

            String[][] lignesTableau = new String[100][5];

            String codeHtml = "";
            int i = 0;
            while (nbCanauxProprio > 0) {
                System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Traitement du " + i + "e canal propriétaire.");

                //Récupération de l'identifiant du canal
                lignesTableau[i][0] = Long.toString(canauxProprioTrouves.get(i).getId());
                System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Identifiant du " + i + "e canal propriétaire : " + lignesTableau[i][0] + ".");

                //Récupération du titre du canal
                lignesTableau[i][1] = canauxProprioTrouves.get(i).getTitre();
                System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Titre du " + i + "e canal propriétaire : " + lignesTableau[i][1] + ".");

                //Récupération de l'utilisateur propriétaire du canal
                List<User> userProprioTrouve = userRepository.findByIdAndDesactive(canauxProprioTrouves.get(i).getUserproprio(), 0);
                lignesTableau[i][2] = userProprioTrouve.get(0).getPrenom() + " " + userProprioTrouve.get(0).getNom();
                System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Utilisateur propriétaire du " + i + "e canal propriétaire : " + lignesTableau[i][2] + ".");

                //Récupération de 3 utilisateurs invités du canal
                String[] invites = {"", "", "", ""};
                List<Invites> invitesTrouves = invitesRepository.findByIdcanal(canauxProprioTrouves.get(i).getId());
                int nbUserInvites = invitesTrouves.size();
                System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Nombres d'invités du " + i + "e canal propriétaire : " + nbUserInvites + ".");
                if (nbUserInvites > 4) {
                    nbUserInvites = 4;
                }
                int j = 0;
                while (nbUserInvites > 0) {
                    System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Traitement du " + j + "e invité pour le canal " + i + ".");
                    if (j < 3) {
                        List<User> userInvites = userRepository.findByIdAndDesactive(invitesTrouves.get(j).getIduser(), 0);
                        System.out.println("(*) taille" + userInvites.size());
                        if (userInvites.size() <= 0) {
                            invites[j] = "";
                            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Prénom et nom du " + j + "e invité pour le canal " + i + " : " + invites[j] + ".");
                        } else {
                            invites[j] = userInvites.get(0).getPrenom() + " " + userInvites.get(0).getNom();
                            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Prénom et nom du " + j + "e invité pour le canal " + i + " : " + invites[j] + ".");
                        }
                    } else {
                        invites[j] = "...";
                        System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Fin de la chaine des invités avec : " + invites[j] + ".");
                    }
                    j++;
                    nbUserInvites--;
                }
                String invitesTotal = "";
                for (int k = 0; k < 4; k++) {
                    invitesTotal = invitesTotal + ", " + invites[k];
                    System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Composition de la chaine totale des invités : " + invitesTotal + ".");
                }
                lignesTableau[i][3] = invitesTotal;
                System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Utilisateurs invités du " + i + "e canal propriétaire : " + lignesTableau[i][3] + ".");

                // Récupération de la date de création du canal
                lignesTableau[i][4] = canauxProprioTrouves.get(i).getDate();
                System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Date de création du " + i + "e canal propriétaire : " + lignesTableau[i][4] + ".");

                // Condition d'arrêt de la boucle
                i++;
                nbCanauxProprio--;
            }

            // Construction de la portion HTML des lignes du tableau
            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Construction de la portion HTML des lignes du tableau");
            System.out.println("(*) ligne1: " + lignesTableau[0][0]);
            int position = 0;
            while (nbCanauxTous > 0) {
                codeHtml = codeHtml + "<tr id='tab_l" + lignesTableau[position][0] + "' onMouseEnter={() => {objJS.titre_en_vert(" + lignesTableau[position][0] + ")}} onMouseLeave={() => {objJS.titre_en_vert_fin(" + lignesTableau[position][0] + ")}}>" +
                                            "<td style='border-right: 1px dimgrey solid;'>" +
                                                lignesTableau[position][0] +
                                            "</td>" +
                                            "<td class='cellule_description' style='border-right: 1px dimgrey solid;'>" +
                                                "<strong>" +
                                                    "<span id='tab_t" + lignesTableau[position][0] + "'>" +
                                                        lignesTableau[position][1] +
                                                    "</span>" +
                                                    "<br />" +
                                                    lignesTableau[position][2] +
                                                "</strong>" +
                                                lignesTableau[position][3] +
                                            "</td>" +
                                            "<td>" +
                                                lignesTableau[position][4] +
                                            "</td>" +
                                        "</tr>";
                position++;
                nbCanauxTous--;
            }


            System.out.println("(*) html final : " + codeHtml);

            // Envoi des données au React
            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Retour de la fonction : packet de données contenant le code HTML du tableau affichant tous les canaux de l'utilisateur.");
            Donnees donneesRetour = new Donnees(
                    codeHtml,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "valide");
            return donneesRetour;
        }
    }


    // ContenuAccueilA - Transmettre la liste des canaux pour affichage
    @PostMapping(value = "/contenu_accueil/canalproprio", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Canal> ContenuAccueilA(@RequestBody Token tokenEntrant, HttpServletRequest request)
    {
        // Initialisations
        String ipClient = request.getRemoteAddr();
        System.out.println(" ");
        System.out.println("(!) USER-ContenuAccueilA - " + ipClient +" - Requête POST entrante sur le chemin /canalproprio.");

        // Vérification de l'authentification avec ip et token
        System.out.println("(!) USER-ContenuAccueilA - " + ipClient +" - Vérification de l'authentification de l'utilisateur d'identifiant " + tokenEntrant.getIdClient() + ".");
        if (!((utilisateursAuthentifies.get(ipClient)).equals(tokenEntrant.getTokenClient())))
        {
            // Echec dans le cas d'un changement d'ip ou d'un mauvais token
            System.out.println("(!) USER-ContenuAccueilA - " + ipClient + " - L'utilisateur n'a pas pu être correctement authentifié.");
            System.out.println("(!) USER-ContenuAccueilA - " + ipClient + " - Retour de la fonction : packet de données vide.");
            return null;
        } else {
            // Succès dans le cas où l'authentification est confirmée
            System.out.println("(!) USER-ContenuAccueilA - " + ipClient + " - L'utilisateur a bien été authentifié.");

            // Préparation des données à envoyer
            System.out.println("(!) USER-ContenuAccueilA - " + ipClient + " - Récupération, calcul et préparation des données à envoyer.");
            List<Canal> canauxProprioTrouves = canalRepository.findByUserproprio(tokenEntrant.getIdClient());
            List<Invites> canauxInvitesTrouves = invitesRepository.findByIduser(tokenEntrant.getIdClient());

            // Envoi des données au React
            System.out.println("(!) USER-ContenuAccueilA - " + ipClient + " - Retour de la fonction : packet de données contenant la liste des canaux dont l'utilisateur est propriétaires.");
            return canauxProprioTrouves;
        }
    }


    // ContenuAccueilB - Transmettre les informations sur l'utilisateur proprio d'un canal pour affichage
    @PostMapping(value = "/contenu_accueil/userproprio/{numero}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Donnees ContenuAccueilB(@RequestBody Token tokenEntrant, @PathVariable("numero") String numero, HttpServletRequest request)
    {
        // Initialisations
        String ipClient = request.getRemoteAddr();
        System.out.println(" ");
        System.out.println("(!) USER-ContenuAccueilB - " + ipClient +" - Requête POST entrante sur le chemin /userproprio avec numero=" + numero + ".");

        // Vérification de l'authentification avec ip et token
        System.out.println("(!) USER-ContenuAccueilB - " + ipClient +" - Vérification de l'authentification de l'utilisateur d'identifiant " + tokenEntrant.getIdClient() + ".");
        if (!((utilisateursAuthentifies.get(ipClient)).equals(tokenEntrant.getTokenClient())))
        {
            // Echec dans le cas d'un changement d'ip ou d'un mauvais token
            System.out.println("(!) USER-ContenuAccueilB - " + ipClient + " - L'utilisateur n'a pas pu être correctement authentifié.");
            System.out.println("(!) USER-ContenuAccueilB - " + ipClient + " - Retour de la fonction : packet de données vide.");
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
            System.out.println("(!) USER-ContenuAccueilB - " + ipClient + " - L'utilisateur a bien été authentifié.");

            // Préparation des données à envoyer
            System.out.println("(!) USER-ContenuAccueilB - " + ipClient + " - Récupération, calcul et préparation des données à envoyer.");
            Optional<User> utilisateurProprio = userRepository.findById(Long.valueOf(1));
            System.out.println("!!!" + utilisateurProprio.isEmpty() + "--"+ Long.valueOf(numero));

            // Envoi des données au React
            System.out.println("(!) USER-ContenuAccueilB - " + ipClient + " - Retour de la fonction : packet de données contenant nom/prenom de l'utilisateur propriétaire demandé.");
            Donnees donneesRetour = new Donnees(
                    String.valueOf(utilisateurProprio.get().getNom()),
                    String.valueOf(utilisateurProprio.get().getPrenom()),
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "valide");
            return donneesRetour;
        }
    }


    // ContenuAccueilC - Transmettre les informations sur 3 utilisateurs invités d'un canal pour affichage
    @PostMapping(value = "/contenu_accueil/usersinvites/{numero}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Donnees ContenuAccueilC(@RequestBody Token tokenEntrant, @PathVariable("numero") Integer numero, HttpServletRequest request)
    {
        // Initialisations
        String ipClient = request.getRemoteAddr();
        System.out.println(" ");
        System.out.println("(!) USER-ContenuAccueilC - " + ipClient +" - Requête POST entrante sur le chemin /userproprio avec numero=" + numero + ".");

        // Vérification de l'authentification avec ip et token
        System.out.println("(!) USER-ContenuAccueilC - " + ipClient +" - Vérification de l'authentification de l'utilisateur d'identifiant " + tokenEntrant.getIdClient() + ".");
        if (!((utilisateursAuthentifies.get(ipClient)).equals(tokenEntrant.getTokenClient())))
        {
            // Echec dans le cas d'un changement d'ip ou d'un mauvais token
            System.out.println("(!) USER-ContenuAccueilC - " + ipClient + " - L'utilisateur n'a pas pu être correctement authentifié.");
            System.out.println("(!) USER-ContenuAccueilC - " + ipClient + " - Retour de la fonction : packet de données vide.");
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
            System.out.println("(!) USER-ContenuAccueilC - " + ipClient + " - L'utilisateur a bien été authentifié.");

            // Préparation des données à envoyer
            System.out.println("(!) USER-ContenuAccueilC - " + ipClient + " - Récupération, calcul et préparation des données à envoyer.");
            List<Invites> utilisateursInvites = invitesRepository.findByIdcanal(numero);
            String[] prenoms = new String[]{"", "", ""};
            String[] noms = new String[]{"", "", ""};
            int taille = utilisateursInvites.size();
            int max = 3;
            int i = 0;
            while (taille > 0 && max > 0){
                Optional<User> utilisateurInvite = userRepository.findById(utilisateursInvites.get(i).getIduser());
                prenoms[i]=utilisateurInvite.get().getPrenom();
                noms[i]=utilisateurInvite.get().getNom();
                i++;
                taille --;
                max--;
            }

            // Envoi des données au React
            System.out.println("(!) USER-ContenuAccueilC - " + ipClient + " - Retour de la fonction : packet de données contenant nom/prenom de l'utilisateur propriétaire demandé.");
            Donnees donneesRetour = new Donnees(
                    prenoms[0],
                    noms[0],
                    prenoms[1],
                    noms[1],
                    prenoms[2],
                    noms[2],
                    "",
                    "",
                    "",
                    "",
                    "valide");
            return donneesRetour;
        }
    }


    // BandeauLatAccueil - Transmettre les informations sur l'utilisateur actuel pour affichage
    @PostMapping(value = "/bandeau_lat_accueil", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Donnees BandeauLatAccueil(@RequestBody Token tokenEntrant, HttpServletRequest request)
    {
        // Initialisations
        String ipClient = request.getRemoteAddr();
        System.out.println(" ");
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