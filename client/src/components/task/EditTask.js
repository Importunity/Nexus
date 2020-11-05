import { Button, FormControl, InputLabel, makeStyles, Select, TextField, Typography } from '@material-ui/core';
import React, { useState } from 'react';
import { updateTask } from '../../api/TaskAPI';

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

export default function EditTask(props){
    const classes = useStyles();
    const[task, setTask] = useState(props.taskToEdit);
    const handleChange = (event) => {
        console.log(event.target.name)
        setTask({...task, [event.target.name]: event.target.value})
    }
    const editTaskSubmit = (event) => {
        event.preventDefault();
        const taskRequest = task;
        updateTask(taskRequest)
            .then(response => {
                console.log(response)
            }).catch(error => {
                console.log(error)
            })
    }


    return (
        <div className={classes.paper}>
            <form onSubmit={editTaskSubmit} >
                <Typography className="creation-field" variant="h4">
                    Edit Task
                </Typography>
                <TextField className="creation-field" required label="Task Title" fullWidth name="title" placeholder={`${props.taskToEdit.task.title}`} onChange={handleChange}></TextField>
                <TextField className="creation-field" label="Task Description" multiline rows={5} variant="filled" fullWidth name="description" placeholder={`${props.taskToEdit.task.description}`} onChange={handleChange} ></TextField>
                <FormControl variant="outlined" className={classes.formControl}>
                    <InputLabel htmlFor="level">Priority Level</InputLabel>
                    <Select
                    id="level"
                    native
                    label="Priority Level"
                    style={{width: 150}}
                    className="creation-field"
                    name="level"
                    onChange={handleChange}
                    value={task.task.level}
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
                <Button type="submit" variant="contained" color="primary">Edit Task</Button>
            </form>
        </div>
    );
}