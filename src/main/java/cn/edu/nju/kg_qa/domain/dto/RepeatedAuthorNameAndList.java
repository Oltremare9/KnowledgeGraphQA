package cn.edu.nju.kg_qa.domain.dto;

import cn.edu.nju.kg_qa.domain.entity.AuthorNode;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/3/3 22:40<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@QueryResult
public class RepeatedAuthorNameAndList {
    String nodeName;

    List<AuthorNode> repeatedNodes;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<AuthorNode> getRepeatedNodes() {
        return repeatedNodes;
    }

    public void setRepeatedNodes(List<AuthorNode> repeatedNodes) {
        this.repeatedNodes = repeatedNodes;
    }
}
