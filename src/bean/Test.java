package bean;

import java.io.Serializable;

public class Test implements Serializable{
	// 学生情報Bean
	private Student student;

	// 科目情報Bean
	private Subject subject;

	// 学校情報Bean
	private School school;

	private String studentNo;
	private String subjectCd;
	private String schoolCd;
	private int no;
	private int point;
	private String classNum;

	// 学生情報Beanの取得
	public Student getStudent() {
		return student;
	}

	// 学生情報Beanの設定
	public void setStudent(Student student) {
		this.student = student;
	}

	// 科目情報Beanの取得
	public Subject getSubject() {
		return subject;
	}

	// 科目情報Beanの設定
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	// 学校情報Beanの取得
	public School getSchool() {
		return school;
	}

	// 学校情報Beanの設定
	public void setSchool(School school) {
		this.school = school;
	}

    // Getter and Setter for studentNo
    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    // Getter and Setter for subjectCd
    public String getSubjectCd() {
        return subjectCd;
    }

    public void setSubjectCd(String subjectCd) {
        this.subjectCd = subjectCd;
    }

    // Getter and Setter for schoolCd
    public String getSchoolCd() {
        return schoolCd;
    }

    public void setSchoolCd(String schoolCd) {
        this.schoolCd = schoolCd;
    }

    // Getter and Setter for no (test number)
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    // Getter and Setter for point (score)
    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    // Getter and Setter for classNum
    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }
}