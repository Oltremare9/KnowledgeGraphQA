package cn.edu.nju.kg_qa.repository;

import cn.edu.nju.kg_qa.domain.entity.BookNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Description: 针对作者的node查询 <br/>
 * date: 2020/12/22 20:04<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Repository
public interface BookRepository extends Neo4jRepository<BookNode,Long> {

    /**
    * @Description: 作者名 模糊查询
    * @Param: 作者名
    * @return:
    */
    @Query("Match ans=(p:book)-[r]-(a:author) where a.name=~'.*'+$authorName+'.*' return ans")
    List<BookNode> findBooksByAuthorName(@Param("authorName")String name);

    /***
     * 书籍名模糊查询
     * @param bookName
     * @return
     */
    List<BookNode> findAllByNameContains(String bookName);
}
