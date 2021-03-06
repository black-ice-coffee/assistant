import React, {Component} from 'react';
import {Navbar, Nav, NavItem, NavDropdown, Glyphicon, MenuItem} from 'react-bootstrap';
import { browserHistory } from 'react-router';

export default class Header extends Component{
    constructor(props){
        super(props);
    }

    render(){
        return (
            <Navbar fluid className="navbar-fixed-top">
                <Navbar.Header>
                    <Navbar.Brand>
                        <a href="/">Assistant</a>
                    </Navbar.Brand>
                    <Navbar.Toggle />
                </Navbar.Header>
                <Navbar.Collapse>
                    <Nav>
                        <NavItem href="/rss">RSS</NavItem>
                        <NavItem href="/note">Note</NavItem>
                        <NavItem href="/map">Map</NavItem>
                    </Nav>
                    <Nav pullRight>
                        <NavItem href="/about">About</NavItem>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }

}