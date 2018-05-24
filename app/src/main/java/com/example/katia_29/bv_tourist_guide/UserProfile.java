package com.example.katia_29.bv_tourist_guide;

public class UserProfile {
    String name,gender,birthdate,profession,contactNumber,status;

    public UserProfile(String name, String gender, String birthdate, String profession, String contactNumber, String status) {
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.profession = profession;
        this.contactNumber = contactNumber;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }



    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
