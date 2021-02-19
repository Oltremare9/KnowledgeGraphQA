package cn.edu.nju.kg_qa.domain.relation;

import cn.edu.nju.kg_qa.domain.base.BaseNode;
import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Description: <br/>
 * date: 2021/2/17 22:23<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */

@RelationshipEntity(type = "assist",value = "assist")
public class AssistRelation<S extends BaseNode,E extends BaseNode> extends BaseRelation {

    @StartNode
    private S startNode;

    public S getStartNode() {
        return startNode;
    }

    public void setStartNode(S startNode) {
        this.startNode = startNode;
    }

    public E getEndNode() {
        return endNode;
    }

    public void setEndNode(E endNode) {
        this.endNode = endNode;
    }

    @EndNode
    private E endNode;

    @Override
    public void setRelationType(String relationType) {
        super.setRelationType("assist");
    }

    public AssistRelation(){
        this.setRelationType("");
    }
}

