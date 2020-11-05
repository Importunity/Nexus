import React, { useEffect, useState } from 'react';
import RemoveIcon from '@material-ui/icons/Remove';
import '../../styles/project.css';
import { Fab, Tooltip } from '@material-ui/core';
import { removeProject } from '../../api/ProjectAPI';

export default function DeleteProject(props){
    

    return (
        <div>
            <Fab color="primary" size="small" >
                <RemoveIcon />
            </Fab>
        </div>
    );
}