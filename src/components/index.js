// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : index.js                                                                            //
// Description : Script JS de déclaration et d'export des différents composants dans le dossier         //
// Date de dernière mise à jour : 24/06/2022                                                            //
// ==================================================================================================== //

// ========== Barres supérieures ==========//
// Page de login
export { default as BarreSupLogin } from "./BarreSupLogin";
// Page d'accueil
export { default as BarreSupAccueil } from "./BarreSupAccueil";
// Page d'accueil proprio et invités
export { default as BarreSupAccueilProprioInvites } from "./BarreSupAccueilProprioInvites";
// Page de conversation
export { default as BarreSupCanal } from "./BarreSupCanal";


// ========== Contenus ========== //
// Page de login
export { default as ContenuLogin } from "./ContenuLogin";
// Page d'accueil
export { default as ContenuAccueil } from "./ContenuAccueil";
// Page d'accueil proprio
export { default as ContenuAccueilProprio } from "./ContenuAccueilProprio";
// Page d'accueil invités
export { default as ContenuAccueilInvites } from "./ContenuAccueilInvites";
// Page de création d'un nouveau canal
export { default as ContenuCreerCanal } from "./ContenuCreerCanal";
// Page de canal
export { default as ContenuCanal } from "./ContenuCanal";


// ========== Bandeau lattéral ========== //
// Page de login
export { default as BandeauLatLogin } from "./BandeauLatLogin";
// Page d'accueil
export { default as BandeauLatAccueil } from "./BandeauLatAccueil";
// Page de conversation
export { default as BandeauLatCanal } from "./BandeauLatCanal";