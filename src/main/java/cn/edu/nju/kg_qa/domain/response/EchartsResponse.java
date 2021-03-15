package cn.edu.nju.kg_qa.domain.response;

import cn.edu.nju.kg_qa.domain.dto.EcharsNodesDto;
import cn.edu.nju.kg_qa.domain.dto.EchartsCategotyDto;
import cn.edu.nju.kg_qa.domain.dto.EchartsRelationDto;

import java.util.ArrayList;

/**
 * Description: <br/>
 * date: 2021/3/13 20:51<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */

public class EchartsResponse {
    ArrayList<EcharsNodesDto> nodes;

    ArrayList<EchartsRelationDto> links;

    ArrayList<EchartsCategotyDto> categories;

    public ArrayList<EcharsNodesDto> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<EcharsNodesDto> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<EchartsRelationDto> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<EchartsRelationDto> links) {
        this.links = links;
    }

    public ArrayList<EchartsCategotyDto> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<EchartsCategotyDto> categories) {
        this.categories = categories;
    }
}
