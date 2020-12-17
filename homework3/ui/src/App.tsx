import React, { Component } from 'react';
import './App.css';
import backgroundImage from "./assets/background.jpg";

class App extends Component<{}, {}> {
  render() {
    return (
        <div className="App"
             style={{
                 backgroundImage: `url(${backgroundImage})`,
                 backgroundSize: "cover",
                 height: "100vh"
             }}
        >
        </div>
    );
  }

}

export default App;
