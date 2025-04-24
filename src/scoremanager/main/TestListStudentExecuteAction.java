package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.StudentDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // Check if teacher is logged in
        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // Parameter handling
        String studentNo = req.getParameter("f4");
        Map<String, String> errors = new HashMap<>();

        // Initialize DAOs
        StudentDao studentDao = new StudentDao();
        TestListStudentDao testListDao = new TestListStudentDao();

        // Handle student search
        if (studentNo == null || studentNo.isEmpty()) {
            errors.put("f4", "学生番号を入力してください");
        } else {
            // Get student information
            Student student = studentDao.get(studentNo);
            if (student == null) {
                errors.put("f4", "該当する学生が見つかりません");
            } else {
                // Get test results for the student
                List<TestListStudent> testResults = testListDao.findByStudent(studentNo);

                // Set attributes for display
                req.setAttribute("student", student);
                req.setAttribute("testResults", testResults);
            }
        }

        // Set request attributes
        req.setAttribute("f4", studentNo);
        req.setAttribute("errors", errors);

        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}