package bean;

import java.io.Serializable;

public class TestListStudent implements Serializable{
	private String subjectName;
	private String subjectCd;
	private int num;
	private int point;

	   public String getSubjectName() {
	        return subjectName;
	    }

	    public void setSubjectName(String subjectName) {
	        this.subjectName = subjectName;
	    }

	    // Getter and Setter for subjectCd
	    public String getSubjectCd() {
	        return subjectCd;
	    }

	    public void setSubjectCd(String subjectCd) {
	        this.subjectCd = subjectCd;
	    }

	    // Getter and Setter for num (number of students)
	    public int getNum() {
	        return num;
	    }

	    public void setNum(int num) {
	        this.num = num;
	    }

	    // Getter and Setter for point (total score or average score)
	    public int getPoint() {
	        return point;
	    }

	    public void setPoint(int point) {
	        this.point = point;
	    }


}
