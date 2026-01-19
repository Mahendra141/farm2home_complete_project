import { useState, useEffect, useRef } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { verifyOtp, sendOtp } from "@/api/otpApi";
import { createOrder } from "@/api/orderApi";
import { useDispatch } from "react-redux";
import { setLastOrder } from "@/store/orderSlice";
import { clearCart } from "@/store/cartSlice";
import styles from "./OtpVerify.module.css";

export default function OtpVerify() {
  const { state } = useLocation();
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const { phone, payload } = state || {};

  const [otp, setOtp] = useState(["", "", "", "", "", ""]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);
  const [success, setSuccess] = useState(false); // ✅ NEW

  const [seconds, setSeconds] = useState(30);
  const inputsRef = useRef([]);

  useEffect(() => {
    if (seconds === 0) return;
    const t = setInterval(() => setSeconds(s => s - 1), 1000);
    return () => clearInterval(t);
  }, [seconds]);

  const handleChange = (value, index) => {
    if (!/^\d?$/.test(value)) return;
    const newOtp = [...otp];
    newOtp[index] = value;
    setOtp(newOtp);
    if (value && index < 5) inputsRef.current[index + 1].focus();
  };

  const triggerError = () => {
    setError(true);
    setTimeout(() => setError(false), 500);
  };

  const handleVerify = async () => {
    const enteredOtp = otp.join("");
    if (enteredOtp.length !== 6) {
      triggerError();
      return;
    }

    try {
      setLoading(true);
      const res = await verifyOtp(phone, enteredOtp);

      if (!res.data) {
        triggerError();
        return;
      }

      // ✅ SHOW SUCCESS TICK
      setSuccess(true);

      // ⏱ wait for animation
      setTimeout(async () => {
        const orderResponse = await createOrder(payload);

        const fullOrder = {
          id: orderResponse.orderId,
          status: orderResponse.status,
          createdAt: new Date().toISOString(),
          totalAmount: orderResponse.totalAmount,
          items: payload.items,
          deliveryDetails: payload.deliveryDetails
        };

        dispatch(setLastOrder(fullOrder));
        dispatch(clearCart());

        navigate("/order-success");
      }, 1200);

    } catch {
      triggerError();
    } finally {
      setLoading(false);
    }
  };

  const handleResend = async () => {
    await sendOtp(phone);
    setSeconds(30);
    setOtp(["", "", "", "", "", ""]);
    inputsRef.current[0].focus();
  };

  /* ✅ SUCCESS SCREEN */
  if (success) {
    return (
      <div className={styles.wrapper}>
        <div className={styles.successCard}>
          <div className={styles.checkmark}>
            ✓
          </div>
          <h2>OTP Verified</h2>
          <p>Proceeding to checkout…</p>
        </div>
      </div>
    );
  }

  return (
    <div className={styles.wrapper}>
      <div className={`${styles.card} ${error ? styles.shake : ""}`}>
        <h2>Verify OTP</h2>
        <p className={styles.subText}>OTP sent to <b>{phone}</b></p>

        <div className={styles.otpBox}>
          {otp.map((digit, i) => (
            <input
              key={i}
              ref={el => inputsRef.current[i] = el}
              value={digit}
              onChange={e => handleChange(e.target.value, i)}
              maxLength="1"
            />
          ))}
        </div>

        <button
          onClick={handleVerify}
          disabled={loading}
          className={styles.verifyBtn}
        >
          {loading ? "Verifying..." : "Verify OTP"}
        </button>

        <div className={styles.resend}>
          {seconds > 0
            ? <span>Resend OTP in {seconds}s</span>
            : <button onClick={handleResend}>Resend OTP</button>
          }
        </div>
      </div>
    </div>
  );
}
