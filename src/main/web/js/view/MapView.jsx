import React, {Component} from 'react';
import {render} from 'react-dom';
import Map from '../component/map/Map';

export default class MapView extends Component {
    constructor(props) {
        super(props)
        this.state = {
            width: window.innerWidth,
            height: window.innerHeight - 55
        }
        this.updateSize = this.updateSize.bind(this)
    }

    componentDidMount(){
        window.addEventListener("resize", this.updateSize);
    }

    componentWillUnmount() {
        window.removeEventListener("resize", this.updateSize)
    }

    updateSize(){
        this.setState({
            width: window.innerWidth,
            height: window.innerHeight - 55
        })
    }

    render() {
        const mapPageStyle = {
            height: this.state.height,
            width: this.state.width
        }
        return (
            <div id="map-container" style={mapPageStyle}>
                <Map />
            </div>
        )
    }
}