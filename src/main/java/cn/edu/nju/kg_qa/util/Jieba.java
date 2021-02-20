package cn.edu.nju.kg_qa.util;

import cn.edu.nju.kg_qa.config.Config;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.huaban.analysis.jieba.WordDictionary;
import junit.framework.TestCase;

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

    static{
        Path path = Paths.get(new File(Jieba.class.getClassLoader().
                getResource("dicts/dicts.dict").getPath()).getAbsolutePath());
        Path path1=new File(Config.JIEBA_SOUGOU_PATH).toPath();
        WordDictionary.getInstance().loadUserDict(path);
    }

    public List<SegToken> cutSequence(String sentence) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> res = segmenter.process(sentence, JiebaSegmenter.SegMode.SEARCH);
        System.out.println(res.toString());



        segmenter = new JiebaSegmenter();
        res = segmenter.process(sentence, JiebaSegmenter.SegMode.SEARCH);
        System.out.println(res.toString());

        return res;
    }


    public static void main(String args[]) {
        Jieba jieba = new Jieba();
        jieba.cutSequence("三联出版社出版商是谁");
    }
}
