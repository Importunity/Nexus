import {
  AppBar,
  Button,
  Grid,
  Toolbar
} from "@material-ui/core";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import React, { useEffect, useState } from "react";
import "../../styles/navbar.css";
import Login from "../login/Login";
import { getCurrentUser } from "../../api/UserAPI";
import { TOKEN } from "../../constants/types";

function Home() {
  return <div>HELLO</div>;
}

function Logout(){
    return <Button>Logout</Button>
}


function NavigationBar() {
    const[logged, setLogged] = useState({currentUser: null, isAuthenticated: false, isLoading: false});
    const logoutUser = () => {
        localStorage.removeItem(TOKEN);
        setLogged({currentUser: null, isAuthenticated: false});
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
    <div className="initial-container">
      <Router>
        <AppBar style={{ background: '#2E3B55' }} position="static">
          <Toolbar>
            <Grid
              justify="space-between" 
              container
            >
            <Grid item>
                <Link to={'/'} className="menu-item">NEXUS</Link>
            </Grid>

            <Grid item>
                <div>
                    <Button>
                        <Link to={'/login'} className="menu-item">Login</Link>
                    </Button>
                    {logged.isAuthenticated ? (
                        <div onClick={logoutUser}>
                            <Logout />
                        </div>
                    ): null }
                </div>
            </Grid>
            </Grid>
          </Toolbar>
        </AppBar>
        <Switch>
          <Route exact path="/" component={Home} />
          <Route path="/login" component={Login} />
        </Switch>
      </Router>
    </div>
  );
}

export default NavigationBar;
