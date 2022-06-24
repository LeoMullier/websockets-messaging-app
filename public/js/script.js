// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : script.js                                                                           //
// Description : Script JS pour gérer les actions dynamiques sur les pages Web                          //
// Date de dernière mise à jour : 24/06/2022                                                            //
// ==================================================================================================== //


/*========== INITIALISATIONS ==========*/
// var panneau_etat = 'a';
// var retour_etat = "off";
var panneau = document.getElementById("panneau")
var corps = document.getElementById("corps")


function titre_en_vert(id)
{
    let tab_titre = document.getElementById("tab_t"+id);
    tab_titre.style.color = "#9ae69a";

    //let tab_ligne = document.getElementById("tab_l"+id);
    //tab_ligne.onclick = function() {window.location.assign("canal.html")};
}


function titre_en_vert_fin(id)
{
    let tab_titre = document.getElementById("tab_t"+id);
    tab_titre.style.color = "white";
}


/*========== MISE EN PLACE DU TABLEAU TRIABLE (FONCTION FOURNIE DANS LE TD) ==========*/
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


/*========== AFFICHER LE MESSAGE EN LIGNE DANS LE TABLEAU ==========*/
function afficher_enligne_oui(id){
    let cellule_droite = document.getElementById("participant_n_"+id);
    cellule_droite.innerHTML = "est actuellement en ligne";
}


/*========== AFFICHER LE MESSAGE N'EST PAS EN LIGNE DANS LE TABLEAU ==========*/
function afficher_enligne_non(id){
    let cellule_droite = document.getElementById("participant_n_"+id);
    cellule_droite.innerHTML = "est déconnecté(e)";
}


/*========== MASQUER LE MESSAGE EN LIGNE DANS LE TABLEAU ==========*/
function masquer_enligne(id, chaine){
    let cellule_droite = document.getElementById("participant_n_"+id);
    cellule_droite.innerHTML = chaine;
}

/*========== POSITIONNEMENT EN BAS DE LA CONVERSATION ==========*/
function scroll_bas()
{
    alert("test scroll")
    let conversation_hauteur = document.getElementById("conversation").clientHeight;
    let corps = document.getElementById("corps")
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
                this.scroll_bas()
            },10)
        }
        this.scroll_bas()
    }

})
