import { BASE_URL } from "../constants/types";
import { request } from "./RequestJSON";

export function getAllTasks(){
    return request(
        
    )
}

export function getUserTasks(username){
    return request({
        url: BASE_URL + "/users/" + username,
        method: 'GET'
    })
}
