package cn.edu.nju.kg_qa.domain.dto;

import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * Description: <br/>
 * date: 2021/3/2 18:38<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@QueryResult
public class NodeNameAndNodeIdDto {
    String nodeName;
    Long nodeId;

    public NodeNameAndNodeIdDto(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }
}
