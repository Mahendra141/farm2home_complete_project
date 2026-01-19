import { useEffect, useState } from "react";
import axios from "@/api/axios";
import AdminProductForm from "./AdminProductForm";
import styles from "./AdminProducts.module.css";

export default function AdminProducts() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [showForm, setShowForm] = useState(false);
  const [editProduct, setEditProduct] = useState(null);
  const [deleteTarget, setDeleteTarget] = useState(null);
  const [deleting, setDeleting] = useState(false);

  const fetchProducts = async () => {
    try {
      const res = await axios.get("/api/admin/products");
      setProducts(res.data);
    } catch {
      setError("Failed to load products");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  const handleDelete = async () => {
    if (!deleteTarget) return;

    setDeleting(true);
    try {
      await axios.delete(
        `/api/admin/products/${deleteTarget.id}`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("adminToken")}`
          }
        }
      );
      setDeleteTarget(null);
      fetchProducts();
    } catch {
      alert("Failed to disable product");
    } finally {
      setDeleting(false);
    }
  };

  if (loading) return <p>Loading products...</p>;
  if (error) return <p className={styles.error}>{error}</p>;

  return (
    <div className={styles.wrapper}>
      <div className={styles.header}>
        <h2>Products</h2>
        <button
          className={styles.addBtn}
          onClick={() => setShowForm(true)}
        >
          + Add Product
        </button>
      </div>

      <table className={styles.table}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Image</th>
            <th>Name</th>
            <th>Category</th>
            <th>Unit</th>
            <th>Price</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {products.map((p) => (
            <tr
              key={p.id}
              className={!p.available ? styles.disabledRow : ""}
            >
              <td>{p.id}</td>
              <td>
                <img
                  src={p.imageUrl}
                  alt={p.name}
                  className={styles.image}
                />
              </td>
              <td>{p.name}</td>
              <td>{p.category}</td>
              <td>{p.unit}</td>
              <td>
                <strong>₹{p.finalPrice}</strong>
                {p.discountActive && (
                  <div style={{ fontSize: "12px", color: "#16a34a" }}>
                    {p.discountPercent}% OFF
                  </div>
                )}
              </td>
              <td>
                <span
                  className={
                    p.available
                      ? styles.badgeActive
                      : styles.badgeInactive
                  }
                >
                  {p.available ? "Active" : "Disabled"}
                </span>
              </td>
              <td>
                {p.available && (
                  <>
                    <button
                      className={styles.editBtn}
                      onClick={() => setEditProduct(p)}
                    >
                      Edit
                    </button>
                    <button
                      className={styles.deleteBtn}
                      onClick={() => setDeleteTarget(p)}
                    >
                      Delete
                    </button>
                  </>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* ADD / EDIT PRODUCT MODAL */}
      {(showForm || editProduct) && (
        <AdminProductForm
          product={editProduct}
          onClose={() => {
            setShowForm(false);
            setEditProduct(null);
          }}
          onSuccess={fetchProducts}
        />
      )}

      {/* DELETE CONFIRMATION */}
      {deleteTarget && (
        <div className={styles.modalOverlay}>
          <div className={styles.modal}>
            <h3>Disable Product</h3>
            <p>
              Are you sure you want to disable{" "}
              <b>{deleteTarget.name}</b>?
            </p>

            <div className={styles.actions}>
              <button onClick={() => setDeleteTarget(null)}>
                Cancel
              </button>
              <button
                className={styles.deleteBtn}
                onClick={handleDelete}
                disabled={deleting}
              >
                {deleting ? "Disabling..." : "Yes, Disable"}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
