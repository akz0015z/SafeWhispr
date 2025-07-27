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
- Click **Add Project**, must give it the name `safewhispr`), and follow the setup steps

2. Enable Firebase Services
In your project:
Click Build on the Left Side Menu and Go to **Firestore Database**  
Click **Create Database**  
Leave the databsase id as (default) and the Location
Then Click Start in test mode
Then Click Build again and Go to **Authentication**  
Click Get Started then **Sign-in Providers Under Native Providers**  
Enable **Email/Password**

3. Generate Your Firebase Admin SDK Key
In the Firebase Console:  
Beside Project Overview on Top Left Click ⚙️ then **Project settings**  
Go to the **Service Accounts** tab  
Click **Generate new private key**  
A `.json` file will download.

4. Add the JSON Key to the App
Rename the downloaded file to: **firebase-service-account** because **.json is already the source file**
Drag it from file explorer onto: **src/main/resources/** or copy and paste it to it's directory **src/main/resources/firebase-service-account.json**

5. 📂 Add Your Service Account Key to Project
Place the downloaded firebase-service-account.json inside:

Open this file in your IDE:

**src/main/java/com/safe/whispr/safewhispr/FirebaseInitializer.java**

On Line 47, replace the **.setProjectId("your_project_id_here")** with your Firebase Project ID: eg. `safwhisper-5342g`

You can find your Project ID in Firebase:
Beside Project Overview on Top Left Click ⚙️ then **Project settings**> General > Project ID    


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
