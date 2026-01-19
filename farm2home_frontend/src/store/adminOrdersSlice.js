import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import {
  getAllOrders,
  updateOrderStatus
} from "../services/orderService";

/**
 * ✅ Fetch all admin orders
 * IMPORTANT: getAllOrders() MUST return response.data
 */
export const fetchAdminOrders = createAsyncThunk(
  "adminOrders/fetch",
  async (_, { rejectWithValue }) => {
    try {
      const data = await getAllOrders(); // ✅ only JSON array
      return data;
    } catch (err) {
      return rejectWithValue("Failed to fetch orders");
    }
  }
);

/**
 * ✅ Update order status
 */
export const changeOrderStatus = createAsyncThunk(
  "adminOrders/updateStatus",
  async ({ orderId, status }, { rejectWithValue }) => {
    try {
      await updateOrderStatus(orderId, status);
      return { orderId, status };
    } catch (err) {
      return rejectWithValue("Failed to update status");
    }
  }
);

const adminOrdersSlice = createSlice({
  name: "adminOrders",
  initialState: {
    orders: [],
    loading: false,
    error: null
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      // FETCH ORDERS
      .addCase(fetchAdminOrders.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchAdminOrders.fulfilled, (state, action) => {
        state.loading = false;
        state.orders = action.payload; // ✅ PURE JSON
      })
      .addCase(fetchAdminOrders.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      })

      // UPDATE STATUS
      .addCase(changeOrderStatus.fulfilled, (state, action) => {
        const order = state.orders.find(
          (o) => o.id === action.payload.orderId
        );
        if (order) {
          order.status = action.payload.status;
        }
      });
  }
});

export default adminOrdersSlice.reducer;
