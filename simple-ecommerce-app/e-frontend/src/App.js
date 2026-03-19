import React, { useEffect, useState } from "react";
import axios from "axios";

function App() {
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:8080/products")
      .then(res => setProducts(res.data));
  }, []);

  const addToCart = (product) => {
    setCart([...cart, product]);
  };

  return (
    <div style={{ padding: "20px" }}>
      <h1>Simple E-Commerce</h1>

      <h2>Products</h2>
      <div style={{ display: "flex", gap: "20px" }}>
        {products.map(p => (
          <div key={p.id} style={{ border: "1px solid #ccc", padding: "10px" }}>
            <img src={p.imageUrl} alt="" width="100" />
            <h3>{p.name}</h3>
            <p>₹{p.price}</p>
            <button onClick={() => addToCart(p)}>Add to Cart</button>
          </div>
        ))}
      </div>

      <h2>Cart ({cart.length})</h2>
      {cart.map((item, i) => (
        <p key={i}>{item.name}</p>
      ))}
    </div>
  );
}

export default App;