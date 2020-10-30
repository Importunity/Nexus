import { BASE_URL } from "../constants/types";
import { request } from "./RequestJSON";



export function createTask(taskRequest){
    return request({
        url: BASE_URL + "/projects/" + taskRequest.projectId + "/tasks",
        method: "POST",
        body: JSON.stringify(taskRequest)
    })
}