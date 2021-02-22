package cn.edu.nju.kg_qa.constant;

/**
 * Description: 单跳问题id<=100 多跳问题id>100 <br/>
 * date: 2021/2/21 21:12<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public enum PresetQuestionEnum {
    WHO_BELONG_TO_NATION(1, CypherArgsEnum.LABEL_AUTHOR.getPropertyName(),CypherArgsEnum.LABEL_NATION.getPropertyName(),
            CypherArgsEnum.RELATION_HUMANOF.getPropertyName(),
            "[]是什么时代（国家）的人？"),
    WHO_WRITE_BOOK(2, CypherArgsEnum.LABEL_AUTHOR.getPropertyName(),CypherArgsEnum.LABEL_BOOK.getPropertyName(),
            CypherArgsEnum.RELATION_WRITE.getPropertyName(),
            "[]写了哪些书？"),
    WHO_ASSIST_BOOK(3, CypherArgsEnum.LABEL_AUTHOR.getPropertyName(),CypherArgsEnum.LABEL_BOOK.getPropertyName(),
            CypherArgsEnum.RELATION_ASSIT.getPropertyName(),
            "[]协助完成了哪些书？"),
    WHO_BORN_CITY(4, CypherArgsEnum.LABEL_AUTHOR.getPropertyName(),CypherArgsEnum.LABEL_CITY.getPropertyName(),
            CypherArgsEnum.RELATION_BORN.getPropertyName(),
            "[]的籍贯是？"),
    WHO_SPECIALIZE_DIRECTION(5,CypherArgsEnum.LABEL_AUTHOR.getPropertyName(),CypherArgsEnum.LABEL_DIRECTION.getPropertyName(),
            CypherArgsEnum.RELATION_SPECIALIZE.getPropertyName(),
            "[]的研究方向是？"),
    WHO_WORKIN_COMPANY(6,CypherArgsEnum.LABEL_AUTHOR.getPropertyName(),CypherArgsEnum.LABEL_COMPANY.getPropertyName(),
            CypherArgsEnum.RELATION_WORDIN.getPropertyName(),
            "[]任职于何处"),

    DIRECTION_SPECIALIZE_AUTHOR(7,CypherArgsEnum.LABEL_DIRECTION.getPropertyName(),CypherArgsEnum.LABEL_AUTHOR.getPropertyName(),
            CypherArgsEnum.RELATION_SPECIALIZE.getPropertyName(),
            "还有哪些人从事[]领域研究？"),

    COMPANY_WORDIN_AUTHOR(8,CypherArgsEnum.LABEL_COMPANY.getPropertyName(),CypherArgsEnum.LABEL_AUTHOR.getPropertyName(),
            CypherArgsEnum.RELATION_WORDIN.getPropertyName(),
            "还有哪些人工作于[]？"),

    NATION_HUMANOF_AUTHOR(9,CypherArgsEnum.LABEL_NATION.getPropertyName(),CypherArgsEnum.LABEL_AUTHOR.getPropertyName(),
            CypherArgsEnum.RELATION_HUMANOF.getPropertyName(),
            "还有哪些人属于[]？"),

    CITY_BORN_AUTHOR(10,CypherArgsEnum.LABEL_CITY.getPropertyName(),CypherArgsEnum.LABEL_AUTHOR.getPropertyName(),
            CypherArgsEnum.RELATION_BORN.getPropertyName(),
            "还有哪些人也是[]人？");


    private Integer qid;

    private String srcNodeType;

    private String dstNodeType;

    private String relationName;

    private String questionDescription;

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public String getSrcNodeType() {
        return srcNodeType;
    }

    public void setSrcNodeType(String srcNodeType) {
        this.srcNodeType = srcNodeType;
    }

    public String getDstNodeType() {
        return dstNodeType;
    }

    public void setDstNodeType(String dstNodeType) {
        this.dstNodeType = dstNodeType;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    PresetQuestionEnum(Integer qid, String srcNodeType, String dstNodeType, String relationName, String questionDescription) {
        this.qid = qid;
        this.srcNodeType = srcNodeType;
        this.dstNodeType = dstNodeType;
        this.relationName = relationName;
        this.questionDescription = questionDescription;
    }
}
