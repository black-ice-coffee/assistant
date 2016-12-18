import React, { Component } from 'react';
import { Link } from 'react-router';

import HeaderView from './HeaderView';

export default class App extends Component {
  render() {
    const { children } = this.props;

    return (
      <div>
        <HeaderView/>
        <section id="root-container">{ children }</section>
      </div>
    );
  }
}