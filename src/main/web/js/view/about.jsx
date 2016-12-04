import React, { Component } from 'react';

export default class About extends Component {
  render() {
    const { children } = this.props;

    return (
      <div className='About'>
        About
      </div>
    );
  }
}