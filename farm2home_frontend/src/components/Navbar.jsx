import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import styles from "./Navbar.module.css";
import logo from "@/assets/farm_logo.jpg";
import { ShoppingCart, ChevronDown } from "lucide-react";
import { setCategory } from "@/store/productSlice";
import SearchBox from "./SearchBox";

export default function Navbar() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const items = useSelector((state) => state.cart.items);
  const totalQty = items.reduce((sum, item) => sum + item.qty, 0);

  const selectCategory = (category) => {
    dispatch(setCategory(category));
    navigate("/", { state: { scrollToProducts: true } });
  };

  return (
    <nav className={styles.navbar}>
      {/* LOGO */}
      <Link to="/" className={styles.logoWrap}>
        <img src={logo} alt="Farm2Home" className={styles.logoImg} />
        <span className={styles.logoText}>Farm2Home</span>
      </Link>

      {/* SEARCH */}
      <SearchBox />

      {/* MENU */}
      <ul className={styles.menu}>
        <li><Link to="/">Home</Link></li>
        {/* SHOP MEGA DROPDOWN */}
<li className={styles.shop}>
  <span className={styles.shopTrigger}>
    Shop <ChevronDown size={16} />
  </span>

  <div className={styles.shopDropdown}>
    {/* LEFT COLUMN */}
    <div className={styles.shopColumn}>
      <h4>Fresh Produce</h4>

      <div
        className={styles.shopItem}
        onClick={() => selectCategory("Vegetables")}
      >
        🥦 Vegetables
      </div>

      <div
        className={styles.shopItem}
        onClick={() => selectCategory("Fruits")}
      >
        🍎 Fruits
      </div>

      <div
        className={`${styles.shopItem} ${styles.disabled}`}
      >
        🥛 Milk & Dairy
      </div>
    </div>

    {/* RIGHT INFO CARD */}
    <div className={styles.comingSoon}>
      <h4>Coming Soon 🚀</h4>
      <p>
        Fresh milk & dairy subscriptions directly from local farmers.
      </p>
      <span>Launching shortly</span>
    </div>
  </div>
</li>


        {/* ✅ HOVER CATEGORY DROPDOWN */}
        <li className={styles.category}>
          <span className={styles.categoryTrigger}>
            Categories <ChevronDown size={16} />
          </span>

          <ul className={styles.dropdown}>
            <li onClick={() => selectCategory("Vegetables")}>
              Vegetables
            </li>
            <li onClick={() => selectCategory("Fruits")}>
              Fruits
            </li>
            {/* <li
                onClick={(e) => selectCategory(e, "Leafy Greens")}
              >
                Leafy Greens
              </li> */}
          </ul>
        </li>
      </ul>

      {/* RIGHT ACTIONS */}
      <div className={styles.rightActions}>
       <div className={styles.adminWrap}>
  <Link to="/admin/login" className={styles.adminBtn}>
    Admin
  </Link>

  <div className={styles.adminHint}>
    🔒 Admin access only<br />
    Manage products & orders
  </div>
</div>

        <Link to="/cart" className={styles.cart}>
          <ShoppingCart size={22} />
          {totalQty > 0 && (
            <span className={styles.badge}>{totalQty}</span>
          )}
        </Link>
      </div>
    </nav>
  );
}
