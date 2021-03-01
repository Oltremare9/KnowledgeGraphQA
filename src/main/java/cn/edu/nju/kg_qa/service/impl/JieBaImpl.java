package cn.edu.nju.kg_qa.service.impl;

import cn.edu.nju.kg_qa.domain.dto.NodeNameAndLabelsDto;
import cn.edu.nju.kg_qa.repository.JieBaRepository;
import cn.edu.nju.kg_qa.service.qaService.JieBaService;
import cn.edu.nju.kg_qa.util.Jieba;
import com.huaban.analysis.jieba.SegToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: <br/>
 * date: 2021/2/21 1:56<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class JieBaImpl implements JieBaService {
    @Autowired
    JieBaRepository jieBaRepository;
    /**
     * 结巴分词
     * @param sentence
     * @return
     */
    @Override
    public List<SegToken> jieBaCutSentence(String sentence) {
        Jieba jieba=new Jieba();
        List<SegToken> list = jieba.cutSequence(sentence);
        return list;
    }

    /***
     * 根据节点名 右模糊匹配节点全名称+标签
     * @param nodeName
     * @return
     */
    @Override
    public List<NodeNameAndLabelsDto> getWordLabelAndNameAndRelation(String nodeName){
        if(nodeName.length()<2){
            return new ArrayList<>();
        }
        return jieBaRepository.getWordLabelAndNameAndRelation(nodeName);
    }

    @Override
    public List<String> getWordLabelByExactName(String nodeName) {
        return jieBaRepository.getWordLabelByExactName(nodeName);
    }

    @Override
    public List<NodeNameAndLabelsDto> getWordLabelAndNameAndRelationByNodeId(String nodeId) {
        return jieBaRepository.getWordLabelAndNameAndRelationByNodeId(nodeId);
    }
}
