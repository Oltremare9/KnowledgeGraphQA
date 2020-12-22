package cn.edu.nju.kg_qa.repository;

import cn.edu.nju.kg_qa.domain.entity.AuthorNode;
import cn.edu.nju.kg_qa.domain.entity.InstituteNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Description: 针对作者的node查询 <br/>
 * date: 2020/12/22 20:04<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Repository
public interface InstituteRepository extends Neo4jRepository<InstituteNode,Long> {

}
