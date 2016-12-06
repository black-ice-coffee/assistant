import React, { Component } from 'react';
import {Grid, Row, Col, Panel, Jumbotron, ListGroup, ListGroupItem} from 'react-bootstrap';
import {Responsive, WidthProvider} from 'react-grid-layout';
const ReactGridLayout = WidthProvider(Responsive);

import {observer} from 'mobx-react';

import RSSStore from '../store/rssStore';

class RSSItem extends Component{
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

@observer
class RSSAddress extends Component{
    constructor(props){
        super(props);
        this.rssSelect = this.rssSelect.bind(this);
        this.renderGroup = this.renderGroup.bind(this);
        this.state = {
            rssGroups : []
        };
    }

    componentDidMount(){
        this.props.store.fetchRSS().subscribe(
            (res) => {this.setState({rssGroups: res})}
        );
    }

    rssSelect(url){
        this.props.store.updateUrl(url);
    }

    renderGroup(group){
        const children = group.items.map((model, index) => {
            return <ListGroupItem key={index} onClick={(event) => this.rssSelect(model.url)}>{model.name}</ListGroupItem>
        });
        return (
            <Panel header={group.name} key={group.name}>
                <ListGroup>{children}</ListGroup>
            </Panel>
        );
    }

    render(){
        const groups = this.state.rssGroups.map((model, index) => {
            return this.renderGroup(model);
        });
        return (
            <div>{groups}</div>
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
                            <RSSItem model={model} key={index}/>
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