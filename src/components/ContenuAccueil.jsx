// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : ContenuLogin.jsx                                                                    //
// Description : Script JS pour afficher le contenu lorsqu'on est sur la phase de login                 //
// Date de dernière mise à jour : 10/06/2022                                                            //
// ==================================================================================================== //


//Importations
import React from "react";
import { NavLink } from "react-router-dom";
//import * as myscript from "../../public/js/script"

//Fonction principale de render
function ContenuAccueil() {
	//document.title = "UTC - Accueil";

	return (
		<div className="contenulogin">
			<main id="corps">
				<nav>
					<a href="utilisateur_tous.html">
						<p class="titre1">
							Tous mes canaux</p></a>
					<a href="utilisateur_proprietaires.html">
						<p class="titre2">
							Canaux propriétaires</p></a>
					<a href="utilisateur_invites.html">
						<p class="titre2">
							Canaux invités</p></a>
				</nav>

				<article>
					<p class="texte">
						Vous vous trouvez actuellement sur votre espace principal de messagerie UTC. Nous vous présentons ci-dessous la liste des canaux auxquels vous participez, que ce soit ceux que vous avez créés ou ceux pour lesquels vous êtes invité. Cliquez simplement sur le canal de votre choix pour y entrer.
					</p>
					<table id="example" class="table table-striped" style={{width: 5 + '%'}}>
					<thead>
							<tr>
								<th style={{width: '50px'}}>n°</th>
								<th>Description</th>
								<th style={{width: '200px'}}>Date de création</th>
							</tr>
						</thead>
						<tbody>
							<tr id="tab_l1000">
								<td style={{border :'0px 1px 0px 0px dimgrey solid'}}>16</td>
								<td class="cellule_description" style={{border: '0px 1px 0px 0px dimgrey solid'}}><strong><span id="tab_t1000">Titre de la conversation</span><br />Léo Mullier</strong>, Bastian Cosson, Cédric Martinet, Ahamed Lounis, ...</td>
								<td>28/03/2022</td>
							</tr>	
						</tbody>
					</table>
				</article>
			</main>
		</div>
	);
}

export default ContenuAccueil;
