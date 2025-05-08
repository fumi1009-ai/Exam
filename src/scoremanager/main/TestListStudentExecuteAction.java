package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession(false);
        Teacher teacher = (Teacher) session.getAttribute("user");
        Map<String, String> errors = new HashMap<>();
        StudentDao sDao = new StudentDao();
        TestListStudentDao tlsDao = new TestListStudentDao();
        List<TestListStudent> studentTests = null;
        Student selectedStudent = null;

        String entYearStr = ""; // 入力された入学年度（文字列）
		List<Test> tests = null;  // 成績リスト
		List<Subject> subjectList = null; // 科目リスト（プルダウン用）
		List<String> classNumList = null; // クラス番号リスト（プルダウン用）
		List<Integer> entYearSet = new ArrayList<>(); // 入学年度リスト（プルダウン用）
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear(); // 現在の年

        String studentNo = req.getParameter("f4");

        ClassNumDao cNumDao = new ClassNumDao();
		SubjectDao subDao = new SubjectDao();
		TestDao testDao = new TestDao();

        for (int i = year - 10; i <= year + 1; i++) {
			entYearSet.add(i);
		}

		classNumList = cNumDao.filter(teacher.getSchool());
		subjectList = subDao.filter(teacher.getSchool());

        if (studentNo == null || studentNo.isEmpty()) {
            errors.put("student_search", "学生番号を入力してください");
        } else {
            selectedStudent = sDao.get(studentNo);

            if (selectedStudent == null) {
                errors.put("student_search", "指定された学生番号の学生は存在しません");
            } else {
                studentTests = tlsDao.filter(selectedStudent);
            }
        }

        req.setAttribute("selectedStudent", selectedStudent);
        req.setAttribute("studentTests", studentTests);
        req.setAttribute("f4", studentNo);
        req.setAttribute("errors", errors);
        req.setAttribute("resultType", "student");
        req.setAttribute("tests", tests);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumList);
		req.setAttribute("subject_set", subjectList);
		req.setAttribute("errors", errors);

        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}
