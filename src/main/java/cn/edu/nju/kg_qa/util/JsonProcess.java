package cn.edu.nju.kg_qa.util;

import cn.edu.nju.kg_qa.util.dto.CrawlerJsonList;
import cn.edu.nju.kg_qa.util.dto.AuthorBeanPatch;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: <br/>
 * date: 2021/2/18 16:39<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class JsonProcess {
    ArrayList<CrawlerJsonList> list = new ArrayList<>();

    public void readJson(File jsonFile) throws IOException {
        String line = "";
        FileReader fileReader = new FileReader(jsonFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while ((line = bufferedReader.readLine()) != null) {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(line);
            JsonObject jsonObject = element.getAsJsonObject();
            CrawlerJsonList crawlerJsonList = new CrawlerJsonList();
            //过滤json
            if (jsonObject.get("ID 号") == null) {
                continue;
            }

            JsonElement ISBNElement = jsonObject.get("ISBN");
            String ISBN = (null == ISBNElement) ? "" : ISBNElement.toString();
            crawlerJsonList.set_ID(ISBN);

            JsonElement _IDElement = jsonObject.get("ID 号");
            String id = _IDElement.toString();
            crawlerJsonList.set_ID(id);

            JsonElement titleAndAuthorElement = jsonObject.get("题名与责任");
            String titleAndAuthor = titleAndAuthorElement.toString();
            crawlerJsonList.set题名与责任(titleAndAuthor);

            JsonElement editionElement = jsonObject.get("版本项");
            String edition = (null == editionElement) ? "" : editionElement.toString();
            crawlerJsonList.set版本项(edition);


            JsonElement publicationElement = jsonObject.get("出版项");
            List<String> publication = new ArrayList<>();
            if (publicationElement != null) {
                if (publicationElement.isJsonPrimitive() || publicationElement.isJsonObject()) {
                    publication.add(publicationElement.toString());
                } else if (publicationElement.isJsonArray()) {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    publication = gson.fromJson(publicationElement, type);
                }
            }
            crawlerJsonList.set出版项(publication);

            JsonElement languageElement = jsonObject.get("语言");
            String language = (null == languageElement) ? "" : languageElement.toString();
            crawlerJsonList.set语言(language);

            JsonElement generalNotesElement = jsonObject.get("一般附注");
            List<String> generalNotes = new ArrayList<>();
            if (generalNotesElement != null) {
                if (generalNotesElement.isJsonPrimitive() || generalNotesElement.isJsonObject()) {
                    generalNotes.add(generalNotesElement.toString());
                } else if (generalNotesElement.isJsonArray()) {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    generalNotes = gson.fromJson(generalNotesElement, type);
                }
            }
            crawlerJsonList.set一般附注(generalNotes);


            JsonElement executiveSummaryElement = jsonObject.get("内容提要");
            List<String> executiveSummary = new ArrayList<>();
            if (executiveSummaryElement != null) {
                if (executiveSummaryElement.isJsonPrimitive() || executiveSummaryElement.isJsonObject()) {
                    executiveSummary.add(executiveSummaryElement.toString());
                } else if (executiveSummaryElement.isJsonArray()) {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    executiveSummary = gson.fromJson(executiveSummaryElement, type);
                }
            }
            crawlerJsonList.set内容提要(executiveSummary);

            JsonElement titleElement = jsonObject.get("题名");
            List<String> title = new ArrayList<>();
            if (titleElement != null) {
                if (titleElement.isJsonPrimitive() || titleElement.isJsonObject()) {
                    title.add(titleElement.toString());
                } else if (titleElement.isJsonArray()) {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    title = gson.fromJson(titleElement, type);
                }
            }
            crawlerJsonList.set题名(title);

            JsonElement themeElement = jsonObject.get("主题");
            List<String> theme = new ArrayList<>();
            if (themeElement != null) {
                if (themeElement.isJsonPrimitive() || themeElement.isJsonObject()) {
                    theme.add(themeElement.toString());
                } else if (themeElement.isJsonArray()) {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    theme = gson.fromJson(themeElement, type);
                }
            }
            crawlerJsonList.set主题(theme);

            JsonElement chineseLibraryClassificationNumberElement = jsonObject.get("中图分类号");
            List<String> chineseLibraryClassificationNumber = new ArrayList<>();
            if (chineseLibraryClassificationNumberElement != null) {
                if (chineseLibraryClassificationNumberElement.isJsonPrimitive() || chineseLibraryClassificationNumberElement.isJsonObject()) {
                    chineseLibraryClassificationNumber.add(chineseLibraryClassificationNumberElement.toString());
                } else if (chineseLibraryClassificationNumberElement.isJsonArray()) {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    chineseLibraryClassificationNumber = gson.fromJson(chineseLibraryClassificationNumberElement, type);
                }
            }
            crawlerJsonList.set中图分类号(chineseLibraryClassificationNumber);


            JsonElement additionalItemsElement = jsonObject.get("附加款目");
            List<String> additionalItems = new ArrayList<>();
            if (additionalItemsElement != null) {
                if (additionalItemsElement.isJsonPrimitive() || additionalItemsElement.isJsonObject()) {
                    additionalItems.add(additionalItemsElement.toString());
                } else if (additionalItemsElement.isJsonArray()) {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    additionalItems = gson.fromJson(additionalItemsElement, type);
                }
            }
            crawlerJsonList.set附加款目(additionalItems);

            JsonElement seriesItemsElement = jsonObject.get("丛编项");
            List<String> seriesItems = new ArrayList<>();
            if (seriesItemsElement != null) {
                if (seriesItemsElement.isJsonPrimitive() || seriesItemsElement.isJsonObject()) {
                    seriesItems.add(seriesItemsElement.toString());
                } else if (seriesItemsElement.isJsonArray()) {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    seriesItems = gson.fromJson(seriesItemsElement, type);
                }
            }
            crawlerJsonList.set丛编项(seriesItems);

            List<AuthorBeanPatch> list = new ArrayList<>();
            AuthorBeanPatch authorBeanPatch = new AuthorBeanPatch();
            JsonElement authorElement = jsonObject.get("著者");
            if (authorElement != null) {
                //单个名字
                if (authorElement.isJsonPrimitive()) {
                    extractFromSingleAuthorElement(authorElement, authorBeanPatch);
                } else if (authorElement.isJsonArray()) {
                    JsonArray authorArray = jsonObject.getAsJsonArray("著者");
                    for (JsonElement singleAuthorElement : authorArray) {
                        extractFromSingleAuthorElement(singleAuthorElement, authorBeanPatch);
                    }
                } else if (authorElement.isJsonObject()) {
                    extractFromSingleAuthorElement(authorElement, authorBeanPatch);
                }
                list.add(authorBeanPatch);
            }
            crawlerJsonList.set著者(list);

            this.list.add(crawlerJsonList);

        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        for (CrawlerJsonList crawlerJsonList1 : this.list) {
            System.out.println(gson.toJson(crawlerJsonList1));
        }
    }

    public void extractFromSingleAuthorElement(JsonElement singleAuthorElement, AuthorBeanPatch authorBeanPatch) {
        String systemNumber = "";
        List<String> nameHeading = new ArrayList<>();
        List<String> headingNotes = new ArrayList<>();
        List<String> pureReference = new ArrayList<>();
        if (singleAuthorElement.isJsonPrimitive()) {
            nameHeading = new ArrayList<>();
            nameHeading.add(0, singleAuthorElement.toString());
            authorBeanPatch.set名称标目(nameHeading);
        } else if (singleAuthorElement.isJsonObject()) {
            JsonObject singleAuthorObject = singleAuthorElement.getAsJsonObject();
            JsonElement systemNumberElement = singleAuthorObject.get("系统号");
            systemNumber = systemNumberElement.getAsString();

            JsonElement pureReferenceElement = singleAuthorObject.get("单纯参照");
            if (pureReferenceElement != null) {
                if (pureReferenceElement.isJsonPrimitive()) {
                    pureReference.add(pureReferenceElement.toString());
                } else if (pureReferenceElement.isJsonArray()) {
                    JsonArray jsonArray = pureReferenceElement.getAsJsonArray();
                    for (JsonElement jsonElement : jsonArray) {
                        pureReference.add(jsonElement.toString());
                    }
                }
            }

            JsonElement nameHeadingElement = singleAuthorObject.get("名称标目");
            if (nameHeadingElement != null) {
                if (nameHeadingElement.isJsonPrimitive()) {
                    nameHeading.add(nameHeadingElement.toString());
                } else if (nameHeadingElement.isJsonArray()) {
                    JsonArray jsonArray = nameHeadingElement.getAsJsonArray();
                    for (JsonElement jsonElement : jsonArray) {
                        nameHeading.add(jsonElement.toString());
                    }
                }
            }

            JsonElement headingNotesElement = singleAuthorObject.get("标目附注");
            if (headingNotesElement != null) {
                if (headingNotesElement.isJsonPrimitive()) {
                    headingNotes.add(headingNotesElement.toString());
                } else if (headingNotesElement.isJsonArray()) {
                    JsonArray jsonArray = headingNotesElement.getAsJsonArray();
                    for (JsonElement jsonElement : jsonArray) {
                        headingNotes.add(jsonElement.toString());
                    }
                }
            }
        }
        authorBeanPatch.set系统号(systemNumber);
        authorBeanPatch.set名称标目(nameHeading);
        authorBeanPatch.set单纯参照(pureReference);
        authorBeanPatch.set标目附注(headingNotes);
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
