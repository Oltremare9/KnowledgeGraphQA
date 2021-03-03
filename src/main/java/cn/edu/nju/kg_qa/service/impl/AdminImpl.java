package cn.edu.nju.kg_qa.service.impl;

import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.domain.dto.RepeatedAuthorNameAndList;
import cn.edu.nju.kg_qa.domain.dto.ResultDto;
import cn.edu.nju.kg_qa.domain.entity.AuthorNode;
import cn.edu.nju.kg_qa.repository.AdminRepository;
import cn.edu.nju.kg_qa.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: <br/>
 * date: 2021/3/3 21:53<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class AdminImpl implements AdminService {

    private Logger logger = LoggerFactory.getLogger(AdminImpl.class);

    @Autowired
    AdminRepository adminRepository;

    @Override
    @Transactional
    public String deleteNodeAndRelationById(Integer id) {
        Long deleteRelation = adminRepository.deleteRelationByIdentity(id);
        Long res = adminRepository.deleteBaseNodeByIdentity(id);
        logger.info("删除关系结果为:" + res + " 删除节点关系为:" + deleteRelation);
        return "success";
    }

    @Override
    public List<RepeatedAuthorNameAndList> getAllRepeatedAuthorNodes(int skip) {
        List<RepeatedAuthorNameAndList> list = adminRepository.getAllRepeatedAuthorNodes(skip, Config.limit);
        List<RepeatedAuthorNameAndList> res = new ArrayList<>();
        for (RepeatedAuthorNameAndList node : list) {
            boolean flag = true;
            for (AuthorNode authorNode : node.getRepeatedNodes()) {
                if (authorNode.getId().contains("_")) {
                    flag = false;
                    break;
                }
            }
            if (!flag) {
                res.add(node);
            }
        }
        logger.info("重复节点为" + list.toString());
        return res;
    }

    @Override
    public int getRelationPathNumsByNodeId(Long nodeId1, Long nodeId2) {
        //2到4跳内到达 为潜在同一节点
        String flag = adminRepository.getRelationPathNumsByNodeIdJump2(nodeId1, nodeId2);
        if(flag.equals("1")) {
            return 2;
        }else{
            flag = adminRepository.getRelationPathNumsByNodeIdJump3(nodeId1, nodeId2);
            if(flag.equals("1")) {
                return 3;
            }else{
                flag = adminRepository.getRelationPathNumsByNodeIdJump4(nodeId1, nodeId2);
                if(flag.equals("1")) {
                    return 4;
                }
            }
        }
        return 0;
    }
}
