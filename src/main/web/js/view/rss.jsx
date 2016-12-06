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

class RSSAddress extends Component{
    constructor(props){
        super(props);
        this.rssSelect = this.rssSelect.bind(this);
    }

    rssSelect(url){
        this.props.store.updateUrl(url);
    }

    render(){        
        return (
            <ListGroup>
                <ListGroupItem href="#thoi-su" onClick={(event) => this.rssSelect('http://vnexpress.net/rss/thoi-su.rss')}>Thoi su</ListGroupItem>
                <ListGroupItem href="#the-gioi" onClick={(event) => this.rssSelect('http://vnexpress.net/rss/the-gioi.rss')}>The gioi</ListGroupItem>
            </ListGroup>
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
            <Grid>
                <Row>
                    <Col md={2}>
                        <RSSAddress store={self.rssStore}/>
                    </Col>
                    <Col md={9}>
                        {children}
                    </Col>
                </Row>                
            </Grid>
            
        );
    }

}

export default RSSList;