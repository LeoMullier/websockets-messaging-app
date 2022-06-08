import { useState } from "react";

function App() {
	const React = require('react');
	const ReactDOM = require('react-dom');
	//const client = require('./client');


	const [login, setLogin] = useState("");
	const [mdp, setMdp] = useState("");


	let ValiderFormulaireAuth = async (e) => {
		e.preventDefault();
		console.log("(*) Je passe dans la fonction JS.");

		try {



			(async () => {
				const rawResponse = await fetch('http://localhost:8080/user/test2', {
					method: 'POST',
					headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json'
					},
					body: JSON.stringify({login: login, mdp: mdp})
				});
				const content = await rawResponse.json();

				console.log(content);
			})();



		} catch (err) {
			console.log(err);
		}
	};

	return (
		<div className="App">
			<form onSubmit={handleSubmit}>
				<input
				type="text" 
				value={login_}
				placeholder="login"
				onChange={(e) => setLogin(e.target.value)}
				/>
				<input
				type="text"
				value={mdp_}
				placeholder="password"
				onChange={(e) => setMdp(e.target.value)}
				/>

				<button type="submit">Create</button>

				<div className="message">{message ? <p>{message}</p> : null}</div>
			</form>
		</div>
	);
}

export default App;