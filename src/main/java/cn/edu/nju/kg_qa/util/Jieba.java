package cn.edu.nju.kg_qa.util;

import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.service.extractService.HandleBookSeriesService;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.huaban.analysis.jieba.WordDictionary;
import junit.framework.TestCase;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Description: <br/>
 * date: 2021/2/20 22:44<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class Jieba {

    static Logger logger = LoggerFactory.getLogger(Jieba.class);

    static {
        Path sougou_path = new File(Config.JIEBA_SOUGOU_PATH).toPath();
        Path entity_path = new File(Config.JIEBA_ENTITY_PATH).toPath();
        WordDictionary.getInstance().loadUserDict(sougou_path);
        WordDictionary.getInstance().loadUserDict(entity_path);
        logger.warn("导入停用词表");
    }

    static public List<SegToken> cutSequence(String sentence) {
        logger.info("待切分句子为：" + sentence);
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> res = segmenter.process(sentence, JiebaSegmenter.SegMode.SEARCH);
        logger.info("分词结果：" + res.toString());

        return res;
    }


    public static void main(String args[]) {
        Jieba.cutSequence("三联出版社出版商是谁");
    }
}
