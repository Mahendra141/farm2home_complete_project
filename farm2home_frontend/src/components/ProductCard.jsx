import { useDispatch, useSelector } from "react-redux";
import {
  addItem,
  incrementQty,
  decrementQty,
} from "@/store/cartSlice";
import { useState } from "react";
import styles from "./ProductCard.module.css";

export default function ProductCard({
  id,
  name,
  basePrice,
  finalPrice,
  discountPercent,
  discountActive,
  image,
}) {
  const dispatch = useDispatch();
  const [loaded, setLoaded] = useState(false);

  const cartItem = useSelector((state) =>
    state.cart.items.find((i) => i.id === id)
  );

  const qty = cartItem?.qty || 0;

  const imageSrc =
    image && image.trim()
      ? image
      : "/images/vegetables/default.jpg";

  return (
    <div className={styles.card}>
      {/* IMAGE */}
      <div className={styles.imageWrapper}>
        {!loaded && <div className={styles.imagePlaceholder} />}

        <img
          src={imageSrc}
          alt={name}
          loading="lazy"
          className={`${styles.productImage} ${loaded ? styles.loaded : ""
            }`}
          onLoad={() => setLoaded(true)}
          onError={(e) => {
            e.currentTarget.onerror = null;
            e.currentTarget.src =
              "/images/vegetables/default.jpg";
            setLoaded(true);
          }}
        />
      </div>

      {/* INFO */}
      <div className={styles.info}>
        <h3 className={styles.name}>{name}</h3>

        {/* 🔥 PRICE SECTION */}
        <div className={styles.priceBox}>
          {discountActive ? (
            <>
              <span className={styles.originalPrice}>
                ₹{basePrice}
              </span>
              <span className={styles.finalPrice}>
                ₹{finalPrice}
              </span>
              <span className={styles.discountBadge}>
                {discountPercent}% OFF
              </span>
            </>
          ) : (
            <span className={styles.finalPrice}>
              ₹{finalPrice}
            </span>
          )}
          <span className={styles.unit}>/ kg</span>
        </div>

        {qty === 0 ? (
          <button
            className={styles.addBtn}
            onClick={() =>
              dispatch(
                addItem({
                  id,
                  name,
                  price: finalPrice,
                  basePrice: basePrice, // 🔥 REQUIRED
                })
              )

            }
          >
            Add
          </button>
        ) : (
          <div className={styles.qty}>
            <button onClick={() => dispatch(decrementQty(id))}>
              −
            </button>
            <span>{qty}</span>
            <button onClick={() => dispatch(incrementQty(id))}>
              +
            </button>
          </div>
        )}
      </div>
    </div>
  );
}
