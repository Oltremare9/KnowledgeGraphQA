package cn.edu.nju.kg_qa.domain.entity;

import cn.edu.nju.kg_qa.domain.base.BaseNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Description: <br/>
 * date: 2020/12/22 18:35<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */

@ApiModel(value = "出版机构实体类")
@NodeEntity(label = "institute")
public class InstituteNode extends BaseNode {

    @Property
    @ApiModelProperty(value = "出版机构名称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Property
    @ApiModelProperty(value = "出版机构位置")
    private String position;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = "institute";
    }

    private String label;

    public InstituteNode(String name, String position, String label) {
        this.name = name;
        this.position = position;
        setLabel("");
    }
    public InstituteNode(){
        setLabel("");
    }
}
