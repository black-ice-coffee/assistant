import {observable, observe} from 'mobx';

import Validator from 'validatorjs'

export default class FormModel{
    @observable isValid;
    @observable isValidating;
    @observable error;
    constructor() {
        this.isValid = true
        this.isValidating = false
        this.error = {}
        this.rules = {}
    }

    enableValidation(){
        var self = this
        if(!this.isValidating){
            this.isValidating = true
            this.disposer = observe(this, (change) => {
                for(let key of Object.keys(this.rules)){
                    if(change.name === key){
                        self.validate()
                        return;
                    }
                }
            });
        }
        this.validate()
    }

    disableValidation(){
        if(this.isValidating){
            this.isValidating = false
            this.disposer()
        }
    }

    validate(inputs,rules){
        if(this.isValidating){
            const validator = new Validator(inputs,rules);
            validator.passes();
            if(validator.errorCount > 0){
                this.isValid = false
                this.error = validator.errors.all();
            }else{
                this.isValid = true
                this.error = {}
            }
        }
    }
}