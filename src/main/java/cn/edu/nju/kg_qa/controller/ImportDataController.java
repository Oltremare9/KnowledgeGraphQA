package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.domain.entity.InstituteNode;
import cn.edu.nju.kg_qa.service.extractService.*;
import com.alibaba.fastjson.JSON;
import com.csvreader.CsvReader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Description: <br/>
 * date: 2021/1/12 21:31<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Api(tags = "导入数据接口")
@RestController
@RequestMapping("/importData")
public class ImportDataController {
    private Logger logger= LoggerFactory.getLogger(ImportDataController.class);
    @Autowired
    HandleAuthorService handleAuthorService;
    @Autowired
    HandleBookSeriesService handleBookSeriesService;
    @Autowired
    HandleBookService handleBookService;
    @Autowired
    HandleCityService handleCityService;
    @Autowired
    HandleConceptService handleConceptService;
    @Autowired
    HandleDateService handleDateService;
    @Autowired
    HandleInstituteService handleInstituteService;

    @ApiOperation(value = "实体：作者 国籍\n" + "关系：assist write humanOf ")
    @PostMapping(value = "/importAuthor")
    public String importAuthor() {
        File file = null;
        try {
            file = ResourceUtils.getFile(Config.IN_CSV_PATH);
        } catch (FileNotFoundException e) {
            logger.error("未找到文件");
            e.printStackTrace();
        }
        try {
            int count = 10001;
            int i = 0;
            CsvReader csvReader = new CsvReader(file.getAbsolutePath(), ',', Charset.forName("UTF-8"));
            while (i++ < count) {
                try {
                    if (!csvReader.readRecord()) {
                        break;
                    }

                } catch (IOException e) {
                    logger.error("e:文件IO读取错误");
                    e.printStackTrace();
                }
                handleAuthorService.extractAuthor(csvReader);
                handleAuthorService.writeNationEntity();
                handleAuthorService.writeAuthorsEntity();
                handleAuthorService.writeAssistRelation();
                handleAuthorService.writeHumanOfRelation();
                handleAuthorService.writeWriteRelation();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "true";
    }
}
