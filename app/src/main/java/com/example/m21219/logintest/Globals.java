package com.example.m21219.logintest;

/**
 * Created by lukas on 25.02.17.
 */
public class Globals {
    private static Globals ourInstance = new Globals();

    public static Globals getInstance() {
        return ourInstance;
    }

    private Globals() {
    }

        private static String sUserID;

        public static  String getUserID() {
            return sUserID;
        }

        public static void setUserID(String UserID) {
            sUserID=UserID;
        }
}
