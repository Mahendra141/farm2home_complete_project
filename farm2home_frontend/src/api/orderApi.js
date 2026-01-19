// import api from "../api/axios";

// // Admin
// export const getAllAdminOrders = async () => {
//   const res = await api.get("/api/admin/orders");
//   return res.data;
// };

// export const updateOrderStatus = async (orderId, status) => {
//   await api.put(`/api/admin/orders/${orderId}/status`, null, {
//     params: { status }
//   });
// };

// // User
// export const createOrder = async (payload) => {
//   const res = await api.post("/api/orders", payload);
//   return res.data;
// };
import api from "./axios";

// ✅ USER – CREATE ORDER (OTP FLOW)
export const createOrder = async (payload) => {
  const res = await api.post("/api/orders", payload);
  return res.data;
};
