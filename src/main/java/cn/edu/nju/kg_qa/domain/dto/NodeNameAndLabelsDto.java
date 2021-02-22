package cn.edu.nju.kg_qa.domain.dto;

import org.neo4j.ogm.annotation.Labels;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.awt.*;
import java.util.ArrayList;

/**
 * Description: <br/>
 * date: 2021/2/21 23:36<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */

@QueryResult
public class NodeNameAndLabelsDto {
    private String nodeName;

    private ArrayList<String> label;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<String> getLabel() {
        return label;
    }

    public void setLabel(ArrayList<String> label) {
        this.label = label;
    }

    private String relationType;

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }
}
