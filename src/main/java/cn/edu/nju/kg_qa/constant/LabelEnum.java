package cn.edu.nju.kg_qa.constant;

/**
 * Description: <br/>
 * date: 2021/2/21 21:25<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public enum LabelEnum {

    LABEL_AUTHOR(1,"author"),
    LABEL_BOOK(2,"book"),
    LABEL_BOOKSERIES(3,"sub"),
    LABEL_CITY(4,"city"),
    LABEL_CONCEPT(5,"concept"),
    LABEL_INSTITUTE(6,"institute"),
    LABEL_NATION(7,"nation"),
    LABEL_DIRECTION(8,"research"),
    LABEL_COMPANY(9,"company");

    private String entityName;

    private int eid;

    public int getId() {
        return eid;
    }

    public void setId(int id) {
        this.eid = id;
    }

    LabelEnum(int eid, String propertyName) {
        this.entityName = propertyName;
        this.eid = eid;
    }

    public String getPropertyName() {

        return entityName;
    }

    public void setPropertyName(String propertyName) {
        this.entityName = propertyName;
    }
}
