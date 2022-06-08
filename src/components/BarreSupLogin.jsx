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
function BarreSupLogin() {
	return (
		<div className="barresuplogin">
			<header>
				<div class="entete sans_curseur" id="entete">
					{/* Logo de l'UTC */}
					<img class="entete_image sans_curseur" id="entete_image" src="/img/logo.png"/>

					{/* Titre de la messagerie */}
					<p class="entete_titre sans_curseur" id="entete_titre">
						UTC - Utilitaire Textuel pour la Communication
					</p>
				</div>

				{/* Bonton d'action à droite */}
				<div class="entete_action">
					<div class="entete_action_texte" onclick="changer_panneau()">Se connecter</div>
					<img class="entete_action_image" src="/img/login.png" onclick="changer_panneau()"/>
				</div>
			</header>
		</div>
	);
}

export default BarreSupLogin;
