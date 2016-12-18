import React, { Component } from 'react';
import { Link } from 'react-router';

export default class Home extends Component {
  render() {
    const { children } = this.props;

    return (
      <div className='Home'>
        Home
      </div>
    );
  }
}