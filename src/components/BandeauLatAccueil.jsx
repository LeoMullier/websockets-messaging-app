// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : BandeauLatLogin.jsx                                                                 //
// Description : Script JS pour afficher le bandeau latéral lorsqu'on est sur la phase de login         //
// Date de dernière mise à jour : 11/06/2022                                                            //
// ==================================================================================================== //


// Importations
import React from 'react';
import axios from 'axios';
import parse from "html-react-parser";


// Classe principale
export default class BandeauLatAccueil extends React.Component{
	// Constructeur et états
	constructor(props) 
	{
		super(props);
		this.state = 
		{
			prenom: '',
			nom: '',
			login: '',
			admin: '',
			enligne: '',
			nbcanaux: ''
		};
	}
	

	// Envoi de la requête
	async componentDidMount() 
	{
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


		// Requête pour récupérer les infos utilisateurs à afficher		
		const json = JSON.stringify({ idClient: idClientTmp, tokenClient: tokenClientTmp });
		const res = await axios.post('http://localhost:8080/user/bandeau_lat_accueil', json, {
			headers: {
				'Content-Type': 'application/json'
			}
		})
		.then((res) => {
			// Réception de la réponse du serveur
			localStorage.setItem("dataReq", JSON.stringify(res.data));
		});
			
		var data = JSON.parse(localStorage.getItem("dataReq"));
		if (data == "" || data.status != "valide")
		{
			// Actions à réaliser pour une réponse négative
			window.location.assign("/bienvenue")
		} else {
			// Actions à réaliser pour une réponse positive
			this.setState({ prenom: data.l0 })
			this.setState({ nom: data.l1 })
			this.setState({ login: data.l2 })
			this.setState({ admin: data.l3 })
			this.setState({ enligne: data.l4 })
			this.setState({ nbcanaux: data.l5 })
		}
		

		// Traitement de l'affichage de la ligne enligne
		if (this.state.enligne == 1)
		{
			this.setState({ enligne: "<br /><font color='#33cc33'>●</font> en ligne" })
		} else {
			this.setState({ enligne: "" })
		}

		// Traitement de l'affichage de la ligne administrateur
		if (this.state.admin == 1)
		{
			this.setState({ admin: "<br /><font color='orange'>●</font> administrateur" })
		} else {
			this.setState({ admin: "" })
		}
	}


	// Fonction de render  
	render() {
		return (
			<div className="bandeaulatlogin">
				<aside id="panneau">
					<div class="panneau_corps">
						<center>
							<img class="panneau_image" src="https://picsum.photos/500"/>
							<br />
							<span class="titre1">{this.state.prenom} {this.state.nom}</span>
							<br />{this.state.login}
							<br />Inscrit sur {this.state.nbcanaux} conversations
							<br />
							<br />
							{parse(this.state.enligne)}
							{parse(this.state.admin)}
						</center>
					</div>
					
					<footer class="panneau_pied">
						<p>
							&lt;/&gt; Développé pour SR03 en P2022 par
							<br />Bastian Cosson, Léo Mullier, Cédric Martinet
						</p>
					</footer>
				</aside>
			</div>
		)
	}
}