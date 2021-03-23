package cn.edu.nju.kg_qa.service;

import cn.edu.nju.kg_qa.domain.dto.NameAndScoreDto;
import cn.edu.nju.kg_qa.domain.dto.RepeatedAuthorNameAndList;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Description: <br/>
 * date: 2021/3/3 21:38<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public interface AdminService {

    /***
     * 删除某一个特定节点
     * @param id
     * @return
     */
    String deleteNodeAndRelationById(Integer id);

    /**
     * 获取所有重复作者节点
     * @param skip
     * @return
     */
    List<RepeatedAuthorNameAndList> getAllRepeatedAuthorNodes(int skip);

    /**
     * 根据跳数返回是否两点间存在关系 有返回1
     * @param nodeId1
     * @param nodeId2
     * @return
     */
    int getRelationPathNumsByNodeId(Long nodeId1, Long nodeId2);

    /**
     * 获取访问数据 type 0 问题 1 节点类型 2 具体节点类型排序
     * @param type
     * @param nodeType
     * @return
     */
    ArrayList<NameAndScoreDto> getStatisticsByKeyAndType(int type, String nodeType);

}
