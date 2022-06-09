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


// Fonction principale
const BandeauLatLogin = () => {
	// Définition de toutes les variables états
	const [etatTmp, setetatTmp] = React.useState({
		loginTmp: '',
		mdpTmp: ''
	});


	// Fonction de soumission du formulaire
	async function actionValiderFormulaire ()
	{
		event.preventDefault();
		const json = JSON.stringify({ login: etatTmp, mdp: "utc" });
		const res = await axios.post('http://localhost:8080/user/test2', json, {
		headers: {
			// Overwrite Axios's automatically set Content-Type
			'Content-Type': 'application/json'
		}
		})
		.then((res) => {
			// Res.data is the response from your server
			localStorage.setItem("apiData1", JSON.stringify(res.data));
			 
			});
			
		   var data = JSON.parse(localStorage.getItem("apiData1"));
			alert(data.nom)
			
			/*
alert('retout:' + res.data.data)
		res.data.data; // '{"answer":42}'
		res.data.headers['Content-Type']; // 'application/json',
		


		/*
		// Préparation des données Json
		const donneesJson = new FormData();
		donneesJson.append("login", etatTmp.loginTmp)
		donneesJson.append("mdp", etatTmp.mdpTmp)
		
		
		// Envoi de la requête vers serveur Rest
		try {
			const response = await fetch('http://localhost:8080/user/test2',
			{
				method: 'POST',
				headers: { 'Accept': 'application/json', 'Content-Type': 'application/json' },
				body:JSON.stringify({login: etatTmp.loginTmp, mdp: etatTmp.mdpTmp})
			});
			const result = await response.json();

			// Action à la réception de la réponse
			//alert("test" + result)
			//console.log(result);

		} catch(error) {
			// Gestion des exceptions
			alert("(!) Désolé, nous n'avons pu faire aboutir l'envoi de la requête ou la réception de sa réponse. Veuillez réessayer ultérieurement. Message d'erreur associé : " + error)
		}*/
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




