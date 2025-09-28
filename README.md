# Dropbox

📂 Dropbox – File Management System

This project is a Dropbox-like file management system built with:

Backend: Spring Boot (REST APIs for file upload, download, preview, and listing)

Frontend: React (modern UI for interacting with files)


It allows users to:
✅ Upload supported files (txt, png, jpg, jpeg, json)
✅ List all uploaded files
✅ Preview file contents (text, image, JSON)
✅ Download files

---

🚀 Project Structure

project-root/
├── backend/       # Java spring boot (REST APIs)
├── frontend/      # React frontend (UI for users)

---

⚙️ Backend Setup (Spring Boot)

1. Navigate to backend folder

cd backend

2. Configure Local Storage Path

Inside src/main/resources/application.yaml, add the following property:

file:
  storage:
    location: /absolute/path/to/local/storage

> ⚠️ Replace /absolute/path/to/local/storage with the directory where you want to store uploaded files.
Example (Mac/Linux): /Users/aditya/Desktop/uploads
Example (Windows): C:/Users/aditya/uploads



Make sure this folder exists before running the app.


---

3. Build and Run Backend

mvn clean install
mvn spring-boot:run

By default, the backend will run on:
👉 http://localhost:8080


---

4. API Endpoints

Method	Endpoint	Description

GET	/files	List all uploaded files
POST	/upload	Upload a new file
GET	/files/{id}	Download a file by ID
GET	/preview/{id}	Preview file content (if text/image/json)



---

🎨 Frontend Setup (React)

1. Navigate to frontend folder

cd frontend

2. Install Dependencies

npm install

3. Start Development Server

npm start

Frontend will run on:
👉 http://localhost:3000


---

🔗 Connecting Frontend and Backend

Make sure backend (8080) is running before starting frontend (3000).
The frontend consumes backend APIs for:

Listing files (/files)

Uploading (/upload)

Previewing (/preview/{id})

Downloading (/files/{id})



---

🛠️ Features

Upload: Drag & drop or select supported files to upload.

List: See all uploaded files in a clean, modern UI.

Preview:

.txt → Plain text view

.json → Formatted JSON view

.png/.jpg/.jpeg → Image preview


Download: Click a button to download any file.

Validation: Restricts uploads to supported file types.



---

✨ Modern UI inspired by Dropbox (React + Tailwind).
Files are shown in cards with icons, preview, and action buttons.


---

🏃‍♂️ Running Full Application

1. Start backend (mvn spring-boot:run).


2. Start frontend (npm start).


3. Visit http://localhost:3000.


4. Upload a file → List updates instantly.


5. Click on file → Preview / Download.




---

✅ Supported File Types

.txt

.json

.png

.jpg

.jpeg


> Other formats will be rejected during upload.



---

🔮 Future Enhancements

User authentication & login

Shareable links for files

Cloud storage support (AWS S3 / GCP)

Advanced search & filters
