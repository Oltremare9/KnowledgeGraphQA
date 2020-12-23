package cn.edu.nju.kg_qa.repository;

import cn.edu.nju.kg_qa.domain.entity.AuthorNode;
import cn.edu.nju.kg_qa.domain.entity.ConceptNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: 针对作者的node查询 <br/>
 * date: 2020/12/22 20:04<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Repository
public interface ConceptRepository extends Neo4jRepository<ConceptNode,Long> {

    /**
     * 概念名模糊查找概念
     * @param conceptName
     * @return
     */
    List<ConceptNode> findAllByNameContains(String conceptName);

    /**
     * 书名 模糊查找概念
     * @param bookName
     * @return
     */
    @Query("Match ans=(p:book)-[r]-(a:concept) where p.name=~'.*'+$bookName+'.*' return ans")
    List<ConceptNode> findConceptNodesByBookName(String bookName);
}
