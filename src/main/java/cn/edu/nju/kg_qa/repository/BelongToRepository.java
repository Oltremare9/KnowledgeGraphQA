package cn.edu.nju.kg_qa.repository;

import cn.edu.nju.kg_qa.domain.relation.BelongRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: <br/>
 * date: 2020/12/24 17:25<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Repository
public interface BelongToRepository extends Neo4jRepository<BelongRelation,Long> {

    /**
     * 根据概念名查询关系路径节点
     * @param conceptName
     * @return
     */
    @Query("Match (p:book)-[r]-(c:concept) where c.name=$conceptName return p,r,c")
    List<BelongRelation> findBelongToRelationByConceptName(@Param("conceptName") String conceptName);

    /**
     * 根据书籍名查询关系路径节点
     * @param bookName
     * @return
     */
    @Query("Match (p:book)-[r]-(c:concept) where p.name=$bookName return p,r,c")
    List<BelongRelation> findBelongToRelationByBookName(@Param("bookName") String bookName);
}
