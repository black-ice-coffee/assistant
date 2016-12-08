import React, { Component } from 'react';
import {Grid, Row, Col} from 'react-bootstrap';
import {Panel, Jumbotron, Glyphicon, Modal,FormGroup, FormControl, Button, PageHeader} from 'react-bootstrap';
import {ListGroup, ListGroupItem} from 'react-bootstrap';
import {Responsive, WidthProvider} from 'react-grid-layout';
const ReactGridLayout = WidthProvider(Responsive);

import {observer} from 'mobx-react';

import RSSStore from '../store/rssStore';
import {RSSGroup, RSSItem} from '../model/rssModel'

class RSSItemValue extends Component{
    constructor(props){
        super(props);
    }

    render(){
        var model = this.props.model;
        return (
            <Panel header={model.title}>
            {model.summary}
            </Panel>
        );
    }
}

class RSSItemGroup extends Component{
    constructor(props){
        super(props);
        var rssItem = new RSSItem();
        this.state = {
            showModal: false,
            group: this.props.group,
            model: rssItem
        };
        this.rssSelect = this.rssSelect.bind(this);
        this.add = this.add.bind(this);
        this.open = this.open.bind(this);
        this.close = this.close.bind(this);
    }

    add(){
        var {group, model} = this.state
        model.enableValidation();
        if(!model.isValid)return;
        this.props.store.addRssItem(group, model).subscribe(
            (res) => {
                this.close();
                this.setState({group: res.data});
            }
        )
    }

    open(){
        this.setState({showModal: true});
    }

    close() {
    this.setState({ showModal: false });
    }

    handleChange(event) {
        let {model} =  this.state
        model[event.target.name] = event.target.value
    }

    rssSelect(url){
        this.props.store.updateUrl(url);
    }

    render(){
        var group = this.state.group;
        const children = group.items.map((model, index) => {
            return <ListGroupItem key={index} onClick={(event) => this.rssSelect(model.url)}>{model.name}</ListGroupItem>
        });
        return (
            <Panel key={group.name}>
                <div>
                    <labelp>{group.name}</labelp>
                    <Glyphicon glyph="plus" className="clickable pull-right" onClick={this.open }/>
                </div>
                <ListGroup fill>
                    {children}
                </ListGroup>                
                <Modal show={this.state.showModal} onHide={this.close}>
                    <Modal.Header>Add RSS</Modal.Header>
                    <Modal.Body>
                        <form>
                            <FormGroup controlId="formName">
                                <FormControl type="text" name="name" placeholder="RSS Name" onChange={(event) => this.handleChange(event)}/>
                            </FormGroup>
                            <FormGroup controlId="formUrl">
                                <FormControl type="text" name="url" placeholder="RSS URL" onChange={(event) => this.handleChange(event)}/>
                            </FormGroup>
                        </form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button bsStyle="primary" onClick={this.add}>Add</Button>
                        <Button onClick={this.close}>Cancel</Button>
                    </Modal.Footer>
                </Modal>
            </Panel>
        );
    }

}

@observer
class RSSAddress extends Component{
    constructor(props){
        super(props);
        var rssgroup = new RSSGroup();
        this.state = {
            rssGroups : [],
            showModal: false,
            model: rssgroup
        };
        this.add = this.add.bind(this);
        this.open = this.open.bind(this);
        this.close = this.close.bind(this);
        this.loadRssGroup = this.loadRssGroup.bind(this);
    }

    componentDidMount(){
        this.loadRssGroup();
    }

    loadRssGroup(){
        this.props.store.fetchRSS().subscribe(
            (res) => {this.setState({rssGroups: res.data})}
        );
    }

    add(){
        var {model} = this.state
        model.enableValidation();
        if(!model.isValid)return;
        this.props.store.addGroup(model).subscribe(
            (res) => {
                this.loadRssGroup();
            }
        )
    }

    open(){
        this.setState({showModal: true});
    }

    close() {
    this.setState({ showModal: false });
    }

    handleChange(event) {
        let {model} =  this.state
        model[event.target.name] = event.target.value
    }

    render(){
        const groups = this.state.rssGroups.map((model, index) => {
            return <RSSItemGroup group={model} store={this.props.store} key={index}/>;
        });
        return (
            <div>
                <div>
                    <labelp>Groups</labelp>
                    <Glyphicon glyph="plus" className="clickable pull-right" onClick={this.open }/>
                </div>
                {groups}                
                <Modal show={this.state.showModal} onHide={this.close}>
                    <Modal.Header>Add group</Modal.Header>
                    <Modal.Body>
                        <form>
                            <FormGroup controlId="formName">
                                <FormControl type="text" name="name" placeholder="Group Name" onChange={(event) => this.handleChange(event)}/>
                            </FormGroup>
                        </form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button bsStyle="primary" onClick={this.add}>Add</Button>
                        <Button onClick={this.close}>Cancel</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        );
    }
}

@observer
class RSSList extends Component{
    constructor(props){
        super(props);
        var self = this;
        self.rssStore = new RSSStore();
        self.rssStore.items = [];
        this.buildRss  = this.buildRss.bind(this);  
    }

    buildRss(){
        var self = this;
        if(self.rssStore.isLoading){
            return <Panel header="loading">{self.rssStore.url}</Panel>;
        }
        return (
                <div>
                    <Panel>{self.rssStore.url}</Panel>
                    {self.rssStore.items.map((model, index) => {
                        return (
                            <RSSItemValue model={model} key={index}/>
                        )
                    })}
                </div>
        );
    }

    render(){
        var self = this;
        const children = this.buildRss();

        return (
            <Grid fluid>
                <Row>
                    <Col md={2}>
                        <RSSAddress store={self.rssStore}/>
                    </Col>
                    <Col md={10}>
                        {children}
                    </Col>
                </Row>                
            </Grid>
            
        );
    }

}

export default RSSList;