import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Store from "./pages/Store";
import Admin from "./pages/Admin";

function App() {
  return (
    <Router>
      <nav>
        <Link to="/">Store</Link> | 
        <Link to="/admin">Admin</Link>
      </nav>

      <Routes>
        <Route path="/" element={<Store />} />
        <Route path="/admin" element={<Admin />} />
      </Routes>
    </Router>
  );
}

export default App;