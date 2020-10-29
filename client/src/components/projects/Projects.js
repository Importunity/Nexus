import { Backdrop, Button, Card, CardContent,   makeStyles, Modal,  Typography } from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import { userloadProjects } from '../../api/ProjectAPI';
import '../../styles/project.css';
import CreateProject from './CreateProject';

const useStyles = makeStyles((theme) => ({
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
    modal: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    }
}));


function Projects(props){
    const classes = useStyles();
    const[projects, setProjects] = useState([]);
    const[tasks, setTasks] = useState({tasks: []});

    useEffect(() => {
        (async () => {
            let response = await userloadProjects();
            setProjects(response);
        })();
        
    }, []);
    const projectClick = (projectId, index) => () => {
        //console.log(projects[index].tasks);
        if(projects[index].tasks.length === 0){
            alert("Project Contains No Tasks");
        }else{
            setTasks({tasks: projects[index].tasks})
        }
        
    }

    const[createProject, setCreateProject] = useState(false);
    const createProjectClick = () => {
        setCreateProject(true);
    }

    const closeCreateProject = () => {
        setCreateProject(false);
    }





    return (
        <div className="initial-container project-container">
            <div className="row">
                <div className="col-md-2">
                    <div className="project-sidebar">
                        <div onClick={createProjectClick}>
                            <Button variant="contained" color="primary" fullWidth>Create Project</Button>
                        </div>
                        <Modal className="projectModal" open={createProject} onClose={closeCreateProject} closeAfterTransition BackdropComponent={Backdrop}>
                            <CreateProject createProject={createProject}></CreateProject>
                        </Modal>
                        {projects.map((project, index) => {
                            return (
                                <div key = {project.id} onClick={projectClick(project.id, index)}>
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
                <div className="col-md-10">
                    <div className="task-container">
                        <div className="row">
                            <div className="col-md-2 task-column">
                                {tasks.tasks.map((task) => {
                                    return( task.level === 1?
                                        <div key={task.id}>
                                            <Card className="task-card" >
                                                <CardContent>
                                                    <Typography className={classes.title} color="primary" gutterBottom>
                                                        Task Title:
                                                    </Typography>
                                                    <Typography>
                                                        {task.title}
                                                    </Typography>
                                                    <Typography className={classes.title} color="primary" gutterBottom>
                                                        Task Description:
                                                    </Typography>
                                                    <Typography>
                                                        {task.description}
                                                    </Typography>
                                                </CardContent>
                                            </Card>
                                        </div> : null
                                    )
                                })}
                                
                            </div>
                            <div className="col-md-2 task-column">
                                {tasks.tasks.map((task) => {
                                    return( task.level === 2?
                                        <div key={task.id}>
                                            <Card className="task-card" >
                                                <CardContent>
                                                    <Typography className={classes.title} color="primary" gutterBottom>
                                                        Task Title:
                                                    </Typography>
                                                    <Typography>
                                                        {task.title}
                                                    </Typography>
                                                    <Typography className={classes.title} color="primary" gutterBottom>
                                                        Task Description:
                                                    </Typography>
                                                    <Typography>
                                                        {task.description}
                                                    </Typography>
                                                </CardContent>
                                            </Card>
                                        </div> : null
                                    )
                                })}
                                
                            </div>
                            <div className="col-md-2 task-column">
                                {tasks.tasks.map((task) => {
                                    return( task.level === 3?
                                        <div key={task.id}>
                                            <Card className="task-card" >
                                                <CardContent>
                                                    <Typography className={classes.title} color="primary" gutterBottom>
                                                        Task Title:
                                                    </Typography>
                                                    <Typography>
                                                        {task.title}
                                                    </Typography>
                                                    <Typography className={classes.title} color="primary" gutterBottom>
                                                        Task Description:
                                                    </Typography>
                                                    <Typography>
                                                        {task.description}
                                                    </Typography>
                                                </CardContent>
                                            </Card>
                                        </div> : null
                                    )
                                })}
                                
                            </div>
                            <div className="col-md-2 task-column">
                                {tasks.tasks.map((task) => {
                                    return( task.level === 4?
                                        <div key={task.id}>
                                            <Card className="task-card" >
                                                <CardContent>
                                                    <Typography className={classes.title} color="primary" gutterBottom>
                                                        Task Title:
                                                    </Typography>
                                                    <Typography>
                                                        {task.title}
                                                    </Typography>
                                                    <Typography className={classes.title} color="primary" gutterBottom>
                                                        Task Description:
                                                    </Typography>
                                                    <Typography>
                                                        {task.description}
                                                    </Typography>
                                                </CardContent>
                                            </Card>
                                        </div> : null
                                    )
                                })}
                                
                            </div>
                            <div className="col-md-2 task-column">
                                {tasks.tasks.map((task) => {
                                    return( task.level === 5?
                                        <div key={task.id}>
                                            <Card className="task-card" >
                                                <CardContent>
                                                    <Typography className={classes.title} color="primary" gutterBottom>
                                                        Task Title:
                                                    </Typography>
                                                    <Typography>
                                                        {task.title}
                                                    </Typography>
                                                    <Typography className={classes.title} color="primary" gutterBottom>
                                                        Task Description:
                                                    </Typography>
                                                    <Typography>
                                                        {task.description}
                                                    </Typography>
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