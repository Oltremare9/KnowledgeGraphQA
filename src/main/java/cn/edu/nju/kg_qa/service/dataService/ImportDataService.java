package cn.edu.nju.kg_qa.service.dataService;

import cn.edu.nju.kg_qa.config.Config;
import org.neo4j.driver.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

/**
 * Description: <br/>
 * date: 2021/1/13 1:25<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class ImportDataService {

    @Value("${spring.data.neo4j.uri}")
    private static String uri;


    @Value("${spring.data.neo4j.username}")
    private static String username;


    @Value("${spring.data.neo4j.password}")
    private static String password;

    public Driver createDrive() {
        return GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }

//    public Driver createDrive() {
//        return GraphDatabase.driver("bolt://http://182.61.50.186/:7687", AuthTokens.basic("neo4j", "root"));
//    }

//    public Driver createDrive() {
//        return GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "root"));
//    }

    private Logger logger = LoggerFactory.getLogger(ImportDataService.class);

    public void importDataForRelation(File relationFile, String relationName) {
        Driver driver = createDrive();
        List<String> lines = null;
        try {
            lines = Files.readAllLines(relationFile.toPath(),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("读取csv文件错误，path：" + relationFile.getAbsolutePath());
            e.printStackTrace();
        }
        logger.warn("当前处理文件为:{}", relationFile.toString());
        logger.warn("共计", lines.size());
        String header = lines.get(0);
        String headers[] = header.split(",");
        String label1 = headers[0].split("_")[0];
        String property1 = headers[0].split("_")[1];
        String label2 = headers[1].split("_")[0];
        String property2 = headers[1].split("_")[1];
        String cypher = "MATCH (e:" + label1 + "{" + property1 + ":\'value0\'}),(q:" + label2 + "{" + property2 + ":\'value1\'})" +
                "CREATE (e)-[r:" + relationName + "]->(q)";

        for (int transactionNum = 0; transactionNum <= lines.size() / 20; transactionNum++) {
            try (Session session = driver.session()) {
                try (Transaction tx = session.beginTransaction()) {
                    for (int i = transactionNum * 20 + 1; i <= Math.min((transactionNum + 1) * 20, lines.size() - 1); i++) {

                        String[] array = lines.get(i).split(",");
                        String execute = cypher;
                        if (array.length < 2) {
                            continue;
                        }
                        for (int j = 0; j < 2; j++) {
                            execute = execute.replace("value" + j, array[j]);
                        }

                        tx.run(execute);
                    }
                    tx.commit();
                }
            }
            logger.info("当前处理:{}条", transactionNum * 20);
        }
        driver.close();
    }

    /**
     * @param entityFile                实体预处理文件
     * @param entityName                实体类型 label名称
     * @param entityNameForJieBa        jieba分词文件 name-权重
     * @param entityNameAndTypeForJieBa jieba对照列表 name-type
     */
    public void importDataForEntity(File entityFile, String entityName, List<String> entityNameForJieBa, List<String> entityNameAndTypeForJieBa) {
        Driver driver = createDrive();
        int indexOfEntityName = -1;
        List<String> lines = null;
        try {
            lines = Files.readAllLines(entityFile.toPath(),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("读取csv文件错误，path：" + entityFile.getAbsolutePath());
            e.printStackTrace();
        }
        System.out.println(entityFile.getName());
        String header = lines.get(0);
        String headers[] = header.split(",");
        int propertyNum = headers.length;
        //+" property1:value, property2:value, property3:value} )";
        String cypher = "merge (p:" + entityName + "{";
        cypher = cypher + headers[0] + ":value0})";
        cypher = cypher + "set p+={";
        for (int i = 0; i < propertyNum; i++) {
            if (headers[i].equals("name")) {
                indexOfEntityName = i;
            }
        }
        for (int i = 1; i < propertyNum; i++) {
            if (i != 1) {
                cypher += ",";
            }
            cypher += headers[i] + ":value" + i;
        }
        cypher += "}";


        for (int transactionNum = 0; transactionNum <= lines.size() / 2000; transactionNum++) {
            try (Session session = driver.session()) {
                try (Transaction tx = session.beginTransaction()) {
                    for (int i = transactionNum * 2000 + 1; i <= Math.min((transactionNum + 1) * 2000, lines.size() - 1); i++) {
                        System.out.println(lines.get(i));
                        String[] array = lines.get(i).split(",");
                        String execute = cypher;
                        for (int j = 0; j < propertyNum; j++) {
                            execute = execute.replace("value" + j, "'" + array[j].replaceAll("“|”|\"|\\\\", "") + "'");
                            if (j == indexOfEntityName) {
                                if (!entityFile.getName().contains("concept")) {
                                    String str = "[`\\\\~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%…&*（）——+|{}【】‘；：”“’。，、？《》]";
                                    String value = array[j];
                                    value = value.replaceAll(str, "");
                                    entityNameForJieBa.add(value);
                                    entityNameAndTypeForJieBa.add(value + "\t" + entityName);
                                }
                            }
                        }
                        tx.run(execute);
                    }
                    tx.commit();
                }
                logger.info("" + transactionNum * 2000 + 1);
            }
        }
        driver.close();
    }

    /**
     * 写入jieba分词文件
     *
     * @param jieBaWords
     */
    public void writeJieBaWords(List<String> jieBaWords) {
        File dicts = new File(Config.JIEBA_ENTITY_PATH);
        if (dicts.exists()) {
            dicts.delete();
        }
        try {
            dicts.createNewFile();
        } catch (IOException e) {
            logger.error("创建jieba分词文件失败");
            e.printStackTrace();
        }
        try {
            FileWriter fileWriter = new FileWriter(dicts, true);
            for (String s : jieBaWords) {
                s = s.replaceAll(" ", "");
                s = s.replaceAll(" ", "");
                fileWriter.write(s + "    5\n");
            }
            FileReader fileReader=new FileReader(Config.OUT_CSV_PATH+"otherNames.txt");
            BufferedReader bufferedReader=new BufferedReader(fileReader);
            String line="";
            while((line=bufferedReader.readLine())!=null){
                fileWriter.write(line.split("!")[0].replaceAll(" |\"","")+"\t5\n");
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            logger.error("fileWriter创建失败");
            e.printStackTrace();
        }
        logger.warn("jieba文件写入成功");
    }

    /**
     * 写入jieba对照文件
     *
     * @param entityNameAndTypeForJieBa
     */
    public void writeJieBaContrast(List<String> entityNameAndTypeForJieBa) {
        File dicts = new File(Config.JIEBA_Contrast_PATH);
        if (dicts.exists()) {
            dicts.delete();
        }
        try {
            dicts.createNewFile();
        } catch (IOException e) {
            logger.error("创建jieba对照文件失败");
            e.printStackTrace();
        }
        try {
            FileWriter fileWriter = new FileWriter(dicts, true);
            for (String s : entityNameAndTypeForJieBa) {
                s = s.replaceAll(" ", "");
                s = s.replaceAll(" ", "");
                fileWriter.write(s + "\n");
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            logger.error("fileWriter创建失败");
            e.printStackTrace();
        }
        logger.warn("jieba对照文件写入成功");
    }


}
