package cn.edu.nju.kg_qa.service.CSVDataHandle;

import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.config.TableHead;
import cn.edu.nju.kg_qa.domain.dto.AuthorBeanPatch;
import cn.edu.nju.kg_qa.domain.dto.CrawlerJsonList;
import cn.edu.nju.kg_qa.util.Hash;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static HashMap<String, AuthorBeanPatch> author_Entity = new HashMap<>();
    public static ArrayList<String> write_Relation = new ArrayList<>();
    public static HashMap<String, String> nation_Entity = new HashMap<>();
    public static HashMap<String, String> humanOf_Relation = new HashMap<>();
    public static HashMap<String, String> assist_Relation = new HashMap<>();

    Pattern p = Pattern.compile(".*\\d+.*");

    private Logger logger = LoggerFactory.getLogger(HandleAuthorService.class);

    public void clear() {
        author_Entity.clear();
        write_Relation.clear();
        nation_Entity.clear();
        humanOf_Relation.clear();
        assist_Relation.clear();
    }

    public void extractAuthor(CsvReader csvReader) {
        String isbn = "";
        String assistant = "";
        try {
            isbn = csvReader.get(0).replaceAll("\"", "");
            assistant = csvReader.get(8).replaceAll("\\\\", "");
            CrawlerJsonList bookJson = new CrawlerJsonList();
            //优先使用爬虫数据
            if (csvReader.get(19) != null) {
                bookJson = new Gson().fromJson(csvReader.get(19), CrawlerJsonList.class);
                if (bookJson.get著者() != null && bookJson.get著者().size() != 0 && !bookJson.get著者().get(0).get系统号().equals("")) {
                    doExtractAuthorAsJson(bookJson, isbn);
                    return;
                }
            }
            doExtractAuthorAsCsv(csvReader, isbn);
            extractAssistant(isbn, assistant);
        } catch (IOException e) {
            System.out.println("e:读取字段错误,isbn:" + isbn);
            e.printStackTrace();
        }

    }

    private void doExtractAuthorAsCsv(CsvReader csvReader, String isbn) {
        String author = "";
        String assistant = "";
        String authorId = "";
        try {
            author = csvReader.get(2).replaceAll("\\\\", "");
            author = author.replaceAll("・", "");
            authorId = (Hash.stringToHash(author) + 1000000000) + "";
            assistant = csvReader.get(8).replaceAll("\\\\", "");
            author = extractNation(authorId, author);
            AuthorBeanPatch authorBean = new AuthorBeanPatch();
            author = extractBirth(author, authorBean);
            author = author.trim();
            if (author.startsWith(" ")) {
                author = author.replaceFirst(" ", "");
            }
            if (author.contains(" ")) {
                author = author.split(" ")[0];
            }
            authorBean.setTrueName(author.replaceAll("・|\\s| ", ""));
            authorBean.set系统号(authorId);
            this.putIntoAuthorMap(authorId, authorBean);
            write_Relation.add(authorId + "!" + isbn);
            this.extractAssistant(isbn, assistant);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void doExtractAuthorAsJson(CrawlerJsonList bookJson, String isbn) {
        String author = "";
        String assistant = "";
        String authorId = "";
        if (bookJson.get著者() != null && bookJson.get著者().size() != 0) {
            int i = 0;
            for (AuthorBeanPatch authorBean : bookJson.get著者()) {
                authorId = authorBean.get系统号();
                author = authorBean.get名称标目().get(0);
                author = author.replaceAll("・", "");
                author = author.replaceAll("\\\\", "");
                author = author.replaceAll("\"", "");
                System.out.println(author);
                author = extractNation(authorId, author);
                author = extractBirth(author, authorBean);
                if (null == author) {
                    return;
                }
                author = author.trim();
                if (author.startsWith(" ")) {
                    author = author.replaceFirst(" ", "");
                }
                if (author.contains(" ")) {
                    author = author.split(" ")[0];
                }
                authorBean.setTrueName(author.replaceAll("・|\\s| ", ""));
                this.putIntoAuthorMap(authorId, authorBean);
                write_Relation.add(authorId + "!" + isbn);
            }
        }
    }

    private String extractBirth(String authorName, AuthorBeanPatch authorBeanPatch) {
        authorName = authorName.replaceAll(" ", "");
        String year = "";
        String birth = "";
        if (authorName.trim().contains("(") && authorName.trim().contains(")") && !authorName.trim().startsWith("(")) {
            year = authorName;
            int startIndex = year.lastIndexOf("(");
            int endIndex = year.lastIndexOf(")");
            birth = year.substring(startIndex, endIndex + 1);

            Matcher m = p.matcher(birth);
            if (m.matches()) {
                System.out.println(birth);
                authorName = year.substring(0, startIndex);
                System.out.println(authorName);
            } else {
                return authorName.trim().replaceAll("\\p{Punct}", "");
            }

        }
        authorBeanPatch.setYear(birth);
        return authorName.trim().replaceAll("\\p{Punct}", "");
    }

    /**
     * 对作者名中含国籍的进行过滤 存储humanOf关系
     *
     * @param
     * @return
     */
    private String extractNation(String authorId, String authorName) {
        if (TableHead.AUTHOR.getValue().equals(authorName)) {
            return null;
        }
        authorName = authorName.replaceAll(" ", "");
        String nation = authorName;
        if (nation.trim().startsWith("(")) {
            authorName = nation.substring(nation.indexOf(")") + 1);
            nation = nation.substring(1);
            int index = nation.indexOf(")");
            nation = nation.substring(0, index);
            if (!nation_Entity.containsKey(nation) && !nation.replaceAll("\"", "").equals("")) {
                nation_Entity.put(nation, "");
            }
            humanOf_Relation.put(authorId + "!" + nation, "");

            System.out.println(authorName + "!" + nation);
        }
        return authorName;
    }

    private void extractAssistant(String isbn, String assistant) {
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
            AuthorBeanPatch authorBean = new AuthorBeanPatch();
            String authorId = (Hash.stringToHash(assist) + 1000000000) + "";
            assist = this.extractNation(authorId, assist);
            if (null == assist) {
                return;
            }
            String trueName = this.extractBirth(assist, authorBean);
            authorBean.set系统号(authorId);
            trueName = trueName.trim();
            if (trueName.startsWith(" ")) {
                trueName = trueName.replaceFirst(" ", "");
            }
            if (trueName.contains(" ")) {
                trueName = trueName.split(" ")[0];
            }
            authorBean.setTrueName(trueName.replaceAll("・|\\s| ", ""));
            this.putIntoAuthorMap(authorId, authorBean);
            assist_Relation.put(authorId + "!" + isbn, "");
        }
    }

    private void putIntoAuthorMap(String authorId, AuthorBeanPatch authorBeanPatch) {
        if (!author_Entity.containsKey(authorId)) {
            author_Entity.put(authorId, authorBeanPatch);
        } else {
            //todo 消歧
        }
    }


    public void writeAuthorsEntity() {
        File authorEntityFile = null;
        try {
            authorEntityFile = ResourceUtils.getFile(Config.OUT_CSV_PATH + "author_entity.csv");
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
            cWriter.writeRecord("id,name,birth,description".split(","), true);
        } catch (IOException e) {
            System.out.println("e:写入表头失败");
            e.printStackTrace();
        }
        for (HashMap.Entry<String, AuthorBeanPatch> entry : author_Entity.entrySet()) {
            String mapKey = entry.getKey();
            AuthorBeanPatch mapValue = entry.getValue();
            System.out.println(mapKey + ":" + mapValue);
            String description = " ";
            String year = " ";
            try {
                if (mapValue.get标目附注() != null && mapValue.get标目附注().size() != 0) {
                    description = mapValue.get标目附注().toString();
                }
                if (mapValue.getYear() != null && !mapValue.getYear().equals("")) {
                    year = mapValue.getYear();
                }
                cWriter.writeRecord((mapValue.get系统号() + "&!" + mapValue.getTrueName() + "&!" + year + "&!" + description.replaceAll("[|]|\"", "")).
                        split("&!"), true);
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
            nationEntityFile = ResourceUtils.getFile(Config.OUT_CSV_PATH + "nation_entity.csv");
        } catch (FileNotFoundException e) {
            logger.error("文件{}未找到", nationEntityFile.getAbsolutePath());
            e.printStackTrace();
        }
        logger.debug("文件名为{}", nationEntityFile.getAbsolutePath());
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
        File writeRelationFile = null;
        try {
            writeRelationFile = ResourceUtils.getFile(Config.OUT_CSV_PATH + "write_relation.csv");
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
            cWriter.writeRecord("author_id,book_id".split(","), true);
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
            cWriter.writeRecord("author_id,nation_name".split(","), true);
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
        File assistRelationFile = new File(Config.OUT_CSV_PATH + "assist_relation.csv");
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
            cWriter.writeRecord("author_id,book_id".split(","), true);
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
