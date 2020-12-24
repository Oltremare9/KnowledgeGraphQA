package cn.edu.nju.kg_qa.repository;

import cn.edu.nju.kg_qa.domain.relation.BelongRelation;
import cn.edu.nju.kg_qa.domain.relation.PublishRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: <br/>
 * date: 2020/12/24 22:54<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Repository
public interface PublishRepository extends Neo4jRepository<PublishRelation,Long> {

    /**
     * 根据机构名查询关系路径节点
     * @param instituteName
     * @return
     */
    @Query("Match (p:book)-[r]-(c:institute) where c.name=$instituteName return p,r,c")
    List<PublishRelation> findPublishRelationByInstituteName(@Param("instituteName") String instituteName);

    /**
     * 根据书籍名查询关系路径节点
     * @param bookName
     * @return
     */
    @Query("Match (p:book)-[r]-(c:institute) where p.name=$bookName return p,r,c")
    List<PublishRelation> findPublishRelationByBookName(@Param("bookName") String bookName);
}
