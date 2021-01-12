package cn.edu.nju.kg_qa.service.extractService;

import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.config.TableHead;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
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

    public void extractBook(CsvReader csvReader) {
        String id = "";
        String classification = "";
        String date = "";
        String name = "";
        TableHead s=TableHead.ISBN;
        try {
            id = csvReader.get(0);
            name = csvReader.get(1).replaceAll("\\\\","");
            classification = csvReader.get(7);
            date = csvReader.get(5);
        } catch (IOException e) {
            System.out.println("e:读取字段错误,isbn:" + id);
            e.printStackTrace();
        }
        if(name.equalsIgnoreCase("books")){
            return;
        }
        list.add(id + "$" + name + "$" + classification + "$" + date);
    }

    public void writeBookEntity(){
        File bookEntityFile = new File(Config.OUT_CSV_PATH + "\\" + "book_entity.csv");
        if (bookEntityFile.exists()) {
            bookEntityFile.delete();
        }
        try {
            bookEntityFile.createNewFile();
        } catch (IOException e) {
            System.out.println("e:新建book实体book_entity失败");
            e.printStackTrace();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(bookEntityFile));
        } catch (IOException e) {
            System.out.println("e:创建book实体bufferWriter失败");
            e.printStackTrace();
        }
        CsvWriter cWriter = new CsvWriter(writer, ',');
        try {
            cWriter.writeRecord("id,name,classification,date".split(","), true);
        } catch (IOException e) {
            System.out.println("e:写入表头失败");
            e.printStackTrace();
        }
        for (String bookEntity:list) {
            try {
                cWriter.writeRecord((bookEntity).replaceAll("\\$",",").split(","), true);
            } catch (IOException e) {
                System.out.println("e:写入数据失败+key:" + bookEntity);
                e.printStackTrace();
            }
            cWriter.flush();//刷新数据
        }
    }
}
