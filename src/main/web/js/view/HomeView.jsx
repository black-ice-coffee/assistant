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
          <div><iframe width="100%" height="300px" scrolling="no" src="//www.tygia.com/api.php?column=1&amp;title=0&amp;chart=0&amp;gold=1&amp;rate=1&amp;expand=0&amp;color=1D4C75&amp;nganhang=VIETCOM&amp;ngoaite=usd,jpy,chf,eur,gbp,aud&amp;fontsize=80&amp;change=0"></iframe></div>
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