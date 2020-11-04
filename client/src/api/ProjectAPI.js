import { BASE_URL } from '../constants/types';
import {request} from './RequestJSON';

export function createProject(projectRequest){
    return request({
        url: BASE_URL + "/projects",
        method: "POST",
        body: JSON.stringify(projectRequest)
    })
}


export function userloadProjects(projectRequest){
    return request({
        url: BASE_URL + "/projects",
        method: "GET",
        body: JSON.stringify(projectRequest)
    })

}

export function removeProject(projectRequest){
    return request({
        url: BASE_URL + "/projects/" + projectRequest.projectId,
        method: "DELETE",
        body: JSON.stringify(projectRequest)
    })
}


