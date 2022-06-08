// ==================================================================================================== //
// Projet : Utilitaire Textuel de Communication                                                         //
// Auteurs : Bastian COSSON, Léo MULLIER, Cédric Martinet                                               //
//                                                                                                      //
// Nom du fichier : BandeauLatLogin.jsx                                                                 //
// Description : Script JS pour afficher le bandeau latéral lorsqu'on est sur la phase de login         //
// Date de dernière mise à jour : 08/06/2022                                                            //
// ==================================================================================================== //


// Importations
import React from 'react';
import axios from 'axios';



const BandeauLatLogin = () => {
  
  const [formValue, setformValue] = React.useState({
    loginTmp: '',
    mdpTmp: ''
  });

  
    const handleSubmit = async() => {
		// store the states in the form data
		const loginFormData = new FormData();
		loginFormData.append("login", formValue.loginTmp)
		loginFormData.append("mdp", formValue.mdpTmp)
	  alert("hey")
		try {
		  // make axios post request
		  const response = await axios({
			method: "post",
			url: "http://localhost:8080/user/test2",
			data: loginFormData,
			headers: { "Content-Type": "application/json" },
		  });
		} catch(error) {
		  console.log(error)
		}
	  }
  

  const handleChange = (event) => {
    setformValue({
      ...formValue,
      [event.target.name]: event.target.value
    });
  }

  return (
	<div className="bandeaulatlogin">
				<aside id="panneau">
					<div class="panneau_corps">
					<center>
							{/* Image aléatoire */}
							<img class="panneau_image" src="https://picsum.photos/500"/>

							{/* Formulaire d'authentification */}
    <form onSubmit={handleSubmit}>
		<label>Email :</label>
		<br />
		<input type="text" class="login_champ" placeholder="Tapez ici..."  required onChange={handleChange}/>
		<br />
		<br />
		<label>Mot de passe :</label>
		<br />
		<input type="password" class="login_champ" placeholder="Tapez ici..."  required onChange={handleChange}/>
		<br />
		<br />
		<input type="checkbox" class="login_check" checked="checked"/> Se souvenir de moi
		<br />
		<input class="login_valider" type="submit" value="Se connecter"/>
      {/* <p>Login Form</p>
      <input
        type="email"
        name="email"
        placeholder="enter an email"
        value={formValue.email}
        onChange={handleChange}
      />
      <input
        type="password"
        name="password"
        placeholder="enter a password"
        value={formValue.password}
        onChange={handleChange}
      />
      <button
        type="submit"
      >
        Login
      </button> */}
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
  )
};

export default BandeauLatLogin;