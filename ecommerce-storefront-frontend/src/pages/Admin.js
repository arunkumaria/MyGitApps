import React, { useState, useEffect } from "react";
import axios from "axios";

function Admin() {
  const [product, setProduct] = useState({
    name: "",
    price: "",
    description: "",
    imageUrl: ""
  });

  const [products, setProducts] = useState([]);

  const fetchProducts = () => {
    axios.get("http://localhost:8080/api/products")
      .then(res => setProducts(res.data));
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  const handleChange = (e) => {
    setProduct({
      ...product,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async () => {
    try {
      await axios.post("http://localhost:8080/api/products", {
        ...product,
        price: parseFloat(product.price)
      });

      alert("✅ Product Added");

      setProduct({
        name: "",
        price: "",
        description: "",
        imageUrl: ""
      });

      fetchProducts(); // refresh list

    } catch (err) {
      console.error(err);
      alert("❌ Error adding product");
    }
  };

  const handleDelete = async (id) => {
    await axios.delete(`http://localhost:8080/api/products/${id}`);
    fetchProducts();
  };

  return (
    <div style={{ padding: 20 }}>
      <h1>📊 Admin Dashboard</h1>

      <h2>Add Product</h2>

      <input name="name" value={product.name} placeholder="Name" onChange={handleChange} /><br />
      <input name="price" value={product.price} placeholder="Price" onChange={handleChange} /><br />
      <input name="imageUrl" value={product.imageUrl} placeholder="Image URL" onChange={handleChange} /><br />
      <input name="description" value={product.description} placeholder="Description" onChange={handleChange} /><br />

      <button onClick={handleSubmit}>Add Product</button>

      <hr />

      <h2>All Products</h2>

      {products.map(p => (
        <div key={p.id} style={{ borderBottom: "1px solid #ccc", marginBottom: 10 }}>
          <b>{p.name}</b> - ₹{p.price}
          <button onClick={() => handleDelete(p.id)} style={{ marginLeft: 10 }}>
            Delete
          </button>
        </div>
      ))}
    </div>
  );
}

export default Admin;