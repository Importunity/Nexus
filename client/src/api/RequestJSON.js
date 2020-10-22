import { TOKEN } from '../constants/types';

export const request = (options) => {
    // headers content type
    const headers = new Headers({
        'Content-Type': 'application/json',
    });
    // retrieve the token from local storage
    if(localStorage.getItem(TOKEN)){
        // append the local storage token to the bearer and set the key to authorization
        // key = Authorization value = Bearer + token
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(TOKEN))
    }
    // initialize the defaults to the headers
    const defaults = {headers : headers};
    // assigns options to defaults 
    options = Object.assign({}, defaults, options);
    // fetch the json
    return fetch(options.url, options)
        .then(response => 
            response.json()
                .then(json => {
                    // if the response is not ok
                    if(!response.ok){
                        // then reject the json
                        return Promise.reject(json);
                    }
                    return json;
                })
        );
};