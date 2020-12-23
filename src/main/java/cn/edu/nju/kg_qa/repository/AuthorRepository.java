package cn.edu.nju.kg_qa.repository;

import cn.edu.nju.kg_qa.domain.entity.AuthorNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
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
public interface AuthorRepository extends Neo4jRepository<AuthorNode,Long> {

    /**
     * 通过作者名 模糊查询
     * @param authorName
     * @return
     */
    List<AuthorNode> findAllByNameContains(String authorName);

    /**
     * 书名模糊查询作者名
     * @param bookName
     * @return
     */
    @Query("Match ans=(p:book)-[r]-(a:author) where p.name=~'.*'+$bookName+'.*' return ans")
    List<AuthorNode> findAuthorByBookName(@Param("bookName") String bookName);
}
