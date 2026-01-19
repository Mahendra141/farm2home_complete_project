export const buildWhatsAppLink = (phone, message) => {
  return `https://wa.me/${phone}?text=${encodeURIComponent(message)}`;
};

export const orderEnquiryMessage = () =>
  "Hi, I want to order vegetables 🥬";

export const orderStatusMessage = (orderId, status) =>
  `Your order #${orderId} is now ${status}. Thank you for shopping with Farm2Home 🌾`;
