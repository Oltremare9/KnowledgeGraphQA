package cn.edu.nju.kg_qa.constant;

/**
 * Description: <br/>
 * date: 2021/3/1 18:41<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public enum RedisPrefix {
    Q_Type_ZSet(1,"Q_Type_ZSet",""),
    E_Type_ZSet(2,"E_Type_ZSet",""),
    E_Node_ZSet(3,"E_","E_+节点类型名+_节点id")
    ;

    private int prefixId;
    private String prefix;
    private String comment;

    RedisPrefix(int prefixId, String prefix, String comment) {
        this.prefixId = prefixId;
        this.prefix = prefix;
        this.comment = comment;
    }

    public int getPrefixId() {
        return prefixId;
    }

    public void setPrefixId(int prefixId) {
        this.prefixId = prefixId;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
