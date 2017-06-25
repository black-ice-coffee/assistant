import React, { Component } from 'react';
import { Link } from 'react-router';
import {Grid, Row, Col} from 'react-bootstrap';

export default class Home extends Component {
  render() {
    const { children } = this.props;

    return (
    <Grid fluid>
      <Row>
        <Col md={4}>
          <div className="coinmarketcap-currency-widget" data-currency="bitcoin" data-base="USD" ></div>
        </Col>
        <Col md={4}>
          <div className="coinmarketcap-currency-widget" data-currency="ethereum" data-base="USD"  data-secondary="BTC"></div>
        </Col>
        <Col md={4}>
          <div className="coinmarketcap-currency-widget" data-currency="ripple" data-base="USD"  data-secondary="BTC"></div>
        </Col>
      </Row>
      <Row>
        <Col md={4}>
          <div><iframe width="100%" height="363px" scrolling="no" src="http://giavangvn.org/tygiahomnay/short/widgets"></iframe></div>
        </Col>
        <Col md={4}>
          <div className="coinmarketcap-currency-widget" data-currency="litecoin" data-base="USD"  data-secondary="BTC"></div>
        </Col>
        <Col md={4}>
          <div className="coinmarketcap-currency-widget" data-currency="ethereum-classic" data-base="USD"  data-secondary="BTC"></div>
        </Col>
      </Row>
    </Grid>
    );
  }
}