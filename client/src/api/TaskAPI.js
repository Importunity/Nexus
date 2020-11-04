import { BASE_URL } from "../constants/types";
import { request } from "./RequestJSON";



export function createTask(taskRequest){
    return request({
        url: BASE_URL + "/projects/" + taskRequest.projectId + "/tasks",
        method: "POST",
        body: JSON.stringify(taskRequest)
    })
}

export function removeTask(taskRequest){
    return request({
        url: BASE_URL + "/projects/" + taskRequest.projectId + "/" + taskRequest.taskId,
        method: "DELETE",
        body: JSON.stringify(taskRequest)
    })
}

export function updateTask(taskRequest){
    return request({
        url: BASE_URL + "/projects/" + taskRequest.projectId + "/" + taskRequest.task.id,
        method: "PUT",
        body: JSON.stringify(taskRequest)
    })
}