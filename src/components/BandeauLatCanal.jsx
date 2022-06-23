// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : BandeauLatCanal.jsx                                                                 //
// Description : Script JS pour afficher le bandeau lattéral lorsqu'on est sur une conversation         //
// Date de dernière mise à jour : 23/06/2022                                                            //
// ==================================================================================================== //


// Importations
import React from 'react';
import axios from 'axios';
import ReactHtmlParser from 'react-html-parser';
import scriptJS from "./script.jsx";
import DOMPurify from "dompurify";


// Classe principale
export default class BandeauLatCanal extends React.Component{
	// Constructeur et états
	constructor(props) 
	{
		super(props);
		this.state = 
		{
			titre: '',
			description: '',
			date: '',
			participants: '',
			objJS : new scriptJS()
		};
	}


	// Méthode à l'ouverture du composant
	async componentDidMount() 
	{
		// Test de l'importation des fonctions JS
		var objJS = new scriptJS();
    	//objJS.tester();
		

		// Lecture du cookie d'authentification (partie idClient)
		let name = "idClient" + "=";
		let decodedCookie = decodeURIComponent(document.cookie);
		let ca = decodedCookie.split(';');
		for(let i = 0; i <ca.length; i++) 
		{
			let c = ca[i];
			while (c.charAt(0) == ' ') 
			{
				c = c.substring(1);
			}
			if (c.indexOf(name) == 0) 
			{
				var idClientTmp = c.substring(name.length, c.length);
			}
		}	
		
		
		// Lecture du cookie d'authentification (partie tokenClient)
		name = "tokenClient" + "=";
		decodedCookie = decodeURIComponent(document.cookie);
		ca = decodedCookie.split(';');
		for(let i = 0; i <ca.length; i++) 
		{
			let c = ca[i];
			while (c.charAt(0) == ' ') 
			{
				c = c.substring(1);
			}
			if (c.indexOf(name) == 0) 
			{
				var tokenClientTmp = c.substring(name.length, c.length);
			}
		}


		// Actualisation du cookie d'authentification (partie idClient)
		let d = new Date();
		d.setTime(d.getTime() + (0.01*24*60*60*1000)); // Expiration dans 15min
		let expires = "expires="+ d.toUTCString();
		document.cookie = "idClient" + "=" + idClientTmp + ";" + expires + ";path=/";


		// Actualisation du cookie d'authentification (partie tokenClient)
		d = new Date();
		d.setTime(d.getTime() + (0.01*24*60*60*1000)); // Expiration dans 15min
		expires = "expires="+ d.toUTCString();
		document.cookie = "idToken" + "=" + tokenClientTmp + ";" + expires + ";path=/";


		// Initialisations
		const parametres = new URLSearchParams(window.location.search);
		const idCanal = parametres.get("id");


		// Requête pour vérifier la légitimité de l'utilisateur à accéder à ce canal et pour récupérer les infos du canal à afficher		
		var json = JSON.stringify({ idClient: idClientTmp, tokenClient: tokenClientTmp, idCanal: idCanal });
		var res = await axios.post('http://localhost:8080/user/bandeau_lat_canal?id=' + idCanal, json, {
			headers: {
				'Content-Type': 'application/json'
			}
		})
		.then((res) => {
			// Réception de la réponse du serveur
			localStorage.setItem("dataReq", JSON.stringify(res.data));
		});
			
		let data = JSON.parse(localStorage.getItem("dataReq"));
		if (data == "")
		{
			// Actions à réaliser pour une réponse négative
			window.location.assign("/bienvenue")
		} else {
			this.setState({ titre: data.l1 })
			this.setState({ description: data.l2 })
			this.setState({ date: data.l3 })
			this.setState({ participants: data.l4 })
		}

		const script = document.createElement("script");
		script.src = "/js/script.js";
		script.async = true;
		document.body.appendChild(script);
	}


	// Méthode à la fermeture du composant
	async componentWillUnmount() 
	{
		
	}


	// Fonction de render  
	render() {
		// Portion du code HTML retournée
		return (
			<aside id="panneau">
				<div class="panneau_corps">
					<br />
					<span class="titre1 centrer">
						{this.state.titre}
					</span>
					<br />
					{this.state.description} Ce canal a été ouvert le {this.state.date}.
					<br />
					<br />
					<br />
					<span class="titre2 centrer">
						Participants
					</span>
					<table class="sans_curseur" border="0" cellspacing="0" cellpadding="0" width="100%" dangerouslySetInnerHTML={{__html:this.state.participants}}>
						
					</table>
				</div>
				
				<footer class="panneau_pied">
					<p>
						&lt;/&gt; Développé pour SR03 en P2022 par
						<br />Bastian Cosson, Léo Mullier, Cédric Martinet
					</p>
				</footer>
			</aside>
		);
	}
}