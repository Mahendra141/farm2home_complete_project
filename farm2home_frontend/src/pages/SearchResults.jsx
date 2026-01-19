import { useSearchParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import styles from "./SearchResults.module.css";

import Navbar from "../components/Navbar";
import ProductCard from "../components/ProductCard";
import Footer from "../components/Footer";

export default function SearchResults() {
  const [params] = useSearchParams();
  const query = params.get("q");

  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!query) return;

    axios
      .get(`http://localhost:8080/api/products/search?q=${query}`)
      .then((res) => setProducts(res.data))
      .finally(() => setLoading(false));
  }, [query]);

  return (
    <>
      <Navbar />

      <section style={{ padding: "40px" }}>
        <h2>Search results for "{query}"</h2>

        {loading && <p>Searching...</p>}

        {!loading && products.length === 0 && (
          <p style={{ marginTop: "20px", color: "#6b7280" }}>
  No products found for "<strong>{query}</strong>"
</p>

        )}

        <div className={styles.grid}>
          {products.map((p) => (
            <ProductCard
  key={p.id}
  id={p.id}
  name={p.name}
  basePrice={p.basePrice}
  finalPrice={p.finalPrice}
  discountPercent={p.discountPercent}
  discountActive={p.discountActive}
  image={p.imageUrl}
/>

          ))}
        </div>
      </section>

      <Footer />
    </>
  );
}
