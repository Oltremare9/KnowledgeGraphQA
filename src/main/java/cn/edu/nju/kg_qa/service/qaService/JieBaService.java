package cn.edu.nju.kg_qa.service.qaService;

import cn.edu.nju.kg_qa.domain.dto.NodeNameAndLabelsDto;
import com.huaban.analysis.jieba.SegToken;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/2/22 1:15<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public interface JieBaService {
    /**
     * 结巴分词
     * @param sentence
     * @return
     */
    public List<SegToken> jieBaCutSentence(String sentence);


    /***
     * 根据节点名 右模糊匹配节点全名称+标签
     * @param nodeName
     * @return
     */
    public List<NodeNameAndLabelsDto> getWordLabelAndName(String nodeName);
}
