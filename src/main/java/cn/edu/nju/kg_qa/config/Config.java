package cn.edu.nju.kg_qa.config;

import net.bytebuddy.description.modifier.FieldManifestation;

/**
 * Description: <br/>
 * date: 2021/1/12 21:20<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class Config {
    public final static String IN_CSV_PATH="/data/origin.csv";
    public final static String OUT_CSV_PATH="/usr/local/neo4j/neo4j-community-4.1.5/import/";
//    public final static String IN_CSV_PATH="C:\\Users\\11346\\Desktop\\origin.csv";
//    public final static String OUT_CSV_PATH="C:\\Users\\11346\\Desktop\\temp切分\\";
    public final static int EXTRACT_NUM=10001;
    public final static int BATCH_IMPORT_SIZE=2000;

    public final static String JIEBA_ENTITY_PATH="/data/jieba.dict";
    public final static String JIEBA_SOUGOU_PATH="/data/sougou.dict";
}
