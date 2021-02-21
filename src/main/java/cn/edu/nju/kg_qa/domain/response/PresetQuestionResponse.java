package cn.edu.nju.kg_qa.domain.response;

/**
 * Description: <br/>
 * date: 2021/2/22 1:06<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class PresetQuestionResponse {
    Integer questionId;

    String questionContent;

    String nodeName;

    Integer nodeId;

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
