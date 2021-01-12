package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.service.extractService.ImportDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.neo4j.driver.AuthTokens;
import cn.edu.nju.kg_qa.config.Config;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Profiles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * Description: <br/>
 * date: 2021/1/13 1:02<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Api(tags = "导入数据接口")
@RestController
@RequestMapping("/importData")
public class ImportDateController implements AutoCloseable {

    @Autowired
    ImportDataService importDataService;

    private Logger logger= LoggerFactory.getLogger(ImportDateController.class);
    private static final Driver driver = GraphDatabase.driver("bolt://49.235.238.192:7687",
            AuthTokens.basic("neo4j", "root"));

    @Override
    public void close() throws Exception {
        driver.close();
    }

    @ApiOperation(value = "导入所有实体节点")
    @PostMapping(value = "/importAllEntities")
    public String importAllEntity() {
        File folder = new File(Config.OUT_CSV_PATH);
        File[] files = folder.listFiles();
        for (File entityFile : files) {
            String fileName = entityFile.getName();
            String[] splitName = fileName.split("_");
            if (splitName.length != 2) {
                logger.error("文件命名错误");
                continue;
            }
            String entityName = splitName[0];
            String typeName = splitName[1];
            if (!typeName.startsWith("entity")) {
                logger.error("文件命名错误，不确定是否为entity");
                continue;
            }
            importDataService.importDataForEntity(entityFile, entityName,driver);
        }
        return "true";
    }

    @ApiOperation(value = "导入所有关系")
    @PostMapping(value = "/importAllRelation")
    public String importAllRelation() {
        File folder = new File(Config.OUT_CSV_PATH);
        File[] files = folder.listFiles();
        for (File relationFile : files) {
            String fileName = relationFile.getName();
            String[] splitName = fileName.split("_");
            if (splitName.length != 2) {
                logger.error("文件命名错误");
                continue;
            }
            String relationName = splitName[0];
            String typeName = splitName[1];
            if (!typeName.startsWith("relation")) {
                logger.error("文件命名错误，不确定是否为relation");
                continue;
            }
            importDataService.importDataForRelation(relationFile, relationName,driver);
        }
        return "true";
    }
}
