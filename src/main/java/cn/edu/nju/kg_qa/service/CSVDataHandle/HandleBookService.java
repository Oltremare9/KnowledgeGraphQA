package cn.edu.nju.kg_qa.service.CSVDataHandle;

import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.config.TableHead;
import cn.edu.nju.kg_qa.domain.dto.CrawlerJsonList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Description: <br/>
 * date: 2021/1/12 20:06<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class HandleBookService {
    public static ArrayList<String> list = new ArrayList<>();

    private Logger logger = LoggerFactory.getLogger(HandleBookService.class);

    public void clear() {
        list.clear();
    }

    public void extractBook(CsvReader csvReader) {
        String id = "";
        String classification = "";
        String date = "";
        String name = "";
        TableHead s = TableHead.ISBN;
        StringBuilder description = new StringBuilder(" ");
        try {
            id = csvReader.get(0);
            name = csvReader.get(1).replaceAll("\\\\", "");
            name = name.replaceAll("\'", "");
            classification = csvReader.get(7);
            date = csvReader.get(5);

            if (csvReader.get(19) != null) {
                CrawlerJsonList bookJson = new Gson().fromJson(csvReader.get(19), CrawlerJsonList.class);
                if (bookJson != null && bookJson.get内容提要() != null) {
                    for (String s1 : bookJson.get内容提要()) {
                        description.append(s1);
                    }
                }
            }
        } catch (IOException e) {
            logger.error("e:读取字段错误,isbn:" + id);
            e.printStackTrace();
        }
        if (name.equalsIgnoreCase("books")) {
            return;
        }
        list.add(id + "!" + name + "!" + classification + "!" + date + "!" + description.toString());
    }

    public void writeBookEntity() {
        File bookEntityFile = new File(Config.OUT_CSV_PATH + "book_entity.csv");
        if (bookEntityFile.exists()) {
            bookEntityFile.delete();
        }
        try {
            bookEntityFile.createNewFile();
        } catch (IOException e) {
            logger.error("e:新建book实体book_entity失败");
            e.printStackTrace();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(bookEntityFile));
        } catch (IOException e) {
            logger.error("e:创建book实体bufferWriter失败");
            e.printStackTrace();
        }
        CsvWriter cWriter = new CsvWriter(writer, ',');
        try {
            cWriter.writeRecord("id,name,classification,date,abstract".split(","), true);
        } catch (IOException e) {
            logger.error("e:写入表头失败");
            e.printStackTrace();
        }
        for (String bookEntity : list) {
            try {
                cWriter.writeRecord((bookEntity).replaceAll("\"", "").split("!"), true);
            } catch (IOException e) {
                logger.error("e:写入数据失败+key:" + bookEntity);
                e.printStackTrace();
            }
            cWriter.flush();//刷新数据
        }
    }
}
