import {API, HttpMethod, Request, call, promiseCall} from '../component/network/api'
import {observable, action} from 'mobx';

class RSSStore {

    @observable isLoading;

    url = '';
    items = [];

    fetch(){
        const request = new Request(API.getFullUrl(API.rss), {url: this.url});
        return call(request);
    }

    updateUrl(url){
        this.url = url;
        this.isLoading = true;
        const request = new Request(API.getFullUrl(API.rss), {url: this.url});
        promiseCall(request).then((respond) => {
            this.items = respond.data;
            this.isLoading = false;
        }).catch((error) => {
            isLoading = false;
        });        
    }
}

export default RSSStore;