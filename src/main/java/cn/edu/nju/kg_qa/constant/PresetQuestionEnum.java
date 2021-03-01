package cn.edu.nju.kg_qa.constant;

/**
 * Description: 单跳问题id<=100 多跳问题id>100 <br/>
 * date: 2021/2/21 21:12<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */

public enum PresetQuestionEnum {
    AUTHOR_BELONG_TO_NATION(1, LabelEnum.LABEL_AUTHOR.getPropertyName(), LabelEnum.LABEL_NATION.getPropertyName(),
            RelationEnum.RELATION_HUMANOF.getRelationName(),
            "[]是什么时代（国家）的人？"),
    AUTHOR_WRITE_BOOK(2, LabelEnum.LABEL_AUTHOR.getPropertyName(), LabelEnum.LABEL_BOOK.getPropertyName(),
            RelationEnum.RELATION_WRITE.getRelationName(),
            "[]写了哪些书？"),
    AUTHOR_ASSIST_BOOK(3, LabelEnum.LABEL_AUTHOR.getPropertyName(), LabelEnum.LABEL_BOOK.getPropertyName(),
            RelationEnum.RELATION_ASSIT.getRelationName(),
            "[]协助完成了哪些书？"),
    AUTHOR_BORN_CITY(4, LabelEnum.LABEL_AUTHOR.getPropertyName(), LabelEnum.LABEL_CITY.getPropertyName(),
            RelationEnum.RELATION_BORN.getRelationName(),
            "[]的籍贯是？"),
    AUTHOR_SPECIALIZE_DIRECTION(5, LabelEnum.LABEL_AUTHOR.getPropertyName(), LabelEnum.LABEL_DIRECTION.getPropertyName(),
            RelationEnum.RELATION_SPECIALIZE.getRelationName(),
            "[]的研究方向是？"),
    AUTHOR_WORKIN_COMPANY(6, LabelEnum.LABEL_AUTHOR.getPropertyName(), LabelEnum.LABEL_COMPANY.getPropertyName(),
            RelationEnum.RELATION_WORDIN.getRelationName(),
            "[]任职于何处"),

    DIRECTION_SPECIALIZE_AUTHOR(7, LabelEnum.LABEL_DIRECTION.getPropertyName(), LabelEnum.LABEL_AUTHOR.getPropertyName(),
            RelationEnum.RELATION_SPECIALIZE.getRelationName(),
            "还有哪些人从事[]领域研究？"),

    COMPANY_WORDIN_AUTHOR(8, LabelEnum.LABEL_COMPANY.getPropertyName(), LabelEnum.LABEL_AUTHOR.getPropertyName(),
            RelationEnum.RELATION_WORDIN.getRelationName(),
            "还有哪些人工作于[]？"),

    NATION_HUMANOF_AUTHOR(9, LabelEnum.LABEL_NATION.getPropertyName(), LabelEnum.LABEL_AUTHOR.getPropertyName(),
            RelationEnum.RELATION_HUMANOF.getRelationName(),
            "还有哪些人属于[]？"),

    CITY_BORN_AUTHOR(10, LabelEnum.LABEL_CITY.getPropertyName(), LabelEnum.LABEL_AUTHOR.getPropertyName(),
            RelationEnum.RELATION_BORN.getRelationName(),
            "还有哪些人也是[]人？"),

    BOOK_WRITE_AUTHOR(11, LabelEnum.LABEL_BOOK.getPropertyName(), LabelEnum.LABEL_AUTHOR.getPropertyName(),
            RelationEnum.RELATION_WRITE.getRelationName(),
            "[]是谁写的？"),

    BOOK_ASSIST_AUTHOR(12, LabelEnum.LABEL_BOOK.getPropertyName(), LabelEnum.LABEL_AUTHOR.getPropertyName(),
            RelationEnum.RELATION_ASSIT.getRelationName(),
            "[]由谁协助完成？"),

    BOOK_PUBLISH_INSTITUTE(13, LabelEnum.LABEL_BOOK.getPropertyName(), LabelEnum.LABEL_INSTITUTE.getPropertyName(),
            RelationEnum.RELATION_PUBLISH.getRelationName(),
            "[]是由哪家出版社出版？"),

    INSTITUTE_PUBLISH_BOOK(14, LabelEnum.LABEL_INSTITUTE.getPropertyName(), LabelEnum.LABEL_BOOK.getPropertyName(),
            RelationEnum.RELATION_PUBLISH.getRelationName(),
            "[]出版了哪些书？"),

    BOOKSERIES_SUB_BOOK(15, LabelEnum.LABEL_BOOKSERIES.getPropertyName(), LabelEnum.LABEL_BOOK.getPropertyName(),
            RelationEnum.RELATION_SUBBOOK.getRelationName(),
            "[]系列下还有哪些书？"),

    BOOK_SUB_BOOKSEIRES(16, LabelEnum.LABEL_BOOK.getPropertyName(), LabelEnum.LABEL_BOOKSERIES.getPropertyName(),
            RelationEnum.RELATION_SUBBOOK.getRelationName(),
            "[]隶属于什么系列？"),

    CONCEPT_BELONG_BOOK(17, LabelEnum.LABEL_CONCEPT.getPropertyName(), LabelEnum.LABEL_BOOK.getPropertyName(),
            RelationEnum.RELATION_BELONG.getRelationName(),
            "[]领域下有哪些书？"),

    BOOK_BELONG_CONCEPT(18, LabelEnum.LABEL_BOOK.getPropertyName(), LabelEnum.LABEL_CONCEPT.getPropertyName(),
            RelationEnum.RELATION_BELONG.getRelationName(),
            "[]属于哪个分类？");


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

    @Override
    public String toString() {
        return "{" +
                "qid=" + qid +
                ", srcNodeType='" + srcNodeType + '\'' +
                ", dstNodeType='" + dstNodeType + '\'' +
                ", relationName='" + relationName + '\'' +
                ", questionDescription='" + questionDescription + '\'' +
                '}';
    }
}
