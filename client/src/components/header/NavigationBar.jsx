import {
  AppBar,
  Button,
  Toolbar,
  Typography
} from "@material-ui/core";
import { BrowserRouter as Router, Switch, Route, Link, Redirect } from "react-router-dom";
import React, {useEffect, useState } from "react";
import "../../styles/navbar.css";
import Login from "../login/Login";
import { getCurrentUser } from "../../api/UserAPI";
import { TOKEN } from "../../constants/types";
import Projects from "../projects/Projects";

function Home() {
  return (
    <div className="container">
      <h1 id="about-title">About</h1>
      <div className="container">
          <p id="about-content">Nexus is a project manager application created in spring boot as the backend framework and react as the frontend framework.  </p>
      </div>
    </div>
  );
}


function PrivateRoute ({component: Component, isAuthenticated, ...rest}){
  return (
    <Route {...rest}
      render={(props) => isAuthenticated
      ?<Component {...props} />
      : <Redirect to={{pathname: '/', state:{from: props.location}}} />
    }
    />
  )
}




function NavigationBar() {

    const[logged, setLogged] = useState({currentUser: null, isAuthenticated: false, isLoading: false});
    const logoutUser = () => {
        localStorage.removeItem(TOKEN);
        setLogged({currentUser: null, isAuthenticated: false, isLoading: false});
    }
    const loadUser = () => {
        setLogged({isLoading: true});
        getCurrentUser().then(response => {
            setLogged({
                currentUser: response, 
                isAuthenticated: true,
                isLoading: false
            })
        }).catch(error => {
            setLogged({ isLoading: false});
          })
    }
    useEffect(() => {
        loadUser();
    }, [])


    


  return (
    <div className="initial-container" style={{flexGrow: 1}}>
      <Router>
        <AppBar style={{ background: '#2E3B55' }} position="static">
          <Toolbar>
              <Typography style={{flexGrow: 1}}>
                <Link to={'/'} id="title">NEXUS</Link>
              </Typography>
                <div>
                  {logged.isAuthenticated? (
                    <div>
                      <Button >
                        <Link to={'/projects'} className="menu-item">Projects</Link>
                      </Button>
                      <Button >
                        <Link to={'/account'} className="menu-item">Projects</Link>
                      </Button>
                      <Button className="menu-item" onClick={logoutUser}>Logout</Button>
                    </div>
                  ) : null}
                    {!logged.isAuthenticated ? (
                      <div>
                      <Button>
                          <Link to={'/login'} className="menu-item">Login / Register</Link>
                      </Button>
                    </div>
                    ) : null }
                </div>
          </Toolbar>
        </AppBar>
        <Switch>
          <Route exact path="/" component={Home} />
          <Route path="/login" component={() => <Login onLoad={loadUser} />} />
          <PrivateRoute isAuthenticated={logged.isAuthenticated} path="/projects" component={Projects}></PrivateRoute>
        </Switch>
      </Router>
    </div>
  );
}

export default NavigationBar;
