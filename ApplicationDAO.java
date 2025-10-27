package com.college.admission.dao;
import com.college.admission.DBConnection;
import com.college.admission.models.Application;
import java.sql.*; import java.util.*;

public class ApplicationDAO {
    public static int create(Application a) throws SQLException {
        String sql = "INSERT INTO applications (student_id,course_id,merit_score,status) VALUES (?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getStudentId()); ps.setInt(2, a.getCourseId());
            ps.setDouble(3, a.getMeritScore()); ps.setString(4, a.getStatus());
            ps.executeUpdate(); ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }
    public static List<Application> listByCourse(int cid) throws SQLException {
        List<Application> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM applications WHERE course_id=? ORDER BY merit_score DESC")) {
            ps.setInt(1, cid); ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Application a = new Application();
                a.setId(rs.getInt("id")); a.setStudentId(rs.getInt("student_id"));
                a.setCourseId(rs.getInt("course_id")); a.setMeritScore(rs.getDouble("merit_score"));
                a.setStatus(rs.getString("status"));
                list.add(a);
            }
        }
        return list;
    }
    public static void updateStatus(int id, String st) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("UPDATE applications SET status=? WHERE id=?")) {
            ps.setString(1, st); ps.setInt(2, id); ps.executeUpdate();
        }
    }
    public static void updateMerit(int id, double m) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement("UPDATE applications SET merit_score=? WHERE id=?")) {
            ps.setDouble(1, m); ps.setInt(2, id); ps.executeUpdate();
        }
    }
}
