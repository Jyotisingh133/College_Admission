package com.college.admission.models;
public class Application {
    private int id, studentId, courseId; private double meritScore; private String status;
    public int getId(){return id;} public void setId(int id){this.id=id;}
    public int getStudentId(){return studentId;} public void setStudentId(int s){this.studentId=s;}
    public int getCourseId(){return courseId;} public void setCourseId(int c){this.courseId=c;}
    public double getMeritScore(){return meritScore;} public void setMeritScore(double m){this.meritScore=m;}
    public String getStatus(){return status;} public void setStatus(String s){this.status=s;}
}
