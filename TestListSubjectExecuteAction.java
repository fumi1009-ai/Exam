package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap; // HashMap の import を復活
import java.util.List;
import java.util.Map; // Map の import を復活

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// import bean.ClassNum; // ClassNumDao で使用
import bean.Subject;
import bean.Teacher;
// import bean.Test; // 不要
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession(false);
		Teacher teacher = (Teacher) session.getAttribute("user");

		List<Integer> entYearSet = new ArrayList<>();
		List<String> classNumList = null;
		List<Subject> subjectList = null;
		Map<String, String> errors = new HashMap<>(); // コメントアウト解除
		ClassNumDao cNumDao = new ClassNumDao();
		SubjectDao subDao = new SubjectDao();
		TestListSubjectDao tlsDao = new TestListSubjectDao();
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();

		for (int i = year - 10; i <= year + 1; i++) {
			entYearSet.add(i);
		}

		classNumList = cNumDao.filter(teacher.getSchool());
		subjectList = subDao.filter(teacher.getSchool());

		String entYearStr = req.getParameter("f1");
		String classNumStr = req.getParameter("f2");
		String subjectCd = req.getParameter("f3");

		System.out.println("入学年度: " + entYearStr);
		System.out.println("クラス: " + classNumStr);
		System.out.println("科目コード: " + subjectCd);

		int entYear = Integer.parseInt(entYearStr);
		Subject filterSubject = subDao.get(subjectCd, teacher.getSchool());
		List<TestListSubject> subjectTests = null;


		// idk what i'm doing here
		if (filterSubject != null) {
			subjectTests = tlsDao.filter(entYear, classNumStr, filterSubject, teacher.getSchool());
		} else {
			if (subjectCd != null && !subjectCd.isEmpty() && !subjectCd.equals("0")) {
				errors.put("filter", "指定された科目が存在しません。");
			}
		}

		System.out.println("entYear (int): " + entYear);
		System.out.println("filterSubject: " + (filterSubject != null ? filterSubject.getName() : "null"));
		System.out.println("total count: " + (subjectTests != null ? subjectTests.size() : "null"));
		System.out.println("subjectTests: " + subjectTests);

		if (entYearStr == null || entYearStr.isEmpty() || entYearStr.equals("0") || // entYear == "0" を修正
			classNumStr == null || classNumStr.isEmpty() || classNumStr.equals("0") ||
			subjectCd == null || subjectCd.isEmpty() || subjectCd.equals("0")) {
			errors.put("filter", "入学年度、クラス、科目を選択してください。"); // エラーメッセージのキーを filter に変更
		}

		// JSP に渡す属性を設定
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumList);
		req.setAttribute("subject_set", subjectList);
		req.setAttribute("f1", entYearStr);
		req.setAttribute("f2", classNumStr);
		req.setAttribute("f3", subjectCd);
		req.setAttribute("selectedSubject", filterSubject);
		req.setAttribute("subjectTests", subjectTests);
		req.setAttribute("errors", errors); // 復活

		// 検索タイプを設定
		req.setAttribute("testResults", "subject");

		req.getRequestDispatcher("test_list.jsp").forward(req, res);
	}

}