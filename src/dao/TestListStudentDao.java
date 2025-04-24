package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.TestListStudent;

public class TestListStudentDao extends Dao {

    private String baseSql = "SELECT s.name as subject_name, s.cd as subject_cd, " +
                           "t.no as num, t.point as point " +
                           "FROM test t " +
                           "JOIN subject s ON t.subject_cd = s.cd " +
                           "WHERE t.student_no = ? " +
                           "ORDER BY s.cd, t.no";

    public List<TestListStudent> findByStudent(String studentNo) throws Exception {
        List<TestListStudent> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement(baseSql);
            statement.setString(1, studentNo);
            rs = statement.executeQuery();
            list = postFilter(rs);
        } catch (SQLException e) {
            throw e;
        } finally {
            // Close resources
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (statement != null) try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return list;
    }

    private List<TestListStudent> postFilter(ResultSet rs) throws Exception {
        List<TestListStudent> list = new ArrayList<>();
        while (rs.next()) {
            TestListStudent test = new TestListStudent();
            test.setSubjectName(rs.getString("subject_name"));
            test.setSubjectCd(rs.getString("subject_cd"));
            test.setNum(rs.getInt("num"));
            test.setPoint(rs.getInt("point"));
            list.add(test);
        }
        return list;
    }
}