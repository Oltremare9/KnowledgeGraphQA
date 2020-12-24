package cn.edu.nju.kg_qa.service;

import cn.edu.nju.kg_qa.domain.base.Base;
import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import cn.edu.nju.kg_qa.domain.relation.BelongRelation;
import cn.edu.nju.kg_qa.repository.BelongToRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: <br/>
 * date: 2020/12/24 17:39<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class BelongToService {
    @Autowired
    BelongToRepository belongToRepository;
    /**
     * 根据概念名查询关系路径节点
     * @param conceptName
     * @return
     */
    public List<BelongRelation> findBelongToRelationByConceptName(String conceptName){
        return belongToRepository.findBelongToRelationByConceptName(conceptName);
    }

    /**
     * 根据书籍名查询关系路径节点
     * @param bookName
     * @return
     */
    public List<BelongRelation> findBelongToRelationByBookName(String bookName){
        return belongToRepository.findBelongToRelationByBookName(bookName);
    }
}
