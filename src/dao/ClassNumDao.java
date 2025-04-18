package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class ClassNumDao extends Dao {
	public Student get(String class_num, School school) throws Exception {
		   // クラス番号インスタンスを初期化
		   Student classNum = new Student();
		   // データベースへのコネクションを確立
		   Connection connection = getConnection();
		   // プリペアードステートメント
		   PreparedStatement statement = null;
		   try {
		       // プリペアードステートメントにSQLをセット
		       statement = connection.prepareStatement("select * from class_num where class_num = ? and school_cd = ?");
		       // プリペアードステートメントに値をバインド
		       statement.setString(1, class_num);
		       statement.setString(2, school.getCd());
		       // プリペアードステートメントを実行
		       ResultSet rSet = statement.executeQuery();
		       // SchoolDaoを初期化
		       SchoolDao sDao = new SchoolDao();
		       if (rSet.next()) {
		           // リザルトセットが存在する場合
		           // クラス番号インスタンスに取得結果をセット
		           classNum.setClassNum(rSet.getString("class_num"));
		           classNum.setSchool(sDao.get(rSet.getString("school_cd")));
		       } else {
		           // リザルトセットが存在しない場合
		           // クラス番号インスタンスにnullをセット
		           classNum = null;
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
		   return classNum;
		}
	public List<String> filter(School school) throws Exception {
	    // リストを初期化
	    List<String> list = new ArrayList<>();
	    // データベースへのコネクションを確立
	    Connection connection = getConnection();
	    // プリペアドステートメント
	    PreparedStatement statement = null;

	    try {
	        // プリペアドステートメントにSQL文をセット
	        statement = connection
	            .prepareStatement("select class_num from class_num where school_cd=? order by class_num");
	        // プリペアドステートメントに値をバインド
	        statement.setString(1, school.getCd());
	        // プリペアドステートメントを実行
	        ResultSet set = statement.executeQuery();

	        // リザルトセットを全件走査
	        while (set.next()) {
	            // リストにクラス名を追加
	            list.add(set.getString("class_num"));
	        }
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        // プリペアドステートメントを閉じる
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