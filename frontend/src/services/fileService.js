import axios from "axios";

const API_BASE = "http://localhost:8080/api/files";

export const uploadFile = async (file) => {
  const formData = new FormData();
  formData.append("file", file);

  const response = await axios.post(`${API_BASE}/upload`, formData, {
    headers: { "Content-Type": "multipart/form-data" },
  });
  return response.data;
};

export const listFiles = async () => {
  const response = await axios.get(API_BASE);
  return response.data;
};

export const downloadFile = (id, filename) => {
  axios({
    url: `${API_BASE}/download/${id}`,
    method: "GET",
    responseType: "blob",
  }).then((res) => {
    const url = window.URL.createObjectURL(new Blob([res.data]));
    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", filename);
    document.body.appendChild(link);
    link.click();
    link.remove();
  });
};

export const previewFile = (id) => {
  window.open(`${API_BASE}/preview/${id}`, "_blank");
};
