// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : BarreSupAccueilProprioInvites.jsx                                                   //
// Description : Script JS pour afficher la barre supérieure lorsqu'on est sur la liste des canaux PouI //
// Date de dernière mise à jour : 24/06/2022                                                            //
// ==================================================================================================== //


//Importations
import React from "react";
import axios from 'axios';
import parse from "html-react-parser";
import { NavLink } from "react-router-dom";
import scriptJS from "./script.jsx";


// Classe principale
export default class BarreSupAccueilProprioInvites extends React.Component{
	// Constructeur et états
	constructor(props) 
	{
		super(props);
	}


	// Méthode à l'ouverture du composant
	async componentDidMount() 
	{
		
	}


	// Méthode à la fermeture du composant
	async componentWillUnmount() 
	{
		
	}


	// Fonction de render 
	render()
	{
		// Test de l'importation des fonctions JS
		var objJS = new scriptJS();
    	//objJS.tester();


		// Portion du code HTML retournée
		return (
			<div className="barresuplogin">
				<header>
					<div class="entete" id="entete" onClick={() => {objJS.deconnecter_utilisateur()}} onMouseEnter={() => {objJS.afficher_retour_login()}} onMouseLeave={() => {objJS.masquer_retour_login()}}>
						<img class="entete_image" id="entete_image" src="../img/logo.png"/>
						<p class="entete_titre" id="entete_titre">
							UTC - Utilitaire Textuel pour la Communication
						</p>
					</div>
					<div class="entete_action">
						<div class="entete_action_texte" onClick={() => {objJS.changer_panneau()}}>Mon profil</div>
						<img class="entete_action_image" src="../img/utilisateur.png" onClick={() => {objJS.changer_panneau()}}/>
					</div>
				</header>
			</div>
		)
	}
}
