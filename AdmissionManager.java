package com.college.admission;

import com.college.admission.dao.*;
import com.college.admission.models.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

public class AdmissionManager {

    private static final double PERCENTAGE_WEIGHT = 0.6;
    private static final double ENTRANCE_WEIGHT = 0.4;
    private static final double ENTRANCE_MAX = 200.0;

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.println("=== College Admission Management System ===");
        boolean exit = false;
        while (!exit) {
            System.out.println("\n1) Register Student\n2) Apply for Course\n3) Run Allocation\n4) Export CSV\n5) List Courses\n6) Exit");
            System.out.print("Choice: ");
            String ch = scanner.nextLine().trim();
            switch (ch) {
                case "1": register(); break;
                case "2": apply(); break;
                case "3": allocate(); break;
                case "4": export(); break;
                case "5": listCourses(); break;
                case "6": exit = true; break;
                default: System.out.println("Invalid");
            }
        }
    }

    private static void register() {
        try {
            System.out.print("Name: "); String name = scanner.nextLine();
            System.out.print("Email: "); String email = scanner.nextLine();
            System.out.print("Phone: "); String phone = scanner.nextLine();
            System.out.print("Percentage: "); double perc = Double.parseDouble(scanner.nextLine());
            System.out.print("Entrance Score: "); double ent = Double.parseDouble(scanner.nextLine());
            Student s = new Student(name, email, phone, perc, ent);
            int id = StudentDAO.createStudent(s);
            System.out.println("Registered ID: " + id);
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static void apply() {
        try {
            System.out.print("Student ID: "); int sid = Integer.parseInt(scanner.nextLine());
            Student s = StudentDAO.findById(sid);
            if (s == null) { System.out.println("Student not found."); return; }
            listCourses();
            System.out.print("Course ID: "); int cid = Integer.parseInt(scanner.nextLine());
            Course c = CourseDAO.findById(cid);
            if (c == null) { System.out.println("Course not found."); return; }
            double merit = calcMerit(s.getPercentage(), s.getEntranceScore());
            Application app = new Application();
            app.setStudentId(sid); app.setCourseId(cid);
            app.setMeritScore(merit); app.setStatus("PENDING");
            int aid = ApplicationDAO.create(app);
            System.out.println("Applied. Merit: " + merit + " | App ID: " + aid);
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static double calcMerit(double perc, double ent) {
        return Math.round((PERCENTAGE_WEIGHT * perc + ENTRANCE_WEIGHT * (ent / ENTRANCE_MAX * 100)) * 10000.0) / 10000.0;
    }

    private static void allocate() {
        try {
            for (Course c : CourseDAO.listAll()) {
                List<Application> apps = ApplicationDAO.listByCourse(c.getId());
                int seats = c.getTotalSeats(), allocated = 0;
                for (Application a : apps) {
                    Student s = StudentDAO.findById(a.getStudentId());
                    double merit = calcMerit(s.getPercentage(), s.getEntranceScore());
                    ApplicationDAO.updateMerit(a.getId(), merit);
                }
                apps = ApplicationDAO.listByCourse(c.getId());
                for (Application a : apps) {
                    if (allocated >= seats) ApplicationDAO.updateStatus(a.getId(), "REJECTED");
                    else if (a.getMeritScore() >= c.getCutoff()) {
                        ApplicationDAO.updateStatus(a.getId(), "APPROVED");
                        allocated++;
                    } else ApplicationDAO.updateStatus(a.getId(), "REJECTED");
                }
                System.out.println("Course: " + c.getName() + " => Allocated: " + allocated);
            }
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static void export() {
        try {
            for (Course c : CourseDAO.listAll()) {
                List<Application> apps = ApplicationDAO.listByCourse(c.getId());
                try (PrintWriter pw = new PrintWriter(new FileWriter("admissions_course_" + c.getId() + ".csv"))) {
                    pw.println("AppID,StudentID,Merit,Status");
                    for (Application a : apps)
                        pw.println(a.getId() + "," + a.getStudentId() + "," + a.getMeritScore() + "," + a.getStatus());
                }
            }
            System.out.println("Exported CSV files.");
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

    private static void listCourses() {
        try {
            for (Course c : CourseDAO.listAll())
                System.out.println(c.getId() + ") " + c.getName() + " | Seats: " + c.getTotalSeats() + " | Cutoff: " + c.getCutoff());
        } catch (SQLException e) { System.out.println("DB Error: " + e.getMessage()); }
    }
}
