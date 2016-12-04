import {API, HttpMethod, Request, call} from '../component/network/api'

class RSSStore {
    fetchVNExpress(){
        const request = new Request(API.getFullUrl(API.rss), {url: 'http://vnexpress.net/rss/thoi-su.rss'});
        return call(request);
    }

}

export default RSSStore;