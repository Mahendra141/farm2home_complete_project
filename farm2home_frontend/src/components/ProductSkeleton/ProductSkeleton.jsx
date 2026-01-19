import styles from "./ProductSkeleton.module.css";

export default function ProductSkeleton() {
  return (
    <div className={styles.card}>
      <div className={`${styles.image} ${styles.skeleton}`} />
      <div className={`${styles.title} ${styles.skeleton}`} />
      <div className={`${styles.price} ${styles.skeleton}`} />
      <div className={`${styles.button} ${styles.skeleton}`} />
    </div>
  );
}
