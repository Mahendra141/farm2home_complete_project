import { useState } from "react";
import axios from "@/api/axios";
import styles from "./AdminProducts.module.css";

export default function AdminProductForm({ product, onClose, onSuccess }) {
  const [form, setForm] = useState({
    name: product?.name || "",
    price: product?.basePrice || "",
    discountPercent: product?.discountPercent || "",
    discountActive: product?.discountActive || false,
    category: product?.category || "Vegetables",
    unit: product?.unit || "kg",
    available: product?.available ?? true
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm({
      ...form,
      [name]: type === "checkbox" ? checked : value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");

    const payload = {
      name: form.name,
      price: Number(form.price),
      discountPercent: Number(form.discountPercent) || 0,
      discountActive: form.discountActive,
      category: form.category,
      unit: form.unit,
      available: form.available
    };

    try {
      if (product) {
        await axios.put(
          `/api/admin/products/${product.id}`,
          payload,
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("adminToken")}`
            }
          }
        );
      } else {
        await axios.post(
          "/api/admin/products",
          payload,
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("adminToken")}`
            }
          }
        );
      }

      onSuccess();
      onClose();
    } catch (err) {
      console.error(err);
      setError("Failed to save product");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modal}>
        <h3>{product ? "Edit Product" : "Add Product"}</h3>

        {error && <p className={styles.error}>{error}</p>}

        <form onSubmit={handleSubmit} className={styles.form}>
          <input
            name="name"
            placeholder="Product name"
            value={form.name}
            onChange={handleChange}
            required
          />

          <input
            name="price"
            type="number"
            placeholder="Base Price"
            value={form.price}
            onChange={handleChange}
            required
          />

          <input
            name="discountPercent"
            type="number"
            placeholder="Discount %"
            value={form.discountPercent}
            onChange={handleChange}
            min="0"
            max="100"
            disabled={!form.discountActive}
          />

          {/* TOGGLE: DISCOUNT */}
          <div className={styles.toggleRow}>
            <span>Enable Discount</span>
            <label className={styles.switch}>
              <input
                type="checkbox"
                name="discountActive"
                checked={form.discountActive}
                onChange={handleChange}
              />
              <span className={styles.slider}></span>
            </label>
          </div>

          <select name="category" onChange={handleChange} value={form.category}>
            <option value="Vegetables">Vegetables</option>
            <option value="Fruits">Fruits</option>
          </select>

          <select name="unit" onChange={handleChange} value={form.unit}>
            <option value="kg">kg</option>
            <option value="piece">piece</option>
          </select>

          {/* TOGGLE: AVAILABLE */}
          <div className={styles.toggleRow}>
            <span>Available</span>
            <label className={styles.switch}>
              <input
                type="checkbox"
                name="available"
                checked={form.available}
                onChange={handleChange}
              />
              <span className={styles.slider}></span>
            </label>
          </div>

          <div className={styles.actions}>
            <button type="button" onClick={onClose}>
              Cancel
            </button>
            <button type="submit" disabled={loading}>
              {loading ? "Saving..." : "Save"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
