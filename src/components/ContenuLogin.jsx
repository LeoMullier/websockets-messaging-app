// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : ContenuLogin.jsx                                                                    //
// Description : Script JS pour afficher le contenu lorsqu'on est sur la phase de login                 //
// Date de dernière mise à jour : 06/06/2022                                                            //
// ==================================================================================================== //


//Importations
import React from "react";
import { NavLink } from "react-router-dom";

//Fonction principale de render
function ContenuLogin() {
	return (
		<div className="contenulogin">
			<main id="corps">
				{/* Navigation dans les rubriques */}
				<nav>
					<p class="titre1">
						Liste des utilisateurs</p>
					<p class="titre2 sans_curseur">
						Liste des conversations</p>
				</nav>

				{/* Contenu principal */}
				<article>
					<p class="texte">
						Bienvenue sur l'interface administrateur de notre Utilitaire Textuel pour la Communication (ou plus communément appelé UTC) ! Cet espace est réservé aux utilisateurs avec des authorisations avancées afin de maintenir le bon fonctionnement du service.
						<br />
						<br />
						Afin de répondre à vos besoins et de nous adapter à vos préférences, nous vous proposons 2 méthodes de discussion : la discussion instantanée (les messages sont envoyés uniquement aux utilisateurs connectés et sont éphémaires) ou la conversation asynchrone (les messages sont sauvegardés dans le fil de discussion et sont persistants).
						<br />
						<br />
						Pour accéder aux options avancées de configuration de notre messagerie, n'attendez plus et authentifiez-vous sur le bandeau juste à droite !
					</p>
				</article>
			</main>
		</div>
	);
}

export default ContenuLogin;
