import React, { useEffect, useState } from "react";
import axios from "axios";

function Store() {
  const [products, setProducts] = useState([]);

  const fetchProducts = () => {
    axios.get("http://localhost:8080/api/products")
      .then(res => setProducts(res.data))
      .catch(err => console.error(err));
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  return (
    <div style={{ padding: 20 }}>
      <h1>🛍️ Store</h1>

      <button onClick={fetchProducts}>🔄 Refresh</button>

      {products.length === 0 && <p>No products available</p>}

      <div style={{ display: "flex", gap: 20, flexWrap: "wrap" }}>
        {products.map(p => (
          <div key={p.id} style={{ border: "1px solid #ccc", padding: 10 }}>
            <img
              src={p.imageUrl}
              alt={p.name}
              width="150"
              onError={(e) => e.target.style.display = "none"}
            />
            <h3>{p.name}</h3>
            <p>{p.description}</p>
            <h4>₹{p.price}</h4>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Store;