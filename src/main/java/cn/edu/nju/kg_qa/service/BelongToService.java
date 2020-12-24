package cn.edu.nju.kg_qa.service;

import cn.edu.nju.kg_qa.domain.base.Base;
import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import cn.edu.nju.kg_qa.domain.relation.BelongRelation;
import cn.edu.nju.kg_qa.repository.BelongToRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: <br/>
 * date: 2020/12/24 17:39<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class BelongToService {
    @Autowired
    BelongToRepository belongToRepository;

    /**
     * 查询所有belongTo关系
     * @return
     */
    public List<BelongRelation> findAllBelongToRelation(){
        return belongToRepository.findAllBelongToRelation();
    }

}
