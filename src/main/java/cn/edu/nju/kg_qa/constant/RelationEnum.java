package cn.edu.nju.kg_qa.constant;

/**
 * Description: <br/>
 * date: 2021/3/1 18:23<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public enum RelationEnum {
    RELATION_WRITE(1, "write"),
    RELATION_ASSIT(2, "assist"),
    RELATION_BELONG(3, "belongTo"),
    RELATION_HUMANOF(4, "humanOf"),
    RELATION_LOCATEIN(5, "locateIn"),
    RELATION_PUBLISH(6, "publish"),
    RELATION_SUBBOOK(7, "subBookOf"),
    RELATION_MONTH(8, "publishMonth"),
    RELATION_YEAR(9, "publishYear"),
    RELATION_BORN(10, "born"),
    RELATION_SPECIALIZE(11, "specialize"),
    RELATION_WORDIN(12, "workIn");

    private String relationName;

    private int rid;

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    RelationEnum(int rid, String relationName) {
        this.relationName = relationName;
        this.rid = rid;
    }
}
