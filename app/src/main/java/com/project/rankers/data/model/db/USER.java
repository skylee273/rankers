package com.project.rankers.data.model.db;

public class USER {

    // Test
    public String getName() {
        return name;
    }

    public void setName(String name) {
        USER.name = name;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        USER.eMail = eMail;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        USER.age = age;
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
        USER.birthday = birthday;
    }

    public  String getId() {
        return id;
    }

    public  void setId(String id) {
        USER.id = id;
    }


    private static String id = null;
    private static String name = null;
    private static String eMail = null;
    private static String age = null;
    private static String Gender = null;
    private static String birthday = null;





}
