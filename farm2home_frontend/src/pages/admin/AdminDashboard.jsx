import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchAdminOrders } from "../../store/adminOrdersSlice";
import OrdersStatusChart from "./OrdersStatusChart";
import styles from "./AdminDashboard.module.css";

export default function AdminDashboard() {
  const dispatch = useDispatch();
  const { orders, loading } = useSelector(
    (state) => state.adminOrders
  );

  useEffect(() => {
    if (orders.length === 0) {
      dispatch(fetchAdminOrders());
    }
  }, [dispatch, orders.length]);

  if (loading) return <p>Loading dashboard...</p>;

  const totalOrders = orders.length;

  const deliveredOrders = orders.filter(
    (o) => o.status === "DELIVERED"
  ).length;

  const pendingOrders = orders.filter((o) =>
    ["PLACED", "CONFIRMED", "PACKED", "OUT_FOR_DELIVERY"].includes(o.status)
  ).length;

  const revenue = orders
    .filter((o) => o.status === "DELIVERED")
    .reduce((sum, o) => sum + o.totalAmount, 0);

  return (
    <>
      {/* METRIC CARDS */}
      <div className={styles.grid}>
        <DashboardCard title="Total Orders" value={totalOrders} />
        <DashboardCard title="Pending Orders" value={pendingOrders} />
        <DashboardCard title="Delivered Orders" value={deliveredOrders} />
        <DashboardCard title="Revenue" value={`₹${revenue}`} />
      </div>

      {/* CHART */}
      <OrdersStatusChart orders={orders} />
    </>
  );
}

function DashboardCard({ title, value }) {
  return (
    <div className={styles.card}>
      <p className={styles.title}>{title}</p>
      <h2 className={styles.value}>{value}</h2>
    </div>
  );
}
