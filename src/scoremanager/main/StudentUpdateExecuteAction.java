package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {


		 String no = "";
	     String name = "";
	     int entYear = 0;
	     String class_num = "";
	     String isAttendStr = "";
	     boolean isAttend = false;
	     Student student = new Student();
	     StudentDao studentDao = new StudentDao();

	     entYear = Integer.parseInt(req.getParameter("ent_year"));
	     no = req.getParameter("no");
	     name = req.getParameter("name");
	     class_num = req.getParameter("class_num");
	     isAttendStr = req.getParameter("is_attend");

	     if (isAttendStr != null) {
	    	 isAttend = true;
	     }

	     student.setNo(no);
	     student.setName(name);
	     student.setEntYear(entYear);
	     student.setClassNum(class_num);
	     student.setAttend(isAttend);

	     studentDao.save(student);

	     req.getRequestDispatcher("student_update_done.jsp").forward(req, res);

	}
}