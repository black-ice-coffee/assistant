import React, {Component} from 'react';
import {render} from 'react-dom';
import Map from '../component/Map';

import '../../scss/map.scss';

export default class MapView extends Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div id="map-container">
                <Map />
            </div>
        )
    }
}