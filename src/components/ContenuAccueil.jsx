// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : ContenuLogin.jsx                                                                    //
// Description : Script JS pour afficher le contenu lorsqu'on est sur la phase de login                 //
// Date de dernière mise à jour : 11/06/2022                                                            //
// ==================================================================================================== //


// Importations
import React from 'react';
import axios from 'axios';
import parse from "html-react-parser";
import { ContenuAccueil } from '.';


// Classe principale
export default class ContenutAccueil extends React.Component{
	// Constructeur et états
	constructor(props) 
	{
		super(props);
		this.state = 
		{
			PlisteCanaux: '',
			PlisteProprio: [],
			Pliste3Invites: [],
			Phtml: '',
			IlisteCanaux: '',
			IlisteProprio: [],
			Iliste3Invites: [],
			Ihtml: '',

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


		// Initialisations
		var PlisteCanaux = ""
		var PlisteProprio = []
		var Pliste3Invites = []
		var Phtml = ""
		var IlisteCanaux = ""
		var IlisteProprio = []
		var Iliste3Invites = []
		var Ihtml = ""


		// Requête pour récupérer les infos utilisateurs à afficher		
		var json = JSON.stringify({ idClient: idClientTmp, tokenClient: tokenClientTmp });
		var res = await axios.post('http://localhost:8080/user/contenu_accueil/canalproprio', json, {
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
			//this.setState({ PlisteCanaux: data})
			PlisteCanaux = data
			console.log(data)
			//alert(this.state.PlisteCanaux[0].id)
		}


		// Pour chacun des canaux sur lesquels l'utilisateur est propriétaire
		for(let i = 0; i < PlisteCanaux.length; ++i)
		{
			console.log("i:" + i);
			// Requête pour récupérer l'utilisateur proprio de chaque canal à afficher	
			(async function () {
				console.log(PlisteCanaux[i])  	
				json = JSON.stringify({ idClient: idClientTmp, tokenClient: tokenClientTmp });
				res = await axios.post('http://localhost:8080/user/contenu_accueil/userproprio/'+PlisteCanaux[i].id, json, {
					headers: {
						'Content-Type': 'application/json'
					}
				})
				.then((res) => {
					// Réception de la réponse du serveur
					localStorage.setItem("dataReq", JSON.stringify(res.data));
					data = JSON.parse(localStorage.getItem("dataReq"));
					if (data == "" || data.status != "valide")
					{
						// Actions à réaliser pour une réponse négative
						window.location.assign("/bienvenue")
					} else {
						//this.state.PlisteProprio[i] = data.})
						console.log("proprio:", i, data.l0, data.l1)
						//console.log(data)
						//alert(this.state.PlisteCanaux[0].id


						
					}
				});
			})();


			// Requête pour récupérer 3 utilisateurs invités à afficher	pour chaque canal	
			(async function () {
				json = JSON.stringify({ idClient: idClientTmp, tokenClient: tokenClientTmp });
				res = await axios.post('http://localhost:8080/user/contenu_accueil/usersinvites/'+PlisteCanaux[i].id, json, {
					headers: {
						'Content-Type': 'application/json'
					}
				})
				.then((res) => {
					// Réception de la réponse du serveur
					localStorage.setItem("dataReq", JSON.stringify(res.data));
				});
					
				data = JSON.parse(localStorage.getItem("dataReq"));
				if (data == "" || data.status != "valide")
				{
					// Actions à réaliser pour une réponse négative
					window.location.assign("/bienvenue")
				} else {
					console.log("invites:" + i)
					console.log(data)
					let calcul = 1000+ PlisteCanaux[i].id
					Phtml = Phmtl + "<tr id='tab_l" + calcul + "'><td style='border :'0px 1px 0px 0px dimgrey solid''>16</td><td class='cellule_description' style={{border: '0px 1px 0px 0px dimgrey solid'}}><strong><span id='tab_t" + calcul + "'>"+  PlisteCanaux[i].titre + "</span><br />" + PlisteCanaux[i] + "</strong>, " + "ivites" + "</td><td>28/03/2022</td></tr>}"
				}
			})();
		}
		this.setState({ Phtml: Phtml})
	}


	// Fonction de render  
	render() {
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
								{parse(this.state.Phtml)}
							</tbody>
						</table>
					</article>
				</main>
			</div>
		);
	}
}