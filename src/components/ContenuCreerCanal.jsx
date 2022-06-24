// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : ContenuCreerCanal.jsx                                                               //
// Description : Script JS pour afficher le contenu lorsqu'on souhaite créer un nouveau canal           //
// Date de dernière mise à jour : 24/06/2022                                                            //
// ==================================================================================================== //


// Importations
import React from 'react';
import axios from 'axios';
import ReactHtmlParser from 'react-html-parser';
import scriptJS from "./script.jsx";
import DOMPurify from "dompurify";


// Classe principale
export default class ContenuCreerCanal extends React.Component{
	// Constructeur et états
	constructor(props) 
	{
		super(props);
		this.state = 
		{
			participants: '',
			idClient: '',
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
				this.setState({ idClient: idClientTmp })
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
		var json = JSON.stringify({ idClient: idClientTmp, tokenClient: tokenClientTmp });
		var res = await axios.post('http://localhost:8080/user/contenu_creer_canal/liste_utilisateurs', json, {
			headers: {
				'Content-Type': 'application/json'
			}
		})
		.then((res) => {
			// Réception de la réponse du serveur
			localStorage.setItem("dataReq", JSON.stringify(res.data));
		});
			
		let data = JSON.parse(localStorage.getItem("dataReq"));
		if (/*data.l10 != "valide" ||*/ data == "" || data.l0 == "")
		{
			// Actions à réaliser pour une réponse négative
			window.location.assign("accueil")
		} else {
			this.setState({ participants: data.l0 })
			//alert(this.state.participants)
		}


		const script = document.createElement("script");
		script.src = "/js/script.js";
		script.async = true;
		document.body.appendChild(script);

		objJS.scroll_bas()
	}


	// Méthode à la fermeture du composant
	async componentWillUnmount() 
	{
		
	}


	// Fonction de render  
	render() {
		// Changement du titre de la page
		document.title = "UTC - Nouveau canal"
		

		// Portion du code HTML retournée
		return (
			<div className="contenulogin">
				<main id="corps">
					<nav>
						<span class="titre1" style={{cursor: 'pointer'}} onClick={() => window.location.href='nouveau-canal'}>Ajouter un utilisateur</span>
					</nav>

					<article>
						<span class="texte">
							Si vous souhaitez insérer un nouveau canal de discusion dans l'application de messagerie UTC, veuillez remplir le formulaire ci-dessous avec les informations souhaitées. Vous serez considéré(e) comme l'utilisateur ou utilisatrice propriétaire du canal. Lorsque vous validerez ensuite cette étape, le système vérifiera automatiquement qu'il n'y a pas de problème dans la saisie des informations, ni de conflit avec la base de conversations déjà existantes.
						</span>
						<form class="texte" action="http://localhost:8080/user/nouveau-canal" method="post">
							<label>Titre de la conversation</label>
							<br />
							<input class="login_champ" type="text" name="titre" required/>
							<br />
							<br />
							<label>Description de la conversation</label>
							<br />
							<input class="login_champ" type="text-area" name="description" required/>
							<br />
							<br />
							<label>Utilisateurs invités à échanger sur ce groupe<br /><i>Utilisez clic + ctrl pour en sélectionner plusieurs</i></label>
							<br />
							<select class="login_champ" id="invites" style={{height: 200 + 'px'}} name="invites" multiple="multiple" required dangerouslySetInnerHTML={{__html: this.state.participants}}>
								
							</select>
							<br />
							<br />
							<label>Date de fermeture prévue pour le canal</label>
							<br />
							<input class="login_champ" type="datetime-local" name="duree" required/>
							<br />
							<br />
							<input type="hidden" name="proprio" value={this.state.idClient} />
							<input class="login_valider" type="submit" />
						</form>
					</article>
				</main>
				<script>var objJS = new scriptJS();objJS.scroll_bas()</script>
			</div>
		);
	}
}