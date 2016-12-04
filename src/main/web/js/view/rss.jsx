import React, { Component } from 'react';
import {Grid, Row, Column, Panel, Jumbotron} from 'react-bootstrap';

import RSSStore from '../store/rssStore';


class RSSList extends Component{
    constructor(props){
        super(props);
        var self = this;
        self.rssStore = new RSSStore();
        self.state = {
            collection: []
        };
    }

    componentDidMount(){
        var self = this;
        self.rssStore.fetchVNExpress().subscribe(
            (res) => {
                self.setState({
                    collection: res
                });
            },
            (err) => {
                console.log(err);
            }
        );
    }

    render(){
        var self = this;
        var {collection} = self.state;
        return (
            <Grid>
                <Row>
                    <Panel>VNExpress RSS</Panel>
                </Row>
                {collection.map((model, index) => {
                    return (
                        <Row  key={index}>
                        <Panel header={model.title}>
                        {model.summary}
                        </Panel>
                        </Row>
                    )
                })}
            </Grid>
            );
    }

}

export default RSSList;