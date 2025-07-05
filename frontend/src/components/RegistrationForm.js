import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom"; 
import "./RegistrationForm.css";

const RegistrationForm = () => {
  const [visitor, setVisitor] = useState({
    name: "",
    email: "",
    phone: "",
    purpose: ""
  });

  const navigate = useNavigate(); 

  const handleChange = (e) => {
    setVisitor({ ...visitor, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post("http://localhost:8080/api/visitors/register", visitor);
      const qrCode = response.data.qrCode;

      navigate("/qr-display", { state: { qrCode } });
    } catch (error) {
      alert("Registration failed. Try again.");
      console.error(error);
    }
  };

  return (
    <div className="registration-form">
      <h2>Visitor Registration</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" name="name" placeholder="Full Name" required onChange={handleChange} />
        <input type="email" name="email" placeholder="Email" required onChange={handleChange} />
        <input type="text" name="phone" placeholder="Phone Number" required onChange={handleChange} />
        <input type="text" name="purpose" placeholder="Purpose of Visit" required onChange={handleChange} />
        <button type="submit">Register</button>
      </form><br />
      <a href="/admin-login" className="admin-link">You're an Admin ? <a href="/admin-login" className="admin">Admin Login</a></a>
    </div>
  );
};

export default RegistrationForm;
