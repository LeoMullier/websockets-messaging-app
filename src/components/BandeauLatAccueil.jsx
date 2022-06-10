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


// Fonction principale
const BandeauLatAccueil = () => {
	// Définition de toutes les variables états
	const [etatTmp, setetatTmp] = React.useState({
		idClientTmp: '',
		tokenClientTmp: ''
	});


	// Fonction de soumission du formulaire
	async function actionValiderFormulaire (event)
	{
		event.preventDefault();


		// Envoi de la requête
		const json = JSON.stringify({ login: etatTmp.loginTmp, mdp: etatTmp.mdpTmp });
		const res = await axios.post('http://localhost:8080/user/test2', json, {
			headers: {
				'Content-Type': 'application/json'
			}
		})
		.then((res) => {
			// Réception de la réponse du serveur
			localStorage.setItem("apiData1", JSON.stringify(res.data));
		});
			
		var data = JSON.parse(localStorage.getItem("apiData1"));
		if (data == "")
		{

		} else {
			alert(data.tokenClient)
			document.cookie = "idclient=" + data.idClient + "; expires=Mon, 20 Sep 2022 12:00:00 UTC";
			document.cookie = "tokenclient=" + data.tokenClient + "; expires=Mon, 20 Sep 2022 12:00:00 UTC";
			alert(document.cookie)
			window.location.href(accueil)
		}
			
		
	}


	// Fonctions de mise à jour des états
	const actionChangerEtatIdClient = (valeur) => {
		setetatTmp({
			...etatTmp,
			idClientTmp: valeur
		});
	}

	const actionChangerEtatTokenClient = (valeur) => {
		setetatTmp({
			...etatTmp,
			tokenClientTmp: valeur
		});
	}

	

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




