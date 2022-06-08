// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : BandeauLatLogin.jsx                                                                 //
// Description : Script JS pour afficher le bandeau latéral lorsqu'on est sur la phase de login         //
// Date de dernière mise à jour : 06/06/2022                                                            //
// ==================================================================================================== //


//Importations
import React from "react";
import ReactDOM from 'react-dom';
import { NavLink } from "react-router-dom";
import App from "../App";

//Fonction principale de render
function BandeauLatLogin() {
	return (
		<div className="bandeaulatlogin">
			<aside id="panneau">
				<div class="panneau_corps">
					<center>
						{/* Image aléatoire */}
						<img class="panneau_image" src="https://picsum.photos/500"/>

						{/* Formulaire d'authentification */}
						<form onSubmit={ValiderFormulaireAuth}>
							<label>Email :</label>
							<br />
							<input type="text" class="login_champ" placeholder="Tapez ici..." required onChange={(e) => console.log(e)}/>
							<br />
							<br />
							<label>Mot de passe :</label>
							<br />
							<input type="password" class="login_champ" placeholder="Tapez ici..." required onChange={(e) => console.log(e)}/>
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
	);
}

export default BandeauLatLogin;
