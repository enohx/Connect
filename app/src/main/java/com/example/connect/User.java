package com.example.connect;

import androidx.annotation.NonNull;

public class User {
    String name;
    String Lname;
    int age;
    String degree_level;
    String City;
    String Address;
    String Email;
    String PhoneN;
    String Username;
    String Password;


    public User( String fullname, int age, String degree_level, String City,
                String Address,String Email, String PhoneN, String Usrername, String Password){

        String[] splitStr = fullname.trim().split("\\s+");
        String name = splitStr[0];
        String Lname = splitStr[1];


        this.name = name;
        this.Lname = Lname;
        this.age = age;
        this.degree_level = degree_level;
        this.City = City;
        this.Address = Address;
        this.Email = Email;
        this.PhoneN = PhoneN;
        this.Username = Usrername;
        this.Password = Password;

    }

    @NonNull
    @Override
    public String toString() {
        return name+" "+Lname+", "+ age +"\n"+degree_level+ "\n"+ "Based in " + Address;
    }

    //getter methods
    public String getName() {
        return name;
    }

    public String getLname() {
        return Lname;
    }

    public int getAge() {
        return age;
    }

    public String getDegree_level() {
        return degree_level;
    }

    public String getCity() {
        return City;
    }

    public String getAddress() {
        return Address;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhoneN() {
        return PhoneN;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public String Get_fullname(){return name + " " + Lname;}

    //Setter methods

    public void setName(String name) {
        this.name = name;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDegree_level(String degree_level) {
        this.degree_level = degree_level;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhoneN(String phoneN) {
        PhoneN = phoneN;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
