package cn.edu.nju.kg_qa.domain.response;

import cn.edu.nju.kg_qa.domain.dto.NodeNameAndNodeIdDto;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/3/2 17:20<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class TopicResponse {
    private String label;

    private List<NodeNameAndNodeIdDto> nodeNameAndNodeIdDto;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<NodeNameAndNodeIdDto> getNodeNameAndNodeIdDto() {
        return nodeNameAndNodeIdDto;
    }

    public void setNodeNameAndNodeIdDto(List<NodeNameAndNodeIdDto> nodeNameAndNodeIdDto) {
        this.nodeNameAndNodeIdDto = nodeNameAndNodeIdDto;
    }
}
