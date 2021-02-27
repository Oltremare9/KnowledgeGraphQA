package cn.edu.nju.kg_qa.domain.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/2/18 22:41<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class CrawlerJsonList {
    @SerializedName(value = "ID 号")
    private String _ID;
    @SerializedName(value = "题名与责任")
    private String 题名与责任;
    @SerializedName(value = "版本项")
    private String 版本项;
    @SerializedName(value = "出版项")
    private List<String> 出版项;
    @SerializedName(value = "语言")
    private String 语言;
    @SerializedName(value = "一般附注")
    private List<String> 一般附注;
    @SerializedName(value = "内容提要")
    private List<String> 内容提要;
    @SerializedName(value = "题名")
    private List<String> 题名;
    @SerializedName(value = "主题")
    private List<String> 主题;
    @SerializedName(value = "中图分类号")
    private List<String> 中图分类号;
    @SerializedName(value = "著者")
    private List<AuthorBeanPatch> 著者;
    @SerializedName(value = "附加款目")
    private List<String> 附加款目;
    @SerializedName(value = "丛编项")
    private List<String> 丛编项;
    @SerializedName(value = "ISBN")
    private String ISBN;

    public List<String> get丛编项() {
        return 丛编项;
    }

    public void set丛编项(List<String> 丛编项) {
        this.丛编项 = 丛编项;
    }

    @Override
    public String toString() {
        return "CrawlerJsonList{" +
                "_ID='" + _ID + '\'' +
                ",\n 题名与责任='" + 题名与责任 + '\'' +
                ",\n 版本项='" + 版本项 + '\'' +
                ",\n 出版项=" + 出版项 +
                ",\n 语言='" + 语言 + '\'' +
                ",\n 一般附注=" + 一般附注 +
                ",\n 内容提要=" + 内容提要 +
                ",\n 题名=" + 题名 +
                ",\n 主题=" + 主题 +
                ",\n 中图分类号=" + 中图分类号 +
                ",\n 著者=" + 著者 +
                ",\n 附加款目='" + 附加款目 + '\'' +
                ",\n ISBN='" + ISBN + '\'' +
                '}'+"\n";
    }

    public List<String> get附加款目() {
        return 附加款目;
    }

    public void set附加款目(List<String> 附加款目) {
        this.附加款目 = 附加款目;
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String get题名与责任() {
        return 题名与责任;
    }

    public void set题名与责任(String 题名与责任) {
        this.题名与责任 = 题名与责任;
    }

    public String get版本项() {
        return 版本项;
    }

    public void set版本项(String 版本项) {
        this.版本项 = 版本项;
    }

    public List<String> get出版项() {
        return 出版项;
    }

    public void set出版项(List<String> 出版项) {
        this.出版项 = 出版项;
    }

    public String get语言() {
        return 语言;
    }

    public void set语言(String 语言) {
        this.语言 = 语言;
    }

    public List<String> get一般附注() {
        return 一般附注;
    }

    public void set一般附注(List<String> 一般附注) {
        this.一般附注 = 一般附注;
    }

    public List<String> get内容提要() {
        return 内容提要;
    }

    public void set内容提要(List<String> 内容提要) {
        this.内容提要 = 内容提要;
    }

    public List<String> get题名() {
        return 题名;
    }

    public void set题名(List<String> 题名) {
        this.题名 = 题名;
    }

    public List<String> get主题() {
        return 主题;
    }

    public void set主题(List<String> 主题) {
        this.主题 = 主题;
    }

    public List<String> get中图分类号() {
        return 中图分类号;
    }

    public void set中图分类号(List<String> 中图分类号) {
        this.中图分类号 = 中图分类号;
    }

    public List<AuthorBeanPatch> get著者() {
        return 著者;
    }

    public void set著者(List<AuthorBeanPatch> 著者) {
        this.著者 = 著者;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}
