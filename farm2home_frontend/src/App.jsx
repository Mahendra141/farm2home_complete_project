import { BrowserRouter, Routes, Route } from "react-router-dom";
import { useEffect } from "react";

import Home from "./pages/Home";
import Cart from "./pages/Cart";
import DeliveryDetails from "./pages/DeliveryDetails";
import OrderSuccess from "./pages/OrderSuccess";
import SearchResults from "./pages/SearchResults";
import OtpVerify from "./pages/OtpVerify";

import AdminLogin from "./pages/admin/AdminLogin";
import AdminOrders from "./pages/admin/AdminOrders";

import AdminLayout from "./layouts/admin/AdminLayout";
import AdminProtectedRoute from "./routes/AdminProtectedRoute";
import AdminDashboard from "./pages/admin/AdminDashboard";
import AdminProducts from "./pages/admin/AdminProducts";

export default function App() {

  // ✅ DISABLE BROWSER SCROLL RESTORATION (CRITICAL FIX)
  useEffect(() => {
    if ("scrollRestoration" in window.history) {
      window.history.scrollRestoration = "manual";
    }
  }, []);

  return (
    <BrowserRouter>
      <Routes>
        {/* USER */}
        <Route path="/" element={<Home />} />
        <Route path="/search" element={<SearchResults />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/delivery" element={<DeliveryDetails />} />
        <Route path="/order-success" element={<OrderSuccess />} />
        <Route path="/otp-verify" element={<OtpVerify />} />

        {/* ADMIN LOGIN */}
        <Route path="/admin/login" element={<AdminLogin />} />

        {/* ADMIN PROTECTED */}
        <Route element={<AdminProtectedRoute />}>
          <Route path="/admin" element={<AdminLayout />}>
            <Route index element={<AdminDashboard />} />
            <Route path="orders" element={<AdminOrders />} />
            <Route path="products" element={<AdminProducts />} />
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
