package com.example.finalproject;

public class globals {
    private static boolean darkModeEnabled = false;
    private static boolean pushNotifications = false;

    public static boolean isDarkModeEnabled() {
        return darkModeEnabled;
    }

    public static void setDarkModeEnabled(boolean darkModeEnabled) {
        globals.darkModeEnabled = darkModeEnabled;
    }

    public static boolean isPushNotifications() {
        return pushNotifications;
    }

    public static void setPushNotifications(boolean pushNotifications) {
        globals.pushNotifications = pushNotifications;
    }
}
