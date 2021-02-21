package cn.edu.nju.kg_qa.constant;

/**
 * Description: <br/>
 * date: 2021/2/21 21:25<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public enum CypherPropertyPrefixEnum {

    LABEL_AUTHOR("author"),
    LABEL_BOOK("book"),
    LABEL_BOOKSERIES("bookSeries"),
    LABEL_CITY("city"),
    LABEL_CONCEPT("concept"),
    LABEL_INSTITUTE("institute"),
    LABEL_NATION("nation"),

    PROPERTY_LIMIT("limitProperty"),
    PROPERTY_SKIP("skipProperty"),
    PROPERTY_NAME("nameProperty");

    private String propertyName;

    CypherPropertyPrefixEnum(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {

        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
