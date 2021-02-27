package cn.edu.nju.kg_qa.service.crawlerDataHandle;

import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.domain.dto.AuthorBeanPatch;
import cn.edu.nju.kg_qa.domain.dto.CrawlerJsonList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.google.gson.Gson;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: <br/>
 * date: 2021/2/19 17:12<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class Json2CSV {
    int count = 0;

    public void readJson(ArrayList<CrawlerJsonList> jsonList) {
        HashMap<String, AuthorBeanPatch> allISBN2Authors = new HashMap<>();

        for (CrawlerJsonList crawlerJson : jsonList) {
            String isbn = "";
            //书名
            String title = "";
            //出版社城市
            String city = "";
            //出版社名称
            String publishingHouseName = "";
            //出版社年份
            String year = "";
            //assistAuthor
            HashMap<String, String> assistAuthor = new HashMap<>();
            //概念分类
            HashMap<String, String> concepts = new HashMap<>();
            //isbn--著者对应map
            HashMap<String, AuthorBeanPatch> ISBN2Author = new HashMap<>();
            //一般附注
            String generalNotes = "";
            //内容提要
            String contentAbstract = "";
            //中图分类号
            String classification = "";

            isbn = (crawlerJson.getISBN() != null) ? crawlerJson.getISBN().replaceAll("\"", "") : "";

            title = extractTitle(crawlerJson);

            String publishingHouseInfo[] = extractPublishingHouseInformation(crawlerJson);
            city = publishingHouseInfo[0];
            publishingHouseName = publishingHouseInfo[1];
            year = publishingHouseInfo[2];

            concepts = extractConcepts(crawlerJson);

            assistAuthor = extractAdditionalTerms(crawlerJson);

            generalNotes = extractGeneralNotes(crawlerJson);

            contentAbstract = extractContentAbstract(crawlerJson);

            classification = extractClassification(crawlerJson);

            ISBN2Author = extractAuhtors(crawlerJson);

            allISBN2Authors.putAll(ISBN2Author);

        }
//        for (Map.Entry<String, AuthorBeanPatch> entry : allISBN2Authors.entrySet()) {
//                System.out.println(entry.getKey()+entry.getValue());
//        }
//        System.out.println(allISBN2Authors.size());
//        System.out.println(count);
        try {
            saveAsFile(allISBN2Authors);
            saveAllAsFile(jsonList);
            resetOriginFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String extractClassification(CrawlerJsonList crawlerJson) {
        List<String> classificationList = crawlerJson.get中图分类号();
        String classification = "";
        if (null != classificationList) {
            for (String s : classificationList) {
                s = s.replaceAll("\"", "");
                classification += s + ';';
            }
            System.out.println(classification);
        }
        return classification;
    }

    public String extractContentAbstract(CrawlerJsonList crawlerJson) {
        List<String> contentAbstractList = crawlerJson.get内容提要();
        String contentAbstract = "";
        if (null != contentAbstractList) {
            for (String s : contentAbstractList) {
                contentAbstract += s;
            }
//            System.out.println(contentAbstract);
        }
        return contentAbstract;
    }

    public String extractGeneralNotes(CrawlerJsonList crawlerJson) {
        List<String> generalNotesList = crawlerJson.get一般附注();
        String generalNotes = "";
        if (null != generalNotesList) {
            for (String s : generalNotesList) {
                generalNotes += s;
            }
//            System.out.println(generalNotes);
        }
        return generalNotes;
    }

    public String extractTitle(CrawlerJsonList crawlerJson) {
        String titleAndWriter = crawlerJson.get题名与责任();
        String title = "";
        if (null != titleAndWriter) {
            titleAndWriter = titleAndWriter.replaceAll("\"", "");
            titleAndWriter = titleAndWriter.replaceAll("\\[专著]", "");
            titleAndWriter = titleAndWriter.replaceAll(" ", "");
            titleAndWriter = titleAndWriter.replaceAll("  ", "");
            if (titleAndWriter.contains("/")) {
                title = titleAndWriter.split("/")[0];
            }
        }
        return title;
    }

    /**
     * 返回出版社信息 [0] city [1]name [2]year
     *
     * @param crawlerJson
     * @return
     */
    public String[] extractPublishingHouseInformation(CrawlerJsonList crawlerJson) {
        List<String> publishing = crawlerJson.get出版项();
        if (publishing != null && publishing.size() >= 1) {
            String sep[] = publishing.get(0).replaceAll(" |\n|\"", "").split(":|,");
            if (sep.length == 3) {
                return sep;
            }
        }
        return new String[3];
    }

    public HashMap<String, String> extractConcepts(CrawlerJsonList crawlerJson) {
        List<String> concepts = crawlerJson.get主题();
        HashMap<String, String> res = new HashMap<>();
        if (concepts != null) {
            for (int i = 0; i < concepts.size(); i++) {
                String conceptStr = concepts.get(i);
                conceptStr = conceptStr.replaceAll("\"", "");
                String string[] = conceptStr.split(" -- ");
                for (String s : string) {
                    res.put(s, "");
                }
            }
        }
        return res;
    }

    public HashMap<String, String> extractAdditionalTerms(CrawlerJsonList crawlerJson) {
        List<String> additionalTerms = crawlerJson.get附加款目();
        HashMap<String, String> res = new HashMap<>();
        if (additionalTerms != null) {
            for (String s : additionalTerms) {
                s = s.replaceAll("\"", "").trim();
                String strs[] = s.split(" ");
                if (strs.length == 2) {
                    if (strs[0].length() <= 4) {
                        res.put(strs[0], "");
                    }
                }
            }
        }
        return res;
    }

    public HashMap<String, AuthorBeanPatch> extractAuhtors(CrawlerJsonList crawlerJson) {
        List<AuthorBeanPatch> authors = crawlerJson.get著者();
        HashMap<String, AuthorBeanPatch> res = new HashMap<>();
        if (authors != null) {
            for (AuthorBeanPatch singleAuthorBean : authors) {
                System.out.println(singleAuthorBean.toString());
                if (singleAuthorBean.get单纯参照()!=null &&(singleAuthorBean.get单纯参照().size() != 0 || singleAuthorBean.get标目附注().size() != 0)) {
                    res.put(crawlerJson.getISBN().replaceAll("\"", "") + "!" + crawlerJson.get_ID().replaceAll("\"", ""), singleAuthorBean);
                    this.count++;
                }
            }
        }
        return res;
    }

    public void saveAsFile(HashMap<String, AuthorBeanPatch> map) throws IOException {
        File file = new File("C:\\Users\\11346\\Desktop\\标注.txt");
        FileWriter fileWriter = null;
        if (file.exists()) {
            file.delete();
        }
        fileWriter = new FileWriter(file);

        for (Map.Entry<String, AuthorBeanPatch> entry : map.entrySet()) {
            fileWriter.write(entry.getKey() + entry.getValue() + "\n");
        }
        fileWriter.flush();
        fileWriter.close();
    }

    public void saveAllAsFile(ArrayList<CrawlerJsonList> jsonList) throws IOException {
        File file = new File("C:\\Users\\11346\\Desktop\\所有爬虫作者.txt");
        FileWriter fileWriter = null;
        if (file.exists()) {
            file.delete();
        }
        fileWriter = new FileWriter(file);
        Gson gson = new Gson();
        List<String> list = new ArrayList<>();
        for (CrawlerJsonList crawlerJsonList : jsonList) {
            fileWriter.write(gson.toJson(crawlerJsonList) + '\n');
        }

        fileWriter.flush();
        fileWriter.close();
    }

    public void resetOriginFile() throws IOException {
        File file = new File("C:\\Users\\11346\\Desktop\\所有爬虫作者.txt");
        File origin = new File("C:\\Users\\11346\\Desktop\\origin.csv");
        File after = new File("C:\\Users\\11346\\Desktop\\after.csv");

        BufferedReader br = null;
        BufferedWriter bw = null;
        br = new BufferedReader(new InputStreamReader(new FileInputStream(origin)));
        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(after)));


        FileReader authorReader = new FileReader(file);
        BufferedReader authorBR = new BufferedReader(authorReader);

        ArrayList<CrawlerJsonList> list = new ArrayList<>();
        String str = null;
        while ((str = authorBR.readLine()) != null) {
            list.add(new Gson().fromJson(str, CrawlerJsonList.class));
        }

        final String lineSep = System.getProperty("line.separator");
        String line = null;
        int i = 0;
        for (line = br.readLine(); line != null; line = br.readLine()) {
            CrawlerJsonList data = list.get(i++);
            if (data != null ) {
                bw.write(line + ",\"" + new Gson().toJson(data).replaceAll("\"","\"\"") + "\""+lineSep);
            } else {
                bw.write(line + ",\"\"" + lineSep);

            }
            if (i >= list.size()) {
                break;
            }

        }
        if (br != null) {
            br.close();
        }
        if (bw != null) {
            bw.close();
        }

    }


}
