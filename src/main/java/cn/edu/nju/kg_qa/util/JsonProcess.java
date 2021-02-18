package cn.edu.nju.kg_qa.util;

import cn.edu.nju.kg_qa.util.dto.CrawlerJson;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import springfox.documentation.spring.web.json.Json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Description: <br/>
 * date: 2021/2/18 16:39<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class JsonProcess {
    ArrayList<CrawlerJson> list = new ArrayList<>();

    public void readJson(File jsonFile) throws IOException {
        String line = "";
        FileReader fileReader = new FileReader(jsonFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int count=0;
        while ((line = bufferedReader.readLine()) != null) {
            Gson gson = new Gson();

            CrawlerJson jsonData = gson.fromJson(line, CrawlerJson.class);
            list.add(jsonData);
            if(jsonData.get_ID()!=null){
                count++;
            }
            System.out.println(jsonData.toString());

        }
        System.out.println(count);
    }

    public static void main(String args[]) {

        JsonProcess readJson = new JsonProcess();
        File file = new File("C:\\Users\\11346\\Desktop\\all.jl");
        try {
            readJson.readJson(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
