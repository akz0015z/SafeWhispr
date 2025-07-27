/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.safe.whispr.safewhispr;

/**
 *
 * @author manis
 */

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.InputStream;
import java.io.IOException;
import java.util.List;

public class FirebaseInitializer {

    private static Firestore firestore;

    public static void initialize() {
        try {
            
            List<FirebaseApp> apps = FirebaseApp.getApps();
            if (!apps.isEmpty()) {
                System.out.println("⚠ ️Hello Admin! Firebase already initialized. Skipping re-initialization.");
                firestore = FirestoreClient.getFirestore();
                return;
            }

            InputStream serviceAccount = FirebaseInitializer.class
                    .getClassLoader()
                    .getResourceAsStream("firebase-service-account.json");

            if (serviceAccount == null) {
                System.err.println("❌ firebase-service-account.json not found in classpath (src/main/resources).");
                return;
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId("safewhispr")
                    .build();

            System.setProperty("FIRESTORE_EMULATOR_HOST", "firestore.googleapis.com:443");

            FirebaseApp.initializeApp(options);
            firestore = FirestoreClient.getFirestore();
            System.out.println("✅ Hello Admin! Ur Firebase is initialized successfully.");

        } catch (IOException e) {
            System.err.println("❌ Hello Admin! It has Failed to initialize Firebase: " + e.getMessage());
        }
    }

    public static Firestore getFirestore() {
        return firestore;
    }
}