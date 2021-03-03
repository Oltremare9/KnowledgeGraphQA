package cn.edu.nju.kg_qa.service.impl;

import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.domain.dto.RepeatedAuthorNameAndList;
import cn.edu.nju.kg_qa.repository.AdminRepository;
import cn.edu.nju.kg_qa.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Long deleteRelation=adminRepository.deleteRelationByIdentity(id);
        Long res=adminRepository.deleteBaseNodeByIdentity(id);
        logger.info("删除关系结果为:"+res+" 删除节点关系为:"+deleteRelation);
        return "success";
    }

    @Override
    public List<RepeatedAuthorNameAndList> getAllRepeatedAuthorNodes(int skip) {
        List<RepeatedAuthorNameAndList> list = adminRepository.getAllRepeatedAuthorNodes(skip, Config.limit);
        logger.info("重复节点为"+list.toString());
        return list;
    }


}
