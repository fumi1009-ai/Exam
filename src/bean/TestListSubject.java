package bean;

import java.io.Serializable;

public class TestListSubject implements Serializable{

	private int entYear;

	private String studentNo;

	private String studentName;

	private String classNum;

	private int points;

	public int getEntYaer(){

	return entYear;

	}

	public void setEntYear(int entYear){

		this.entYear = entYear;

	}

	public String getStudentNo(){

		return studentNo;

	}

	public void setStudentNo(String studentNo){

		this.studentNo = studentNo;

	}

	public String getStudentname(){

		return studentName;

	}

	public void setStudentName(String studentName){

		this.studentName = studentName;

	}

	public String getClassNum(){

		return classNum;

	}

	public void setClassNum(String classNum){

		this.classNum =classNum;

	}

    public int getPoints() {

        return points;

    }

    public void setPoints(int i) {

        this.points = i;

    }

	public void setNo(int int1) {

		// TODO 自動生成されたメソッド・スタブ

	}

}
