package cn.edu.nju.kg_qa.service;

import cn.edu.nju.kg_qa.domain.relation.BelongRelation;
import cn.edu.nju.kg_qa.domain.relation.PublishRelation;
import cn.edu.nju.kg_qa.repository.PublishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: <br/>
 * date: 2020/12/24 22:54<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class PublishService {
    @Autowired
    PublishRepository publishRepository;

    /**
     * 根据机构名查询关系路径节点
     * @param instituteName
     * @return
     */
    public List<PublishRelation> findPublishRelationByInstituteName(String instituteName){
        return publishRepository.findPublishRelationByInstituteName(instituteName);
    }

    /**
     * 根据书籍名查询关系路径节点
     * @param bookName
     * @return
     */
    public List<PublishRelation> findPublishRelationByBookName(String bookName){
        return publishRepository.findPublishRelationByBookName(bookName);
    }
}
