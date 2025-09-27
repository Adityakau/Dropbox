import React, { useEffect, useState } from "react";
import FileUpload from "../components/FileUpload";
import FileList from "../components/FileList";
import { listFiles } from "../services/fileService";
import "../styles/Home.css";

const Home = () => {
  const [files, setFiles] = useState([]);

  const fetchFiles = async () => {
    const data = await listFiles();
    setFiles(data);
  };

  useEffect(() => {
    fetchFiles();
  }, []);

  return (
    <div className="container">
      <h1> My Files </h1>
      {/* 👈 FileUpload is separate */}
      <FileUpload onUploadSuccess={fetchFiles} />
      {/* 👈 FileList is separate */}
      <FileList files={files} />
    </div>
  );
};

export default Home;