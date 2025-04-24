package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    public List<TestListSubject> findBySubject(String subjectCd, School school) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        if (subjectCd == null || subjectCd.trim().isEmpty()) {
            throw new IllegalArgumentException("Subject code cannot be null or empty");
        }

        String sql = "SELECT t.student_no as student_no, s.name as student_name, " +
                    "t.no as no, t.point as point " +
                    "FROM test t " +
                    "JOIN student s ON t.student_no = s.no " +
                    "WHERE t.subject_cd = ? AND t.school_cd = ? " +
                    "ORDER BY s.name, t.no";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, subjectCd);
            statement.setString(2, school.getCd());

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    TestListSubject test = new TestListSubject();
                    test.setStudentNo(rs.getString("student_no"));
                    test.setStudentName(rs.getString("student_name"));
                    test.setNo(rs.getInt("no"));
                    test.setPoints(rs.getInt("point"));
                    list.add(test);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Database error while fetching test results for subject: " + subjectCd, e);
        }
        return list;
    }
}