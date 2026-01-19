import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { sendOtp } from "@/api/otpApi";
import styles from "./DeliveryDetails.module.css";

export default function DeliveryDetails() {
  const navigate = useNavigate();
  const cartItems = useSelector((s) => s.cart.items);

  const [form, setForm] = useState({
    customerName: "",
    phone: "",
    addressLine: "",
    area: "",
    city: "",
    pincode: "",
  });

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (cartItems.length === 0) {
      alert("Cart is empty");
      return;
    }

    const payload = {
      items: cartItems.map((i) => ({
        productName: i.name,
        quantity: i.qty,
        price: i.price,
      })),
      deliveryDetails: { ...form },
      totalAmount: cartItems.reduce(
        (s, i) => s + i.qty * i.price,
        0
      ),
    };

    try {
      await sendOtp(form.phone);

      navigate("/otp-verify", {
        state: { phone: form.phone, payload },
      });
    } catch {
      alert("Failed to send OTP");
    }
  };

  return (
    <div className={styles.container}>
      <h1>Delivery Details</h1>

      <form onSubmit={handleSubmit} className={styles.form}>
        {[
          ["customerName", "Full Name"],
          ["phone", "Phone"],
          ["addressLine", "House / Street"],
          ["area", "Area"],
          ["city", "City"],
          ["pincode", "Pincode"],
        ].map(([name, placeholder]) => (
          <input
            key={name}
            name={name}
            placeholder={placeholder}
            value={form[name]}
            onChange={handleChange}
            required
          />
        ))}

        <button type="submit" className={styles.submitBtn}>
          Send OTP
        </button>
      </form>
    </div>
  );
}
