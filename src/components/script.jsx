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


    /*========== AFFICHER RETOUR LOGIN ==========*/
    afficher_retour_login()
    {
        if (this.state.retour_etat == "off")
        {
            let entete_image = document.getElementById("entete_image");
            let entete_titre = document.getElementById("entete_titre");
            let entete = document.getElementById("entete");
            let entete_titre_width = entete_titre.offsetWidth;
            entete_image.src = "img/logout.png";
            entete_titre.style.width = entete_titre_width + 'px';
            entete_titre.innerHTML = `Se déconnecter`;
            this.state.retour_etat = "on";
        }
    }


    /*========== AFFICHER LE TITRE DU CANAL EN VERT DANS LE TABLEAU ==========*/
    titre_en_vert(id)
    {
        alert("début")
        let tab_titre = document.getElementById("tab_t"+id);
        tab_titre.style.color = "#9ae69a";

        let tab_ligne = document.getElementById("tab_l"+id);
        tab_ligne.onclick = function() {window.location.assign("canal.html")};
    }


    /*========== MASQUER LE TITRE DU CANAL EN VERT DANS LE TABLEAU ==========*/
    titre_en_vert_fin(id)
    {
        alert("fin")
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
            entete_image.src = "img/logo.png";
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

}
