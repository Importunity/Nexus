import { Card } from '@material-ui/core';
import React from 'react';
import '../../styles/project.css';



function Projects(){
    return (
        <div className="initial-container project-container">
            <div className="row">
                <div className="col-md-3">
                    <Card className="project-sidebar" border={1}>
                        Hello
                    </Card>
                </div>
                <div className="col-md-9">
                    Hello
                </div>
            </div>
        </div>
        
    )
}

export default Projects;