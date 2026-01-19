import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api";

// ✅ Accept optional category
export const fetchProducts = async (category) => {
  const url = category
    ? `${API_BASE_URL}/products?category=${category}`
    : `${API_BASE_URL}/products`;

  const response = await axios.get(url);
  return response.data;
};
