package cn.edu.nju.kg_qa.repository;

import cn.edu.nju.kg_qa.domain.base.BaseNode;
import cn.edu.nju.kg_qa.domain.dto.RepeatedAuthorNameAndList;
import cn.edu.nju.kg_qa.domain.entity.AuthorNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/3/3 21:59<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public interface AdminRepository extends Neo4jRepository<BaseNode,Long> {

    /**
     * 通过特定id删除节点
     * @param id
     * @return
     */
    @Query("match (p) where id(p)=$id delete p")
    Long deleteBaseNodeByIdentity(Integer id);

    /**
     * 根据节点自增id删除节点周围关系
     * @param id
     * @return
     */
    @Query("match (p)-[r]-() where id(p)=$id delete r")
    Long deleteRelationByIdentity(Integer id);


    /**
     * 获取所有重复作者节点
     * @param skip
     * @param limit
     * @return
     */
    @Query("MATCH (n:author) \n" +
            "with n.name as nodeName,collect(n) as repeatedNodes,count(n) as nodeCount\n" +
            "where nodeCount > 1\n" +
            "return nodeName,repeatedNodes skip $skip limit $limit" )
    List<RepeatedAuthorNameAndList> getAllRepeatedAuthorNodes(int skip , int limit);
}
