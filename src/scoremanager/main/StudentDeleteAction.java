package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import dao.StudentDao;
import tool.Action;

public class StudentDeleteAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 学生番号をリクエストパラメータから取得
        String no = req.getParameter("no");

        // 学生番号が指定されている場合のみ処理
        if (no != null && !no.isEmpty()) {
            StudentDao studentDao = new StudentDao();
            Student student = studentDao.get(no); // 学生情報を取得

            // 学生情報を student という名前でリクエストにセット
            req.setAttribute("student", student);
        }

        // 学生削除確認画面（JSP）へフォワード
        req.getRequestDispatcher("student_delete.jsp").forward(req, res);
    }
}