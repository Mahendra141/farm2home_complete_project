import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  lastOrder: JSON.parse(localStorage.getItem("lastOrder")) || null
};

const orderSlice = createSlice({
  name: "order",
  initialState,
  reducers: {
    setLastOrder(state, action) {
      state.lastOrder = action.payload;
      localStorage.setItem("lastOrder", JSON.stringify(action.payload));
    },
    clearLastOrder(state) {
      state.lastOrder = null;
      localStorage.removeItem("lastOrder");
    }
  }
});

export const { setLastOrder, clearLastOrder } = orderSlice.actions;
export default orderSlice.reducer;




// import { createSlice } from "@reduxjs/toolkit";

// const initialState = {
//   lastOrder: JSON.parse(localStorage.getItem("lastOrder")) || null,
// };

// const orderSlice = createSlice({
//   name: "order",
//   initialState,
//   reducers: {
//     setLastOrder(state, action) {
//       state.lastOrder = action.payload;
//       localStorage.setItem("lastOrder", JSON.stringify(action.payload));
//     },
//     clearLastOrder(state) {
//       state.lastOrder = null;
//       localStorage.removeItem("lastOrder");
//     },
//   },
// });

// export const { setLastOrder, clearLastOrder } = orderSlice.actions;
// export default orderSlice.reducer;
