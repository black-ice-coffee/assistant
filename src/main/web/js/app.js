import React from 'react';
import ReactDOM from 'react-dom';

import App from './view/app';
import Home from './view/home';
import About from './view/about';
import RSSList from './view/rss';

import { Router, Route, IndexRoute, browserHistory } from 'react-router';

import '../scss/app.scss';

ReactDOM.render(
  <Router history={ browserHistory }>
    <Route path='/' component={ App }>
      <IndexRoute component={ Home } />
      <Route path='rss' component={ RSSList } />
      <Route path='about' component={ About } />
    </Route>
  </Router>,
  document.getElementById('app')
);