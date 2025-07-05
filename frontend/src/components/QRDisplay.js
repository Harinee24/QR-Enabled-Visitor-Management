import React from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./QRDisplay.css";

const QRDisplay = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { qrCode } = location.state || {};

  if (!qrCode) {
    navigate("/registration-form");
    return null;
  }

  const handleDownload = () => {
    const link = document.createElement("a");
    link.href = `data:image/png;base64,${qrCode}`;
    link.download = "visitor_qrcode.png";
    link.click();
  };

  return (
    <div className="qr-display">
      <h3>Registration Successful!</h3>
      <p>Please show this QR code at the entrance.</p>
      <img
        src={`data:image/png;base64,${qrCode}`}
        alt="QR Code"
        className="qr-image"
      /> <br />
      <button onClick={handleDownload} className="download-btn">
        Download QR Code
      </button> <br />
      <button onClick={() => navigate("/registration-form")} className="back-btn"> 
        Back to Registration
      </button>
    </div>
  );
};

export default QRDisplay;
