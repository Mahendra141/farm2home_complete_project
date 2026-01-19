import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  items: [], // { id, name, price(final), basePrice, qty }
};

const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    addItem(state, action) {
      const item = action.payload;

      const existing = state.items.find(
        (i) => i.id === item.id
      );

      if (existing) {
        existing.qty += 1;
      } else {
        state.items.push({
          id: item.id,
          name: item.name,
          price: item.price,          // final price
          basePrice: item.basePrice,  // 🔥 original price
          qty: 1,
        });
      }
    },

    incrementQty(state, action) {
      const item = state.items.find(
        (i) => i.id === action.payload
      );
      if (item) item.qty += 1;
    },

    decrementQty(state, action) {
      const item = state.items.find(
        (i) => i.id === action.payload
      );
      if (item) {
        item.qty -= 1;
        if (item.qty === 0) {
          state.items = state.items.filter(
            (i) => i.id !== action.payload
          );
        }
      }
    },

    removeItem(state, action) {
      state.items = state.items.filter(
        (i) => i.id !== action.payload
      );
    },

    clearCart(state) {
      state.items = [];
    },
  },
});

/* ===== SELECTORS ===== */

export const selectItemTotal = (state) =>
  state.cart.items.reduce(
    (sum, item) => sum + item.price * item.qty,
    0
  );

export const selectDiscountTotal = (state) =>
  state.cart.items.reduce((sum, item) => {
    if (!item.basePrice) return sum;
    return sum + (item.basePrice - item.price) * item.qty;
  }, 0);

export const {
  addItem,
  incrementQty,
  decrementQty,
  removeItem,
  clearCart,
} = cartSlice.actions;

export default cartSlice.reducer;
