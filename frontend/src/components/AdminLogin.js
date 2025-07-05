import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "./AdminLogin.css";

const AdminLogin = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post("http://localhost:8080/api/admin/login", {
        email,
        password
      });

      if (response.data.status === "success") {
        localStorage.setItem("isAdminLoggedIn", "true");
        navigate("/admin-dashboard");
      } else {
        alert("Invalid login");
      }
    } catch (error) {
      alert("Login failed");
      console.error("Login error:", error);
    }
  };

  return (
    <div className="admin-dashboard">
      <h2>Admin Login</h2>
      <form onSubmit={handleLogin}>
        <input type="email" placeholder="Email" required value={email} onChange={(e) => setEmail(e.target.value)} />
        <input type="password" placeholder="Password" required value={password} onChange={(e) => setPassword(e.target.value)} />
        <button type="submit">Login</button>
      </form> <br />
      <a href="/registration-form" className="visitor-link">You're a Visitor ? <a href="/registration-form" className="visitor">Visitor Registration</a></a>

    </div>
  );
};

export default AdminLogin;
