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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.format.DateTimeFormatter;
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

        if (utilisateursTrouves.isEmpty() == true || utilisateursTrouves.size() > 1)
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
            int nbCanauxProprio2 = nbCanauxProprio;
            int nbCanauxInvites = canauxInvitesTrouves.size();
            int nbCanauxInvites2 = nbCanauxInvites;
            //int nbCanauxTous = nbCanauxProprio + nbCanauxInvites;
            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - " + nbCanauxProprio + " canaux propriétaires trouvés.");
            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - " + nbCanauxInvites + " canaux invités trouvés.");

            String[][] lignesTableau = new String[100][5];

            // Chaîne de caratères finale
            String codeHtmlProprio = "";
            String codeHtmlInvite = "";

            // Recherche de canaux propriétaires
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
                        //System.out.println("(*) taille" + userInvites.size());
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
                    if (invites[k] != "") {
                        if (k == 3) {
                            invitesTotal = invitesTotal + ", ...";
                            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Composition de la chaine totale des invités : " + invitesTotal + ".");
                        } else {
                            invitesTotal = invitesTotal + ", " + invites[k];
                            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Composition de la chaine totale des invités : " + invitesTotal + ".");
                        }
                    }
                }
                lignesTableau[i][3] = invitesTotal;
                System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Utilisateurs invités du " + i + "e canal propriétaire : " + lignesTableau[i][3] + ".");

                // Récupération de la date de création du canal
                lignesTableau[i][4] = canauxProprioTrouves.get(i).getDate().split(" ")[0];
                System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Date de création du " + i + "e canal propriétaire : " + lignesTableau[i][4] + ".");

                // Condition d'arrêt de la boucle
                i++;
                nbCanauxProprio--;
            }

            // Construction de la portion HTML des lignes du tableau
            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Construction de la portion HTML des lignes du tableau");
            int position = 0;
            while (nbCanauxProprio2 > 0) {
                codeHtmlProprio = codeHtmlProprio + "<tr id='tab_l" + lignesTableau[position][0] + "' onMouseEnter='titre_en_vert(" + lignesTableau[position][0]                      + ")' onMouseLeave='titre_en_vert_fin(" + lignesTableau[position][0] + ")' onClick=\"window.location.assign('canal?id=" + lignesTableau[position][0] + "')\">" +
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
                nbCanauxProprio2--;
            }

            // Recherche de canaux invités
            i = 0;
            while (nbCanauxInvites > 0) {
                System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Traitement du " + i + "e canal invité.");

                //Récupération de l'identifiant du canal
                lignesTableau[i][0] = Long.toString(canauxInvitesTrouves.get(i).getIdcanal());
                System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Identifiant du " + i + "e canal invité : " + lignesTableau[i][0] + ".");

                //Récupération du titre du canal
                Optional<Canal> canalInvite = canalRepository.findById(canauxInvitesTrouves.get(i).getIdcanal());
                Canal canalInviteActuel = canalInvite.get();

                lignesTableau[i][1] = canalInviteActuel.getTitre();
                System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Titre du " + i + "e canal invité : " + lignesTableau[i][1] + ".");

                //Récupération de l'utilisateur invité du canal
                List<User> userProprioTrouve = userRepository.findByIdAndDesactive(canalInviteActuel.getUserproprio(), 0);
                lignesTableau[i][2] = userProprioTrouve.get(0).getPrenom() + " " + userProprioTrouve.get(0).getNom();
                System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Utilisateur invité du " + i + "e canal invité : " + lignesTableau[i][2] + ".");

                //Récupération de 3 utilisateurs invités du canal
                String[] invites = {"", "", "", ""};
                List<Invites> invitesTrouves = invitesRepository.findByIdcanal(canalInviteActuel.getId());
                int nbUserInvites = invitesTrouves.size();
                System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Nombres d'invités du " + i + "e canal invité : " + nbUserInvites + ".");
                if (nbUserInvites > 4) {
                    nbUserInvites = 4;
                }
                int j = 0;
                while (nbUserInvites > 0) {
                    System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Traitement du " + j + "e invité pour le canal " + i + ".");
                    if (j < 3) {
                        List<User> userInvites = userRepository.findByIdAndDesactive(invitesTrouves.get(j).getIduser(), 0);
                        //System.out.println("(*) taille" + userInvites.size());
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
                    if (invites[k] != "") {
                        if (k == 3) {
                            invitesTotal = invitesTotal + ", ...";
                            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Composition de la chaine totale des invités : " + invitesTotal + ".");
                        } else {
                            invitesTotal = invitesTotal + ", " + invites[k];
                            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Composition de la chaine totale des invités : " + invitesTotal + ".");
                        }
                    }
                }
                lignesTableau[i][3] = invitesTotal;
                System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Utilisateurs invités du " + i + "e canal invité : " + lignesTableau[i][3] + ".");

                // Récupération de la date de création du canal
                lignesTableau[i][4] = canalInviteActuel.getDate().split(" ")[0];
                System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Date de création du " + i + "e canal invité : " + lignesTableau[i][4] + ".");

                // Condition d'arrêt de la boucle
                i++;
                nbCanauxInvites--;
            }

            // Construction de la portion HTML des lignes du tableau
            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Construction de la portion HTML des lignes du tableau");
            position = 0;
            while (nbCanauxInvites2 > 0) {
                codeHtmlInvite = codeHtmlInvite + "<tr id='tab_l" + lignesTableau[position][0] + "' onMouseEnter='titre_en_vert(" + lignesTableau[position][0]                      + ")' onMouseLeave='titre_en_vert_fin(" + lignesTableau[position][0] + ")' onClick=\"window.location.assign('canal?id=" + lignesTableau[position][0] + "')\">" +
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
                nbCanauxInvites2--;
            }


            // Envoi des données au React
            System.out.println("(!) USER-ContenuAccueilTous - " + ipClient + " - Retour de la fonction : packet de données contenant le code HTML du tableau affichant tous les canaux de l'utilisateur.");
            Donnees donneesRetour = new Donnees(
                    codeHtmlProprio,
                    codeHtmlInvite,
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


    // ContenuAccueilProprio - Transmettre le code HTML du tableau affichant les canaux propriétaires
    @PostMapping(value = "/contenu_accueil/canauxproprio", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Donnees ContenuAccueilProprio(@RequestBody Token tokenEntrant, HttpServletRequest request)
    {
        // Initialisations
        String ipClient = request.getRemoteAddr();
        System.out.println(" ");
        System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient +" - Requête POST entrante sur le chemin /canauxproprio.");

        // Vérification de l'authentification avec ip et token
        System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient +" - Vérification de l'authentification de l'utilisateur d'identifiant " + tokenEntrant.getIdClient() + ".");
        if (!((utilisateursAuthentifies.get(ipClient)).equals(tokenEntrant.getTokenClient())))
        {
            // Echec dans le cas d'un changement d'ip ou d'un mauvais token
            System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - L'utilisateur n'a pas pu être correctement authentifié.");
            System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Retour de la fonction : packet de données vide.");
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
            System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - L'utilisateur a bien été authentifié.");

            // Préparation des données à envoyer
            System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Récupération, calcul et préparation des données à envoyer.");
            List<Canal> canauxProprioTrouves = canalRepository.findByUserproprio(tokenEntrant.getIdClient());
            int nbCanauxProprio = canauxProprioTrouves.size();
            int nbCanauxProprio2 = nbCanauxProprio;
            //int nbCanauxTous = nbCanauxProprio + nbCanauxInvites;
            System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - " + nbCanauxProprio + " canaux propriétaires trouvés.");

            String[][] lignesTableau = new String[100][5];

            // Chaîne de caratères finale
            String codeHtmlProprio = "";

            // Recherche de canaux propriétaires
            int i = 0;
            while (nbCanauxProprio > 0) {
                System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Traitement du " + i + "e canal propriétaire.");

                //Récupération de l'identifiant du canal
                lignesTableau[i][0] = Long.toString(canauxProprioTrouves.get(i).getId());
                System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Identifiant du " + i + "e canal propriétaire : " + lignesTableau[i][0] + ".");

                //Récupération du titre du canal
                lignesTableau[i][1] = canauxProprioTrouves.get(i).getTitre();
                System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Titre du " + i + "e canal propriétaire : " + lignesTableau[i][1] + ".");

                //Récupération de l'utilisateur propriétaire du canal
                List<User> userProprioTrouve = userRepository.findByIdAndDesactive(canauxProprioTrouves.get(i).getUserproprio(), 0);
                lignesTableau[i][2] = userProprioTrouve.get(0).getPrenom() + " " + userProprioTrouve.get(0).getNom();
                System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Utilisateur propriétaire du " + i + "e canal propriétaire : " + lignesTableau[i][2] + ".");

                //Récupération de 3 utilisateurs invités du canal
                String[] invites = {"", "", "", ""};
                List<Invites> invitesTrouves = invitesRepository.findByIdcanal(canauxProprioTrouves.get(i).getId());
                int nbUserInvites = invitesTrouves.size();
                System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Nombres d'invités du " + i + "e canal propriétaire : " + nbUserInvites + ".");
                if (nbUserInvites > 4) {
                    nbUserInvites = 4;
                }
                int j = 0;
                while (nbUserInvites > 0) {
                    System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Traitement du " + j + "e invité pour le canal " + i + ".");
                    if (j < 3) {
                        List<User> userInvites = userRepository.findByIdAndDesactive(invitesTrouves.get(j).getIduser(), 0);
                        //System.out.println("(*) taille" + userInvites.size());
                        if (userInvites.size() <= 0) {
                            invites[j] = "";
                            System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Prénom et nom du " + j + "e invité pour le canal " + i + " : " + invites[j] + ".");
                        } else {
                            invites[j] = userInvites.get(0).getPrenom() + " " + userInvites.get(0).getNom();
                            System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Prénom et nom du " + j + "e invité pour le canal " + i + " : " + invites[j] + ".");
                        }
                    } else {
                        invites[j] = "...";
                        System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Fin de la chaine des invités avec : " + invites[j] + ".");
                    }
                    j++;
                    nbUserInvites--;
                }
                String invitesTotal = "";
                for (int k = 0; k < 4; k++) {
                    if (invites[k] != "") {
                        if (k == 3) {
                            invitesTotal = invitesTotal + ", ...";
                            System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Composition de la chaine totale des invités : " + invitesTotal + ".");
                        } else {
                            invitesTotal = invitesTotal + ", " + invites[k];
                            System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Composition de la chaine totale des invités : " + invitesTotal + ".");
                        }
                    }
                }
                lignesTableau[i][3] = invitesTotal;
                System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Utilisateurs invités du " + i + "e canal propriétaire : " + lignesTableau[i][3] + ".");

                // Récupération de la date de création du canal
                lignesTableau[i][4] = canauxProprioTrouves.get(i).getDate().split(" ")[0];
                System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Date de création du " + i + "e canal propriétaire : " + lignesTableau[i][4] + ".");

                // Condition d'arrêt de la boucle
                i++;
                nbCanauxProprio--;
            }

            // Construction de la portion HTML des lignes du tableau
            System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Construction de la portion HTML des lignes du tableau");
            int position = 0;
            while (nbCanauxProprio2 > 0) {
                codeHtmlProprio = codeHtmlProprio + "<tr id='tab_l" + lignesTableau[position][0] + "' onMouseEnter='titre_en_vert(" + lignesTableau[position][0]                      + ")' onMouseLeave='titre_en_vert_fin(" + lignesTableau[position][0] + ")' onClick=\"window.location.assign('../canal?id=" + lignesTableau[position][0] + "')\">" +
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
                nbCanauxProprio2--;
            }


            // Envoi des données au React
            System.out.println("(!) USER-ContenuAccueilProprio - " + ipClient + " - Retour de la fonction : packet de données contenant le code HTML du tableau affichant tous les canaux de l'utilisateur.");
            Donnees donneesRetour = new Donnees(
                    codeHtmlProprio,
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


    // ContenuAccueilInvites - Transmettre le code HTML du tableau affichant les canaux invites
    @PostMapping(value = "/contenu_accueil/canauxinvites", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Donnees ContenuAccueilInvites(@RequestBody Token tokenEntrant, HttpServletRequest request)
    {
        // Initialisations
        String ipClient = request.getRemoteAddr();
        System.out.println(" ");
        System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient +" - Requête POST entrante sur le chemin /canauxinvites.");

        // Vérification de l'authentification avec ip et token
        System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient +" - Vérification de l'authentification de l'utilisateur d'identifiant " + tokenEntrant.getIdClient() + ".");
        if (!((utilisateursAuthentifies.get(ipClient)).equals(tokenEntrant.getTokenClient())))
        {
            // Echec dans le cas d'un changement d'ip ou d'un mauvais token
            System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - L'utilisateur n'a pas pu être correctement authentifié.");
            System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Retour de la fonction : packet de données vide.");
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
            System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - L'utilisateur a bien été authentifié.");

            // Préparation des données à envoyer
            System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Récupération, calcul et préparation des données à envoyer.");
            List<Invites> canauxInvitesTrouves = invitesRepository.findByIduser(tokenEntrant.getIdClient());
            int nbCanauxInvites = canauxInvitesTrouves.size();
            int nbCanauxInvites2 = nbCanauxInvites;
            //int nbCanauxTous = nbCanauxProprio + nbCanauxInvites;
            System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - " + nbCanauxInvites + " canaux invités trouvés.");

            String[][] lignesTableau = new String[100][5];

            // Chaîne de caratères finale
            String codeHtmlInvite = "";

            // Recherche de canaux invités
            int i = 0;
            while (nbCanauxInvites > 0) {
                System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Traitement du " + i + "e canal invité.");

                //Récupération de l'identifiant du canal
                lignesTableau[i][0] = Long.toString(canauxInvitesTrouves.get(i).getIdcanal());
                System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Identifiant du " + i + "e canal invité : " + lignesTableau[i][0] + ".");

                //Récupération du titre du canal
                Optional<Canal> canalInvite = canalRepository.findById(canauxInvitesTrouves.get(i).getIdcanal());
                Canal canalInviteActuel = canalInvite.get();

                lignesTableau[i][1] = canalInviteActuel.getTitre();
                System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Titre du " + i + "e canal invité : " + lignesTableau[i][1] + ".");

                //Récupération de l'utilisateur invité du canal
                List<User> userProprioTrouve = userRepository.findByIdAndDesactive(canalInviteActuel.getUserproprio(), 0);
                lignesTableau[i][2] = userProprioTrouve.get(0).getPrenom() + " " + userProprioTrouve.get(0).getNom();
                System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Utilisateur invité du " + i + "e canal invité : " + lignesTableau[i][2] + ".");

                //Récupération de 3 utilisateurs invités du canal
                String[] invites = {"", "", "", ""};
                List<Invites> invitesTrouves = invitesRepository.findByIdcanal(canalInviteActuel.getId());
                int nbUserInvites = invitesTrouves.size();
                System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Nombres d'invités du " + i + "e canal invité : " + nbUserInvites + ".");
                if (nbUserInvites > 4) {
                    nbUserInvites = 4;
                }
                int j = 0;
                while (nbUserInvites > 0) {
                    System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Traitement du " + j + "e invité pour le canal " + i + ".");
                    if (j < 3) {
                        List<User> userInvites = userRepository.findByIdAndDesactive(invitesTrouves.get(j).getIduser(), 0);
                        //System.out.println("(*) taille" + userInvites.size());
                        if (userInvites.size() <= 0) {
                            invites[j] = "";
                            System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Prénom et nom du " + j + "e invité pour le canal " + i + " : " + invites[j] + ".");
                        } else {
                            invites[j] = userInvites.get(0).getPrenom() + " " + userInvites.get(0).getNom();
                            System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Prénom et nom du " + j + "e invité pour le canal " + i + " : " + invites[j] + ".");
                        }
                    } else {
                        invites[j] = "...";
                        System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Fin de la chaine des invités avec : " + invites[j] + ".");
                    }
                    j++;
                    nbUserInvites--;
                }
                String invitesTotal = "";
                for (int k = 0; k < 4; k++) {
                    if (invites[k] != "") {
                        if (k == 3) {
                            invitesTotal = invitesTotal + ", ...";
                            System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Composition de la chaine totale des invités : " + invitesTotal + ".");
                        } else {
                            invitesTotal = invitesTotal + ", " + invites[k];
                            System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Composition de la chaine totale des invités : " + invitesTotal + ".");
                        }
                    }
                }
                lignesTableau[i][3] = invitesTotal;
                System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Utilisateurs invités du " + i + "e canal invité : " + lignesTableau[i][3] + ".");

                // Récupération de la date de création du canal
                lignesTableau[i][4] = canalInviteActuel.getDate().split(" ")[0];
                System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Date de création du " + i + "e canal invité : " + lignesTableau[i][4] + ".");

                // Condition d'arrêt de la boucle
                i++;
                nbCanauxInvites--;
            }

            // Construction de la portion HTML des lignes du tableau
            System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Construction de la portion HTML des lignes du tableau");
            int position = 0;
            while (nbCanauxInvites2 > 0) {
                codeHtmlInvite = codeHtmlInvite + "<tr id='tab_l" + lignesTableau[position][0] + "' onMouseEnter='titre_en_vert(" + lignesTableau[position][0]                      + ")' onMouseLeave='titre_en_vert_fin(" + lignesTableau[position][0] + ")' onClick=\"window.location.assign('../canal?id=" + lignesTableau[position][0] + "')\">" +
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
                nbCanauxInvites2--;
            }


            // Envoi des données au React
            System.out.println("(!) USER-ContenuAccueilInvites - " + ipClient + " - Retour de la fonction : packet de données contenant le code HTML du tableau affichant tous les canaux de l'utilisateur.");
            Donnees donneesRetour = new Donnees(
                    codeHtmlInvite,
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


    // BandeauLatCanal - Vérifier la légitimité de l'utilisateur entrant et transmettre les informations sur le canal
    @PostMapping(value = "/bandeau_lat_canal", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Donnees BandeauLatCanal(@RequestBody Token tokenEntrant, HttpServletRequest request, @RequestParam int id) {
        // Initialisations
        String ipClient = request.getRemoteAddr();
        System.out.println(" ");
        System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - Requête POST entrante sur le chemin /bandeau_lat_canal.");

        // Vérification de l'authentification avec ip et token
        System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - Vérification de l'authentification de l'utilisateur d'identifiant " + tokenEntrant.getIdClient() + ".");
        if (!((utilisateursAuthentifies.get(ipClient)).equals(tokenEntrant.getTokenClient()))) {
            // Echec dans le cas d'un changement d'ip ou d'un mauvais token
            System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - L'utilisateur n'a pas pu être correctement authentifié.");
            System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - Retour de la fonction : packet de données vide.");
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
            System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - L'utilisateur a bien été correctement authentifié.");
            System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - Vérification de la légitimité de l'utilisateur à accéder à ce canal.");
            List<Canal> canauxProprioTrouves = canalRepository.findByIdAndUserproprio(Long.valueOf(id), Long.valueOf(tokenEntrant.getIdClient()));
            List<Invites> canauxInvitesTrouves = invitesRepository.findByIdcanalAndIduser(Long.valueOf(id), Long.valueOf(tokenEntrant.getIdClient()));
            if (!(canauxProprioTrouves.size() > 0 || canauxInvitesTrouves.size() > 0)) {
                System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - L'utilisateur n'a pas le droit d'accéder à ce canal.");

                // Envoi des données au React
                System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - Retour de la fonction : packet de données contenant acces refuse.");
                Donnees donneesRetour = new Donnees(
                        "acces refuse",
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
            } else {
                if (canauxProprioTrouves.size() > 0) {
                    System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - L'utilisateur a bien le droit d'accéder à ce canal puisqu'il en est le propriétaire.");

                    // Récupération du titre du canal
                    String titreCanal = canauxProprioTrouves.get(0).getTitre();

                    // Récupération de la description du canal
                    String descriptionCanal = canauxProprioTrouves.get(0).getDescription();

                    // Récupération de la date du canal
                    String dateCanal = canauxProprioTrouves.get(0).getDate().split(" ")[0];

                    // Récupération de la liste d'utilisateurs du canal
                    List<User> userProprio = userRepository.findByIdAndDesactive(canauxProprioTrouves.get(0).getUserproprio(), 0);
                    String utilisateursCanal = "";
                    utilisateursCanal = utilisateursCanal + "<tr onMouseEnter='afficher_enligne_oui(" + userProprio.get(0).getId() + ")' onMouseLeave=\"masquer_enligne(" + userProprio.get(0).getId() + ",'" + userProprio.get(0).getPrenom() + " " + userProprio.get(0).getNom() + "<i>(propriétaire)</i>')\">" +
                            "<td class='participants_gauche'>" +
                            "<font color='#33cc33'>●</font>" +
                            "</td>" +
                            "<td class='participants_droite' id='participant_n_" + userProprio.get(0).getId() + "'>" +
                            userProprio.get(0).getPrenom() + " " + userProprio.get(0).getNom() + "<i>(propriétaire)</i>" +
                            "</td>" +
                            "</tr>";
                    List<Invites> invitesTrouves = invitesRepository.findByIdcanal(id);
                    int nbUserInvites = invitesTrouves.size();
                    System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - Nombres d'invités trouvés pour ce canal : " + nbUserInvites + ".");

                    int j = 0;
                    while (nbUserInvites > 0) {
                        System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - Traitement du " + j + "e invité pour le canal.");
                        List<User> userInvites = userRepository.findByIdAndDesactive(invitesTrouves.get(j).getIduser(), 0);
                        //System.out.println("(*) taille" + userInvites.size());
                        if (userInvites.size() <= 0) {
                            System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - Prénom et nom du " + j + "e invité pour le canal : " + utilisateursCanal + ".");
                        } else {
                            String enligne = "";
                            String statut = "";
                            if (userInvites.get(0).getEnligne() == 1)
                            {
                                enligne = "<font color='#33cc33'>●</font>";
                                statut = "oui";
                            } else {
                                enligne = "<font color='grey'>●</font>";
                                statut = "non";
                            }

                            utilisateursCanal = utilisateursCanal + "<tr onMouseEnter='afficher_enligne_" + statut + "(" + userInvites.get(0).getId() + ")' onMouseLeave=\"masquer_enligne(" + userInvites.get(0).getId() + ",'" + userInvites.get(0).getPrenom() + " " + userInvites.get(0).getNom() + "')\">" +
                                    "<td class='participants_gauche'>" +
                                    enligne +
                                    "</td>" +
                                    "<td class='participants_droite' id='participant_n_" + userInvites.get(0).getId() + "'>" +
                                    userInvites.get(0).getPrenom() + " " + userInvites.get(0).getNom() +
                                    "</td>" +
                                    "</tr>";
                            System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - Prénom et nom du " + j + "e invité pour le canal : " + utilisateursCanal + ".");
                        }
                        j++;
                        nbUserInvites--;
                    }

                    // Envoi des données au React
                    System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - Retour de la fonction : packet de données contenant acces valide et les infos du canal.");
                    Donnees donneesRetour = new Donnees(
                            "acces valide",
                            titreCanal,
                            descriptionCanal,
                            dateCanal,
                            utilisateursCanal,
                            "",
                            "",
                            "",
                            "",
                            "",
                            "valide");
                    return donneesRetour;
                } else {
                    System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - L'utilisateur a bien le droit d'accéder à ce canal puisqu'il y est invité.");
                    Optional<Canal> canalInviteTrouve = canalRepository.findById(canauxInvitesTrouves.get(0).getIdcanal());

                    // Récupération du titre du canal
                    String titreCanal = canalInviteTrouve.get().getTitre();

                    // Récupération de la description du canal
                    String descriptionCanal = canalInviteTrouve.get().getDescription();

                    // Récupération de la date du canal
                    String dateCanal = canalInviteTrouve.get().getDate().split(" ")[0];

                    // Récupération de la liste d'utilisateurs du canal
                    List<User> userProprio = userRepository.findByIdAndDesactive(canalInviteTrouve.get().getUserproprio(), 0);
                    String utilisateursCanal = "";
                    String enligne = "";
                    String statut = "";
                    if (userProprio.get(0).getEnligne() == 1)
                    {
                        enligne = "<font color='#33cc33'>●</font>";
                        statut = "oui";
                    } else {
                        enligne = "<font color='grey'>●</font>";
                        statut = "non";
                    }
                    utilisateursCanal = utilisateursCanal + "<tr onMouseEnter='afficher_enligne_" + statut +"(" + userProprio.get(0).getId() + ")' onMouseLeave=\"masquer_enligne(" + userProprio.get(0).getId() + ",'" + userProprio.get(0).getPrenom() + " " + userProprio.get(0).getNom() + "<i>(propriétaire)</i>')\">" +
                            "<td class='participants_gauche'>" +
                            enligne +
                            "</td>" +
                            "<td class='participants_droite' id='participant_n_" + userProprio.get(0).getId() + "'>" +
                            userProprio.get(0).getPrenom() + " " + userProprio.get(0).getNom() + "<i>(propriétaire)</i>" +
                            "</td>" +
                            "</tr>";
                    List<Invites> invitesTrouves = invitesRepository.findByIdcanal(id);
                    int nbUserInvites = invitesTrouves.size();
                    System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - Nombres d'invités trouvés pour ce canal : " + nbUserInvites + ".");

                    int j = 0;
                    while (nbUserInvites > 0) {
                        System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - Traitement du " + j + "e invité pour le canal.");
                        List<User> userInvites = userRepository.findByIdAndDesactive(invitesTrouves.get(j).getIduser(), 0);
                        //System.out.println("(*) taille" + userInvites.size());
                        if (userInvites.size() <= 0) {
                            System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - Prénom et nom du " + j + "e invité pour le canal : " + utilisateursCanal + ".");
                        } else {
                            enligne = "";
                            statut = "";
                            if (userInvites.get(0).getEnligne() == 1)
                            {
                                enligne = "<font color='#33cc33'>●</font>";
                                statut = "oui";
                            } else {
                                enligne = "<font color='grey'>●</font>";
                                statut = "non";
                            }
                            utilisateursCanal = utilisateursCanal + "<tr onMouseEnter='afficher_enligne_" + statut + "(" + userInvites.get(0).getId() + ")' onMouseLeave=\"masquer_enligne(" + userInvites.get(0).getId() + ",'" + userInvites.get(0).getPrenom() + " " + userInvites.get(0).getNom() + "')\">" +
                                    "<td class='participants_gauche'>" +
                                    enligne +
                                    "</td>" +
                                    "<td class='participants_droite' id='participant_n_" + userInvites.get(0).getId() + "'>" +
                                    userInvites.get(0).getPrenom() + " " + userInvites.get(0).getNom() +
                                    "</td>" +
                                    "</tr>";
                            System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - Prénom et nom du " + j + "e invité pour le canal : " + utilisateursCanal + ".");
                        }
                        j++;
                        nbUserInvites--;
                    }

                    // Envoi des données au React
                    System.out.println("(!) USER-BandeauLatCanal - " + ipClient + " - Retour de la fonction : packet de données contenant acces valide et les infos du canal.");
                    Donnees donneesRetour = new Donnees(
                            "acces valide",
                            titreCanal,
                            descriptionCanal,
                            dateCanal,
                            utilisateursCanal,
                            "",
                            "",
                            "",
                            "",
                            "",
                            "valide");
                    return donneesRetour;
                }
            }
        }
    }


    // ContenuCreerCanal - Transmettre la liste des utilisateurs qu'il est possible d'inviter
    @PostMapping(value = "/contenu_creer_canal/liste_utilisateurs", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Donnees ContenuCreerCanal(@RequestBody Token tokenEntrant, HttpServletRequest request) {
        // Initialisations
        String ipClient = request.getRemoteAddr();
        System.out.println(" ");
        System.out.println("(!) USER-ContenuCreerCanal - " + ipClient + " - Requête POST entrante sur le chemin /contenu_creer_canal/liste_utilisateurs.");

        // Vérification de l'authentification avec ip et token
        System.out.println("(!) USER-ContenuCreerCanal - " + ipClient + " - Vérification de l'authentification de l'utilisateur d'identifiant " + tokenEntrant.getIdClient() + ".");
        if (!((utilisateursAuthentifies.get(ipClient)).equals(tokenEntrant.getTokenClient()))) {
            // Echec dans le cas d'un changement d'ip ou d'un mauvais token
            System.out.println("(!) USER-ContenuCreerCanal - " + ipClient + " - L'utilisateur n'a pas pu être correctement authentifié.");
            System.out.println("(!) USER-ContenuCreerCanal - " + ipClient + " - Retour de la fonction : packet de données vide.");
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
            System.out.println("(!) USER-ContenuCreerCanal - " + ipClient + " - L'utilisateur a bien été correctement authentifié.");
            System.out.println("(!) USER-ContenuCreerCanal - " + ipClient + " - Préparation de la liste des utilisateurs qu'il est possible d'inviter.");

            List<User> futursInvites = userRepository.findByDesactive(0);
            int nbInvites = futursInvites.size();
            int i = 0;
            String codeHTML = "";
            while (nbInvites > 0) {
                if (futursInvites.get(i).getId() != tokenEntrant.getIdClient()) {
                    codeHTML = codeHTML + "<option value=" + futursInvites.get(i).getId() + ">" + futursInvites.get(i).getPrenom() + " " + futursInvites.get(i).getNom() + "</option>";
                }
                nbInvites--;
                i++;
            }

            // Envoi des données au React
            System.out.println("(!) USER-ContenuCreerCanal - " + ipClient + " - Retour de la fonction : packet de données contenant le code HTML des utilisateurs invités possibles.");
            Donnees donneesRetour = new Donnees(
                    codeHTML,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "valide"
            );
            return donneesRetour;
        }
    }


    // ContenuCreerCanal - Créer un nouveau canal en base de données
    @PostMapping(value = "/nouveau-canal"/*, consumes = MediaType.APPLICATION_JSON_VALUE*/)
    public String NouveauCanal(/*@RequestBody Token tokenEntrant, */HttpServletRequest request) {
        // Initialisations
        String ipClient = request.getRemoteAddr();
        System.out.println(" ");
        System.out.println("(!) USER-ContenuCreerCanal - " + ipClient + " - Requête POST entrante sur le chemin /contenu_creer_canal/liste_utilisateurs.");
        String titreTmp = request.getParameter("titre");
        String descriptionTmp = request.getParameter("description");
        String[] invitesTmp = request.getParameterValues("invites");
        String dureeTmp = request.getParameter("duree").split("T")[0] + " " + request.getParameter("duree").split("T")[1] + ":00";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dateTmp = String.valueOf(now);
        String proprioTmp = request.getParameter("proprio");
        //System.out.println("(*) " + invitesTmp[0] + "///"+ invitesTmp[1] + "//" + dureeTmp);

        // Vérification de l'authentification avec ip et token
        /*
        System.out.println("(!) USER-ContenuCreerCanal - " + ipClient + " - Vérification de l'authentification de l'utilisateur d'identifiant " + tokenEntrant.getIdClient() + ".");
        if (!((utilisateursAuthentifies.get(ipClient)).equals(tokenEntrant.getTokenClient()))) {
            // Echec dans le cas d'un changement d'ip ou d'un mauvais token
            System.out.println("(!) USER-ContenuCreerCanal - " + ipClient + " - L'utilisateur n'a pas pu être correctement authentifié.");
            System.out.println("(!) USER-ContenuCreerCanal - " + ipClient + " - Retour de la fonction : packet de données vide.");
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
            System.out.println("(!) USER-ContenuCreerCanal - " + ipClient + " - L'utilisateur a bien été correctement authentifié.");
            */

            System.out.println("(!) USER-NouveauCanal - " + ipClient + " - Vérification des données fournies.");

            List<Canal> canalExistant = canalRepository.findByTitre(titreTmp);
            if (canalExistant.size() != 0) {
                System.out.println("(!) USER-NouveauCanal - " + ipClient + " - Retour de la fonction : packet de données contenant incompatible.");
                return ("(!) Une erreur est survenue, veuillez vérifier vos informations saisies et réessayer plus tard.");
            } else {
                Canal canalTmp = new Canal();
                canalTmp.setUserproprio(Long.valueOf(proprioTmp));
                canalTmp.setTitre(titreTmp);
                canalTmp.setDescription(descriptionTmp);
                canalTmp.setDate(dateTmp);
                canalTmp.setDuree(dureeTmp);
                canalTmp.setSauvegarde(0);
                canalRepository.saveAndFlush(canalTmp);
                System.out.println("(!) USER-NouveauCanal - " + ipClient + " - La canal a bien été ajouté en base de données.");

                List<Canal> nouveauCanal = canalRepository.findByTitre(titreTmp);
                for (String element : invitesTmp)
                {
                    Invites nouvelInvite = new Invites();
                    nouvelInvite.setIdcanal(nouveauCanal.get(0).getId());
                    nouvelInvite.setIduser(Long.valueOf(element));
                    invitesRepository.saveAndFlush(nouvelInvite);
                    System.out.println("(!) USER-NouveauCanal - " + ipClient + " - L'utilisateur n°" + element + " a bien été associé au nouveau canal.");
                }
                return ("<script>window.location.assign('http://localhost:3000/accueil/proprio')</script>");
            }
        //}
    }
}