package cn.edu.nju.kg_qa.repository;

import cn.edu.nju.kg_qa.domain.relation.PublishRelation;
import cn.edu.nju.kg_qa.domain.relation.WriteRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: <br/>
 * date: 2020/12/24 23:21<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Repository
public interface WriteRepository extends Neo4jRepository<WriteRelation, Long> {
    /**
     * 根据作者名查询关系路径节点
     *
     * @param authorName
     * @return
     */
    @Query("Match (p:book)-[r]-(c:author) where c.name=$authorName return p,r,c")
    List<WriteRelation> findWriteRelationByAuthorName(@Param("authorName") String authorName);

    /**
     * 根据书籍名查询关系路径节点
     *
     * @param bookName
     * @return
     */
    @Query("Match (p:book)-[r]-(c:author) where p.name=$bookName return p,r,c")
    List<WriteRelation> findWriteRelationByBookName(@Param("bookName") String bookName);
}
