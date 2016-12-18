import {observable} from 'mobx'
import FormModel from './formModel'

class RSSGroup extends FormModel{
    @observable name

    constructor(){
        super()
        this.isValid = true
        this.rules = {
            name: 'required'
        }
    }

    validate() {
        var input = {
            name:this.name
        }
        super.validate(input,this.rules)
    }

}

class RSSItem extends FormModel{
    @observable name;
    @observable url;

    constructor(){
        super()
        this.isValid = true
        this.rules = {
            name: 'required',
            url: 'required'
        }
    }

    validate() {
        var input = {
            name:this.name,
            url: this.url
        }
        super.validate(input,this.rules)
    }
}

export {RSSGroup, RSSItem};