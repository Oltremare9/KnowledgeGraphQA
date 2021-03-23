package cn.edu.nju.kg_qa.service.impl;

import cn.edu.nju.kg_qa.component.DataCache;
import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.constant.RedisPrefix;
import cn.edu.nju.kg_qa.domain.base.BaseNode;
import cn.edu.nju.kg_qa.domain.dto.NameAndScoreDto;
import cn.edu.nju.kg_qa.domain.dto.RepeatedAuthorNameAndList;
import cn.edu.nju.kg_qa.domain.entity.AuthorNode;
import cn.edu.nju.kg_qa.repository.AdminRepository;
import cn.edu.nju.kg_qa.repository.ComplexNodeRepository;
import cn.edu.nju.kg_qa.service.AdminService;
import cn.edu.nju.kg_qa.util.RedisUtil;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Autowired
    ComplexNodeRepository complexNodeRepository;

    @Autowired
    RedisUtil redisUtil;

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

    @Override
    public ArrayList<NameAndScoreDto> getStatisticsByKeyAndType(int type, String nodeType) {
        Set<ZSetOperations.TypedTuple<Object>> redisSet=new HashSet<>();
        ArrayList<NameAndScoreDto> res=new ArrayList<>();
        if(type==0){
            redisSet= redisUtil.revRangeWithScore(RedisPrefix.Q_Type_ZSet.getPrefix(), 0, -1);
            for(ZSetOperations.TypedTuple<Object> typedTuple:redisSet){
                NameAndScoreDto dto=new NameAndScoreDto();
                dto.setName(DataCache.presetQuestionEnumMap.get(Integer.parseInt(typedTuple.getValue().toString())).getQuestionDescription());
                dto.setScore(typedTuple.getScore());
                res.add(dto);
            }
        }else if (type==1){
            redisSet= redisUtil.revRangeWithScore(RedisPrefix.E_Type_ZSet.getPrefix(), 0, -1);
            for(ZSetOperations.TypedTuple<Object> typedTuple:redisSet){
                NameAndScoreDto dto=new NameAndScoreDto();
                dto.setScore(typedTuple.getScore());
                dto.setName(typedTuple.getValue().toString());
                res.add(dto);
            }
        }else if (type==2){
            redisSet= redisUtil.revRangeWithScore(RedisPrefix.E_Node_ZSet.getPrefix()+"author", 0, -1);
            for(ZSetOperations.TypedTuple<Object> typedTuple:redisSet){
                NameAndScoreDto dto=new NameAndScoreDto();
                BaseNode node =complexNodeRepository.findNodeByIdAnd(Long.parseLong(typedTuple.getValue().toString())).get(0);
                dto.setScore(typedTuple.getScore());
                dto.setName(node.getName());
                res.add(dto);
            }
        }
        return res;
    }
}
