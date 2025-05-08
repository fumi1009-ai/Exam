package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

	// 科目選択取得
	public Test get(Student student, Subject subject, School school, int no) throws Exception {
		// インスタンス初期化
		Test test = new Test();
		Connection connection = getConnection();
		PreparedStatement statement = null;
	    ResultSet rSet = null;
		try {
			statement = connection.prepareStatement(
				    "SELECT student.ent_year, student.class_num, " +
				    "student.no, student.name, test.point, test.subject_cd, test.school_cd " +
				    "FROM student LEFT JOIN test " +
				    "ON student.no = test.student_no AND student.school_cd = test.school_cd " +
				    "AND student.class_num = test.class_num " +
				    "AND test.subject_cd = ? " +
				    "AND test.no = ? " +
				    "WHERE test.school_cd = ? " +
				    "AND test.student_no = ? " +
				    "ORDER BY student.ent_year, student.class_num, student.no, student.name, test.subject_cd, test.school_cd;"
				);
			statement.setString(1, subject.getCd());
			statement.setInt(2, no);
			statement.setString(3, school.getCd());
			statement.setString(4, student.getNo());
	        rSet = statement.executeQuery();

			if (rSet.next()) {
				// リザルトセットが存在時、検索結果セット
				student.setEntYear(rSet.getInt("ent_year"));
				student.setName(rSet.getString("name"));
				test.setStudent(student);
				test.setClassNum(rSet.getString("class_num"));
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				subject.setCd(rSet.getString("subject_cd"));
				test.setSubject(subject);
				school.setCd(rSet.getString("school_cd"));
				test.setSchool(school);
			} else {
				test = null;
			}
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
		return test;
	}

	// arraylistへの格納処理
	private List<Test> postFilter(ResultSet rSet) throws Exception {
		List<Test> list = new ArrayList<>();
		try{
			// 渡された値をlistへセット
			while (rSet.next()){
				Test test_l = new Test();
				Student student = new Student();
				Subject subject = new Subject();
				School school = new School();
				student.setEntYear(rSet.getInt("ent_year"));
				student.setName(rSet.getString("name"));
				student.setNo(rSet.getString("no"));
				test_l.setStudent(student);
				test_l.setClassNum(rSet.getString("class_num"));
				test_l.setNo(rSet.getInt("no"));
				test_l.setPoint(rSet.getInt("point"));
				subject.setCd(rSet.getString("subject_cd"));
				test_l.setSubject(subject);
				school.setCd(rSet.getString("school_cd"));
				test_l.setSchool(school);

				list.add(test_l);
			}
		} catch (SQLException | NullPointerException e){ e.printStackTrace();}
		return list;
	}

	// 成績一覧取得
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
		// 定義
		List<Test> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet set = null;
	    try {
	    	// sql処理
	    	statement = connection.prepareStatement(
	    		    "SELECT student.ent_year, student.class_num, student.no, student.name, "
	    		  + "test.point, test.subject_cd, test.school_cd "
	    		  + "FROM student "
	    		  + "LEFT JOIN test ON student.no = test.student_no "
	    		  + "AND student.school_cd = test.school_cd "
	    		  + "AND student.class_num = test.class_num "
	    		  + "AND test.subject_cd = ? "
	    		  + "AND test.no = ? "
	    		  + "AND test.school_cd = ? "
	    		  + "WHERE student.ent_year = ? AND student.class_num = ? "
	    		  + "GROUP BY student.ent_year, student.class_num, student.no, student.name, test.subject_cd, test.school_cd;"
	    		);
			statement.setString(1, subject.getCd());
			statement.setInt(2, num);
			statement.setString(3, school.getCd());
			statement.setInt(4, entYear);
			statement.setString(5, classNum);
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

	// 登録、変更処理
	public boolean save(List<Test> list) throws Exception {
		Connection connection = getConnection();
		// 実行件数
		int count = 0;

		try {
			// リスト内を処理
			for (Test test : list) {
				// それぞれを一つずつ処理
				boolean isUpdated = save(test, connection);
				if (isUpdated) {
					count++;
				}
			}
		} catch (Exception e) {
			throw e;
		}  finally {
		    if (connection != null) {
		        try {
		            connection.close();
		        } catch (SQLException sqle) {
		            throw sqle;
		        }
		    }
		}

		return count > 0;
	}

	// 変更、登録処理
	private boolean save(Test test, Connection connection) throws SQLException {
		PreparedStatement statement = null;
	    int count = 0;

	    try {
			// 科目が存在しているかどうかのチェック用データ
			Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());

			if (old == null) {
				statement = connection.prepareStatement(
						"INSERT INTO test(student_no, subject_cd, school_cd, no, point, class_num) values(?, ?, ?, ?, ?, ?)");
				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject().getCd());
				statement.setString(3, test.getSchool().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());
			} else {
	            statement = connection.prepareStatement(
	                    "UPDATE test SET point = ? WHERE student_no = ? AND subject_cd = ? AND no = ?"); // SQL 修正
	            statement.setInt(1, test.getPoint());
	            statement.setString(2, test.getStudent().getNo());
	            statement.setString(3, test.getSubject().getCd());
	            statement.setInt(4, test.getNo());
			}
			// SQL実行
	        count = statement.executeUpdate();
	    } catch (Exception e){
	    	try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	    } finally {
	        if (statement != null) {
	        	try {
		            statement.close(); // connection ではなく statement を閉じる
		        } catch (SQLException sqle) {
		        	sqle.printStackTrace(); // エラーログ推奨
		        }
	        }
	    }

	    return count > 0; // 更新が成功した場合はtrue
	}
}