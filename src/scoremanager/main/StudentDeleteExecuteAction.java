package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StudentDao;
import tool.Action;

public class StudentDeleteExecuteAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. 学生番号を取得
        String no = req.getParameter("no");

        // 2. 学生情報を削除
        if (no != null && !no.isEmpty()) {
            StudentDao studentDao = new StudentDao();
            studentDao.deleteStudent(no);  // deleteStudent メソッドで削除処理
        }

        // 3. 完了画面
        req.getRequestDispatcher("student_delete_done.jsp").forward(req, res);
    }
}