package cn.edu.nju.kg_qa.service;

import cn.edu.nju.kg_qa.common.CommonResult;
import cn.edu.nju.kg_qa.util.Jieba;
import com.huaban.analysis.jieba.SegToken;
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
    /**
     * 结巴分词
     * @param sentence
     * @return
     */
    public List<SegToken> jieBaCutSentence(String sentence) {
        List<SegToken> list = Jieba.cutSequence(sentence);
        return list;
    }
}
