// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : BarreSupLogin.jsx                                                                   //
// Description : Script JS pour afficher la barre supérieure lorsqu'on est sur la phase de login        //
// Date de dernière mise à jour : 06/06/2022                                                            //
// ==================================================================================================== //

//Importations
import React from "react";
import { NavLink } from "react-router-dom";

//Fonction principale de render
function BarreSupAccueil() {
	return (
		<div className="barresuplogin">
			<header>
				<div class="entete" id="entete" onMouseEnter="afficher_retour_login()" onMouseLeave="masquer_retour_login()">
					<img class="entete_image" id="entete_image" src="img/logo.png"/>
					<p class="entete_titre" id="entete_titre">
						UTC - Utilitaire Textuel pour la Communication
					</p>
				</div>
				<div class="entete_action">
					<div class="entete_action_texte" onClick="changer_panneau()">Mon profil</div>
					<img class="entete_action_image" src="img/utilisateur.png" onClick="changer_panneau()"/>
				</div>
			</header>
		</div>
	);
}

export default BarreSupAccueil;
