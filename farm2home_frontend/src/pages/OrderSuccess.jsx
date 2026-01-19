import { useNavigate } from "react-router-dom";
import { useRef, useState, useEffect } from "react";
import { useSelector } from "react-redux";
import Confetti from "react-confetti";
import styles from "./OrderSuccess.module.css";

const WHATSAPP_NUMBER = "919441219018";

export default function OrderSuccess() {
  const navigate = useNavigate();
  const order = useSelector((s) => s.order.lastOrder);

  const cardRef = useRef(null);
  const whatsappOpenedRef = useRef(false);

  const [showConfetti, setShowConfetti] = useState(true);
  const [dimensions, setDimensions] = useState({ width: 0, height: 0 });

  useEffect(() => {
    if (cardRef.current) {
      setDimensions({
        width: cardRef.current.offsetWidth,
        height: cardRef.current.offsetHeight,
      });
    }

    const t = setTimeout(() => setShowConfetti(false), 3000);
    return () => clearTimeout(t);
  }, []);

  useEffect(() => {
    if (!order || whatsappOpenedRef.current) return;

    const t = setTimeout(() => {
      handleWhatsApp();
      whatsappOpenedRef.current = true;
    }, 2500);

    return () => clearTimeout(t);
  }, [order]);

  if (!order) {
    return (
      <div className={styles.wrapper}>
        <h2>Order placed successfully 🎉</h2>
        <button onClick={() => navigate("/")}>Back to Home</button>
      </div>
    );
  }

  const delivery = order.deliveryDetails || {};
  const fullAddress =
    [
      delivery.addressLine,
      delivery.area,
      delivery.city,
      delivery.pincode,
    ]
      .filter(Boolean)
      .join(", ") || "Address not provided";

  const handleWhatsApp = () => {
    let msg = `🛒 *Farm2Home – New Order*\n\n`;
    msg += `🆔 Order ID: ${order.id}\n`;
    msg += `📅 Date: ${new Date(order.createdAt).toLocaleString()}\n\n`;

    msg += `📦 *Items*\n`;
    order.items?.length
      ? order.items.forEach(
          (i, idx) =>
            (msg += `${idx + 1}. ${i.productName} × ${i.quantity}\n`)
        )
      : (msg += "No items found\n");

    msg += `\n💰 *Total:* ₹${order.totalAmount}\n\n`;
    msg += `📍 *Delivery*\n`;
    msg += `${delivery.customerName || "N/A"}\n`;
    msg += `${delivery.phone || "N/A"}\n`;
    msg += `${fullAddress}\n\n`;

    if (fullAddress !== "Address not provided") {
      msg += `🗺️ https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(
        fullAddress
      )}\n\n`;
    }

    msg += `🚚 Please confirm delivery time.`;

    window.open(
      `https://wa.me/${WHATSAPP_NUMBER}?text=${encodeURIComponent(msg)}`,
      "_blank"
    );
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.card} ref={cardRef}>
        {showConfetti && (
          <Confetti
            width={dimensions.width}
            height={dimensions.height}
            recycle={false}
          />
        )}

        <h1 className={styles.title}>🎉 Order Confirmed!</h1>

        <p><b>Order ID:</b> #{order.id}</p>
        <p><b>Status:</b> {order.status}</p>

        <h3>Items</h3>
        {order.items?.length ? (
          order.items.map((i, idx) => (
            <div key={idx} className={styles.itemRow}>
              <span>{i.productName} × {i.quantity}</span>
              <span>₹{i.price * i.quantity}</span>
            </div>
          ))
        ) : (
          <p>No items found</p>
        )}

        <div className={styles.total}>Total: ₹{order.totalAmount}</div>

        <button className={styles.whatsapp} onClick={handleWhatsApp}>
          Checkout on WhatsApp
        </button>

        <button className={styles.back} onClick={() => navigate("/")}>
          Back to Home
        </button>
      </div>
    </div>
  );
}
