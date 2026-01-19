import { useDispatch, useSelector } from "react-redux";
import {
  incrementQty,
  decrementQty,
  removeItem,
  clearCart,
  selectItemTotal,
  selectDiscountTotal,
} from "@/store/cartSlice";
import { useNavigate } from "react-router-dom";
import styles from "./Cart.module.css";

export default function Cart() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const items = useSelector((state) => state.cart.items);

  const itemTotal = useSelector(selectItemTotal);
  const discountTotal = useSelector(selectDiscountTotal);

  const deliveryFee = itemTotal > 299 ? 0 : 20;
  const grandTotal = itemTotal - discountTotal + deliveryFee;

  if (items.length === 0) {
    return (
      <div className={styles.container}>
        <h2>Your cart is empty 🛒</h2>
        <button className={styles.shopBtn} onClick={() => navigate("/")}>
          ← Go back to Shopping
        </button>
      </div>
    );
  }

  return (
    <div className={styles.container}>
      <h1 className={styles.title}>Your Cart</h1>

      <div className={styles.layout}>
        {/* ===== CART ITEMS ===== */}
        <div className={styles.cartCard}>
          {items.map((item) => (
            <div key={item.id} className={styles.cartItem}>
              <div>
                <div className={styles.name}>{item.name}</div>
                <div className={styles.unitPrice}>₹{item.price} / kg</div>
              </div>

              <div className={styles.qty}>
                <button onClick={() => dispatch(decrementQty(item.id))}>−</button>
                <span>{item.qty}</span>
                <button onClick={() => dispatch(incrementQty(item.id))}>+</button>
              </div>

              <div className={styles.lineTotal}>
                ₹{(item.price * item.qty).toFixed(1)}
              </div>

              <button
                className={styles.remove}
                onClick={() => dispatch(removeItem(item.id))}
              >
                ✕
              </button>
            </div>
          ))}
        </div>

        {/* ===== BILL SUMMARY ===== */}
        <div className={styles.billCard}>
          <h3>Bill Summary</h3>

          {discountTotal > 0 && (
            <div className={styles.savings}>
              🎉 You saved ₹{discountTotal} on this order
            </div>
          )}

          <div className={styles.row}>
            <span>Item Total</span>
            <span>₹{itemTotal}</span>
          </div>

          <div className={styles.row}>
            <span>Discount</span>
            <span className={styles.discount}>-₹{discountTotal}</span>
          </div>

          <div className={styles.row}>
            <span>Delivery Fee</span>
            <span>{deliveryFee === 0 ? "FREE" : `₹${deliveryFee}`}</span>
          </div>

          <hr />

          <div className={styles.totalRow}>
            <span>Grand Total</span>
            <span>₹{grandTotal}</span>
          </div>

          <button
            className={styles.checkout}
            onClick={() => navigate("/delivery")}
          >
            Proceed to Delivery
            <div className={styles.subText}>Delivery in 30–45 mins</div>
          </button>

          <button
            className={styles.clear}
            onClick={() => dispatch(clearCart())}
          >
            Clear Cart
          </button>

          <div className={styles.trust}>
            ✔ Fresh from farms <br />
            ✔ Same-day delivery <br />
            ✔ No middlemen
          </div>
        </div>
      </div>
    </div>
  );
}
