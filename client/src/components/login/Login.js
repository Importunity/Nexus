import React, {useState} from 'react';
import {login} from '../../api/UserAPI';
import { AppBar, Box, Tab, Tabs, TextField, Typography} from '@material-ui/core';
import {TOKEN} from '../../constants/types';
import Proptypes from 'prop-types';
import '../../styles/login.css';
import Register from './Register';
import { Alert } from '@material-ui/lab';


function TabPanel(props) {
    const { children, value, index, ...other } = props;
  
    return (
      <div
        role="tabpanel"
        hidden={value !== index}
        id={`simple-tabpanel-${index}`}
        aria-labelledby={`simple-tab-${index}`}
        {...other}
      >
        {value === index && (
          <Box p={3}>
            <Typography component={'span'}>
                {children}
            </Typography>
          </Box>
        )}
      </div>
    );
}
  
TabPanel.propTypes = {
    children: Proptypes.node,
    index: Proptypes.any.isRequired,
    value: Proptypes.any.isRequired,
};

function a11yProps(index) {
return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
};
}
  

function Login(props){
    const[errors, setErrors] = useState(false);
    const[info, setInfo] = useState({usernameOrEmail: '', password: ''});
    
    const handleChange = (event) => {
        setInfo({...info, [event.target.name]: event.target.value});
    }

    const [value, setValue] = useState(0);
    const handleTab = (event, newValue) => {
        setValue(newValue);
    };

    const handleSubmit = event => {
        event.preventDefault();
        const loginRequest = Object.assign({}, info);
        login(loginRequest)
            .then((response) => {
                localStorage.setItem(TOKEN, response.token);
                props.onLoad();
            }).catch(error => {
                if(error.status === 401) {
                    setErrors(true);
                    console.log('Your Username or Password is incorrect. Please try again!') ;                 
                } else {
                    console.log("oops something went wrong");                                         
                }
            });

        event.target.reset();
    }
    
    return(
        <div className="container login-container">
            <div className="row">
                <div className="col-md-2"></div>
                <div className="col-md-4">
                    <AppBar position="static" className="login-bar">
                        <Tabs value={value} onChange={handleTab} aria-label="simple tabs example">
                            <Tab label="Login" {...a11yProps(0)} className="login-tab" />
                            <Tab label="User Registration" {...a11yProps(1)} className="login-tab" />
                        </Tabs>
                    </AppBar>
                    <TabPanel value={value} index={0} className="login-tabpanel">
                        <form id="login-form" onSubmit={handleSubmit}>
                            <TextField required className="login-input" name="usernameOrEmail" id="usernameOrEmail" label="Username Or Email" fullWidth onChange={handleChange}/>
                            <TextField id="password" className="login-input" name="password" label="Password" type="password" autoComplete="current-password"  fullWidth onChange={handleChange} />
                            {errors? 
                                <Alert className="alert" variant="standard" color="error" >Your Username or Password is incorrect. Please try again</Alert> : null }

                            <div className="outer-login-btn" id="button-5">
                                <div id="translate">
                                </div>
                                <button className="login-btn" form="login-form">Login</button>
                            </div>
                        </form>
                    </TabPanel>
                    <TabPanel value={value} index={1} className="login-tabpanel">
                        <Register />
                    </TabPanel>
                </div>
                <div className="col-md-4"></div>
            </div>
        </div>
    );
}


export default Login;