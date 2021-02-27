package cn.edu.nju.kg_qa.service.CSVDataHandle;

import cn.edu.nju.kg_qa.config.Config;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * Description: 处理excel中概念<br/>
 * date: 2021/1/12 20:07<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class HandleConceptService {
    private Logger logger= LoggerFactory.getLogger(HandleConceptService.class);
    public static HashMap<String, String> concept_Entity = new HashMap<>();
    public static HashMap<String,String> write_Relation = new HashMap<>();

    public void clear(){
        concept_Entity.clear();
        write_Relation.clear();
    }
    public void extractConcepts(CsvReader csvReader) {
        String isbn = "";
        String[] concepts = new String[0];
        try {
            isbn = csvReader.get(0);
            concepts = csvReader.get(12).replaceAll("\\\\","").split("－");
        } catch (IOException e) {
            logger.error("e:读取concept错误,isbn:" + isbn);
            e.printStackTrace();
        }
        for (String concept : concepts) {
            if (concept.trim().equals("") || concept.equals("topic")) {
                continue;
            }
            //如果有组合词
            if (concept.contains("；")) {
                String[] sun_concepts = concept.split("；");
                for (String sub_concept : sun_concepts) {
                    if (sub_concept.trim().equals("")) {
                        continue;
                    }
                    if (!concept_Entity.containsKey(sub_concept)) {
                        concept_Entity.put(sub_concept, "");
                    }
                    write_Relation.put(isbn + "!" + sub_concept,"");
                }
                //无组合词
            } else {
                if (!concept_Entity.containsKey(concept)) {
                    concept_Entity.put(concept, "");
                }
                write_Relation.put(isbn + "!" + concept,"");
            }

        }
    }

    public void writeConceptsEntity() {
        File conceptEntityFile = new File(Config.OUT_CSV_PATH  + "concept_entity.csv");
        if (conceptEntityFile.exists()) {
            conceptEntityFile.delete();
        }
        try {
            conceptEntityFile.createNewFile();
        } catch (IOException e) {
            logger.error("e:新建concept实体concept_entity失败");
            e.printStackTrace();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(conceptEntityFile));
        } catch (IOException e) {
            logger.error("e:创建concept实体bufferWriter失败");
            e.printStackTrace();
        }
        CsvWriter cWriter = new CsvWriter(writer, ',');
        try {
            cWriter.writeRecord("name".split(","), true);
        } catch (IOException e) {
            logger.error("e:写入表头失败");
            e.printStackTrace();
        }
        for (HashMap.Entry<String, String> entry : concept_Entity.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            logger.error(mapKey + ":" + mapValue);
            try {
                cWriter.writeRecord((mapKey).split(","), true);
            } catch (IOException e) {
                logger.error("e:写入数据失败+key:" + mapKey);
                e.printStackTrace();
            }
            cWriter.flush();//刷新数据
        }
    }

    public void writeConceptsRelation() {
        File conceptRelationFile = new File(Config.OUT_CSV_PATH  + "belongTo_relation.csv");
        if (conceptRelationFile.exists()) {
            conceptRelationFile.delete();
        }
        try {
            conceptRelationFile.createNewFile();
        } catch (IOException e) {
            logger.error("e:新建belongTo关系belongTo_relation失败");
            e.printStackTrace();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(conceptRelationFile));
        } catch (IOException e) {
            logger.error("e:创建belongTo关系bufferWriter失败");
            e.printStackTrace();
        }
        CsvWriter cWriter = new CsvWriter(writer, ',');
        try {
            cWriter.writeRecord("book_id,concept_name".split(","), true);
        } catch (IOException e) {
            logger.error("e:写入表头失败");
            e.printStackTrace();
        }
        for (HashMap.Entry<String, String> entry : write_Relation.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            logger.error(mapKey + ":" + mapValue);
            try {
                cWriter.writeRecord((mapKey).split("!"), true);
            } catch (IOException e) {
                logger.error("e:写入数据失败+key:" + mapKey);
                e.printStackTrace();
            }
            cWriter.flush();//刷新数据
        }
    }
}
