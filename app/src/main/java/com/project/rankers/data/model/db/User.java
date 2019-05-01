package com.project.rankers.data.model.db;

public class User {
    private static String userID;
    private static String userName;
    private static String userEmail;
    private static String userPhone;

    public  String getUserID() {
        return userID;
    }

    public  void setUserID(String userID) {
        User.userID = userID;
    }

    public  String getUserName() {
        return userName;
    }

    public  void setUserName(String userName) {
        User.userName = userName;
    }

    public  String getUserEmail() {
        return userEmail;
    }

    public  void setUserEmail(String userEmail) {
        User.userEmail = userEmail;
    }

    public  String getUserPhone() {
        return userPhone;
    }

    public  void setUserPhone(String userPhone) {
        User.userPhone = userPhone;
    }

    public  String getUserBirthday() {
        return userBirthday;
    }

    public  void setUserBirthday(String userBirthday) {
        User.userBirthday = userBirthday;
    }

    private static String userBirthday;


}
