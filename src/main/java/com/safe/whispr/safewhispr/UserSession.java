/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.safe.whispr.safewhispr;

/**
 *
 * @author manis
 */
public class UserSession {
    private static String username = "User";
    private static boolean anonymous = false;

    // set the username
    public static void setUsername(String name) {
        username = name;
    }

    // get the username
    public static String getUsername() {
        return username;
    }

    // set the posting mode using a string 
    public static void setPostingMode(String mode) {
        anonymous = mode.equalsIgnoreCase("Anonymous");
    }

    // set the anonymous mode using a boolean 
    public static void setAnonymous(boolean value) {
        anonymous = value;
    }

    // checks if the user is anonymous
    public static boolean isAnonymous() {
        return anonymous;
    }
}