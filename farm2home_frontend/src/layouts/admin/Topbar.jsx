import { useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./AdminLayout.module.css";

export default function Topbar() {
  const navigate = useNavigate();
  const [showConfirm, setShowConfirm] = useState(false);

  // Temporary source (can come from API later)
  const adminName = localStorage.getItem("adminName") || "Admin";

  const handleLogout = () => {
    localStorage.removeItem("adminToken");
    localStorage.removeItem("adminName");
    navigate("/");
  };

  return (
    <>
      <header className={styles.topbar}>
        <h3 className={styles.title}>Admin Panel</h3>

        <div className={styles.adminInfo}>
          <div className={styles.avatar}>
            {adminName.charAt(0).toUpperCase()}
          </div>
          <span className={styles.name}>{adminName}</span>

          <button
            className={styles.logoutBtn}
            onClick={() => setShowConfirm(true)}
          >
            Logout
          </button>
        </div>
      </header>

      {/* CONFIRM MODAL */}
      {showConfirm && (
        <div className={styles.overlay}>
          <div className={styles.modal}>
            <h4>Confirm Logout</h4>
            <p>Are you sure you want to logout?</p>

            <div className={styles.actions}>
              <button
                className={styles.cancelBtn}
                onClick={() => setShowConfirm(false)}
              >
                Cancel
              </button>
              <button
                className={styles.confirmBtn}
                onClick={handleLogout}
              >
                Logout
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
