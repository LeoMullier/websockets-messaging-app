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
	Bienvenue,
	Navigation,
	Footer,
	Home,
	About,
	Contact,
	Blog,
	Posts,
	Post,
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
			<Route path="/bienvenue" element={<BarreSupLogin />} />
			<Route path="/accueil" element={<BarreSupAccueil />} />
		</Routes>

		{/* Affichage du contenu de la page */}
		<Routes>
			<Route path="/" element={<Home />} />
			{/* <Redriect from='/' to="/bienvenue" /> */}
			<Route path="/bienvenue" element={<ContenuLogin />} />
			<Route path="/accueil" element={<ContenuAccueil />} />

			<Route path="/about" element={<About />} />
			<Route path="/contact" element={<Contact />} />
			<Route path="/blog" element={<Blog />}/>
			<Route path="" element={<Posts />} />
			<Route path=":postSlug" element={<Post />} />
		</Routes>

		{/* Affichage de la bandeaux latéraux */}
		<Routes>
			<Route path="/bienvenue" element={<BandeauLatLogin />}/>
			<Route path="/accueil" element={<BandeauLatAccueil />} />
		</Routes>
	</Router>,

	// Composants à insérer dans le div root de index.html
	document.getElementById("root")
);

serviceWorker.unregister();
