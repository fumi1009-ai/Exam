package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

	public Subject get(String cd, School school) throws Exception {
		// 科目Beanを生成
		Subject subject = new Subject();

		// コネクションを確立
		Connection connection = getConnection();

		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from subject where cd=? and school_cd=?");
			// プリペアードステートメントに教員IDをバインド
			statement.setString(1, cd);
			statement.setString(2, school.getCd());

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// 学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 教員インスタンスに検索結果をセット
				subject.setCd(resultSet.getString("cd"));
				subject.setName(resultSet.getString("name"));
				subject.setSchoolCd(resultSet.getString("school_cd"));
			} else {
				// リザルトセットが存在しない場合
				// 教員インスタンスにnullをセット
				subject = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return subject;
	}

	public List<Subject> filter(School school) throws Exception {
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		List<Subject> list = new ArrayList<>();

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from subject where school_cd=?");
			// プリペアードステートメントに教員IDをバインド
			statement.setString(1, school.getCd());

			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Subject subject = new Subject();
				subject.setCd(resultSet.getString("cd"));
				subject.setName(resultSet.getString("name"));
				subject.setSchoolCd(resultSet.getString("school_cd"));

				list.add(subject);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
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

	public boolean save(Subject subject) {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    try {
	    	String schoolCd = subject.getSchoolCd();
	    	String subjectCd = subject.getCd();

	    	if (isExists(schoolCd, subjectCd)) {
	    		// 対象の科目が存在するのでレコード更新
	    		connection = getConnection();
	    		statement = connection.prepareStatement(
	    	            "update subject set name=? where school_cd=? and cd=?"
    	        );
	    		statement.setString(1, subject.getName());
    	        statement.setString(2, schoolCd);
    	        statement.setString(3, subjectCd);
	    	} else {
	    		// 対象の科目が存在するのでレコード新規追加
	    		connection = getConnection();
	    		statement = connection.prepareStatement(
	    	            "insert into subject (cd, name, school_cd) values (?, ?, ?)"
    	        );
    	        statement.setString(1, subject.getCd());
    	        statement.setString(2, subject.getName());
    	        statement.setString(3, subject.getSchoolCd());
	    	}

	        int rows = statement.executeUpdate();
	        return rows > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (statement != null) statement.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	private boolean isExists(String schoolCd, String subjectCd) throws Exception{
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		boolean result = false;
		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from subject where school_cd=? and cd=?");
			// プリペアードステートメントに教員IDをバインド
			statement.setString(1, schoolCd);
			statement.setString(2, subjectCd);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				// リザルトセットが存在する場合
				result = true;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return result;
	}
	public boolean deleteSubject(String no, String schoolCd) {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    try {
	        connection = getConnection();
	        statement = connection.prepareStatement(
	            "DELETE FROM SUBJECT WHERE CD = ? AND NAME = ?"
	        );
	        statement.setString(1, no);
	        statement.setString(2, schoolCd);
	        int rows = statement.executeUpdate();
	        return rows > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        try {
	            if (statement != null) statement.close();
	            if (connection != null) connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
