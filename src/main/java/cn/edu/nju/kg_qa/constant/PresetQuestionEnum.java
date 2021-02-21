package cn.edu.nju.kg_qa.constant;

/**
 * Description: <br/>
 * date: 2021/2/21 21:12<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public enum PresetQuestionEnum {
    WHO_ASSIST_SOMEONE(1, CypherPropertyPrefixEnum.LABEL_AUTHOR.getPropertyName(),
            "[]与谁有合作关系？"),
    WHO_BELONG_TO_NATION(2, CypherPropertyPrefixEnum.LABEL_AUTHOR.getPropertyName(),
            "[]是什么时代的人？"),
    WHO_WRITE_BOOK(3, CypherPropertyPrefixEnum.LABEL_AUTHOR.getPropertyName(),
            "[]写了哪些书？"),
    WHO_COOPERATE_INSTITUTE(4, CypherPropertyPrefixEnum.LABEL_AUTHOR.getPropertyName(),
            "[]与哪些出版社有合作？");



    PresetQuestionEnum(Integer id, String startLabel, String questionDescription) {
        this.id = id;
        this.startLabel = startLabel;
        this.questionDescription = questionDescription;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartLabel() {
        return startLabel;
    }

    public void setStartLabel(String startLabel) {
        this.startLabel = startLabel;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    private Integer id;

    private String startLabel;

    private String questionDescription;


}
