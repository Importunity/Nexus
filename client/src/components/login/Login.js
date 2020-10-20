import React, {useState} from 'react';
import {login, getCurrentUser} from '../../api/UserAPI';
import {Button, TextField} from '@material-ui/core';
import {TOKEN} from '../../constants/types';

function Login(){

    const[info, setInfo] = useState({usernameOrEmail: '', password: ''});

    const handleChange = (event) => {
        setInfo({...info, [event.target.name]: event.target.value});
    }

    const handleSubmit = event => {
        event.preventDefault();
        const loginRequest = Object.assign({}, info);
        login(loginRequest)
            .then((response) => {
                console.log("token: " + response.token);
                localStorage.setItem(TOKEN, response.token);
            }).catch(error => {
                if(error.status === 401) {
                    console.log('Your Username or Password is incorrect. Please try again!') ;                 
                } else {
                    console.log("oops something went wrong");                                         
                }
            });
        getCurrentUser().then((response) =>{
            console.log(response);
        })
        event.target.reset();
    }
    return(
        <div className="container">
            <div className="row">
                <div className="col-md-4"></div>
                <div className="col-md-4">
                    <form onSubmit={handleSubmit}>
                        <TextField required className="login-input" name="usernameOrEmail" id="usernameOrEmail" label="Username Or Email" fullWidth onChange={handleChange}/>
                        <TextField id="password" className="login-input" name="password" label="Password" type="password" autoComplete="current-password"  fullWidth onChange={handleChange} />
                        <button className="btn btn-danger">Hello</button>
                    </form>
                </div>
                <div className="col-md-4"></div>
            </div>
        </div>
    );
}

export default Login;