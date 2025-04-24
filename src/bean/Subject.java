package bean;

import java.io.Serializable;

/**
* JavaBean representing a Subject
*/
public class Subject implements Serializable {

    private String schoolcd; // 学校コード: String
    private String cd;       // 科目コード: String
    private String name;     // 科目名: String

    /**
     * Getter and Setter for schoolcd
     */
    public String getSchoolCd() {
        return schoolcd;
    }

    public void setSchoolCd(String schoolcd) {
        this.schoolcd = schoolcd;
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