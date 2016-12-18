import React, { Component } from 'react';
import L from 'leaflet';
// postCSS import of Leaflet's CSS
import 'leaflet/dist/leaflet.css';

import './map.scss';

// store the map configuration properties in an object,
// we could also move this to a separate file & import it if desired.
let config = {};
config.params = {
  center: [10.801109,106.6611673],
  zoomControl: false,
  zoom: 13,
  maxZoom: 19,
  minZoom: 1,
  scrollwheel: false,
  legends: true,
  infoControl: false,
  attributionControl: true
};
config.tileLayer = {
  uri: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png',
  params: {
    minZoom: 1,
    id: '',
    accessToken: ''
  }
};

// array to store unique names of Brooklyn subway lines,
// this eventually gets passed down to the Filter component
let subwayLineNames = [];

class Map extends Component {
  constructor(props) {
    super(props);
    this.state = {
      map: null,
      tileLayer: null,
      geojsonLayer: null,
      geojson: null,
      subwayLinesFilter: '*',
      numEntrances: null
    };
    this._mapNode = null;
  }

  componentDidMount() {
    // code to run just after the component "mounts" / DOM elements are created
    // we could make an AJAX request for the GeoJSON data here if it wasn't stored locally
    //this.getData();
    // create the Leaflet map object
    if (!this.state.map) this.init(this._mapNode);
  }

  componentWillUnmount() {
    // code to run just before unmounting the component
    // this destroys the Leaflet map object & related event listeners
    this.state.map.remove();
  }

  init(id) {
    if (this.state.map) return;
    // this function creates the Leaflet map object and is called after the Map component mounts
    let map = L.map(id, config.params);
    L.control.zoom({ position: "bottomleft"}).addTo(map);
    L.control.scale({ position: "bottomleft"}).addTo(map);

    // a TileLayer is used as the "basemap"
    const tileLayer = L.tileLayer(config.tileLayer.uri, config.tileLayer.params).addTo(map);

    map.invalidateSize();

    // set our state to include the tile layer
    this.setState({ map, tileLayer });
  }

  render() {
    return (
      <div id="mapUI">
        <div ref={(node) => this._mapNode = node} id="map" />
      </div>
    );
  }
}

export default Map;