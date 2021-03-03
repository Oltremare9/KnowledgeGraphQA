package cn.edu.nju.kg_qa.service;

import cn.edu.nju.kg_qa.domain.dto.RepeatedAuthorNameAndList;

import java.util.List;

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

}
