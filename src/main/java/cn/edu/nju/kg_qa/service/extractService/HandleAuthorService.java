package cn.edu.nju.kg_qa.service.extractService;

import ch.qos.logback.classic.turbo.ReconfigureOnChangeFilter;
import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.config.TableHead;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import lombok.extern.flogger.Flogger;
import net.bytebuddy.jar.asm.Handle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.nju.kg_qa.config.Config;
import org.springframework.util.ResourceUtils;

/**
 * Description:
 * 实体：作者 国籍
 * 关系：assist write humanOf <br/>
 * date: 2021/1/12 19:57<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class HandleAuthorService {
    public static HashMap<String, String> author_Entity = new HashMap<>();
    public static ArrayList<String> write_Relation = new ArrayList<>();
    public static HashMap<String, String> nation_Entity = new HashMap<>();
    public static HashMap<String, String> humanOf_Relation = new HashMap<>();
    public static HashMap<String, String> assist_Relation = new HashMap<>();

    private Logger logger= LoggerFactory.getLogger(HandleAuthorService.class);

    public void clear(){
        author_Entity.clear();
        write_Relation.clear();
        nation_Entity.clear();
        humanOf_Relation.clear();
        assist_Relation.clear();
    }

    public void extractAuthor(CsvReader csvReader) {
        String isbn = "";
        String author = "";
        String assistant = "";
        try {
            isbn = csvReader.get(0).replaceAll("\"","");
            author = csvReader.get(2).replaceAll("\\\\", "");
            assistant = csvReader.get(8).replaceAll("\\\\", "");
        } catch (IOException e) {
            System.out.println("e:读取字段错误,isbn:" + isbn);
            e.printStackTrace();
        }
        if (TableHead.AUTHOR.getValue().equals(author)) {
            return;
        }
        //过滤国籍成功
        author = extractNation(author);
        if (null == author) {
            return;
        }
        this.putIntoAuthorMap(author);
        write_Relation.add(author + "!" + isbn);
        extractAssistant(author, assistant);
    }

    /**
     * 对作者名中含国籍的进行过滤 存储humanOf关系
     *
     * @param author
     * @return
     */
    private String extractNation(String author) {
        if (TableHead.AUTHOR.getValue().equals(author)) {
            return null;
        }
        author = author.replaceAll(" ", "");
        String nation = author;
        if (nation.trim().startsWith("(")) {
            author = nation.substring(nation.indexOf(")") + 1);
            nation = nation.substring(1);
            int index = nation.indexOf(")");
            nation = nation.substring(0, index);
            if (!nation_Entity.containsKey(nation) && !nation.replaceAll("\"","").equals("")) {
                nation_Entity.put(nation, "");
            }
            humanOf_Relation.put(author + "!" + nation, "");
        }
        return author;
    }

    private void extractAssistant(String author, String assistant) {
        if (null == assistant || "".equals(assistant.trim())) {
            return;
        }
        String[] assistants = assistant.split(",|;");
        for (String assist : assistants) {
            assist = assist.replaceAll(" ", "");
            if (assist.trim().length() == 1) {
                continue;
            }
            if (assist.contains("编")
                    || assist.contains("注")
                    || assist.contains("译")
                    || assist.contains("著")
                    || assist.contains("绘")
                    || assist.contains("朗诵")
                    || assist.contains("写")
                    || assist.contains("校")
                    || assist.contains("整理")
                    || assist.contains("点")
                    || assist.contains("图")
                    || assist.contains("陈述")) {
                continue;
            }
            assist = this.extractNation(assist);
            if (null == assist) {
                return;
            }
            this.putIntoAuthorMap(assist);
            assist_Relation.put(assist + "!" + author, "");
        }
    }

    /**
     * 作者存放入实体 未来消歧
     *
     * @param authorName
     */
    private void putIntoAuthorMap(String authorName) {
        if (authorName.trim().equals("")) {
            return;
        }
        if (!author_Entity.containsKey(authorName)) {
            author_Entity.put(authorName, "");
        } else {
            //todo 消歧
        }
    }


    public void writeAuthorsEntity() {
        File authorEntityFile = null;
        try {
            authorEntityFile = ResourceUtils.getFile(Config.OUT_CSV_PATH+"author_entity.csv");
        } catch (FileNotFoundException e) {
            logger.error("文件未找到");
            e.printStackTrace();
        }

        if (authorEntityFile.exists()) {
            authorEntityFile.delete();
        }
        try {
            authorEntityFile.createNewFile();
        } catch (IOException e) {
            System.out.println("e:新建author实体author_entity失败");
            e.printStackTrace();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(authorEntityFile));
        } catch (IOException e) {
            System.out.println("e:创建author实体bufferWriter失败");
            e.printStackTrace();
        }
        CsvWriter cWriter = new CsvWriter(writer, ',');
        try {
            //todo 可能会丰富字段
            cWriter.writeRecord("name".split(","), true);
        } catch (IOException e) {
            System.out.println("e:写入表头失败");
            e.printStackTrace();
        }
        for (HashMap.Entry<String, String> entry : author_Entity.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            System.out.println(mapKey + ":" + mapValue);
            try {
                cWriter.writeRecord((mapKey).split(","), true);
            } catch (IOException e) {
                System.out.println("e:写入数据失败+key:" + mapKey);
                e.printStackTrace();
            }
            cWriter.flush();//刷新数据
        }
    }

    public void writeNationEntity() {
        File nationEntityFile = null;
        try {
            nationEntityFile = ResourceUtils.getFile(Config.OUT_CSV_PATH+"nation_entity.csv");
        } catch (FileNotFoundException e) {
            logger.error("文件{}未找到",nationEntityFile.getAbsolutePath());
            e.printStackTrace();
        }
        logger.debug("文件名为{}",nationEntityFile.getAbsolutePath());
        if (nationEntityFile.exists()) {
            nationEntityFile.delete();
        }
        try {
            nationEntityFile.createNewFile();
        } catch (IOException e) {
            System.out.println("e:新建nation实体nationEntityFile失败");
            e.printStackTrace();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(nationEntityFile));
        } catch (IOException e) {
            System.out.println("e:创建nation实体bufferWriter失败");
            e.printStackTrace();
        }
        CsvWriter cWriter = new CsvWriter(writer, ',');
        try {
            cWriter.writeRecord("name".split(","), true);
        } catch (IOException e) {
            System.out.println("e:写入表头失败");
            e.printStackTrace();
        }
        for (HashMap.Entry<String, String> entry : nation_Entity.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            System.out.println(mapKey + ":" + mapValue);
            try {
                cWriter.writeRecord((mapKey).split(","), true);
            } catch (IOException e) {
                System.out.println("e:写入数据失败+key:" + mapKey);
                e.printStackTrace();
            }
            cWriter.flush();//刷新数据
        }
    }

    public void writeWriteRelation() {
        File writeRelationFile =null;
        try {
            writeRelationFile = ResourceUtils.getFile(Config.OUT_CSV_PATH+"write_relation.csv");
        } catch (FileNotFoundException e) {
            logger.error("未找到指定文件writeWriteRelation");
            e.printStackTrace();
        }
        if (writeRelationFile.exists()) {
            writeRelationFile.delete();
        }
        try {
            writeRelationFile.createNewFile();
        } catch (IOException e) {
            System.out.println("e:新建write关系write_relation失败");
            e.printStackTrace();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(writeRelationFile));
        } catch (IOException e) {
            System.out.println("e:创建write关系bufferWriter失败");
            e.printStackTrace();
        }
        CsvWriter cWriter = new CsvWriter(writer, ',');
        try {
            cWriter.writeRecord("author_name,book_id".split(","), true);
        } catch (IOException e) {
            System.out.println("e:写入表头失败");
            e.printStackTrace();
        }
        for (String relation : write_Relation) {

            String[] a = relation.split("!");
            System.out.println(a[0]);
            String mapKey = a[0];
            String mapValue = a[1];
            System.out.println(mapKey + ":" + mapValue);
            try {
                cWriter.writeRecord((mapKey + "," + mapValue).split(","), true);
            } catch (IOException e) {
                System.out.println("e:写入数据失败+data:" + mapKey + "," + mapValue);
                e.printStackTrace();
            }
            cWriter.flush();//刷新数据
        }
    }

    public void writeHumanOfRelation() {
        File humanOfRelationFile = new File(Config.OUT_CSV_PATH + "humanOf_relation.csv");
        if (humanOfRelationFile.exists()) {
            humanOfRelationFile.delete();
        }
        try {
            humanOfRelationFile.createNewFile();
        } catch (IOException e) {
            System.out.println("e:新建humanOf关系humanOf_relation失败");
            e.printStackTrace();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(humanOfRelationFile));
        } catch (IOException e) {
            System.out.println("e:创建humanOf关系humanOf_relation失败");
            e.printStackTrace();
        }
        CsvWriter cWriter = new CsvWriter(writer, ',');
        try {
            cWriter.writeRecord("author_name,nation_name".split(","), true);
        } catch (IOException e) {
            System.out.println("e:写入表头失败");
            e.printStackTrace();
        }
        for (HashMap.Entry<String, String> entry : humanOf_Relation.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            System.out.println(mapKey + ":" + mapValue);
            try {
                cWriter.writeRecord((mapKey).split("!"), true);
            } catch (IOException e) {
                System.out.println("e:写入数据失败+key:" + mapKey);
                e.printStackTrace();
            }
            cWriter.flush();//刷新数据
        }
    }

    public void writeAssistRelation() {
        File assistRelationFile = new File(Config.OUT_CSV_PATH  + "assist_relation.csv");
        if (assistRelationFile.exists()) {
            assistRelationFile.delete();
        }
        try {
            assistRelationFile.createNewFile();
        } catch (IOException e) {
            System.out.println("e:新建assist关系assist_relation失败");
            e.printStackTrace();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(assistRelationFile));
        } catch (IOException e) {
            System.out.println("e:创建assist关系assist_relation失败");
            e.printStackTrace();
        }
        CsvWriter cWriter = new CsvWriter(writer, ',');
        try {
            cWriter.writeRecord("author_name,author_name".split(","), true);
        } catch (IOException e) {
            System.out.println("e:写入表头失败");
            e.printStackTrace();
        }
        for (HashMap.Entry<String, String> entry : assist_Relation.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            System.out.println(mapKey + ":" + mapValue);
            try {
                cWriter.writeRecord((mapKey).split("!"), true);
            } catch (IOException e) {
                System.out.println("e:写入数据失败+key:" + mapKey);
                e.printStackTrace();
            }
            cWriter.flush();//刷新数据
        }
    }

}
