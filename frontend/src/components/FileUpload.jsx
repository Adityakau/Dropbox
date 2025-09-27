import React, { useState, useRef } from "react";
import { uploadFile } from "../services/fileService";
import "../styles/FileUpload.css";

const allowedTypes = ["text/plain", "image/png", "image/jpeg", "application/json", "application/pdf"];

const FileUpload = ({ onUploadSuccess }) => {
  const [file, setFile] = useState(null);
  const [error, setError] = useState("");
  const fileInputRef = useRef(null);

  const handleFileChange = (e) => {
    const selected = e.target.files[0];
    if (selected && !allowedTypes.includes(selected.type)) {
      setError("Unsupported file type");
      setFile(null);
      return;
    }
    setError("");
    setFile(selected);
  };

  const handleUpload = async () => {
    if (!file) return;
    try {
      await uploadFile(file);
      setFile(null);

      // 3. Reset the file input value using the ref
      if (fileInputRef.current) {
        fileInputRef.current.value = "";
      }

      onUploadSuccess();
    } catch (err) {
      setError("Upload failed");
    }
  };

  return (
     <div className="file-upload content-block">
          <input type="file" onChange={handleFileChange} ref={fileInputRef} />
          <button onClick={handleUpload} disabled={!file}>Upload</button>
          {error && <p className="error">{error}</p>}
        </div>
  );
};

export default FileUpload;