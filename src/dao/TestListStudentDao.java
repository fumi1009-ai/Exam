package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

	// arraylistへの格納処理
	public List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
		List<TestListStudent> list = new ArrayList<>();
		try{
			// 渡された値をlistへセット
			while (rSet.next()){
				TestListStudent tl_student = new TestListStudent();
				tl_student.setSubjectName(rSet.getString("name"));
				tl_student.setSubjectCd(rSet.getString("subject_cd"));
				tl_student.setNum(rSet.getInt("no"));
				tl_student.setPoint(rSet.getInt("point"));
				list.add(tl_student);
			}
		} catch (SQLException | NullPointerException e){ e.printStackTrace();}
		return list;
	}

	// 選択学生のデータ取得
	public List<TestListStudent> filter(Student student) throws Exception {
		// 定義
		List<TestListStudent> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet set = null;
	    try {
	    	// sql処理
	        statement = connection.prepareStatement("SELECT subject.name, test.subject_cd, test.no, test.point "
	        									+ "FROM test LEFT JOIN subject "
	        									+ "ON test.subject_cd = subject.cd WHERE test.student_no = ? "
	        									+ "ORDER BY test.subject_cd, test.no"
	        									);
	        statement.setString(1, student.getNo());
	        set = statement.executeQuery();
	        list = postFilter(set);

	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }

	    return list;
	}
}

