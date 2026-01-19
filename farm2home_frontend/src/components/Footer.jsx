import styles from './Footer.module.css';

export default function Footer() {
  return (
    <footer className={styles.footer}>
      <div className={styles.container}>
        <div className={styles.section}>
          <h3>Farm2Home</h3>
          <p>
            Fresh vegetables and fruits delivered directly
            from local farmers to your home.
          </p>
        </div>

        <div className={styles.section}>
          <h3>Quick Links</h3>
          <ul>
            <li>Shop</li>
            <li>Categories</li>
            <li>About Us</li>
            <li>Contact</li>
          </ul>
        </div>

        <div className={styles.section}>
          <h3>Delivery</h3>
          <p>Morning: 6 AM – 9 AM</p>
          <p>Evening: 5 PM – 8 PM</p>
          <p>Town delivery only</p>
        </div>

        <div className={styles.section}>
          <h3>Contact</h3>
          <p>📞 +91 9110878083</p>
          <p>📍 palamaner </p>
          <p>📲 WhatsApp orders available</p>
        </div>
      </div>

      <div className={styles.bottom}>
        © {new Date().getFullYear()} Farm2Home. All rights reserved.
      </div>
    </footer>
  );
}
