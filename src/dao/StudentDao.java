package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {
	/**
	* getメソッド 教員IDを指定して教員インスタンスを1件取得する
	*
	* @param id:String
	*            教員ID
	* @return 教員クラスのインスタンス 存在しない場合はnull
	* @throws Exception
	*/
	private String baseSql = "select * from student where school_cd=?";

	private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
		List<Student> list = new ArrayList<>();
		try {
			while (rSet.next()) {
				Student student = new Student();
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_Attend"));
				student.setSchool(school);
				// 学校フィールドには学校コードで検索した学校インスタンスをセット
				list.add(student);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;
		String condition = "and ent_year=? and class_num=?";
		String order = " order by no asc";
		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = "and is_attend=true";
		}
		try {
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
			rSet = statement.executeQuery();
			list = postFilter(rSet, school);
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

	public List<Student> filter(School school, boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;
		String order = " order by no asc";
		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = "and is_attend=true";
		}
		try {
			statement = connection.prepareStatement(baseSql + conditionIsAttend + order);
			statement.setString(1, school.getCd());
			rSet = statement.executeQuery();
			list = postFilter(rSet, school);
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

	public Student get(String no) throws Exception {
		// 教員インスタンスを初期化
		Student student = new Student();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from student where no=?");
			// プリペアードステートメントに教員IDをバインド
			statement.setString(1, no);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// 学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 教員インスタンスに検索結果をセット
				student.setNo(resultSet.getString("no"));
				student.setName(resultSet.getString("name"));
				student.setEntYear(resultSet.getInt("ent_year"));
				student.setClassNum(resultSet.getString("class_num"));
				student.setAttend(resultSet.getBoolean("is_attend"));
				// 学校フィールドには学校コードで検索した学校インスタンスをセット
				student.setSchool(schoolDao.get(resultSet.getString("school_cd")));
			} else {
				// リザルトセットが存在しない場合
				// 教員インスタンスにnullをセット
				student = null;
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

		return student;
	}

	public boolean save(Student student) throws Exception {
	    // コネクションを確立
	    Connection connection = getConnection();
	    // プリペアードステートメント
	    PreparedStatement statement = null;
	    // 実行件数
	    int count = 0;

	    try {
	        // データベースから学生を取得
	        Student old = get(student.getNo());
	        if (old == null) {
	            // 学生が存在しなかった場合
	            // プリペアードステートメントにINSERT文をセット
	            statement = connection.prepareStatement(
	                "insert into student(no, name, ent_year, class_num, is_attend, school_cd) values(?, ?, ?, ?, ?, ?)");
	            // プリペアードステートメントに値をバインド
	            statement.setString(1, student.getNo());
	            statement.setString(2, student.getName());
	            statement.setInt(3, student.getEntYear());
	            statement.setString(4, student.getClassNum());
	            statement.setBoolean(5, student.isAttend());
	            statement.setString(6, student.getSchool().getCd());
	        } else {
	            // 学生が存在した場合
	            // プリペアードステートメントにUPDATE文をセット
	            statement = connection.prepareStatement(
	                "update student set name=?, ent_year=?, class_num=?, is_attend=? where no=?");
	            // プリペアードステートメントに値をバインド
	            statement.setString(1, student.getName());
	            statement.setInt(2, student.getEntYear());
	            statement.setString(3, student.getClassNum());
	            statement.setBoolean(4, student.isAttend());
	            statement.setString(5, student.getNo());
	        }

	        // プリペアードステートメントを実行
	        count = statement.executeUpdate();

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

	    if (count > 0) {
	        // 実行件数が1件以上ある場合
	        return true;
	    } else {
	        // 実行件数が0件の場合
	        return false;
	    }
	}
	/**
	* loginメソッド 教員IDとパスワードで認証する
	*
	* @param id:String
	*            教員ID
	* @param password:String
	*            パスワード
	* @return 認証成功:教員クラスのインスタンス, 認証失敗:null
	* @throws Exception
	*/

	public int deleteStudent(String no) throws Exception {
		Connection con=getConnection();
		PreparedStatement st=con.prepareStatement("delete from student where no = ?");
		st.setString(1, no);
		int line=st.executeUpdate();
		st.close();
		con.close();
		return line;
	}

}