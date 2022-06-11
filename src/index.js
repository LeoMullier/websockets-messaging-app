// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : index.js                                                                            //
// Description : Script JS central pour afficher les bons composants en fonction des URL                //
// Date de dernière mise à jour : 08/06/2022                                                            //
// ==================================================================================================== //

// Importation des ressources
import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import * as serviceWorker from "./serviceWorker";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";


// Importations des composants de barres supérieures
import {
	BarreSupLogin,
	BarreSupAccueil,
} from "./components";


// Importation des composants de contenus
import {
	ContenuLogin,
	ContenuAccueil,
} from "./components";


// Importations des composants de bandeaux latéraux
import {
	BandeauLatLogin,
	BandeauLatAccueil,
} from "./components";


// Methode render pour afficher les composants
ReactDOM.render(
	<Router>
		{/* Affichage de la barre supérieure */}
		<Routes>
			<Route exact path="/bienvenue" element={<BarreSupLogin />} />
			<Route exact path="/accueil" element={<BarreSupAccueil />} />
		</Routes>

		{/* Affichage du contenu de la page */}
		<Routes>
			<Route exact path="/bienvenue" element={<ContenuLogin />} />
			<Route exact path="/accueil" element={<ContenuAccueil />} />
		</Routes>

		{/* Affichage de la bandeaux latéraux */}
		<Routes>
			<Route exact path="/bienvenue" element={<BandeauLatLogin />}/>
			<Route exact path="/accueil" element={<BandeauLatAccueil />} />
		</Routes>
	</Router>,

	// Composants à insérer dans le div root de index.html
	document.getElementById("root")
);

serviceWorker.unregister();
