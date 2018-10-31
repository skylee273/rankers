package com.project.rankers.model;

public class User {

    // Test
    public String getName() {
        return name;
    }

    public void setName(String name) {
        User.name = name;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        User.eMail = eMail;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        User.age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        User.birthday = birthday;
    }

    private static String name = null;
    private static String eMail = null;
    private static String age = null;
    private static String Gender = null;
    private static String birthday = null;

    public  String getId() {
        return id;
    }

    public  void setId(String id) {
        User.id = id;
    }

    private static String id = null;


}
