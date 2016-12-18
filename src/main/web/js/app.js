import React from 'react';
import ReactDOM from 'react-dom';

import AppView from './view/AppView';
import HomeView from './view/HomeView';
import AboutView from './view/AppView';
import RSSView from './view/RSSView';
import MapView from './view/MapView';
import NoteView from './view/NoteView';

import { Router, Route, IndexRoute, browserHistory } from 'react-router';

import './app.scss';

ReactDOM.render(
  <Router history={ browserHistory }>
    <Route path='/' component={ AppView }>
      <IndexRoute component={ HomeView } />
      <Route path='rss' component={ RSSView } />
      <Route path='map' component={ MapView } />
      <Route path='note' component={ NoteView } />
      <Route path='about' component={ AboutView } />
    </Route>
  </Router>,
  document.getElementById('app')
);