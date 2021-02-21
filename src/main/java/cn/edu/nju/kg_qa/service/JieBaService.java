package cn.edu.nju.kg_qa.service;

import cn.edu.nju.kg_qa.common.CommonResult;
import cn.edu.nju.kg_qa.domain.entity.NodeNameAndLabelsData;
import cn.edu.nju.kg_qa.repository.JieBaRepository;
import cn.edu.nju.kg_qa.util.Jieba;
import com.huaban.analysis.jieba.SegToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/2/21 1:56<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class JieBaService {
    @Autowired
    JieBaRepository jieBaRepository;
    /**
     * 结巴分词
     * @param sentence
     * @return
     */
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
    public List<NodeNameAndLabelsData> getWordLabelAndName(String nodeName){
        return jieBaRepository.getWordLabelAndName(nodeName);
    }
}
