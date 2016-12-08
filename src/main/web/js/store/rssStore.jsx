import {API, HttpMethod, Request, call, promiseCall} from '../component/network/api'
import {observable, action} from 'mobx';

class RSSStore {

    @observable isLoading;

    url = '';
    items = [];

    fetchRSS(){
        const request = new Request(API.getFullUrl(API.groups), {url: this.url});
        return call(request);
    }

    updateUrl(url){
        this.url = url;
        this.isLoading = true;
        const request = new Request(API.getFullUrl(API.feedSummary), {url: this.url});
        promiseCall(request).then((respond) => {
            this.items = respond.data.data;
            this.isLoading = false;
        }).catch((error) => {
            isLoading = false;
        });
    }

    addGroup(group){
        const request = new Request(API.getFullUrl(API.groups), {name: group.name}, HttpMethod.post);
        return call(request);
    }

    addRssItem(group, rssItem){
        const api = API.groupItems.replace(":id", group.id);
        const request = new Request(API.getFullUrl(api), {name: rssItem.name, url: rssItem.url}, HttpMethod.post);
        return call(request);
    }
}

export default RSSStore;