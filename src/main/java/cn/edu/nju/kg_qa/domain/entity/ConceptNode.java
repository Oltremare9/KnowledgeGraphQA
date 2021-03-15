package cn.edu.nju.kg_qa.domain.entity;

import cn.edu.nju.kg_qa.domain.base.BaseNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Description: <br/>
 * date: 2020/12/22 18:40<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@ApiModel(value = "概念实体类")
@NodeEntity(label = "concept")
public class ConceptNode extends BaseNode {

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = "concept";
    }

    private String label;

    public ConceptNode(String name, String label) {
        this.name = name;
        setLabel("");
    }
    public ConceptNode(){
        setLabel("");
    }
}
