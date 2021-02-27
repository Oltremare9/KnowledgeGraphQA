package cn.edu.nju.kg_qa.component;

import cn.edu.nju.kg_qa.constant.PresetQuestionEnum;
import cn.edu.nju.kg_qa.service.CSVDataHandle.HandleDateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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


    }

    @PreDestroy
    public void destroy() {

    }
}
