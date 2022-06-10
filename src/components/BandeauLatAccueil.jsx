// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : BandeauLatLogin.jsx                                                                 //
// Description : Script JS pour afficher le bandeau latéral lorsqu'on est sur la phase de login         //
// Date de dernière mise à jour : 09/06/2022                                                            //
// ==================================================================================================== //


// Importations
import React from 'react';
import axios from 'axios';
import { useState } from 'react';
//import { Rectangle } from '../authentification.js';
//import utilisateurAuthentifie from '../index.js'

function getCookie(cname) {
	let name = cname + "=";
	let decodedCookie = decodeURIComponent(document.cookie);
	let ca = decodedCookie.split(';');
	for(let i = 0; i <ca.length; i++) {
	  let c = ca[i];
	  while (c.charAt(0) == ' ') {
		c = c.substring(1);
	  }
	  if (c.indexOf(name) == 0) {
		return c.substring(name.length, c.length);
	  }
	}
	return "";
  }

// Fonction principale
const BandeauLatAccueil = () => {
	let idClientTmp = getCookie("idClient")
	var tokenClientTmp = getCookie("tokenClient")
	alert("cookie id : " + idClientTmp)
	alert("cookie token : " + tokenClientTmp)
	

	// Fonction de render
	return (
		<div className="bandeaulatlogin">
			<aside id="panneau">
				<div class="panneau_corps">
					<center>
						<img class="panneau_image" src="https://picsum.photos/500"/>
						<br />
						<span class="titre1">
							Léo Mullier
						</span>
						<br />leo.mullier@etu.utc.fr
						<br />Inscrit sur 6 conversations
						<br />
						<br />
						<br /><font color="#33cc33">●</font> en ligne
						<br /><font color="orange">●</font> administrateur
					</center>
				</div>
				
				<footer class="panneau_pied">
					<p>
						&lt;/&gt; Développé pour SR03 en P2022 par
						<br />Bastian Cosson, Léo Mullier, Cédric Martinet
					</p>
				</footer>
				<script>
					
						actionChangerEtatIdClient("bonjour");
						alert(etatTmp.idClientTmp);
					
				</script>
			</aside>
		</div>
	)
};


// Export vers le reste du projet
export default BandeauLatAccueil;




