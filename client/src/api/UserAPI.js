import {BASE_URL, TOKEN} from '../constants/types';
import {request} from './RequestJSON';


export function login(loginRequest){
    return request({
        url: BASE_URL + "/auth/login",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function register(registerRequest){
    return request({
        url: BASE_URL + "/auth/register",
        method: 'POST',
        body: JSON.stringify(registerRequest)
    });
}

export function checkUsernameAvailability(username) {
    return request({
        url: BASE_URL + "/user/checkUsernameAvailability?username=" + username,
        method: 'GET'
    });
}

export function checkEmailAvailability(email) {
    return request({
        url: BASE_URL + "/user/checkEmailAvailability?email=" + email,
        method: 'GET'
    });
}

export function getCurrentUser() {
    if(!localStorage.getItem(TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: BASE_URL + "/user/me",
        method: 'GET'
    });
}

export function getUserProfile(username) {
    return request({
        url: BASE_URL + "/users/" + username,
        method: 'GET'
    });
}

