// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : script.js                                                                           //
// Description : Script JS pour gérer les actions dynamiques sur les pages Web                          //
// Date de dernière mise à jour : 23/06/2022                                                            //
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

