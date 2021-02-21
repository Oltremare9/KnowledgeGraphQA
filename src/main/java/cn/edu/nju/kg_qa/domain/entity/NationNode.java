package cn.edu.nju.kg_qa.domain.entity;

import cn.edu.nju.kg_qa.domain.base.BaseNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Description: <br/>
 * date: 2021/2/17 22:15<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@ApiModel(value = "国籍、朝代实体")
@NodeEntity(label = "nation")
public class NationNode extends BaseNode {
    @Property
    @ApiModelProperty(value = "国籍名/朝代名")
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
        this.label = "nation";
    }

    @ApiModelProperty(value = "节点类别")
    private String label;


    public NationNode(String name, String label) {
        this.name = name;
        this.setLabel("");
    }

    public NationNode() {
        this.setLabel("");
    }
}
