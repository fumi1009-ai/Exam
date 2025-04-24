package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. 学生番号を取得
        String cd = req.getParameter("cd");
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");  // 教師情報取得

        String schoolCd = teacher.getSchool().getCd();

        System.out.println("0424です cd -> " + cd);
    	System.out.println("0424です schoolCd -> " + schoolCd);

        // 科目を削除
        if (cd != null) {
        	System.out.println("いくよ？");
        	SubjectDao subjectDao = new SubjectDao();
        	subjectDao.deleteSubject(cd, schoolCd);  // deleteSubject メソッドで削除処理
        }

        // 3. 完了画面
        req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
    }
}