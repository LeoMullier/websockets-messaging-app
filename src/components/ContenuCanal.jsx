// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : ContenuCanal.jsx                                                                    //
// Description : Script JS pour afficher le contenu lorsqu'on est sur la phase de canal                 //
// Date de dernière mise à jour : 23/06/2022                                                            //
// ==================================================================================================== //


// Importations
import React from 'react';
import axios from 'axios';
import ReactHtmlParser from 'react-html-parser';
import scriptJS from "./script.jsx";
import DOMPurify from "dompurify";


// Classe principale
export default class ContenuCanal extends React.Component{
	// Constructeur et états
	constructor(props) 
	{
		super(props);
		this.state = 
		{
			titre: '',
			description: '',
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
		// Changement du titre de la page
		document.title = "UTC - Conversation"
		alert("contenu canal")

		// Portion du code HTML retournée
		return (
			<div className="contenulogin">
				<main id="corps">
					<div id="conversation">
						<p class="message_infos_droite">
							<b>Moi</b> 10:54
						</p>
						<div class="message_droite">Salut c'est moi</div>

						<p class="message_infos_gauche">
							<b>Benjamin</b> 10:56
						</p>
						<div class="message_gauche">Salut Léo, comment vas-tu ?</div>
						
						<p class="message_infos_droite">
							<b>Moi</b> 11h00
						</p>
						<div class="message_droite">Plutôt bien et toi ? Quoi de neuf</div>
						
						<p class="message_infos_gauche">
							<b>Benjamin</b> 11h03
						</p>
						<div class="message_gauche">Super bien, on avance dans nos projets perso</div>
						
						<p class="message_infos_droite">
							<b>Moi</b> 11h04
						</p>
						<div class="message_droite">Trop bien, je viens de finir le projet de SR03, ça rend bien notre interface</div>
						
						<div class="message_droite">Et en plus on a fait un jeu de mot avec "UTC" 😅</div>
						
						<div class="message_droite">Qu'en penses tu ?</div>

						<p class="message_infos_gauche">
							<b>Benjamin</b> 12:54
						</p>
						<div class="message_gauche">Ah oui en effet bien joué!</div>
						
						<p class="message_infos_gauche">
							<b>Noé</b> 12:59
						</p>
						<div class="message_gauche">Bonjour les amis, je suis ravi de vous retrouver</div>
						
						<p class="message_infos_droite">
							<b>Moi</b> 12:59
						</p>
						<div class="message_droite">Moi aussi, comment vas-tu de si bon matin ?</div>
						
						<p class="message_infos_gauche">
							<b>Noé</b> 12:59
						</p>
						<div class="message_gauche">Parfaitement bien, merci et toi?</div>
						
						<p class="message_infos_gauche">
							<b>Justine</b> 14:28
						</p>
						<div class="message_gauche">Hello tout le monde, je suis nouveau ici</div>
						
						<p class="message_infos_droite">
							<b>Moi</b> maintenant
						</p>
						<div id="message_saisie">
							<div class="message_saisie_bulle" id="message_saisie_bulle" contenteditable="true" ></div>
						</div>
					</div>
				</main>
			</div>
		);
	}
}