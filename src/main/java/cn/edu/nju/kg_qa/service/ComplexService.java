package cn.edu.nju.kg_qa.service;

import cn.edu.nju.kg_qa.domain.base.BaseNode;
import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import cn.edu.nju.kg_qa.repository.ComplexNodeRepository;
import cn.edu.nju.kg_qa.repository.ComplexRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: <br/>
 * date: 2020/12/25 1:25<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class ComplexService {
    @Autowired
    ComplexRelationRepository complexRelationRepository;

    @Autowired
    ComplexNodeRepository complexNodeRepository;

    /**
     * 根据节点名返回所有符合条件的关系
     *
     * @param nodeName
     * @return
     */
    public List<BaseRelation> findAnyProperRelationByName(String nodeName) {
        return complexRelationRepository.findAnyProperRelationByName(nodeName);
    }

    /**
     * 根据节点名返回所有符合条件的节点
     *
     * @param nodeName
     * @return
     */
    public List<BaseNode> findAnyProperNodesByName(String nodeName) {
        return complexNodeRepository.findAnyProperNodesByName(nodeName);
    }
}
