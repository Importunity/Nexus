import { Button, makeStyles, TextField } from '@material-ui/core';
import { Alert } from '@material-ui/lab';
import React, { useEffect, useState } from 'react';
import { createProject as createProjectAPI } from '../../api/ProjectAPI';
import { PROJECT_DESCRIPTION_MAX, PROJECT_NAME_MAX } from '../../constants/types';
import '../../styles/project.css';

const useStyles = makeStyles((theme) => ({
    modal: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    paper: {
        backgroundColor: theme.palette.background.paper,
        border: '2px solid #000',
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
        width: 1000,
        marginLeft: 500,
        marginTop: 100
    }
}));

function CreateProject(props){
    const classes = useStyles();

    const[errors, setErrors] = useState(false);
    const[success, setSuccess] = useState(false);


    const[projectInfo, setProjectInfo] = useState({name: '', description: ''});
    const projectFieldsChange = (event) => {
        setProjectInfo({...projectInfo, [event.target.name]: event.target.value});
    }
    
    useEffect(() => {
        if(projectInfo.name.length > PROJECT_NAME_MAX){
            setErrors(true);
        }
        if(projectInfo.description.length > PROJECT_DESCRIPTION_MAX){
            setErrors(true);
        }
    }, [projectInfo])
    const projectCreateSubmit = (event) => {
        event.preventDefault();
        if(!errors){
            const projectRequest = projectInfo;
            createProjectAPI(projectRequest)
                .then(response => {
                    console.log(response);
                    console.log("registration success");
                    setSuccess(true)
                }).catch(error => {
                    console.log("oops something went wrong")
                    console.log(error);
                    setSuccess(false);
                })
        }
        props.handleChange(props.count + 1);
    }
    return(
        <div className={classes.paper}>
            <form onSubmit={projectCreateSubmit}>
                <h1 className="creation-field">Project Creation</h1>
                <TextField className="creation-field" required label="Project Name:" fullWidth name="name" onChange={projectFieldsChange}/>
                {projectInfo.name.length > PROJECT_NAME_MAX? 
                    <Alert className="creation-field" severity="error">name length is greater than {PROJECT_NAME_MAX} characters</Alert> : null 
                }
                <TextField className="creation-field" label="Project Description" multiline rows={5} variant="filled" fullWidth name="description" onChange={projectFieldsChange} />
                {projectInfo.description.length > PROJECT_DESCRIPTION_MAX? 
                    <Alert className="creation-field" severity="error">description length is greater than {PROJECT_DESCRIPTION_MAX} characters</Alert> : null 
                }
                <Button type="submit" label="Submit" color="primary" variant="contained">Submit</Button>
                {success? (
                        <Alert className="creation-field" severity="success">Project Created</Alert> 
                    ): null 
                }
            </form>
        </div>
    );
}

export default CreateProject;