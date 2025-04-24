package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");  // 教師情報取得

        // フォームからの入力データを取得
        String cd = req.getParameter("cd");  // 科目コード
        String name = req.getParameter("name");  // 科目名

        Map<String, String> errors = new HashMap<>();
        SubjectDao subjectDao = new SubjectDao();

        // 入力チェック
        if (cd == null || cd.isEmpty()) {
            errors.put("cd", "科目コードを入力してください");
        }
        if (name == null || name.isEmpty()) {
            errors.put("name", "科目名を入力してください");
        }

        // エラーがある場合は戻す
        if (!errors.isEmpty()) {
            session.setAttribute("errors", errors);
            res.sendRedirect("SubjectCreate.action");
            return;
        }

        // 科目コードの重複チェック（学校単位で確認）
        if (subjectDao.get(cd, teacher.getSchool()) != null) {
            errors.put("cd", "この科目コードは既に使われています");
            session.setAttribute("errors", errors);
            res.sendRedirect("SubjectCreate.action");
            return;
        }

        // 科目情報を作成
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchoolCd(teacher.getSchool().getCd());  // 学校コードセット
     // 科目情報をデータベースに保存
        boolean isSaved = subjectDao.save(subject);

        if (isSaved) {
            session.setAttribute("successMessage", "科目の登録が完了しました");
            res.sendRedirect("subject_create_done.jsp");
        } else {
            session.setAttribute("errorMessage", "科目の登録に失敗しました");
            res.sendRedirect("subject_create.jsp");
        }
    }
}
