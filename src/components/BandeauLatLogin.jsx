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


// Créer ou actualiser un cookie
function setCookie(cname, cvalue) {
	const d = new Date();
	d.setTime(d.getTime() + (0.01*24*60*60*1000));
	let expires = "expires="+ d.toUTCString();
	document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}


// Fonction principale
const BandeauLatLogin = () => {
	// Définition de toutes les variables états
	const [etatTmp, setetatTmp] = React.useState({
		loginTmp: '',
		mdpTmp: ''
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
			setCookie("idClient", data.idClient)
			setCookie("tokenClient", data.tokenClient)
			alert(document.cookie)
			window.location.assign("/accueil")
		}
			
		
	}


	// Fonctions de mise à jour des états
	const actionChangerEtatLogin = (event) => {
		setetatTmp({
			...etatTmp,
			loginTmp: event.target.value
		});
	}

	const actionChangerEtatMdp = (event) => {
		setetatTmp({
			...etatTmp,
			mdpTmp: event.target.value
		});
	}


	// Fonction de render
	return (
		<div className="bandeaulatlogin">
			<aside id="panneau">
				<div class="panneau_corps">
					<center>
					{/* Image aléatoire */}
					<img class="panneau_image" src="https://picsum.photos/500"/>

					{/* Formulaire d'authentification */}
					<form onSubmit={actionValiderFormulaire}>
						<label>Email :</label>
						<br />
						<input type="text" class="login_champ" placeholder="Tapez ici..."  required onChange={actionChangerEtatLogin}/>
						<br />
						<br />
						<label>Mot de passe :</label>
						<br />
						<input type="password" class="login_champ" placeholder="Tapez ici..."  required onChange={actionChangerEtatMdp}/>
						<br />
						<br />
						<input type="checkbox" class="login_check" checked="checked"/> Se souvenir de moi
						<br />
						<input class="login_valider" type="submit" value="Se connecter"/>
						</form>
					</center>
				</div>

				{/* Pied de page */}
				<footer class="panneau_pied">
					<p>
						&lt;/&gt; Développé pour SR03 en P2022 par
						<br />Bastian Cosson, Léo Mullier, Cédric Martinet
					</p>
				</footer>
			</aside>
		</div>
	)
};


// Export vers le reste du projet
export default BandeauLatLogin;




