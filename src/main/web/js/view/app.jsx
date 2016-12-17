import React, { Component } from 'react';
import { Link } from 'react-router';

import Header from './header';

export default class App extends Component {
  render() {
    const { children } = this.props;

    return (
      <div>
        <Header/>
        <section id="root-container">{ children }</section>
      </div>
    );
  }
}