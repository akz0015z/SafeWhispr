# SafeWhispr

SafeWhispr is a secure messaging application built using JavaFX and Firebase. It allows users to communicate anonymously or publicly through a real-time chat interface. All messages are encrypted before being stored in Firestore for privacy and security.

---

# Features
**Login / Signup System** using Firebase Authentication  
**Real-Time Chat** with Firebase Firestore  
**Anonymous Mode** for private identity-less posting  
**AES Encryption** of all messages stored in the database  
**Two Chat Modes**: Anonymous and Public  
Timestamps for every message  
Clean, styled JavaFX UI

---

# 🔐 Firebase & Firestore Configuration

To run **SafeWhispr** with full functionality (Login, Signup, Chat Messaging), you must connect it to a Firebase project. Here's how:

1. Create a Firebase Project
- Visit: [https://console.firebase.google.com](https://console.firebase.google.com)
- Click **Add Project**, give it a name (e.g., `SafeWhisprApp`), and follow the setup steps

2. Enable Firebase Services
In your project:
Go to **Firestore Database**  
Click **Create Database**  
Start in test mode
Go to **Authentication**  
Click **Sign-in method**  
Enable **Email/Password**

3. Generate Your Firebase Admin SDK Key
In the Firebase Console:  
Click ⚙️ **Project settings**  
Go to the **Service Accounts** tab  
Click **Generate new private key**  
A `.json` file will download.

4. Add the JSON Key to the App
Rename the downloaded file to: **firebase-service-account.json**
Place it here in your project directory: **src/main/resources/firebase-service-account.json** under a default package


# 🗂 Directory Structure

src/
├── com.safe.whispr.safewhispr/
│ ├── App.java
│ ├── ChatUI.java
│ ├── FirebaseInitializer.java
│ ├── AES.java / AESEncryption.java
│ ├── LoginController.java / SignupController.java
│ ├── ChoiceController.java / PrimaryController.java / SecondaryController.java
│ └── UserSession.java
│
├── main/resources/
│ ├── firebase-service-account.json ← Your Firebase key goes here
│ ├── fxml/
│ │ ├── Login.fxml / Signup.fxml / Choice.fxml
│ └── images/
│ └── clouds.png


---
# 💬 Notes
The code uses proper branching (`main`, `development`, `feature/*`) for versioning.
All features listed in the final report have been implemented and tested.
Message content is encrypted using AES before being stored in Firestore.

---

**GitHub:** [https://github.com/akz0015z/SafeWhispr](https://github.com/akz0015z/SafeWhispr)
