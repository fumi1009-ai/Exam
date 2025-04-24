package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

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

        // 科目コード

        String cd = req.getParameter("cd");

        // 科目名

		String name = req.getParameter("name");

		SubjectDao subjectDao = new SubjectDao();

		Subject subject = new Subject();

		// 学校コード

		subject.setSchoolCd(school.getCd());

		// 科目コード

		subject.setCd(cd);

		// 科目名

		subject.setName(name);

		subjectDao.save(subject);

		req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);

	}

}

