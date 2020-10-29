import { Card, CardContent, makeStyles, Typography } from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import { userloadProjects } from '../../api/ProjectAPI';
import '../../styles/project.css';

const useStyles = makeStyles({
    root: {
      minWidth: 275,
      backgroundColor: "#3d3f3f",
    },
    title: {
      fontSize: 14,
    },
    pos: {
      marginBottom: 12,
    },
});


function Projects(props){
    const classes = useStyles();
    const[projects, setProjects] = useState([]);
    const[tasks, setTasks] = useState({tasks: []});
    const[taskLevels, setTaskLevels] = useState({level1: [], level2: [], level3: [], level4: [], level5: []});

    useEffect(() => {
        (async () => {
            let response = await userloadProjects();
            setProjects(response);
        })();
        
    }, []);
    const projectClick = projectId => () => {
        setTasks({tasks: projects[projectId - 1].tasks})
        
    }

    console.log(tasks);
    useEffect(() => {
        for(var i = 0; i < tasks.tasks.length; i++){
            const task = tasks.tasks[i];
            switch(task.level){
                case 1:
                    setTaskLevels(prevState => ({
                        level1: [...(prevState.level1 || []), task.level]
                    }))
                    break;
                case 3:
                    setTaskLevels(prevState => ({
                        level3: [...(prevState.level3 || []), ]
                    }))
                    break;
                default: 
                    console.log('none');
            }
        }
    }, [tasks.tasks])
    console.log(taskLevels.level3.length)


    return (

        <div className="initial-container project-container">
            <div className="row">
                <div className="col-md-3">
                    <div className="project-sidebar">
                        {projects.map((project) => {
                            return (
                                <div key = {project.id} onClick={projectClick(project.id)}>
                                    <Card className={classes.root, "project-card"} variant="outlined">
                                        <CardContent>
                                            <Typography className={classes.title} color="primary" gutterBottom>
                                                {project.id - 1} : Project Name
                                            </Typography>
                                            <Typography className={classes.pos} color="textPrimary" variant="h5">
                                                {project.name}
                                            </Typography>
                                            <Typography className={classes.title} color="primary" gutterBottom>
                                                Project Description
                                            </Typography>
                                            <Typography variant="body1" component="p">
                                                {project.description}
                                            </Typography>
                                        </CardContent>
                                    </Card>
                                </div>
                            )
                        })}
                    </div>
                </div>
                <div className="col-md-9">
                    <div className="task-container">
                        <div className="row">
                            <div className="col-md-2">
                                {tasks.tasks.map((task) => {
                                    return( task.level === 1?
                                        <div key={task.id}>
                                            <Card>
                                                <CardContent>
                                                    {task.level}
                                                </CardContent>
                                            </Card>
                                        </div> : null
                                    )
                                })}
                                
                            </div>
                            <div className="col-md-2">
                                {tasks.tasks.map((task) => {
                                    return( task.level === 2?
                                        <div key={task.id}>
                                            <Card>
                                                <CardContent>
                                                    {task.level}
                                                </CardContent>
                                            </Card>
                                        </div> : null
                                    )
                                })}
                                
                            </div>
                            <div className="col-md-2">
                                {tasks.tasks.map((task) => {
                                    return( task.level === 3?
                                        <div key={task.id}>
                                            <Card>
                                                <CardContent>
                                                    {task.level}
                                                </CardContent>
                                            </Card>
                                        </div> : null
                                    )
                                })}
                                
                            </div>
                            <div className="col-md-2">
                                {tasks.tasks.map((task) => {
                                    return( task.level === 4?
                                        <div key={task.id}>
                                            <Card>
                                                <CardContent>
                                                    {task.level}
                                                </CardContent>
                                            </Card>
                                        </div> : null
                                    )
                                })}
                                
                            </div>
                            <div className="col-md-2">
                                {tasks.tasks.map((task) => {
                                    return( task.level === 5?
                                        <div key={task.id}>
                                            <Card>
                                                <CardContent>
                                                    {task.level}
                                                </CardContent>
                                            </Card>
                                        </div> : null
                                    )
                                })}
                                
                            </div>
                        </div>
                    </div>
                </div>
                
            </div>
        </div>
        
    )
}

export default Projects;