import React from 'react';
import "../../styles/generic/NavBar.css";
const Navbar = ({}) => {
  return (
    <header className="navbar">
      <div className="navbar-container">
        <div className="navbar-brand">
          <img src="/logo.svg" alt="SwasthyaVani Logo" className="navbar-logo" />
          <h1 className="navbar-title">My HealthBuddy</h1>
        </div>
      </div>
    </header>
  );
};

export default Navbar;
