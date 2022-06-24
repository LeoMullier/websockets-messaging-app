// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : script.jsx                                                                           //
// Description : Script JS pour gérer les actions dynamiques sur les pages Web                          //
// Date de dernière mise à jour : 23/06/2022                                                            //
// ==================================================================================================== //


// Importations
import React from 'react';


// Classe principale
export default class scriptJS extends React.Component{
	// Constructeur et états
	constructor(props) 
	{
        super(props);
		this.state = 
		{
			panneau_etat: 'a',
            retour_etat: "off"
		};
	}


    /*========== TESTER L'IMPORTATION DU SCRIPT JS ===========*/
    tester()
    {
        alert("(!) L'importation du script JS est fonctionnelle.")
    }


    /*========== VERIFICATION DES CARACTERES SAISIS ==========*/
    verifier_caracteres_speciaux()
    {
        // Initialisations
        //var carInterdits = /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
        var carInterdits = /[ `'"<>]/;
        var login = document.getElementById("name");
        var firstname = document.getElementById("firstname");

        // Test de présence de ces caractères interdits
        if(carInterdits.test(login.value))
        {
            alert("(!) Il semblerait que vous aillez entré au moins un caractère non-autorisé, merci de vérifier votre saisie et de retenter l'opération.");
            window.location.href('url-a-completer');
        }
    }


    /*========== DECONNECTER UTILISATEUR ==========*/
    deconnecter_utilisateur()
    {
        // Désactiver le cookie côté client
        document.cookie = document.cookie + ";max-age = 0";  
         

        // Changer le statut enligne de l'utilisateur à 0


        // Rediriger l'utilisateur vers la page de login
        window.location.assign('bienvenue')
    }


    /*========== AFFICHER RETOUR LOGIN ==========*/
    afficher_retour_login()
    {
        if (this.state.retour_etat == "off")
        {
            let entete_image = document.getElementById("entete_image");
            let entete_titre = document.getElementById("entete_titre");
            let entete = document.getElementById("entete");
            let entete_titre_width = entete_titre.offsetWidth;
            entete_image.src = "http://localhost:3000/img/logout.png";
            entete_titre.style.width = entete_titre_width + 'px';
            entete_titre.innerHTML = `Se déconnecter`;
            this.state.retour_etat = "on";
        }
    }


    /*========== AFFICHER RETOUR TOUS MES CANAUX ==========*/
    afficher_retour_tmc()
    {
        if (this.state.retour_etat == "off")
        {
            let entete_image = document.getElementById("entete_image");
            let entete_titre = document.getElementById("entete_titre");
            let entete = document.getElementById("entete");
            let entete_titre_width = entete_titre.offsetWidth;
            entete.onclick = function() {window.location.assign("utilisateur_tous.html")};
            entete_image.src = "http://localhost:3000/img/retour.png";
            entete_titre.style.width = entete_titre_width + 'px';
            entete_titre.innerHTML = `Retour`;
            this.state.retour_etat = "on";
        }
    }


    /*========== MASQUER RETOUR TOUS MES CANAUX ==========*/
    masquer_retour_tmc()
    {
        if (this.state.retour_etat == "on")
        {
            let entete_image = document.getElementById("entete_image");
            let entete_titre = document.getElementById("entete_titre");
            entete_image.src = "http://localhost:3000/img/logo.png";
            entete_titre.style.width = "unset";
            entete_titre.innerHTML = `UTC - Utilitaire Textuel pour la Communication`;
            this.state.retour_etat= "off";
        }
    }


    /*========== AFFICHER LE TITRE DU CANAL EN VERT DANS LE TABLEAU ==========*/
    titre_en_vert(id)
    {
        let tab_titre = document.getElementById("tab_t"+id);
        tab_titre.style.color = "#9ae69a";

        //let tab_ligne = document.getElementById("tab_l"+id);
        //tab_ligne.onclick = function() {window.location.assign("canal.html")};
    }


    /*========== MASQUER LE TITRE DU CANAL EN VERT DANS LE TABLEAU ==========*/
    titre_en_vert_fin(id)
    {
        let tab_titre = document.getElementById("tab_t"+id);
        tab_titre.style.color = "white";
    }


    /*========== MASQUER RETOUR LOGIN ==========*/
    masquer_retour_login()
    {
        if (this.state.retour_etat == "on")
        {
            let entete_image = document.getElementById("entete_image");
            let entete_titre = document.getElementById("entete_titre");
            entete_image.src = "http://localhost:3000/img/logo.png";
            entete_titre.style.width = "unset";
            entete_titre.innerHTML = `UTC - Utilitaire Textuel pour la Communication`;
            this.state.retour_etat = "off";
        }
    }


    /*========== MASQUER PANNEAU LATTERAL ==========*/
    masquer_panneau()
    {
        let panneau = document.getElementById("panneau")
        let corps = document.getElementById("corps")
        panneau.style.visibility = "hidden";
        corps.style.right = "0px";
        this.state.panneau_etat = 'm';
        //console.log("(!) Disparition du panneau lattéral réussie");
    }


    /*========== AFFICHER PANNEAU LATTERAL ==========*/
    afficher_panneau()
    {
        let panneau = document.getElementById("panneau")
        let corps = document.getElementById("corps")
        panneau.style.visibility = "visible";
        corps.style.right = "394px";
        this.state.panneau_etat = 'a';
        //console.log("(!) Apparition du panneau lattéral réussie");
    }


    /*========== PANNEAU LATTERAL DYNAMIQUE ==========*/
    changer_panneau()
    {
        if (this.state.panneau_etat == 'a')
        {
            this.masquer_panneau();
        }
        else
        {
            this.afficher_panneau();
        }
    }


    /*========== ACTIVER LES BOUTONS D'ACTIONS ==========*/
    activer_boutons_actions()
    {
        let img_activer = document.getElementById("action_img_activer")
        let img_desactiver = document.getElementById("action_img_desactiver")
        let img_supprimer = document.getElementById("action_img_supprimer")

        let span_activer = document.getElementById("action_span_activer")
        let span_desactiver = document.getElementById("action_span_desactiver")
        let span_supprimer = document.getElementById("action_span_supprimer")

        img_activer.src = "/img/play.png"
        img_desactiver.src = "/img/pause.png"
        img_supprimer.src = "/img/logout.png"

        span_activer.style.color = "white"
        span_desactiver.style.color = "white"
        span_supprimer.style.color = "white"
    }


    /*========== ACTION ACTIVER UN UTILISATEUR ==========*/
    ajouter_utilisateur()
    {
        window.location.assign("liste-utilisateurs/ajouter")
    }


    /*========== ACTION ACTIVER UN UTILISATEUR ==========*/
    activer_utilisateur()
    {
        let liste_utilisateurs = document.getElementsByName('selection');
        let utilisateur_selectionne = 0
        for (let ligne of liste_utilisateurs)
        {
            if (ligne.checked)
            {
                utilisateur_selectionne = ligne.value
            }
        }
        if (utilisateur_selectionne != 0)
        {
            window.location.assign("liste-utilisateurs/activer/" + utilisateur_selectionne)
        }
    }


    /*========== ACTION DESACTIVER UN UTILISATEUR ==========*/
    desactiver_utilisateur()
    {
        let liste_utilisateurs = document.getElementsByName('selection');
        let utilisateur_selectionne = 0
        for (let ligne of liste_utilisateurs)
        {
            if (ligne.checked)
            {
                utilisateur_selectionne = ligne.value
            }
        }
        if (utilisateur_selectionne != 0)
        {
            window.location.assign("liste-utilisateurs/desactiver/" + utilisateur_selectionne)
        }
    }


    /*========== ACTION SUPPRIMER UN UTILISATEUR ==========*/
    supprimer_utilisateur()
    {
        let liste_utilisateurs = document.getElementsByName('selection');
        let utilisateur_selectionne = 0
        for (let ligne of liste_utilisateurs)
        {
            if (ligne.checked)
            {
                utilisateur_selectionne = ligne.value
            }
        }
        if (utilisateur_selectionne != 0)
        {
            window.location.assign("liste-utilisateurs/supprimer/" + utilisateur_selectionne)
        }
    }


    /*========== POSITIONNEMENT EN BAS DE LA CONVERSATION ==========*/
    scroll_bas()
    {
        let conversation_hauteur = document.getElementById("conversation").clientHeight;
        let corps = document.getElementById("corps")
        console.log("hauteur", conversation_hauteur);
        corps.scrollTop += conversation_hauteur;
        console.log("Scroll :", document.getElementById("main").scrollTop);

    }


    /*========== LARGEUR MAX DE LA ZONE DE SAISIE D'UN MESSAGE ==========*/
    changement_bulle()
    {
        alert("change")
        var editable
        editable = document.getElementById("message_saisie_bulle")
        // console.log(document.getElementById("msg_bas"))
        $(document).keydown(function(event) {
            // console.log(editable)
            if(document.activeElement==editable){
                if ((event.ctrlKey || event.metaKey) && event.keyCode == 86) {
                    setTimeout(()=>{
                        this.scroll_bas()
                    },10)
                }
                this.scroll_bas()
            }

        })
    }
    


    /*========== AFFICHER LE MESSAGE EN LIGNE DANS LE TABLEAU ==========*/
    afficher_enligne_oui(id)
    {
        let cellule_droite = document.getElementById("participant_n_"+id);
        cellule_droite.innerHTML = "est actuellement en ligne";
    }


    /*========== AFFICHER LE MESSAGE N'EST PAS EN LIGNE DANS LE TABLEAU ==========*/
    afficher_enligne_non(id)
    {
        let cellule_droite = document.getElementById("participant_n_"+id);
        cellule_droite.innerHTML = "est déconnecté";
    }


    /*========== MASQUER LE MESSAGE EN LIGNE DANS LE TABLEAU ==========*/
    masquer_enligne(id, chaine)
    {
        let cellule_droite = document.getElementById("participant_n_"+id);
        cellule_droite.innerHTML = chaine;
    }
}
