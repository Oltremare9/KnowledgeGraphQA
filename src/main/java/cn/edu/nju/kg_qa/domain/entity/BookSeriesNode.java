package cn.edu.nju.kg_qa.domain.entity;

import cn.edu.nju.kg_qa.domain.base.BaseNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * Description: <br/>
 * date: 2021/2/17 22:03<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */

@ApiModel(value = "系类丛书实体类")
@NodeEntity(label = "bookSeries")
public class BookSeriesNode extends BaseNode {

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
        this.label = "bookSeries";
    }

    @ApiModelProperty(value = "节点类别")
    private String label;


    public BookSeriesNode(String name, String label) {
        this.name = name;
        this.label = "bookSeries";
    }

    public BookSeriesNode() {
        this.setLabel("");
    }
}
