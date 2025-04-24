package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {

	/**

	 * アクション実行

	 *

	 * @param req リクエスト

	 * @param res レスポンス

	 *

	 */

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// セッションを取得

        HttpSession session = req.getSession();

        // セッションから教師情報を取得

        Teacher teacher = (Teacher)session.getAttribute("user");

        // 教師情報から学校コードを取得

        School school = teacher.getSchool();

        // パラメータから科目コードを取得

        String subjectCd = req.getParameter("cd");

        // 更新対象の科目情報を取得

        SubjectDao subjectDao = new SubjectDao();

        Subject targetSubject = subjectDao.get(subjectCd, school);

        req.setAttribute("cd", targetSubject.getCd());

        req.setAttribute("name", targetSubject.getName());

        // 科目変更画面を表示する

        req.getRequestDispatcher("subject_update.jsp").forward(req, res);

	}

}

