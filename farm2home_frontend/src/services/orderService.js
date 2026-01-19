import axios from "../api/axios";

export const getAllOrders = async () => {
  const token = localStorage.getItem("adminToken");

  const res = await axios.get("/api/admin/orders", {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return res.data; // ✅ MUST return data only
};

export const updateOrderStatus = async (orderId, status) => {
  const token = localStorage.getItem("adminToken");

  await axios.put(
    `/api/admin/orders/${orderId}/status`,
    null,
    {
      params: { status }, // ✅ matches @RequestParam
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }
  );
};
