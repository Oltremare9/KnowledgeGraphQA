package cn.edu.nju.kg_qa.repository;

import cn.edu.nju.kg_qa.domain.base.BaseNode;
import cn.edu.nju.kg_qa.domain.dto.RepeatedAuthorNameAndList;
import cn.edu.nju.kg_qa.domain.dto.ResultDto;
import cn.edu.nju.kg_qa.domain.entity.AuthorNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/3/3 21:59<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Repository
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

    /**
     * 根据跳数返回是否两点间存在关系 有返回1
     * @param nodeId1
     * @param nodeId2
     * @return
     */
    @Query("MATCH (n1),(n2)\n" +
            "where id(n1)=$nodeId1 and id(n2)=$nodeId2\n" +
            "RETURN\n" +
            "CASE\n" +
            "  WHEN (n1)-[*1..2]-(n2)\n" +
            "    THEN \"1\"\n" +
            "  ELSE \"0\"\n" +
            "END")
    String getRelationPathNumsByNodeIdJump2(Long nodeId1, Long nodeId2);

    /**
     * 根据跳数返回是否两点间存在关系 有返回1
     * @param nodeId1
     * @param nodeId2
     * @return
     */
    @Query("MATCH (n1),(n2) " +
            "where id(n1)=$nodeId1 and id(n2)=$nodeId2 " +
            "RETURN " +
            "CASE " +
            "  WHEN (n1)-[*1..3]-(n2)" +
            "    THEN \"1\"\n" +
            "  ELSE \"0\"\n" +
            "END")
    String getRelationPathNumsByNodeIdJump3(Long nodeId1,Long nodeId2);

    /**
     * 根据跳数返回是否两点间存在关系 有返回1
     * @param nodeId1
     * @param nodeId2
     * @return
     */
    @Query("MATCH (n1),(n2) " +
            "where id(n1)=$nodeId1 and id(n2)=$nodeId2 " +
            "RETURN " +
            "CASE " +
            "  WHEN (n1)-[*1..4]-(n2)" +
            "    THEN \"1\"\n" +
            "  ELSE \"0\"\n" +
            "END")
    String getRelationPathNumsByNodeIdJump4(Long nodeId1,Long nodeId2);
}
