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
import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import tool.Action;

public class TestListAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // Check if teacher is logged in
        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // Parameter handling
        String searchType = req.getParameter("f");
        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");
        String studentNo = req.getParameter("f4");

        Map<String, String> errors = new HashMap<>();
        int entYear = 0;

        // Get school and validate
        School school = teacher.getSchool();
        if (school == null) {
            errors.put("school", "学校情報が設定されていません");
        }

        // Prepare year range
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        // Initialize DAOs
        ClassNumDao cNumDao = new ClassNumDao();
        SubjectDao subDao = new SubjectDao();
        StudentDao studentDao = new StudentDao();

        List<String> classNumList = new ArrayList<>();
        List<Subject> subjectList = new ArrayList<>();

        if (school != null) {
            classNumList = cNumDao.filter(school);
            subjectList = subDao.filter(school);
        }

        // Handle search based on type
        if ("sj".equals(searchType)) {
            // Subject-based search
            if (entYearStr == null || entYearStr.isEmpty()) {
                errors.put("f1", "入学年度を選択してください");
            } else {
                try {
                    entYear = Integer.parseInt(entYearStr);
                    if (classNum == null || classNum.isEmpty()) {
                        errors.put("f2", "クラスを選択してください");
                    }
                    if (subjectCd == null || subjectCd.isEmpty()) {
                        errors.put("f3", "科目を選択してください");
                    }

                    if (errors.isEmpty()) {
                        List<Student> students = studentDao.findBySubject(entYear, classNum, subjectCd, school);
                        req.setAttribute("students", students);
                    }
                } catch (NumberFormatException e) {
                    errors.put("f1", "入学年度を正しく入力してください");
                }
            }
        } else if ("st".equals(searchType)) {
            // Student-based search
            if (studentNo == null || studentNo.isEmpty()) {
                errors.put("f4", "学生番号を入力してください");
            } else {
                List<Student> students = studentDao.findByNo(studentNo, school);
                if (students.isEmpty()) {
                    errors.put("f4", "該当する学生が見つかりません");
                } else {
                    req.setAttribute("students", students);
                }
            }
        }

        // Set request attributes
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", studentNo);
        req.setAttribute("school", school);
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumList);
        req.setAttribute("subject_set", subjectList);
        req.setAttribute("errors", errors);

        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}