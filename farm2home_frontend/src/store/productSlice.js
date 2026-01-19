import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { fetchProducts } from "@/services/productService";

// async thunk (supports category)
export const loadProducts = createAsyncThunk(
  "products/loadProducts",
  async (category) => {
    return await fetchProducts(category);
  }
);

const productSlice = createSlice({
  name: "products",
  initialState: {
    list: [],
    loading: false,
    error: null,
    selectedCategory: "Vegetables",
    searchText: "",
  },
  reducers: {
    setCategory(state, action) {
      state.selectedCategory = action.payload;
      state.searchText = "";
    },
    setSearchText(state, action) {
      state.searchText = action.payload;
    },
    clearSearch(state) {
      state.searchText = "";
    },
  },
  extraReducers: (builder) => {
    builder
      // 🔄 REQUEST START
      .addCase(loadProducts.pending, (state) => {
        state.loading = true;
        state.error = null;
        state.list = []; // ✅ CLEAR OLD DATA
      })

      // ✅ SUCCESS
      .addCase(loadProducts.fulfilled, (state, action) => {
        state.list = action.payload;
        state.loading = false;
        state.error = null;
      })

      // ❌ FAILURE
      .addCase(loadProducts.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || "Network Error";
        state.list = []; // ✅ VERY IMPORTANT
      });
  },
});

export const { setCategory, setSearchText, clearSearch } =
  productSlice.actions;

export default productSlice.reducer;
