package cn.edu.nju.kg_qa.domain.entity;

import cn.edu.nju.kg_qa.domain.base.BaseNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Description: <br/>
 * date: 2020/12/22 18:38<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@ApiModel(value = "作者实体类")
@NodeEntity(label = "author")
public class AuthorNode extends BaseNode {

    @Property
    @ApiModelProperty(value = "作者名")
    private String name;

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
        this.label = "author";
    }

    private String label;

    @Property
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AuthorNode(String name, String label) {
        this.name = name;
        this.label = "author";
    }

    public AuthorNode(){
        this.label="author";
    }
    @Property
    @ApiModelProperty(value = "作者生平")
    public String description;
    @Property
    @ApiModelProperty(value = "作者别名")
    public String otherNames;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }
}
