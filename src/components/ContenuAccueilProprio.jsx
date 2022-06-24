// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : ContenuAccueilProprio.jsx                                                           //
// Description : Script JS pour afficher le contenu lorsqu'on est sur la liste des canaux propriétaires //
// Date de dernière mise à jour : 24/06/2022                                                            //
// ==================================================================================================== //


// Importations
import React from 'react';
import axios from 'axios';
import ReactHtmlParser from 'react-html-parser';
import scriptJS from "./script.jsx";
import DOMPurify from "dompurify";


// Classe principale
export default class ContenuAccueilProprio extends React.Component{
	// Constructeur et états
	constructor(props) 
	{
		super(props);
		this.state = 
		{
			lignesTableauProprio: '',
			objJS : new scriptJS()
		};
	}


	// Méthode à l'ouverture du composant
	async componentDidMount() 
	{
		// Importation de la feuille CSS
		var link = document.createElement('link');
        link.rel = 'stylesheet';
        link.type = 'text/css';
        link.href = '../css/style.css';
        document.getElementsByTagName('HEAD')[0].appendChild(link);


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
		


		// Requête pour récupérer les infos utilisateurs à afficher		
		var json = JSON.stringify({ idClient: idClientTmp, tokenClient: tokenClientTmp });
		var res = await axios.post('http://localhost:8080/user/contenu_accueil/canauxproprio', json, {
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
			window.location.assign("../bienvenue")
		} else {
			this.setState({ lignesTableauProprio: data.l0 })
			//PlisteCanaux = data
			//console.log("data : " + data)
			//alert(this.state.lignesTableauProprio)
		}

		const script = document.createElement("script");
		script.src = "../js/script.js";
		script.async = true;
		document.body.appendChild(script);
	}


	// Méthode à la fermeture du composant
	async componentWillUnmount() 
	{
		
	}


	// Fonction de render  
	render() {
		// Changement du titre de la page
		document.title = "UTC - Accueil"

		// Portion du code HTML retournée
		return (
			<div className="contenulogin">
				<main id="corps">
					<nav>
						<a href="../accueil">
							<p class="titre2">
								Tous mes canaux</p></a>
						<a href="proprio">
							<p class="titre1">
								Canaux propriétaires</p></a>
						<a href="invites">
							<p class="titre2">
								Canaux invités</p></a>
					</nav>

					<article>
						<p class="texte">
							Vous vous trouvez actuellement sur votre espace principal de messagerie UTC. Nous vous présentons ci-dessous la liste des canaux dont vous êtes actuellement le propriétaire. Cliquez simplement sur le canal de votre choix pour y entrer.
						</p>
						<table class="table table-striped" id="example" style={{width: 100 + '%'}}>
						<thead>
								<tr>
									<th style={{width: 100 + 'px'}}>n°</th>
									<th>Description</th>
									<th style={{width: 200 + 'px'}}>Date de création</th>
								</tr>
							</thead>
							<tbody dangerouslySetInnerHTML={{__html: this.state.lignesTableauProprio}}>
								
							</tbody>
						</table>
						<div class="action_div">
							<img class="action_img" src="/img/ajouter.png" onClick={() => window.location.assign('../nouveau-canal')} />
							<span class="action_span" style={{color: 'white'}} onClick={() => window.location.assign('../nouveau-canal')}>Créer une nouvelle conversation</span>
						</div>
					</article>
				</main>
			</div>
		);
	}
}