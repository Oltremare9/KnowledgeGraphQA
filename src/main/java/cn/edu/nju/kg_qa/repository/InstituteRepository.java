package cn.edu.nju.kg_qa.repository;

import cn.edu.nju.kg_qa.domain.entity.AuthorNode;
import cn.edu.nju.kg_qa.domain.entity.ConceptNode;
import cn.edu.nju.kg_qa.domain.entity.InstituteNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Description: 针对作者的node查询 <br/>
 * date: 2020/12/22 20:04<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Repository
public interface InstituteRepository extends Neo4jRepository<InstituteNode, Long> {

    /**
     * 根据机构名模糊查询机构
     * @param instituteName
     * @return
     */
    List<InstituteNode> findInstituteNodesByNameContains(String instituteName);


    /**
     * 根据城市名 精准查询机构
     * @param positionName
     * @return
     */
    List<InstituteNode> findInstituteNodesByPosition(String positionName);

    /**
     * 根据图书名查询出版社
     * @param bookName
     * @return
     */
    @Query("Match ans=(p:book)-[r]-(a:institute) where p.name=$bookName return ans")
    List<InstituteNode> findInstituteNodesByBookName(String bookName);
}
