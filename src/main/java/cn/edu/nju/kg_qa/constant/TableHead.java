package cn.edu.nju.kg_qa.constant;

/**
 * Description: <br/>
 * date: 2021/1/5 16:35<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public enum TableHead {
    /*
    作者表头
     */
    AUTHOR("author"),
    /*
    isbn
     */
    ISBN("isbn"),
    /*
    出版社表头
     */
    PUBLISHMENT("publishment"),
    /*
    日期表头
     */
    DATE("date");

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;

    private TableHead(String value) {
        this.value = value;
    }


}
