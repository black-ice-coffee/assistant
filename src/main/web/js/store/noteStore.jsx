import {API, HttpMethod, Request, call, promiseCall} from '../component/network/api'
import {observable, action} from 'mobx';

export default class NoteStore {
    @observable isLoading;

    notes = [];

    fetchNotes(){
        const request = new Request(API.getFullUrl(API.note), {});
        return call(request);
    }

    queryNotes(){
        this.isLoading = true;
        const request = new Request(API.getFullUrl(API.note), {});
        promiseCall(request).then((respond) => {
            this.notes = respond.data.data;
            this.isLoading = false;
        }).catch((error) => {
            isLoading = false;
        });
    }

    addNote(note){
        const request = new Request(API.getFullUrl(API.note), {title: note.title, content: note.content}, HttpMethod.post);
        return call(request);
    }

    saveNote(note){
        const request = new Request(API.getFullUrl(API.note), {id: note.id, title: note.title, content: note.content}, HttpMethod.post);
        return call(request);
    }

};