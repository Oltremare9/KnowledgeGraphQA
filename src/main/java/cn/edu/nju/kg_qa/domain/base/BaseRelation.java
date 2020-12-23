package cn.edu.nju.kg_qa.domain.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;

/**
 * Description: <br/>
 * date: 2020/12/22 19:38<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@ApiModel(value = "关系基类")
@RelationshipEntity()
public class BaseRelation extends Base{
    @Id
    @GeneratedValue
    @ApiModelProperty(value = "neo4j默认主键")
    private Long identity;

    @Property
    @ApiModelProperty(value = "关系名")
    private String relationName;

    public Long getIdentity() {
        return identity;
    }

    public void setIdentity(Long identity) {
        this.identity = identity;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }
}
