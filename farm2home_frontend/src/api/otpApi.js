import axios from "axios";

export const sendOtp = (phone) =>
  axios.post("http://localhost:8080/api/otp/send", { phone });

export const verifyOtp = (phone, otp) =>
  axios.post("http://localhost:8080/api/otp/verify", { phone, otp });
