import { Backdrop, Button, Card, CardContent,   Fab,  Fade,  makeStyles, Modal,  Tooltip,  Typography } from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import { userloadProjects } from '../../api/ProjectAPI';
import '../../styles/project.css';
import CreateProject from './CreateProject';
import AddIcon from '@material-ui/icons/Add';
import CreateTask from '../task/CreateTask';

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
    const[currentProjectId, setCurrentProjectId] = useState(null);
    useEffect(() => {
        (async () => {
            let response = await userloadProjects();
            setProjects(response);
        })();
        
    }, []);
    const projectClick = (projectId, index) => (event) => {
        setCurrentProjectId(projectId);
        //console.log(projects[index].tasks);
        if(projects[index].tasks.length !== 0){
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

    const[createTask, setCreateTask] = useState(false);
    const createTaskClick = () => {
        setCreateTask(true);
    }
    const closeCreateTask = () => {
        setCreateTask(false);
    }




    return (
        <div className="initial-container project-container">
            <div className="row">
                <div className="col-md-2">
                    <div className="project-sidebar">
                        <Button className="project-card" type="button" variant="contained" color="primary" fullWidth onClick={createProjectClick}>Create Project</Button>
                        <Modal open={createProject} onClose={closeCreateProject} closeAfterTransition BackdropComponent={Backdrop}>
                            <Fade in={createProject}>
                                <CreateProject></CreateProject>
                            </Fade>
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
                                            <Tooltip title="Create Task" aria-label="Create Task"  className="plus-icon">
                                                <div onClick={createTaskClick}>
                                                    <Fab color="primary" size="small" >
                                                        <AddIcon />
                                                    </Fab>
                                                </div>
                                            </Tooltip>
                                        </CardContent>
                                    </Card>
                                </div>
                            )
                        })}
                        <Modal open={createTask} onClose={closeCreateTask} closeAfterTransition BackdropComponent={Backdrop}>
                            <Fade in={createTask}>
                                <CreateTask currentProjectId={currentProjectId}></CreateTask>
                            </Fade>
                        </Modal>
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