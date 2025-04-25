package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

	private String baseSql = "select student.ent_year, student.no, student.name, student.class_num, a.no as no1, a.point as point1, b.no as no2, b.point as point2";

	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {

		// リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		try {
			// リザルトセットを全権走査
			while (rSet.next()) {
				// 科目別リストインスタンスを初期化
				TestListSubject tls = new TestListSubject();
				// 科目別リストインスタンスに検索結果をセット
				tls.setEntYear(rSet.getInt("ent_year"));
				tls.setStudentNo(rSet.getString("no"));
				tls.setStudentName(rSet.getString("name"));
				tls.setClassNum(rSet.getString("class_num"));
				tls.putPoint(rSet.getInt("no1"), rSet.getInt("point1"));
				if (rSet.getInt("no2") != 0) {
					tls.putPoint(rSet.getInt("no2"), rSet.getInt("point2"));
				} else {
					tls.putPoint(2, -1);
				}

				// リストに追加
				list.add(tls);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {

		// リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet rSet = null;
		// SQL文の参照テーブル
		String from = " from (select test.student_no, test.subject_cd, test.school_cd, test.no, test.point, test.class_num from test join student on test.student_no = student.no where student.ent_year = ? and subject_cd = ? and test.class_num = ? and test.school_cd = ? and test.no = 1 order by test.student_no) as a";
		// SQL文の結合
		String join = " left join (select test.student_no, test.subject_cd, test.school_cd, test.no, test.point, test.class_num from test join student on test.student_no = student.no where student.ent_year = ? and subject_cd = ? and test.class_num = ? and test.school_cd = ? and test.no = 2 order by test.student_no) as b";
		// SQL文の条件
		String condition = " on a.student_no = b.student_no and a.subject_cd = b.subject_cd and a.class_num = b.class_num";
		// SQL文の結合2
		String join2 = " join student on a.student_no = student.no";
		// SQL文のソート
		String order = " order by a.student_no asc, a.no asc";

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + from + join + condition + join2 + order);
			// プリペアードステートメントに入学年度をバインド
			statement.setInt(1, entYear);
			// プリペアードステートメントに科目番号をバインド
			statement.setString(2, subject.getCd());
			// プリペアードステートメントにクラス番号をバインド
			statement.setString(3, classNum);
			// プリペアードステートメントに学校コードをバインド
			statement.setString(4, school.getCd());
			// 同じ順番でバインド
			statement.setInt(5, entYear);
			statement.setString(6, subject.getCd());
			statement.setString(7, classNum);
			statement.setString(8, school.getCd());
			// プリペアードステートメントを実行
			rSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(rSet);
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
}
