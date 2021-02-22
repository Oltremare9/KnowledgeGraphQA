package cn.edu.nju.kg_qa.constant;

/**
 * Description: <br/>
 * date: 2021/2/21 21:25<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public enum CypherArgsEnum {

    LABEL_AUTHOR("author"),
    LABEL_BOOK("book"),
    LABEL_BOOKSERIES("bookSeries"),
    LABEL_CITY("city"),
    LABEL_CONCEPT("concept"),
    LABEL_INSTITUTE("institute"),
    LABEL_NATION("nation"),
    LABEL_DIRECTION("researchDirection"),
    LABEL_COMPANY("company"),


    RELATION_WRITE("write"),
    RELATION_ASSIT("assist"),
    RELATION_BELONG("belongTo"),
    RELATION_HUMANOF("humanOf"),
    RELATION_LOCATEIN("locateIn"),
    RELATION_PUBLISH("publish"),
    RELATION_SUBBOOK("subBookOf"),
    RELATION_MONTH("publishMonth"),
    RELATION_YEAR("publishYear"),
    RELATION_BORN("born"),
    RELATION_SPECIALIZE("specialize"),
    RELATION_WORDIN("workIn");

    private String propertyName;

    CypherArgsEnum(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {

        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
