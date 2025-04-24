package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // Check if teacher is logged in
        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // Parameter handling
        String subjectCd = req.getParameter("f3");
        Map<String, String> errors = new HashMap<>();

        // Get school
        School school = teacher.getSchool();
        if (school == null) {
            errors.put("school", "学校情報が設定されていません");
        }

        // Initialize DAOs
        SubjectDao subjectDao = new SubjectDao();
        TestListSubjectDao testListDao = new TestListSubjectDao();

        // Handle subject search
        if (subjectCd == null || subjectCd.isEmpty()) {
            errors.put("f3", "科目コードを入力してください");
        } else {
            // Get subject information
            Subject subject = subjectDao.get(subjectCd, school);
            if (subject == null) {
                errors.put("f3", "該当する科目が見つかりません");
            } else {
                // Get test results for the subject
                List<TestListSubject> testResults = testListDao.findBySubject(subjectCd, school);

                // Set attributes for display
                req.setAttribute("subject", subject);
                req.setAttribute("testResults", testResults);
            }
        }
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        // Initialize DAOs
        ClassNumDao cNumDao = new ClassNumDao();
        SubjectDao subDao = new SubjectDao();


        List<String> classNumList = new ArrayList<>();
        List<Subject> subjectList = new ArrayList<>();

        if (school != null) {
            classNumList = cNumDao.filter(school);
            subjectList = subDao.filter(school);
        }


        // Set request attributes
        req.setAttribute("f3", subjectCd);
        req.setAttribute("errors", errors);

        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumList);
        req.setAttribute("subject_set", subjectList);

        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}