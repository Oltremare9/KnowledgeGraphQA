package cn.edu.nju.kg_qa.service.extractService;

import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.util.Jieba;
import com.google.common.io.Resources;
import lombok.extern.flogger.Flogger;
import org.neo4j.driver.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: <br/>
 * date: 2021/1/13 1:25<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class ImportDataService implements AutoCloseable {

    private static final Driver driver = GraphDatabase.driver("bolt://49.235.238.192:7687",
            AuthTokens.basic("neo4j", "root"));
//            private static final Driver driver = GraphDatabase.driver("bolt://localhost:7687",
//            AuthTokens.basic("neo4j", "root"));

    @Override
    public void close() throws Exception {
        driver.close();
    }

    private Logger logger = LoggerFactory.getLogger(ImportDataService.class);

    public void importDataForRelation(File relationFile, String relationName) {

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
    }

    public void importDataForEntity(File entityFile, String entityName, List<String> entityNameForJieBa) {

        int indexOfEntityName = -1;
        List<String> lines = null;
        try {
            lines = Files.readAllLines(entityFile.toPath(),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("读取csv文件错误，path：" + entityFile.getAbsolutePath());
            e.printStackTrace();
        }
        String header = lines.get(0);
        String headers[] = header.split(",");
        int propertyNum = headers.length;
        //+" property1:value, property2:value, property3:value} )";
        String cypher = "CREATE (:" + entityName + "{";
        for (int i = 0; i < propertyNum; i++) {
            if (i != 0) {
                cypher += ",";
            }
            cypher += headers[i] + ":value" + i;
            if (headers[i].equals("name")) {
                indexOfEntityName = i;
            }
        }
        cypher += "})";


        for (int transactionNum = 0; transactionNum <= lines.size() / 2000; transactionNum++) {
            try (Session session = driver.session()) {
                try (Transaction tx = session.beginTransaction()) {
                    for (int i = transactionNum * 2000 + 1; i <= Math.min((transactionNum + 1) * 2000, lines.size() - 1); i++) {
                        String[] array = lines.get(i).split(",");
                        String execute = cypher;
                        for (int j = 0; j < propertyNum; j++) {
                            execute = execute.replace("value" + j, "'" + array[j] + "'");
                            if (j == indexOfEntityName) {
                                entityNameForJieBa.add(array[j]);
                            }
                        }
                        tx.run(execute);
                    }
                    tx.commit();
                }
                logger.info("" + transactionNum * 2000 + 1);
            }
        }
    }

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
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            logger.error("fileWriter创建失败");
            e.printStackTrace();
        }
        logger.warn("jieba文件写入成功");
    }


}
