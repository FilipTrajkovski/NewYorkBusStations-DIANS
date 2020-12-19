import React, { Component } from 'react';
import './App.css';
import backgroundImage from "./assets/background.jpg";
import { Route, Switch } from "react-router";
import SearchPage from "./components/SearchPage";

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
            <Switch>
                <Route path="/" component={SearchPage} />
            </Switch>
        </div>
    );
  }

}

export default App;
