import React from "react";
import { downloadFile, previewFile } from "../services/fileService";
import "../styles/FileList.css";

const FileList = ({ files }) => {
  if (!files || files.length === 0) return <p>No files uploaded yet.</p>;

  return (
    <table className="file-list">
      <thead>
        <tr>
          <th>Filename</th>
          <th>Type</th>
          <th>Size (KB)</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {files.map((file) => (
          <tr key={file.id}>
            <td>{file.originalFilename}</td>
            <td>{file.mimeType}</td>
            <td>{(file.fileSize / 1024).toFixed(2)}</td>
            <td>
              <button onClick={() => previewFile(file.id)}>Preview</button>
              <button onClick={() => downloadFile(file.id, file.originalFilename)}>Download</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default FileList;
