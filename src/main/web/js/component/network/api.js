import axios from 'axios';
//import {Observable} from 'rxjs-es/Observable';
import { Observable } from 'rxjs/Observable';
import QueryString from 'query-string';

const API = {
    domain: "http://localhost:8080",
    feedSummary: "/feed/summary",
    rss: "/feed/rss",
    getFullUrl(api){
        return this.domain + api;
    }
}

const HttpMethod = {
    post: "post",
    get: "get",
    delete: "delete",
    put: "put"
}

class Request{
    constructor(url, data, method = 'get', contentType = "application/json"){
        this.url = url;
        this.data = data;
        this.method = method;
        this.contentType = contentType;
    }
}

function buildRequest(request){
var store = window.masterStore
    var data = request.data
    if(request.contentType === "application/x-www-form-urlencoded"){
        data = QueryString.stringify(request.data)
    }

    var finalRequest = {
        method: request.method,
        url: request.url
    }

    if(request.method === HttpMethod.get){
        finalRequest["params"] = data
    }else{
        finalRequest["data"] = data
    }
/*
    if(store.user.token){
        finalRequest["headers"] = {'Authorization': store.user.token}
    }
*/
    return finalRequest;
}

function call(request) {
    var finalRequest = buildRequest(request);
    return Observable.create(function(observer) {
        axios.request(finalRequest).then(function (response) {
            observer.next(response.data)
            observer.complete()
        }).catch(function (error) {
            observer.error(error)
        });
    });
}

function promiseCall(request){
    var finalRequest = buildRequest(request);
    return axios.request(finalRequest);
}

export {API, HttpMethod, Request, call, promiseCall};