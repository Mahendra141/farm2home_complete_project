import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer
} from "recharts";
import styles from "./AdminDashboard.module.css";

export default function OrdersStatusChart({ orders }) {
  const statusCounts = orders.reduce((acc, order) => {
    acc[order.status] = (acc[order.status] || 0) + 1;
    return acc;
  }, {});

  const data = Object.entries(statusCounts).map(
    ([status, count]) => ({
      status,
      count
    })
  );

  return (
    <div className={styles.chartCard}>
      <h3 className={styles.chartTitle}>Orders by Status</h3>

      <ResponsiveContainer width="100%" height={300}>
        <BarChart data={data}>
          <XAxis dataKey="status" />
          <YAxis allowDecimals={false} />
          <Tooltip />
          <Bar dataKey="count" fill="#0f766e" />
        </BarChart>
      </ResponsiveContainer>
    </div>
  );
}
