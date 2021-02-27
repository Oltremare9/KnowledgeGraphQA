package cn.edu.nju.kg_qa.service.CSVDataHandle;

import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.config.TableHead;
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
 * Description: <br/>
 * date: 2021/1/12 20:07<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class HandleCityService {
    private Logger logger= LoggerFactory.getLogger(HandleCityService.class);
    public static HashMap<String, String> city_Entity = new HashMap<>();
    public static HashMap<String, String> locateIn_Relation = new HashMap<>();

    public void clear(){
        city_Entity.clear();
        locateIn_Relation.clear();
    }
    public void extractCity(CsvReader csvReader) {
        String city = "";
        String institute = "";
        String isbn = "";
        try {
            isbn = csvReader.get(0);
            city = csvReader.get(14);
            institute = csvReader.get(4);
        } catch (IOException e) {
            logger.error("e:读取字段错误,isbn:" + isbn);
            e.printStackTrace();
        }
        if (TableHead.PUBLISHMENT.name().equalsIgnoreCase(institute)) {
            return;
        }
        city_Entity.put(city, "");
        if (!locateIn_Relation.containsKey(institute + "!" + city)) {
            locateIn_Relation.put(institute + "!" + city, "");
        }
    }

    public void writeCityEntity() {
        {
            File cityEntityFile = new File(Config.OUT_CSV_PATH  + "city_entity.csv");
            if (cityEntityFile.exists()) {
                cityEntityFile.delete();
            }
            try {
                cityEntityFile.createNewFile();
            } catch (IOException e) {
                logger.error("e:新建city实体city_entity失败");
                e.printStackTrace();
            }
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(cityEntityFile));
            } catch (IOException e) {
                logger.error("e:创建city实体bufferWriter失败");
                e.printStackTrace();
            }
            CsvWriter cWriter = new CsvWriter(writer, ',');
            try {
                cWriter.writeRecord("name".split(","), true);
            } catch (IOException e) {
                logger.error("e:写入表头失败");
                e.printStackTrace();
            }
            for (HashMap.Entry<String, String> entry : city_Entity.entrySet()) {
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
    }

    public void writeLocateInRelation() {
        File locateInRelationFile = new File(Config.OUT_CSV_PATH +  "locateIn_relation.csv");
        if (locateInRelationFile.exists()) {
            locateInRelationFile.delete();
        }
        try {
            locateInRelationFile.createNewFile();
        } catch (IOException e) {
            logger.error("e:新建locateIn关系locateIn_relation失败");
            e.printStackTrace();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(locateInRelationFile));
        } catch (IOException e) {
            logger.error("e:创建locateIn关系bufferWriter失败");
            e.printStackTrace();
        }
        CsvWriter cWriter = new CsvWriter(writer, ',');
        try {
            cWriter.writeRecord("institute_name,city_name".split(","), true);
        } catch (IOException e) {
            logger.error("e:写入表头失败");
            e.printStackTrace();
        }
        for (HashMap.Entry<String, String> entry : locateIn_Relation.entrySet()) {
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
