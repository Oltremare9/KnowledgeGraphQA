package cn.edu.nju.kg_qa.domain.entity;

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
public class NodeNameAndLabelsData {
    private String nodeName;

    private ArrayList<String> label;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public ArrayList<String> getLabel() {
        return label;
    }

    public void setLabel(ArrayList<String> label) {
        this.label = label;
    }
}
