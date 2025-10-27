package com.college.admission.models;
public class Course {
    private int id; private String name; private int totalSeats; private double cutoff;
    public Course(){}
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public String getName(){return name;} public void setName(String n){this.name=n;}
    public int getTotalSeats(){return totalSeats;} public void setTotalSeats(int s){this.totalSeats=s;}
    public double getCutoff(){return cutoff;} public void setCutoff(double c){this.cutoff=c;}
}
