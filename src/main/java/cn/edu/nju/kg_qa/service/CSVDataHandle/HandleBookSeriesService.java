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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Description: <br/>
 * date: 2021/1/12 20:06<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class HandleBookSeriesService {
    private Logger logger= LoggerFactory.getLogger(HandleBookSeriesService.class);
    public static HashMap<String,String> map=new HashMap<>();
    public static ArrayList<String> relation=new ArrayList<>();

    public void clear(){
        map.clear();
        relation.clear();
    }

    public void extractBookSeries(CsvReader csvReader) {
        String id = "";
        String series = "";
        try {
            id = csvReader.get(0);
            series = csvReader.get(10).replaceAll("\\\\","");
        } catch (IOException e) {
            logger.error("e:读取字段错误,isbn:" + id);
            e.printStackTrace();
        }
        if(null==series || series.trim().equalsIgnoreCase("")){
            return;
        }
        if (series.equalsIgnoreCase("series")) {
            return;
        }
        if(!map.containsKey(series)){
            map.put(series, "");
        }else{
            //todo 消歧
        }
        relation.add(id+"!"+series);
    }

    public void writeBookSeriesEntity(){
        File bookSeriesEntityFile = new File(Config.OUT_CSV_PATH  + "bookSeries_entity.csv");
        if (bookSeriesEntityFile.exists()) {
            bookSeriesEntityFile.delete();
        }
        try {
            bookSeriesEntityFile.createNewFile();
        } catch (IOException e) {
            logger.error("e:新建bookSeries实体bookSeries_entity失败");
            e.printStackTrace();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(bookSeriesEntityFile));
        } catch (IOException e) {
            logger.error("e:创建bookSeries实体bufferWriter失败");
            e.printStackTrace();
        }
        CsvWriter cWriter = new CsvWriter(writer, ',');
        try {
            cWriter.writeRecord("name".split(","), true);
        } catch (IOException e) {
            logger.error("e:写入表头失败");
            e.printStackTrace();
        }
        for (HashMap.Entry<String, String> entry : map.entrySet()) {
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

    public void writeSubBookOfRelation() {
        File subBookOfRelationFile = new File(Config.OUT_CSV_PATH  + "subBookOf_relation.csv");
        if (subBookOfRelationFile.exists()) {
            subBookOfRelationFile.delete();
        }
        try {
            subBookOfRelationFile.createNewFile();
        } catch (IOException e) {
            logger.error("e:新建subBookOf关系subBookOf_relation失败");
            e.printStackTrace();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(subBookOfRelationFile));
        } catch (IOException e) {
            logger.error("e:创建subBookOf关系bufferWriter失败");
            e.printStackTrace();
        }
        CsvWriter cWriter = new CsvWriter(writer, ',');
        try {
            cWriter.writeRecord("book_id,bookSeries_name".split(","), true);
        } catch (IOException e) {
            logger.error("e:写入表头失败");
            e.printStackTrace();
        }
        for (String relation : relation) {

            String[] a = relation.split("!");
            String mapKey = a[0];
            String mapValue = a[1];
            logger.error(mapKey + ":" + mapValue);
            try {
                cWriter.writeRecord((mapKey + "," + mapValue).split(","), true);
            } catch (IOException e) {
                logger.error("e:写入数据失败+data:" + mapKey + "," + mapValue);
                e.printStackTrace();
            }
            cWriter.flush();//刷新数据
        }
    }
}
