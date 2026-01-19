import { configureStore } from "@reduxjs/toolkit";
import cartReducer from "./cartSlice";
import adminOrdersReducer from "./adminOrdersSlice";
import orderReducer from "./orderSlice";
import productReducer from "./productSlice";
/* ---------- LocalStorage helpers (unchanged) ---------- */

const loadCartFromStorage = () => {
  try {
    const data = localStorage.getItem("cart");
    return data ? JSON.parse(data) : undefined;
  } catch {
    return undefined;
  }
};

const saveCartToStorage = state => {
  try {
    localStorage.setItem("cart", JSON.stringify(state.cart));
  } catch {}
};

/* ---------- STORE ---------- */

const store = configureStore({
  reducer: {
    cart: cartReducer,
    adminOrders: adminOrdersReducer, // ✅ ADD THIS
    order: orderReducer,
    products: productReducer
  },
  preloadedState: {
    cart: loadCartFromStorage()
  }
});

store.subscribe(() => {
  saveCartToStorage(store.getState());
});

export default store;
