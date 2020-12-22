package cn.edu.nju.kg_qa.domain.relation;

import cn.edu.nju.kg_qa.domain.base.BaseNode;
import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * Description: <br/>
 * date: 2020/12/22 19:45<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */

@RelationshipEntity(value = "write",type = "write")
public class WriteRelation<S extends BaseNode,E extends BaseNode> extends BaseRelation {
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

}
