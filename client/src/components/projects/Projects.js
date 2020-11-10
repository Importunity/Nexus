import { Backdrop, Button, Card, CardContent,   Divider,   Fab,  Fade,  makeStyles, Modal,  Tooltip,  Typography } from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import { removeProject, userloadProjects } from '../../api/ProjectAPI';
import '../../styles/project.css';
import CreateProject from './CreateProject';
import AddIcon from '@material-ui/icons/Add';
import CreateTask from '../task/CreateTask';
import DeleteIcon from '@material-ui/icons/Delete';
import { removeTask } from '../../api/TaskAPI';
import EditIcon from '@material-ui/icons/Edit';
import EditTask from '../task/EditTask';
import DeleteProject from './DeleteProject';

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
    const[count, setCount] = useState(0);
    useEffect(() => {
        (async () => {
            let response = await userloadProjects();
            setProjects(response);
        })();
    }, [count]);


    const projectClick = (projectId, index) => (event) => {
        setCurrentProjectId(projectId);
        //console.log(projects[index].tasks);
        if(projects[index].tasks.length !== 0){
            setTasks({tasks: projects[index].tasks})
        }
    }

    const handleChange = (value) => {
        setCount(value);
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

    const createTaskHandler = (task) => {
        // adding the task into the array of tasks
        setTasks(prevState => ({tasks: [prevState.tasks, task]}));
    }

    const[deleteTask, setDeleteTask] = useState({taskId: null, projectId: null, index: null});
    const deleteTaskClick = (taskId, projectId) => (event) => {
        setTasks({tasks: tasks.tasks.filter(otherIndex => otherIndex.id !== taskId)})
        setDeleteTask({taskId: taskId, projectId: projectId})
    }
    console.log(tasks.tasks);
    useEffect(() => {
        const taskRequest = deleteTask;
        removeTask(taskRequest).then(response => {
            console.log(response)
        }).catch(error => {
            console.log(error)
        })
    }, [deleteTask])

    const[editTask, setEditTask] = useState(false);
    const[taskToEdit, setTaskToEdit] = useState({task: [], projectId: null});
    const editTaskClick = () => {
        setEditTask(true);
    }
    const closeEditTask = () => {
        setEditTask(false);
    }

    const editTaskHandler = (task, projectId) => (event) => {
        editTaskClick();
        setTaskToEdit({task: task, projectId: projectId});
    }

    // remove project
    const[deleteProject, setDeleteProject] = useState({projectId: null});
    const removeProjectClick = () => {
        setProjects(projects.filter(project => project.id !== currentProjectId));
        setDeleteProject({projectId: currentProjectId});
    }
    useEffect(() => {
        const projectRequest = deleteProject;
        removeProject(projectRequest).then(response => {
            console.log(response);
        }).catch(error => {
            console.log(error);
        })
    }, [deleteProject])
    
    return (
        <div className="initial-container project-container">
            <div className="row">
                <div className="col-md-2">
                    <div className="project-sidebar">
                        <Button className="project-card" type="button" variant="outlined" color="inherit"  fullWidth onClick={createProjectClick}>Create Project</Button>
                        <Modal open={createProject} onClose={closeCreateProject} closeAfterTransition BackdropComponent={Backdrop}>
                            <Fade in={createProject}>
                                <CreateProject count={count} handleChange={handleChange}></CreateProject>
                            </Fade>
                        </Modal>
                        {projects.map((project, index) => {
                            return (
                                <div key = {project.id} onClick={projectClick(project.id, index)}>
                                    <Card className={classes.root, "project-card"} variant="outlined">
                                        <CardContent>
                                            <Typography className={classes.title} color="primary" gutterBottom>
                                                Project Name
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
                                            <div className="row">
                                                <div className="col-4">
                                                    <Tooltip title="Create Task" aria-label="Create Task" >
                                                        <div onClick={createTaskClick}>
                                                            <Fab color="primary" size="small" >
                                                                <AddIcon />
                                                            </Fab>
                                                        </div>
                                                    </Tooltip>
                                                </div>
                                                <div className="col-4"></div>
                                                <div className="col-4">
                                                    <Tooltip title="Delete Project" aria-label="Delete Project"  className="delete-icon">
                                                        <div onClick={removeProjectClick}>
                                                            <DeleteProject deleteProject={deleteProject} />
                                                        </div>
                                                    </Tooltip>
                                                </div>
                                            </div>
                                        </CardContent>
                                    </Card>
                                </div>
                            )
                        })}
                        <Modal  open={createTask} onClose={closeCreateTask} closeAfterTransition BackdropComponent={Backdrop}>
                            <Fade in={createTask}>
                                <CreateTask handleChange={handleChange} count={count} createTaskHandler={createTaskHandler} tasks={tasks} currentProjectId={currentProjectId}></CreateTask>
                            </Fade>
                        </Modal>
                    </div>
                </div>
                <Modal open={editTask} onClose={closeEditTask} closeAfterTransition BackdropComponent={Backdrop}>
                    <Fade in={editTask}>
                        <EditTask handleChange={handleChange} count={count} taskToEdit={taskToEdit} />
                    </Fade>
                </Modal>
                <div className="col-md-10">
                    <div className="task-container">
                        <div className="row">
                            <div className="col task-column">
                                <Typography>
                                    Level 1
                                </Typography>
                                <Divider></Divider>
                                {tasks.tasks.map((task, index) => {
                                    return( task.level === 1 && !task.completed?
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
                                                    <Divider></Divider>
                                                    <div className="row">
                                                        <div className="col-3">
                                                            <Tooltip title="Delete Task" aria-label="Delete Task" className="task-icon">
                                                                <div onClick={deleteTaskClick(task.id, currentProjectId)}>
                                                                    <Fab color="primary" size="small">
                                                                        <DeleteIcon />
                                                                    </Fab>
                                                                </div>
                                                            </Tooltip>
                                                        </div>
                                                        <div className="col-3">
                                                            <Tooltip title="Edit Task" aria-label="Edit Task"  className="task-icon">
                                                                <div onClick={editTaskHandler(task, currentProjectId)}>
                                                                    <Fab color="primary" size="small" >
                                                                        <EditIcon />
                                                                    </Fab>
                                                                </div>
                                                            </Tooltip>
                                                        </div>
                                                        <div className="col-3"></div>
                                                        <div className="col-3"></div>
                                                    </div>
                                                </CardContent>
                                            </Card>
                                        </div> : null
                                    )
                                })}
                                
                            </div>
                            <div className="col task-column">
                                <Typography>
                                    Level 2
                                </Typography>
                                <Divider></Divider>
                                {tasks.tasks.map((task) => {
                                    return( task.level === 2 && !task.completed?
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
                                                    <Divider></Divider>
                                                    <div className="row">
                                                        <div className="col-3">
                                                            <Tooltip title="Delete Task" aria-label="Delete Task" className="task-icon">
                                                                <div onClick={deleteTaskClick(task.id, currentProjectId)}>
                                                                    <Fab color="primary" size="small">
                                                                        <DeleteIcon />
                                                                    </Fab>
                                                                </div>
                                                            </Tooltip>
                                                        </div>
                                                        <div className="col-3">
                                                            <Tooltip title="Edit Task" aria-label="Edit Task"  className="task-icon">
                                                                <div onClick={editTaskHandler(task, currentProjectId)}>
                                                                    <Fab color="primary" size="small" >
                                                                        <EditIcon />
                                                                    </Fab>
                                                                </div>
                                                            </Tooltip>
                                                        </div>
                                                        <div className="col-3"></div>
                                                        <div className="col-3"></div>
                                                    </div>
                                                </CardContent>
                                            </Card>
                                        </div> : null
                                    )
                                })}
                                
                            </div>
                            <div className="col task-column">
                                <Typography>
                                    Level 3
                                </Typography>
                                <Divider></Divider>
                                {tasks.tasks.map((task) => {
                                    return( task.level === 3 && !task.completed?
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
                                                    <Divider></Divider>
                                                    <div className="row">
                                                        <div className="col-3">
                                                            <Tooltip title="Delete Task" aria-label="Delete Task" className="task-icon">
                                                                <div onClick={deleteTaskClick(task.id, currentProjectId)}>
                                                                    <Fab color="primary" size="small">
                                                                        <DeleteIcon />
                                                                    </Fab>
                                                                </div>
                                                            </Tooltip>
                                                        </div>
                                                        <div className="col-3">
                                                            <Tooltip title="Edit Task" aria-label="Edit Task"  className="task-icon">
                                                                <div onClick={editTaskHandler(task, currentProjectId)}>
                                                                    <Fab color="primary" size="small" >
                                                                        <EditIcon />
                                                                    </Fab>
                                                                </div>
                                                            </Tooltip>
                                                        </div>
                                                        <div className="col-3"></div>
                                                        <div className="col-3"></div>
                                                    </div>
                                                </CardContent>
                                            </Card>
                                        </div> : null
                                    )
                                })}
                                
                            </div>
                            <div className="col task-column">
                                <Typography>
                                    Level 4
                                </Typography>
                                <Divider></Divider>
                                {tasks.tasks.map((task) => {
                                    return( task.level === 4 && !task.completed?
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
                                                    <Divider></Divider>
                                                    <div className="row">
                                                        <div className="col-3">
                                                            <Tooltip title="Delete Task" aria-label="Delete Task" className="task-icon">
                                                                <div onClick={deleteTaskClick(task.id, currentProjectId)}>
                                                                    <Fab color="primary" size="small">
                                                                        <DeleteIcon />
                                                                    </Fab>
                                                                </div>
                                                            </Tooltip>
                                                        </div>
                                                        <div className="col-3">
                                                            <Tooltip title="Edit Task" aria-label="Edit Task"  className="task-icon">
                                                                <div onClick={editTaskHandler(task, currentProjectId)}>
                                                                    <Fab color="primary" size="small" >
                                                                        <EditIcon />
                                                                    </Fab>
                                                                </div>
                                                            </Tooltip>
                                                        </div>
                                                        <div className="col-3"></div>
                                                        <div className="col-3"></div>
                                                    </div>
                                                </CardContent>
                                            </Card>
                                        </div> : null
                                    )
                                })}
                                
                            </div>
                            <div className="col task-column">
                                <Typography>
                                    Level 5
                                </Typography>
                                <Divider></Divider>
                                {tasks.tasks.map((task) => {
                                    return( task.level === 5 && !task.completed?
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
                                                    <Divider></Divider>
                                                    <div className="row">
                                                        <div className="col-3">
                                                            <Tooltip title="Delete Task" aria-label="Delete Task" className="task-icon">
                                                                <div onClick={deleteTaskClick(task.id, currentProjectId)}>
                                                                    <Fab color="primary" size="small">
                                                                        <DeleteIcon />
                                                                    </Fab>
                                                                </div>
                                                            </Tooltip>
                                                        </div>
                                                        <div className="col-3">
                                                            <Tooltip title="Edit Task" aria-label="Edit Task"  className="task-icon">
                                                                <div onClick={editTaskHandler(task, currentProjectId)}>
                                                                    <Fab color="primary" size="small" >
                                                                        <EditIcon />
                                                                    </Fab>
                                                                </div>
                                                            </Tooltip>
                                                        </div>
                                                        <div className="col-3"></div>
                                                        <div className="col-3"></div>
                                                    </div>
                                                </CardContent>
                                            </Card>
                                        </div> : null
                                    )
                                })}
                            </div>
                            <div id="end-task-column" className="col task-column">
                                <Typography>
                                    Completed
                                </Typography>
                                <Divider></Divider>
                                {tasks.tasks.map((task) => {
                                    return( task.completed?
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
                                                    <Divider></Divider>
                                                    <div className="row">
                                                        <div className="col-3">
                                                            <Tooltip title="Delete Task" aria-label="Delete Task" className="task-icon">
                                                                <div onClick={deleteTaskClick(task.id, currentProjectId)}>
                                                                    <Fab color="primary" size="small">
                                                                        <DeleteIcon />
                                                                    </Fab>
                                                                </div>
                                                            </Tooltip>
                                                        </div>
                                                        <div className="col-3">
                                                            <Tooltip title="Edit Task" aria-label="Edit Task"  className="task-icon">
                                                                <div onClick={editTaskHandler(task, currentProjectId)}>
                                                                    <Fab color="primary" size="small" >
                                                                        <EditIcon />
                                                                    </Fab>
                                                                </div>
                                                            </Tooltip>
                                                        </div>
                                                        <div className="col-3"></div>
                                                        <div className="col-3"></div>
                                                    </div>
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