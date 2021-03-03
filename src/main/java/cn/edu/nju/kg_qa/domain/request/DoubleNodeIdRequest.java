package cn.edu.nju.kg_qa.domain.request;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;

/**
 * Description: <br/>
 * date: 2021/3/4 0:37<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class DoubleNodeIdRequest {

    @NumberFormat
    @NotNull(message = "节点名称不为空")
    Long nodeId1;

    @NumberFormat
    @NotNull(message = "节点名称不为空")
    Long nodeId2;

    public Long getNodeId1() {
        return nodeId1;
    }

    public void setNodeId1(Long nodeId1) {
        this.nodeId1 = nodeId1;
    }

    public Long getNodeId2() {
        return nodeId2;
    }

    public void setNodeId2(Long nodeId2) {
        this.nodeId2 = nodeId2;
    }
}
