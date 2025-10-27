package com.college.admission.dao;
import com.college.admission.DBConnection;
import com.college.admission.models.Student;
import java.sql.*;

public class StudentDAO {
    public static int createStudent(Student s) throws SQLException {
        String sql = "INSERT INTO students (name,email,phone,percentage,entrance_score) VALUES (?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getName()); ps.setString(2, s.getEmail());
            ps.setString(3, s.getPhone()); ps.setDouble(4, s.getPercentage());
            ps.setDouble(5, s.getEntranceScore()); ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys(); if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    public static Student findById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id); ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id")); s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email")); s.setPhone(rs.getString("phone"));
                s.setPercentage(rs.getDouble("percentage"));
                s.setEntranceScore(rs.getDouble("entrance_score"));
                return s;
            }
        }
        return null;
    }
}
