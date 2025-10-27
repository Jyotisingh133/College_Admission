package com.college.admission.dao;
import com.college.admission.DBConnection;
import com.college.admission.models.Course;
import java.sql.*; import java.util.*;

public class CourseDAO {
    public static List<Course> listAll() throws SQLException {
        List<Course> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM courses");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Course co = new Course();
                co.setId(rs.getInt("id")); co.setName(rs.getString("name"));
                co.setTotalSeats(rs.getInt("total_seats"));
                co.setCutoff(rs.getDouble("cutoff"));
                list.add(co);
            }
        }
        return list;
    }
    public static Course findById(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM courses WHERE id=?")) {
            ps.setInt(1, id); ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Course co = new Course();
                co.setId(rs.getInt("id")); co.setName(rs.getString("name"));
                co.setTotalSeats(rs.getInt("total_seats"));
                co.setCutoff(rs.getDouble("cutoff"));
                return co;
            }
        }
        return null;
    }
}
