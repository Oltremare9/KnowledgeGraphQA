package cn.edu.nju.kg_qa.domain.entity;

import cn.edu.nju.kg_qa.domain.base.BaseNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.neo4j.ogm.annotation.*;

/**
 * Description: <br/>
 * date: 2020/12/22 18:02<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@ApiModel(value = "书籍实体类")
@NodeEntity(label = "book")
public class BookNode extends BaseNode {


    @Property
    @ApiModelProperty(value = "图书isbn唯一id")
    private String id;

    @Property
    @ApiModelProperty(value = "中图分类号")
    private String classification;

    @Property
    @ApiModelProperty(value = "出版日期")
    private String date;

    @Property
    @ApiModelProperty(value = "书籍名称")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = "book";
    }

    private String label;

    public BookNode(String id, String classification, String date, String name, String label) {
        this.id = id;
        this.classification = classification;
        this.date = date;
        this.name = name;
        setLabel(" ");
    }
    public BookNode(){
        setLabel(" ");
    }
}
