package cn.edu.nju.kg_qa.domain.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.awt.datatransfer.DataFlavor;

/**
 * Description: <br/>
 * date: 2020/12/22 19:35<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@ApiModel(value = "node基类")
@NodeEntity(label = "")
public class BaseNode extends Base{

    @Id
    @ApiModelProperty(value = "neo4j默认节点id")
    private Long identity;

    public Long getIdentity() {
        return identity;
    }

    public void setIdentity(Long identity) {
        this.identity = identity;
    }
}
