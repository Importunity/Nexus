import {Button, FormControl, InputLabel, makeStyles, Select, TextField, Typography } from '@material-ui/core';
import React, { useState } from 'react';
import { createTask } from '../../api/TaskAPI';
import '../../styles/project.css'

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

function CreateTask(props){
    const classes = useStyles();
    const[taskInfo, setTaskInfo] = useState({title: '', description: '', level: null, projectId: props.currentProjectId});
    const handleInfoChange = (event) => {
        setTaskInfo({...taskInfo, [event.target.name]: event.target.value})
    }

    const submitCreateTask = (event) => {
        event.preventDefault();
        const taskRequest = taskInfo;
        createTask(taskRequest)
            .then(response => {
                console.log(response);
            }).catch(error => {
                console.log(error);
            })
    }
    console.log(taskInfo);
    return (
        <div className={classes.paper}>
            <form onSubmit={submitCreateTask}>
                <Typography className="creation-field" variant="h4">
                    Task Creation
                </Typography>
                <TextField className="creation-field" required label="Task Title" fullWidth onChange={handleInfoChange} name="title"></TextField>
                <TextField className="creation-field" label="Task Description" multiline rows={5} variant="filled" fullWidth name="description" onChange={handleInfoChange}></TextField>
                <FormControl variant="outlined" className={classes.formControl}>
                    <InputLabel htmlFor="level">Priority Level</InputLabel>
                    <Select
                    id="level"
                    native
                    label="Priority Level"
                    style={{width: 150}}
                    className="creation-field"
                    name="level"
                    onChange={handleInfoChange}
                    >
                    <option aria-label="None" value="" />
                    <option value={1}>One</option>
                    <option value={2}>Two</option>
                    <option value={3}>Three</option>
                    <option value={4}>Four</option>
                    <option value={5}>Five</option>
                    </Select>
                </FormControl>
                <br></br>
                <Button type="submit" variant="contained" color="primary">Create Task</Button>
            </form>
        </div>
    );
}

export default CreateTask;