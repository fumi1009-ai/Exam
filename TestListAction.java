package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
		throws Exception {
		HttpSession session = req.getSession(); //セッション
		Teacher teacher = (Teacher)session.getAttribute("user");

		// local変数宣言
		String entYearStr = ""; // 入力された入学年度（文字列）
		List<Test> tests = null;  // 成績リスト
		List<Subject> subjectList = null; // 科目リスト（プルダウン用）
		List<String> classNumList = null; // クラス番号リスト（プルダウン用）
		List<Integer> entYearSet = new ArrayList<>(); // 入学年度リスト（プルダウン用）
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear(); // 現在の年
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		// DAOインスタンス化
		ClassNumDao cNumDao = new ClassNumDao();
		SubjectDao subDao = new SubjectDao();
		TestDao testDao = new TestDao();

		for (int i = year - 10; i <= year + 1; i++) {
			entYearSet.add(i);
		}
		classNumList = cNumDao.filter(teacher.getSchool());
		subjectList = subDao.filter(teacher.getSchool());

		req.setAttribute("tests", tests);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumList);
		req.setAttribute("subject_set", subjectList);
		req.setAttribute("errors", errors);

		// JSPへフォワード
		req.getRequestDispatcher("test_list.jsp").forward(req, res);
	}
}