import axios from "axios";

const API = "http://localhost:8080/api/products";

export const getProducts = () => axios.get(API);
export const addProduct = (data) => axios.post(API, data);
export const deleteProduct = (id) => axios.delete(`${API}/${id}`);