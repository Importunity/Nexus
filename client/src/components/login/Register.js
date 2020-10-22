import { TextField } from '@material-ui/core';
import React, { useState } from 'react';
import { register } from '../../api/UserAPI';
import '../../styles/login.css'
import { Alert} from '@material-ui/lab';
import { EMAIL_MAX_LENGTH, FIRST_NAME_MAX_LENGTH, FIRST_NAME_MIN_LENGTH, LAST_NAME_MAX_LENGTH, LAST_NAME_MIN_LENGTH, PASSWORD_MAX_LENGTH, PASSWORD_MIN_LENGTH, PHONE_MAX_LENGTH, USERNAME_MAX_LENGTH, USERNAME_MIN_LENGTH } from '../../constants/types';


function Register(){
    const[info, setInfo] = useState({first: '', last: '', username: '', phone: '', email: '', password: ''});

    const[isSubmitted, setIsSubmitted] = useState(false);
    const[errors] = useState([]);
    const handleChange = (event) => {
        setInfo({...info, [event.target.name]: event.target.value});
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        setIsSubmitted(true);
        const registerRequest = info;
        register(registerRequest)
            .then(response =>{
                console.log(response);
                console.log("register success");
            }).catch(error => {
                console.log(error);
                console.log("something went wrong");
            })
        if(info.first.length < FIRST_NAME_MIN_LENGTH || info.first.length > FIRST_NAME_MAX_LENGTH){
            errors.push("first name has to be between: " + FIRST_NAME_MIN_LENGTH + " and " + FIRST_NAME_MAX_LENGTH);
        }
        if(info.last.length < LAST_NAME_MIN_LENGTH || info.last.length > LAST_NAME_MAX_LENGTH){
            errors.push("last name has to be between: " + LAST_NAME_MIN_LENGTH + " and " + LAST_NAME_MAX_LENGTH);
        }
        if(info.username.length < USERNAME_MIN_LENGTH || info.username.length > USERNAME_MAX_LENGTH){
            errors.push("username has to be between: " + USERNAME_MIN_LENGTH + " and " + USERNAME_MAX_LENGTH);
        }
        if(info.email.length > EMAIL_MAX_LENGTH){
            errors.push("Email is less than: " + EMAIL_MAX_LENGTH);
        }
        if(info.phone.length > PHONE_MAX_LENGTH){
            errors.push("Phone number is greater than: " + PHONE_MAX_LENGTH);
        }
        if(info.password.length < PASSWORD_MIN_LENGTH || info.password.length > PASSWORD_MAX_LENGTH){
            errors.push("password has to be between: " + PASSWORD_MIN_LENGTH + " and " + PASSWORD_MAX_LENGTH);
        }
        event.target.reset();
    }

    return(
        <div>
            <form id="register-form" onSubmit={handleSubmit}>
                <TextField required className="login-input" name="first" label="First Name"  fullWidth onChange={handleChange} />
                <TextField required className="login-input" name="last" label="Last Name" fullWidth  onChange={handleChange}/>
                <TextField required className="login-input" name="username" id="Username" label="Username" fullWidth  onChange={handleChange}/>
                <TextField required className="login-input" name="phone" id="phone" label="Phone" fullWidth  onChange={handleChange}/>
                <TextField required className="login-input" name="email" id="email" label="Email" fullWidth  onChange={handleChange}/>
                <TextField id="password" className="login-input" name="password" label="Password" type="password" autoComplete="current-password"  fullWidth  onChange={handleChange}/>
                {errors.map( (error, index) =>{
                    return <Alert key={index} severity="error">{error}</Alert>
                })}
                {errors.length === 0 && isSubmitted?(
                    <Alert severity="success"><strong>Registered Successfully!</strong></Alert>
                ): null}
                <div className="outer-login-btn" id="button-5">
                    <div id="translate"></div>
                    <button className="login-btn" form="register-form">Register</button>
                </div>
            </form>
        </div>
    );
}

export default Register;