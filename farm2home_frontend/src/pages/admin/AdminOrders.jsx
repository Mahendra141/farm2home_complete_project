import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  fetchAdminOrders,
  changeOrderStatus
} from "../../store/adminOrdersSlice";
import OrderDetailsDrawer from "./OrderDetailsDrawer";
import styles from "./AdminOrders.module.css";

const STATUS_LIST = [
  "PLACED",
  "CONFIRMED",
  "PACKED",
  "OUT_FOR_DELIVERY",
  "DELIVERED",
  "CANCELLED"
];

const FILTERS = {
  ALL: "ALL",
  TODAY: "TODAY",
  WEEK: "WEEK",
  MONTH: "MONTH"
};

export default function AdminOrders() {
  const dispatch = useDispatch();
  const { orders, loading } = useSelector(
    (state) => state.adminOrders
  );

  const [selectedOrder, setSelectedOrder] = useState(null);
  const [filter, setFilter] = useState(FILTERS.ALL);

  useEffect(() => {
    dispatch(fetchAdminOrders());
  }, [dispatch]);

  if (loading) return <p>Loading orders...</p>;

  // ---- DATE FILTER LOGIC ----
  const now = new Date();

  const filteredOrders = orders.filter((order) => {
    if (filter === FILTERS.ALL) return true;

    const orderDate = new Date(order.createdAt); // 👈 if different, change here

    if (filter === FILTERS.TODAY) {
      return (
        orderDate.toDateString() === now.toDateString()
      );
    }

    if (filter === FILTERS.WEEK) {
      const diffDays =
        (now - orderDate) / (1000 * 60 * 60 * 24);
      return diffDays <= 7;
    }

    if (filter === FILTERS.MONTH) {
      return (
        orderDate.getMonth() === now.getMonth() &&
        orderDate.getFullYear() === now.getFullYear()
      );
    }

    return true;
  });

  return (
    <>
      <div className={styles.wrapper}>
        <div className={styles.header}>
          <h2 className={styles.heading}>Orders</h2>

          {/* DATE FILTER */}
          <div className={styles.filters}>
            {Object.values(FILTERS).map((f) => (
              <button
                key={f}
                className={`${styles.filterBtn} ${
                  filter === f ? styles.activeFilter : ""
                }`}
                onClick={() => setFilter(f)}
              >
                {f === "ALL" && "All"}
                {f === "TODAY" && "Today"}
                {f === "WEEK" && "This Week"}
                {f === "MONTH" && "This Month"}
              </button>
            ))}
          </div>
        </div>

        <table className={styles.table}>
          <thead>
            <tr>
              <th>Order ID</th>
              <th>Total</th>
              <th>Status</th>
              <th>Update</th>
            </tr>
          </thead>

          <tbody>
            {filteredOrders.map((order) => (
              <tr
                key={order.id}
                className={styles.row}
                onClick={() => setSelectedOrder(order)}
              >
                <td>{order.id}</td>
                <td>₹{order.totalAmount}</td>
                <td>
                  <span
                    className={`${styles.badge} ${styles[order.status]}`}
                  >
                    {order.status}
                  </span>
                </td>
                <td onClick={(e) => e.stopPropagation()}>
                  <select
                    className={styles.select}
                    value={order.status}
                    onChange={(e) =>
                      dispatch(
                        changeOrderStatus({
                          orderId: order.id,
                          status: e.target.value
                        })
                      )
                    }
                  >
                    {STATUS_LIST.map((s) => (
                      <option key={s}>{s}</option>
                    ))}
                  </select>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {filteredOrders.length === 0 && (
          <p className={styles.empty}>No orders found</p>
        )}
      </div>

      {/* ORDER DETAILS DRAWER */}
      <OrderDetailsDrawer
        order={selectedOrder}
        onClose={() => setSelectedOrder(null)}
      />
    </>
  );
}
