import styles from "./AdminOrders.module.css";

export default function OrderDetailsDrawer({
  order,
  onClose
}) {
  if (!order) return null;

  return (
    <>
      {/* Overlay */}
      <div
        className={styles.drawerOverlay}
        onClick={onClose}
      />

      {/* Drawer */}
      <div className={styles.drawer}>
        <div className={styles.drawerHeader}>
          <h3>Order Details</h3>
          <button onClick={onClose}>✕</button>
        </div>

        <div className={styles.drawerContent}>
          <p>
            <strong>Order ID:</strong> {order.id}
          </p>

          <p>
            <strong>Status:</strong>{" "}
            <span
              className={`${styles.badge} ${styles[order.status]}`}
            >
              {order.status}
            </span>
          </p>

          <p>
            <strong>Total Amount:</strong> ₹{order.totalAmount}
          </p>

          {/* EXTENSION POINT */}
          <div className={styles.note}>
            Items, delivery address, and timeline can be
            added here later.
          </div>
        </div>
      </div>
    </>
  );
}
