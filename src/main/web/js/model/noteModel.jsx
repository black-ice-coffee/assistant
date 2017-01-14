import {observable} from 'mobx'
import FormModel from './formModel'

class NoteModel extends FormModel{
    @observable title;

    constructor(){
        super()
        this.isValid = true
        this.rules = {
            title: 'required'
        }
    }

    validate() {
        var input = {
            title:this.title
        }
        super.validate(input,this.rules, { required: 'You forgot to give a :attribute' })
    }
}

export {NoteModel};