import styles from './CategoryCard.module.css';

export default function CategoryCard({ title, image }) {
  return (
    <div className={styles.card}>
      <img src={image} alt={title} className={styles.image} />
      <div className={styles.title}>{title}</div>
    </div>
  );
}
