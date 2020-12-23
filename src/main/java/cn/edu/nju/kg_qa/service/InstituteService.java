package cn.edu.nju.kg_qa.service;

import cn.edu.nju.kg_qa.domain.entity.InstituteNode;
import cn.edu.nju.kg_qa.repository.InstituteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: <br/>
 * date: 2020/12/24 3:33<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class InstituteService {
    @Autowired
    InstituteRepository instituteRepository;

    /**
     * 根据机构名模糊查询机构
     * @param instituteName
     * @return
     */
    public List<InstituteNode> findInstituteByInstituteName(String instituteName){
        return instituteRepository.findInstituteNodesByNameContains(instituteName);
    }

    /**
     * 根据城市名 精准查询机构
     * @param positionName
     * @return
     */
    public List<InstituteNode> findInstituteByPosition(String positionName){
        return instituteRepository.findInstituteNodesByPosition(positionName);
    }

    /**
     * 根据图书名查询出版社
     * @param bookName
     * @return
     */
    public List<InstituteNode> findInstituteNodesByBookName(String bookName){
        return instituteRepository.findInstituteNodesByBookName(bookName);
    }

}
