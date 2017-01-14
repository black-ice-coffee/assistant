import React, {Component} from 'react';
import {Editor, EditorState, RichUtils, convertToRaw, convertFromRaw } from "draft-js"

import {Well, Panel, Modal, FormGroup, Glyphicon , FormControl, Button} from 'react-bootstrap';

import {observer} from 'mobx-react';

import NoteStore from '../store/noteStore';

import {NoteModel} from '../model/noteModel';

class NoteItem extends Component {
    constructor(props) {
        super(props)
        this.state = {
            editorState: EditorState.createWithContent(convertFromRaw(JSON.parse(props.note.content))),
            model: props.note,
            editState: 'floppy-saved'
        };
        this.onChange = (editorState) => this.setState({editorState, editState: 'floppy-disk' });
        this.saveNote = this.saveNote.bind(this);
    }

    saveNote(){
        var {model, editorState} = this.state;
        this.props.store.saveNote({id: model.id, title: model.title, content: JSON.stringify(convertToRaw(editorState.getCurrentContent()))}).subscribe(
            (res) => {
                this.setState({editState: 'floppy-saved'})
            }
        )
    }

    render() {
        const {editorState, model, editState} = this.state;
        return (
            <Panel className="panel panel-primary">
                <div>
                    <label>{model.title}</label>
                    <Glyphicon glyph={editState} className="clickable pull-right" onClick={this.saveNote }/>
                </div>
                <hr/>
                <Editor
                    editorState={editorState}
                    onChange={this.onChange}
                />
            </Panel>
        );
    }

}

@observer
class NoteList extends Component {
    constructor(props) {
        super(props)
        var noteModel = new NoteModel()
        this.state = {
            notes: [],
            showModal: false,
            model: noteModel,
            editorState: EditorState.createEmpty(),
            errors: {}
        };
        this.add = this.add.bind(this);
        this.open = this.open.bind(this);
        this.close = this.close.bind(this);
        this.loadNotes = this.loadNotes.bind(this);
        this.onChange = (editorState) => this.setState({editorState});
        this.validationState = this.validationState.bind(this)
        this.validateInput = this.validateInput.bind(this)
    }

    loadNotes() {
        this.props.store.fetchNotes().subscribe(
            (res) => {
                this.setState({notes: res.data})
            }
        );
    }

    componentDidMount() {
        this.loadNotes();
    }

    add() {
        var {model, editorState} = this.state;
        if(!this.validateInput())
            return
        this.props.store.addNote({title: model.title, content: JSON.stringify(convertToRaw(editorState.getCurrentContent()))}).subscribe(
            (res) => {
                this.setState({showModal: false});
                this.loadNotes();
            }
        )
    }

    open() {
        this.setState({showModal: true});
    }

    close() {
        this.setState({showModal: false});
    }

    handleChange(event) {
        let {model} =  this.state
        model[event.target.name] = event.target.value
        this.validateInput()
    }

    validationState(field){
        const {errors} = this.state;
        if(errors[field]){
            return 'error'
        }

        return null
    }

    validateInput(){
        this.setState({errors: {}})
        var {model} = this.state;
        model.enableValidation();
        if (!model.isValid){
            this.setState({errors: model.errors.errors})
        }
        return model.isValid
    }

    render() {

        const {editorState, notes, errors} = this.state;
        const children = notes.map((model, index) => {
            return <NoteItem note={model} store={this.props.store} key={index}/>;
        });
        return (
            <div className="container-fluid">
                <Panel className="panel panel-primary">
                    <div>
                        <labelp>Notes</labelp>
                        <Glyphicon glyph="plus" className="clickable pull-right" onClick={this.open }/>
                    </div>
                </Panel>
                {children}
                <Modal show={this.state.showModal} onHide={this.close}>
                    <Modal.Header>Add Note</Modal.Header>
                    <Modal.Body>
                        <form>
                            <FormGroup controlId="formtitle" validationState={this.validationState('title')}>
                                <FormControl type="text" name="title" placeholder="Note title" onChange={(event) => this.handleChange(event)}/>
                            </FormGroup>
                            <FormGroup controlId="formcontent">
                                <Editor editorState={editorState} onChange={this.onChange} />
                            </FormGroup>
                        </form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button bsStyle="primary" onClick={this.add}>Add</Button>
                        <Button onClick={this.close}>Cancel</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }
}

class NoteView extends Component {
    constructor(props) {
        super(props);
        this.noteStore = new NoteStore();
    }

    render() {
        return (
            <div className="container-fluid">
                <NoteList store={this.noteStore}/>
            </div>
        );
    }
}

export default NoteView;