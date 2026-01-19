import styles from "./HeroSection.module.css";
import WhatsAppButton from "./WhatsAppButton";

export default function HeroSection() {
  return (
    <section className={styles.hero}>
      {/* Dark overlay */}
      <div className={styles.overlay} />

      <div className={styles.container}>
        <div className={styles.content}>
          <h1 className={styles.title}>
            Fresh, Organic Produce <br />
            Directly From Local Farmers
          </h1>

          <p className={styles.description}>
            We connect you directly with trusted local farmers,
            delivering freshly harvested vegetables and fruits
            to your home every day — no middlemen, no storage delays.
          </p>

          {/* TRUST POINTS */}
          <ul className={styles.points}>
            <li>✔ Sourced from nearby farms</li>
            <li>✔ Harvested fresh every morning</li>
            <li>✔ Quality-checked before delivery</li>
          </ul>

          <div className={styles.buttons}>
            <button className={styles.primaryBtn}>
              Shop Fresh Produce
            </button>

            <WhatsAppButton />
          </div>
        </div>
      </div>
    </section>
  );
}

