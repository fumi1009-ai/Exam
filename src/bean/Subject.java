package bean;

import java.io.Serializable;

/**
 * JavaBean representing a Subject
 */
public class Subject implements Serializable {

    private String school; // 学校コード: String
    private String cd;       // 科目コード: String
    private String name;     // 科目名: String

    /**
     * Getter and Setter for schoolcd
     */
    public String getSchool() {
        return school;
    }

    public void setSchoolCd(String school) {
        this.school = school;
    }

    /**
     * Getter and Setter for cd (subject code)
     */
    public String getCd() {
        return cd;
    }

    public void setCd(String i) {
        this.cd = i;
    }

    /**
     * Getter and Setter for name (subject name)
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
