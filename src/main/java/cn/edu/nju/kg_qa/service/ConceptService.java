package cn.edu.nju.kg_qa.service;

import cn.edu.nju.kg_qa.domain.entity.ConceptNode;
import cn.edu.nju.kg_qa.repository.ConceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: <br/>
 * date: 2020/12/23 17:22<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class ConceptService {
    @Autowired
    ConceptRepository conceptRepository;

    /**
     * 查询所有
     * @return
     */
    public List<ConceptNode> findAllConcept(){
        Iterable<ConceptNode> iterable= conceptRepository.findAll();
        List<ConceptNode> list=new ArrayList<>();
        for(ConceptNode conceptNode:iterable){
            list.add(conceptNode);
        }
        return list;
    }

    /**
     * 概念名模糊检索概念
     * @param conceptName
     * @return
     */
    public List<ConceptNode> findConceptsByConceptName(String conceptName){
        if(null==conceptName){
            return findAllConcept();
        }
        return conceptRepository.findAllByNameContains(conceptName);
    }

    /**
     * 书籍名模糊检索概念
     * @param bookName
     * @return
     */
    public List<ConceptNode> findConceptsByBookName(String bookName){
        return conceptRepository.findConceptNodesByBookName(bookName);
    }
}
