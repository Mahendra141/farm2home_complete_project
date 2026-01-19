import styles from "./WhatsAppButton.module.css";
import { buildWhatsAppLink, orderEnquiryMessage } from "../services/whatsappService";

export default function WhatsAppButton() {
  const phone = "916361030376"; // Farm2Home WhatsApp number

  return (
    <button
      className={styles.button}
      onClick={() =>
        window.open(
          buildWhatsAppLink(phone, orderEnquiryMessage()),
          "_blank"
        )
      }
    >
      📲 Order on WhatsApp
    </button>
  );
}
