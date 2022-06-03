/*============================================================*/
/*                     FICHIER JAVASCRIPT                     */
/*============================================================*/
/* Nom     : script.js                                        */
/* Projet  : SR03 - Devoir 2                                  */
/* Auteurs : B. Cosson; C. Martinet; L. Mullier               */
/*============================================================*/


/*========== INITIALISATIONS ==========*/
var panneau_etat = 'a';
var retour_etat = "off";
var panneau = document.getElementById("panneau")
var corps = document.getElementById("corps")


/*========== VERIFICATION DES CARACTERES SAISIS (FOURNIE DANS LE TD) ==========*/
function valid()
{
    //liste caractère spéciaux
    var format = /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;

    var name = document.getElementById("name");
    var firstname = document.getElementById("firstname");

    if(format.test(name.value))
    {
        alert("Le nom contient des caractères spéciaux");
    }
    else
    {
        alert("Le nom est correct");
    }
}


/*========== MISE EN PLACE DU TABLEAU TRIBALE (FOURNIE DANS LE TD) ==========*/
$(document).ready(function() {
    $('#example').DataTable({
        "language": {
            "sProcessing":     "Traitement en cours...",
            "sSearch":         "Rechercher&nbsp;:",
            "sLengthMenu":     "Afficher _MENU_ &eacute;l&eacute;ments",
            "sInfo":           "Affichage de l'&eacute;l&eacute;ment _START_ &agrave; _END_ sur _TOTAL_ &eacute;l&eacute;ments",
            "sInfoEmpty":      "Affichage de l'&eacute;l&eacute;ment 0 &agrave; 0 sur 0 &eacute;l&eacute;ment",
            "sInfoFiltered":   "(filtr&eacute; de _MAX_ &eacute;l&eacute;ments au total)",
            "sInfoPostFix":    "",
            "sLoadingRecords": "Chargement en cours...",
            "sZeroRecords":    "Aucun &eacute;l&eacute;ment &agrave; afficher",
            "sEmptyTable":     "Aucune donn&eacute;e disponible dans le tableau",
            "oPaginate": {
                "sFirst":      "Premier",
                "sPrevious":   "Pr&eacute;c&eacute;dent",
                "sNext":       "Suivant",
                "sLast":       "Dernier"
            },
            "oAria": {
                "sSortAscending":  ": activer pour trier la colonne par ordre croissant",
                "sSortDescending": ": activer pour trier la colonne par ordre d&eacute;croissant"
            },
            "select": {
                    "rows": {
                        "_": "%d lignes sélectionnées",
                        "0": "Aucune ligne sélectionnée",
                        "1": "1 ligne sélectionnée"
                    } 
            }
        }
    });
} );


/*========== AFFICHER RETOUR TOUS MES CANAUX ==========*/
function afficher_retour_tmc(){
    if (retour_etat = "off")
    {
        let entete_image = document.getElementById("entete_image");
        let entete_titre = document.getElementById("entete_titre");
        let entete = document.getElementById("entete");
        let entete_titre_width = entete_titre.offsetWidth;
        entete.onclick = function() {window.location.assign("utilisateur_tous.html")};
        entete_image.src = "img/retour.png";
        entete_titre.style.width = entete_titre_width + 'px';
        entete_titre.innerHTML = `Retour`;
        retour_etat = "on";
    }
}


/*========== MASQUER RETOUR TOUS MES CANAUX ==========*/
function masquer_retour_tmc(){
    if (retour_etat = "on")
    {
        let entete_image = document.getElementById("entete_image");
        let entete_titre = document.getElementById("entete_titre");
        entete_image.src = "img/logo.png";
        entete_titre.style.width = "unset";
        entete_titre.innerHTML = `UTC - Utilitaire Textuel pour la Communication`;
        retour_etat= "off";
    }
}


/*========== AFFICHER RETOUR LOGIN ==========*/
function afficher_retour_login(){
    if (retour_etat = "off")
    {
        let entete_image = document.getElementById("entete_image");
        let entete_titre = document.getElementById("entete_titre");
        let entete = document.getElementById("entete");
        let entete_titre_width = entete_titre.offsetWidth;
        entete_image.src = "img/logout.png";
        entete_titre.style.width = entete_titre_width + 'px';
        entete_titre.innerHTML = `Se déconnecter`;
        retour_etat = "on";
    }
}


/*========== MASQUER RETOUR LOGIN ==========*/
function masquer_retour_login(){
    if (retour_etat = "on")
    {
        let entete_image = document.getElementById("entete_image");
        let entete_titre = document.getElementById("entete_titre");
        entete_image.src = "img/logo.png";
        entete_titre.style.width = "unset";
        entete_titre.innerHTML = `UTC - Utilitaire Textuel pour la Communication`;
        retour_etat= "off";
    }
}

/*========== MASQUER PANNEAU LATTERAL ==========*/
function masquer_panneau()
{
    panneau.style.visibility = "hidden";
    corps.style.right = "0px";
    panneau_etat = 'm';
    console.log("(!) Disparition du panneau lattéral réussie");
}


/*========== AFFICHER PANNEAU LATTERAL ==========*/
function afficher_panneau()
{
    panneau.style.visibility = "visible";
    corps.style.right = "394px";
    panneau_etat = 'a';
    console.log("(!) Apparition du panneau lattéral réussie");
}


/*========== PANNEAU LATTERAL DYNAMIQUE ==========*/
function changer_panneau()
{
	if (panneau_etat == 'a')
    {
        masquer_panneau();
    }
    else
    {
        afficher_panneau();
    }
}


/*========== POSITIONNEMENT EN BAS DE LA CONVERSATION ==========*/
function scroll_bas()
{
    let conversation_hauteur = document.getElementById("conversation").clientHeight;
    //console.log("hauteur", hauteur_temp);
    corps.scrollTop += conversation_hauteur;
    //console.log("Scroll :", document.getElementById("main").scrollTop);

}


/*========== LARGEUR MAX DE LA ZONE DE SAISIE D'UN MESSAGE ==========*/
var editable
editable = document.getElementById("message_saisie_bulle")
// console.log(document.getElementById("msg_bas"))
$(document).keydown(function(event) {
    // console.log(editable)
    if(document.activeElement==editable){
        if ((event.ctrlKey || event.metaKey) && event.keyCode == 86) {
            setTimeout(()=>{
                scroll_bas()
            },10)
        }
        scroll_bas()
    }

})


/*========== AFFICHER LE MESSAGE EN LIGNE DANS LE TABLEAU ==========*/
function afficher_enligne_oui(id){
    let cellule_droite = document.getElementById("participant_n_"+id);
    cellule_droite.innerHTML = "est actuellement en ligne";
}


/*========== AFFICHER LE MESSAGE N'EST PAS EN LIGNE DANS LE TABLEAU ==========*/
function afficher_enligne_non(id){
    let cellule_droite = document.getElementById("participant_n_"+id);
    cellule_droite.innerHTML = "est déconnecté";
}


/*========== MASQUER LE MESSAGE EN LIGNE DANS LE TABLEAU ==========*/
function masquer_enligne(id, chaine){
    let cellule_droite = document.getElementById("participant_n_"+id);
    cellule_droite.innerHTML = chaine;
}


/*========== AFFICHER LE TITRE DU CANAL EN VERT DANS LE TABLEAU ==========*/
function titre_en_vert(id){
    let tab_titre = document.getElementById("tab_t"+id);
    tab_titre.style.color = "#9ae69a";

    let tab_ligne = document.getElementById("tab_l"+id);
    tab_ligne.onclick = function() {window.location.assign("canal.html")};
}


/*========== MASQUER LE TITRE DU CANAL EN VERT DANS LE TABLEAU ==========*/
function titre_en_vert_fin(id)
{
    let tab_titre = document.getElementById("tab_t"+id);
    tab_titre.style.color = "white";
}


/*========== AFFICHER LES BOUTONS D'EDITION ==========*/
function afficher_boutons(id)
{
    let tab = document.getElementById("example")
    let tab_cellule = document.getElementById("tab_c"+id)
    let corps = document.getElementById("corps")
    let hauteur = tab_cellule.offsetHeight
    let largeur = tab_cellule.offsetWidth
    let pos_x = tab_cellule.offsetLeft - 50
    let pos_y = tab_cellule.offsetTop + tab.offsetTop + (hauteur - 50) / 2
    // console.log("cellule_top:"+tab_cellule.offsetTop)
    // console.log("tab_top:"+tab.offsetTop)
    // console.log("hauteur:"+hauteur)
    // console.log("largeur:"+largeur)
    // console.log("pos_x:"+pos_x)
    // console.log("pos_y:"+pos_y)
    
    tab_cellule.insertAdjacentHTML('afterbegin', "<img id='bouton_editer_" +id+ "' src='img/editer.png' onclick='editer_canal(" + id + ")' style='position: absolute; left: "+ (pos_x+largeur-65) +"px; top: "+ pos_y +"px; height: 50px; cursor: pointer;'>")
    tab_cellule.insertAdjacentHTML('afterbegin', "<img id='bouton_supprimer_" +id+ "' src='img/logout.png' onclick='supprimer_canal(" + id + ")' style='position: absolute; left: "+ (pos_x+largeur) +"px; top: "+ pos_y +"px; height: 50px; cursor: pointer;'>")
}


/*========== MASQUER LES BOUTONS D'EDITION ==========*/
function masquer_boutons(id)
{
    let bouton_e = document.getElementById("bouton_editer_"+id)
    let bouton_s = document.getElementById("bouton_supprimer_"+id)
    bouton_e.remove()
    bouton_s.remove()
}


/*========== ACTIVER LES BOUTONS D'ACTIONS ==========*/
function activer_boutons_actions()
{
    let img_activer = document.getElementById("action_img_activer")
    let img_desactiver = document.getElementById("action_img_desactiver")
    let img_supprimer = document.getElementById("action_img_supprimer")

    let span_activer = document.getElementById("action_span_activer")
    let span_desactiver = document.getElementById("action_span_desactiver")
    let span_supprimer = document.getElementById("action_span_supprimer")

    img_activer.src = "/img/play.png"
    img_desactiver.src = "/img/pause.png"
    img_supprimer.src = "/img/logout.png"

    span_activer.style.color = "white"
    span_desactiver.style.color = "white"
    span_supprimer.style.color = "white"
}


/*========== ACTION ACTIVER UN UTILISATEUR ==========*/
function ajouter_utilisateur()
{
    window.location.assign("liste-utilisateurs/ajouter")
}


/*========== ACTION ACTIVER UN UTILISATEUR ==========*/
function activer_utilisateur()
{
    let liste_utilisateurs = document.getElementsByName('selection');
    let utilisateur_selectionne = 0
    for (let ligne of liste_utilisateurs)
    {
        if (ligne.checked)
        {
            utilisateur_selectionne = ligne.value
        }
    }
    if (utilisateur_selectionne != 0)
    {
        window.location.assign("liste-utilisateurs/activer/" + utilisateur_selectionne)
    }
}


/*========== ACTION DESACTIVER UN UTILISATEUR ==========*/
function desactiver_utilisateur()
{
    let liste_utilisateurs = document.getElementsByName('selection');
    let utilisateur_selectionne = 0
    for (let ligne of liste_utilisateurs)
    {
        if (ligne.checked)
        {
            utilisateur_selectionne = ligne.value
        }
    }
    if (utilisateur_selectionne != 0)
    {
        window.location.assign("liste-utilisateurs/desactiver/" + utilisateur_selectionne)
    }
}


/*========== ACTION SUPPRIMER UN UTILISATEUR ==========*/
function supprimer_utilisateur()
{
    let liste_utilisateurs = document.getElementsByName('selection');
    let utilisateur_selectionne = 0
    for (let ligne of liste_utilisateurs)
    {
        if (ligne.checked)
        {
            utilisateur_selectionne = ligne.value
        }
    }
    if (utilisateur_selectionne != 0)
    {
        window.location.assign("liste-utilisateurs/supprimer/" + utilisateur_selectionne)
    }
}


/*========== DECLENCHEMENT AU CHARGEMENT ==========*/
window.onload = function()
{
    scroll_bas();
}