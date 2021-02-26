package cn.edu.nju.kg_qa.domain.request;

import java.io.Serializable;

import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModelProperty;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
/**
 * Description: <br/>
 * date: 2021/2/23 2:47<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class OneHopRequest {
    private Integer nodeId;

    private Integer qid;

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    @NotEmpty(message = "节点名称不为空")
    String nodeName;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    @ApiModelProperty("分页页码")
    @Nullable
    Integer skip;
}
