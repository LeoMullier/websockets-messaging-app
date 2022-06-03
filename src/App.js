//import "./App.css";
import { useState } from "react";

function App() {
  const [login_, setLogin] = useState("");
  const [mdp_, setMdp] = useState("");
  const [message, setMessage] = useState("");

  let handleSubmit = async (e) => {
    e.preventDefault();
    console.log("(*) Je passe dans la fonction JS.");
    try {
      let res = await fetch("http://localhost:8080/user/test2", {
        method: "POST",
        body: JSON.stringify({
          "login": "lmullier",
          "mdp": "utc",
        }),
        //header: [{"content-type": "application/json"}]
      });
      let resJson = await res.json();


      if (res.status === 200) {
        setLogin("");
        setMdp("");

        setMessage("Request successfully sent");
      } else {
        setMessage("Some error occured");
      }
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