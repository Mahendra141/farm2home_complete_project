import { NavLink } from "react-router-dom";
import styles from "./AdminLayout.module.css";

export default function Sidebar() {
  return (
    <aside className={styles.sidebar}>
      {/* BRAND */}
      <div className={styles.logo}>Farm2Home</div>

      {/* NAVIGATION */}
      <nav className={styles.nav}>
        <NavLink
          to="/admin"
          end
          className={({ isActive }) =>
            isActive ? styles.active : undefined
          }
        >
          Dashboard
        </NavLink>

        <NavLink
          to="/admin/orders"
          className={({ isActive }) =>
            isActive ? styles.active : undefined
          }
        >
          Orders
        </NavLink>

        <NavLink
          to="/admin/products"
          className={({ isActive }) =>
            isActive ? styles.active : undefined
          }
        >
          Products
        </NavLink>
      </nav>
    </aside>
  );
}
