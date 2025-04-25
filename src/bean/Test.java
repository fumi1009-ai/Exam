package bean;

import java.io.Serializable;

public class Test implements Serializable{
	private int no;
	private int point;
	private Student student;
	private Subject subject;
	private School school;
	private String classNum;



	public Subject getSubjet(){
		return subject;
	}

	public void setSubject(Subject subject){
		this.subject = subject;
	}

	public School getSchool(){
		return school;
	}

	public void setSchool(School school){
		this.school = school;
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

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}