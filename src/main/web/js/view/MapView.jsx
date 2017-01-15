import React, {Component} from 'react';
import {render} from 'react-dom';
import { withGoogleMap, GoogleMap, Marker } from "react-google-maps";
import _ from "lodash";

// Wrap all `react-google-maps` components with `withGoogleMap` HOC
// and name it GettingStartedGoogleMap
const GettingStartedGoogleMap = withGoogleMap(props => (
    <GoogleMap
        ref={props.onMapLoad}
        defaultZoom={10}
        defaultCenter={{ lat: 10.800717, lng: 106.6599423 }}
        onClick={props.onMapClick}
    >

    </GoogleMap>
));

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
            <GettingStartedGoogleMap
                containerElement={
                    <div style={mapPageStyle} />
                }
                mapElement={
                    <div style={{ height: `100%` }} />
                }
                onMapLoad={_.noop}
                onMapClick={_.noop}
                onMarkerRightClick={_.noop}
            />
        )
    }
}