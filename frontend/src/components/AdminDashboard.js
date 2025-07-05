import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Html5QrcodeScanner } from "html5-qrcode";
import "./AdminDashboard.css";

const AdminDashboard = () => {
  const [visitors, setVisitors] = useState([]);
  const [showScanner, setShowScanner] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const isLoggedIn = localStorage.getItem("isAdminLoggedIn");
    if (!isLoggedIn) {
      navigate("/admin-login");
    }
  }, [navigate]);

  useEffect(() => {
    fetchVisitors();
  }, []);

  const fetchVisitors = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/admin/visitors");
      setVisitors(response.data);
    } catch (error) {
      console.error("Error fetching visitors:", error);
    }
  };

  useEffect(() => {
    if (showScanner) {
      const scanner = new Html5QrcodeScanner("qr-reader", {
        fps: 10,
        qrbox: 250
      });

      scanner.render(
        async (decodedText) => {
          scanner.clear();
          setShowScanner(false);

          try {
            const response = await axios.post("http://localhost:8080/api/admin/verify-visitor", {
              qrText: decodedText
            });

            if (response.data.status === "success") {
              alert(`✅ Visitor Verified: ${response.data.visitorName}`);
              fetchVisitors();
            } else {
              alert("❌ Visitor not found.");
            }
          } catch (err) {
            console.error("Verification failed", err);
            alert("⚠️ Error during verification.");
          }
        },
        (error) => {
          console.warn("QR scanning error:", error);
        }
      );
    }
  }, [showScanner]);

  const handleLogout = () => {
    localStorage.removeItem("isAdminLoggedIn");
    navigate("/admin-login");
  };

  return (
    <div className="admin-dashboard">
      <h2>Visitor Logs</h2>

      <div className="admin-buttons">
        <button className="back-button" onClick={() => navigate("/registration-form")}>
          Back to Registration
        </button>

        <button className="verify-button" onClick={() => setShowScanner(true)}>
          Scan & Verify Visitor
        </button>

        <button className="logout-button" onClick={handleLogout}>
          Logout
        </button>
      </div>

      {showScanner && <div id="qr-reader" style={{ marginTop: "20px" }}></div>}

      <table className="visitor-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Purpose</th>
            <th>Check-In Time</th>
            <th>Verified</th>
          </tr>
        </thead>
        <tbody>
          {visitors.length > 0 ? (
            visitors.map((visitor) => (
              <tr key={visitor.id}>
                <td>{visitor.id}</td>
                <td>{visitor.name}</td>
                <td>{visitor.email}</td>
                <td>{visitor.phone}</td>
                <td>{visitor.purpose}</td>
                <td>{visitor.checkInTime ? new Date(visitor.checkInTime).toLocaleString() : "N/A"}</td>
                <td>{visitor.verified ? "✅" : "❌"}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="7">No visitor data available</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default AdminDashboard;
