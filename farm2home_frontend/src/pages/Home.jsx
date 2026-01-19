import { useEffect, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useLocation } from "react-router-dom";

import Navbar from "../components/Navbar";
import HeroSection from "../components/HeroSection";
import ProductCard from "../components/ProductCard";
import Footer from "../components/Footer";

import { loadProducts, setCategory } from "../store/productSlice";

const categories = ["Vegetables", "Fruits", "Leafy Greens"];

export default function Home() {
  const dispatch = useDispatch();
  const location = useLocation();
  const productsRef = useRef(null);

  const {
    list,
    loading,
    error,
    selectedCategory,
    searchText,
  } = useSelector((state) => state.products);

  /* ✅ Initial load: NO auto-scroll */
  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  /* ✅ Category change logic */
  useEffect(() => {
    // 🚫 DO NOT LOAD PRODUCTS FOR LEAFY GREENS
    if (selectedCategory === "Leafy Greens") return;

    if (selectedCategory === "Vegetables" || selectedCategory === "Fruits") {
      dispatch(loadProducts(selectedCategory));

      // scroll ONLY when navbar asked for it
      if (location.state?.scrollToProducts) {
        setTimeout(() => {
          productsRef.current?.scrollIntoView({
            behavior: "smooth",
            block: "start",
          });
        }, 100);
      }
    }
  }, [dispatch, selectedCategory, location.state]);

  const filteredProducts = searchText
    ? list.filter((p) =>
        p.name.toLowerCase().includes(searchText.toLowerCase())
      )
    : list;

  return (
    <>
      <Navbar />
      <HeroSection />

      {/* CATEGORY SECTION */}
      <section style={{ padding: "40px" }}>
        <h2>Select Category</h2>

        <div style={{ display: "flex", gap: "16px", marginTop: "20px" }}>
          {categories.map((cat) => (
            <button
              key={cat}
              onClick={() => dispatch(setCategory(cat))}
              style={{
                padding: "10px 20px",
                borderRadius: "6px",
                border: "none",
                cursor: "pointer",
                background:
                  selectedCategory === cat ? "#0a6847" : "#e0e0e0",
                color: selectedCategory === cat ? "#fff" : "#000",
              }}
            >
              {cat}
            </button>
          ))}
        </div>
      </section>

      {/* PRODUCTS / COMING SOON */}
      <section ref={productsRef} style={{ padding: "40px" }}>
        <h2>{selectedCategory}</h2>

        {/* ✅ COMING SOON STATE */}
        {selectedCategory === "Leafy Greens" && (
          <p style={{ fontSize: "18px", marginTop: "20px" }}>
            🚧 Coming soon. We’re working on this category!
          </p>
        )}

{loading && <p>Loading products...</p>}

{error && (
  <div style={{ marginTop: "20px", color: "#dc2626" }}>
    <p>⚠️ Unable to load products</p>
    <p style={{ fontSize: "14px" }}>
      Please check your internet connection or try again later.
    </p>
  </div>
)}


        {selectedCategory !== "Leafy Greens" &&
          !loading &&
          filteredProducts.length === 0 && (
            <p>No products found</p>
          )}

        {selectedCategory !== "Leafy Greens" && (
          <div
            style={{
              display: "grid",
              gridTemplateColumns:
                "repeat(auto-fill, minmax(240px, 1fr))",
              gap: "24px",
              marginTop: "20px",
            }}
          >
            {filteredProducts.map((product) => (
              <ProductCard
  key={product.id}
  id={product.id}
  name={product.name}
  basePrice={product.basePrice}
  finalPrice={product.finalPrice}
  discountPercent={product.discountPercent}
  discountActive={product.discountActive}
  image={product.imageUrl}
/>

            ))}
          </div>
        )}
      </section>

      <Footer />
    </>
  );
}
