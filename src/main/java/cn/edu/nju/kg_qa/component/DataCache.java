package cn.edu.nju.kg_qa.component;

import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.constant.PresetQuestionEnum;
import cn.edu.nju.kg_qa.service.CSVDataHandle.HandleDateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: <br/>
 * date: 2021/2/23 2:54<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Component
public class DataCache {
    public static Map<Integer, PresetQuestionEnum> presetQuestionEnumMap = new ConcurrentHashMap<>();

    public static Map<String,String> otherNamesMap=new ConcurrentHashMap<>();
    private Logger logger= LoggerFactory.getLogger(HandleDateService.class);
    @PostConstruct
    public void init() {
        logger.warn("加载预置问题中---------------------------");
        for(PresetQuestionEnum presetQuestionEnum:PresetQuestionEnum.values()){
            if(presetQuestionEnumMap.containsKey(presetQuestionEnum.getQid())){
                logger.error("预置问题冲突，qid"+presetQuestionEnum.getQid());
            }
            presetQuestionEnumMap.put(presetQuestionEnum.getQid(), presetQuestionEnum);
        }
        logger.warn("加载完成---------------------------");


        logger.warn("加载别名文件中---------------------------");
        File file=new File(Config.OTHER_NAMES_PATH);
        FileReader fileReader;

        try {
            fileReader=new FileReader(file);
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line="";
            while((line=bufferedReader.readLine())!=null){
                if(!line.endsWith("!")){
                    String s[]=line.split("!");
                    otherNamesMap.put(s[0],s[1]);
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("别名文件读取错误");
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("读取别名文件行 错误");
            e.printStackTrace();
        }
        logger.warn("加载别名文件完成---------------------------");
        logger.info(otherNamesMap.toString());

    }

    @PreDestroy
    public void destroy() {

    }
}
