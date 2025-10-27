package com.college.admission.models;
public class Student {
    private int id; private String name, email, phone;
    private double percentage, entranceScore;
    public Student() {}
    public Student(String name,String email,String phone,double p,double e){
        this.name=name;this.email=email;this.phone=phone;this.percentage=p;this.entranceScore=e;
    }
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getName(){return name;} public void setName(String n){this.name=n;}
    public String getEmail(){return email;} public void setEmail(String e){this.email=e;}
    public String getPhone(){return phone;} public void setPhone(String p){this.phone=p;}
    public double getPercentage(){return percentage;} public void setPercentage(double p){this.percentage=p;}
    public double getEntranceScore(){return entranceScore;} public void setEntranceScore(double e){this.entranceScore=e;}
}
