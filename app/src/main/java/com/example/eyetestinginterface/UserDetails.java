package com.example.eyetestinginterface;

public class UserDetails {

    static String user_name;
    static String user_email;
    static String user_phone;
    static String user_id;

    public static String getUser_name() {
        return user_name;
    }

    public static void setUser_name(String user_name) {
        UserDetails.user_name = user_name;
    }

    public static String getUser_email() {
        return user_email;
    }

    public static void setUser_email(String user_email) {
        UserDetails.user_email = user_email;
    }

    public static String getUser_phone() {
        return user_phone;
    }

    public static void setUser_phone(String user_phone) {
        UserDetails.user_phone = user_phone;
    }

    public static String getUser_id() {
        return user_id;
    }

    public static void setUser_id(String user_id) {
        UserDetails.user_id = user_id;
    }
}
