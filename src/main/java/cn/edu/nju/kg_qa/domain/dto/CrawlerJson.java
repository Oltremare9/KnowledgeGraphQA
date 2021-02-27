package cn.edu.nju.kg_qa.domain.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Description: <br/>
 * date: 2021/2/18 16:40<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class CrawlerJson {


    public String get头标区() {
        return 头标区;
    }

    public void set头标区(String 头标区) {
        this.头标区 = 头标区;
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String get通用数据() {
        return 通用数据;
    }

    public void set通用数据(String 通用数据) {
        this.通用数据 = 通用数据;
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

    public Object get出版项() {
        return 出版项;
    }

    public void set出版项(Object 出版项) {
        this.出版项 = 出版项;
    }

    public String get载体形态项() {
        return 载体形态项;
    }

    public void set载体形态项(String 载体形态项) {
        this.载体形态项 = 载体形态项;
    }

    public String get语言() {
        return 语言;
    }

    public void set语言(String 语言) {
        this.语言 = 语言;
    }

    public Object get一般附注() {
        return 一般附注;
    }

    public void set一般附注(Object 一般附注) {
        this.一般附注 = 一般附注;
    }

    public Object get内容提要() {
        return 内容提要;
    }

    public void set内容提要(Object 内容提要) {
        this.内容提要 = 内容提要;
    }

    public Object get题名() {
        return 题名;
    }

    public void set题名(Object 题名) {
        this.题名 = 题名;
    }

    public String get所有单册() {
        return 所有单册;
    }

    public void set所有单册(String 所有单册) {
        this.所有单册 = 所有单册;
    }

    public Object get主题() {
        return 主题;
    }

    public void set主题(Object 主题) {
        this.主题 = 主题;
    }

    public Object get中图分类号() {
        return 中图分类号;
    }

    public void set中图分类号(Object 中图分类号) {
        this.中图分类号 = 中图分类号;
    }

    public Object get著者() {
        return 著者;
    }

    public void set著者(Object 著者) {
        this.著者 = 著者;
    }

    public Object get馆藏() {
        return 馆藏;
    }

    public void set馆藏(Object 馆藏) {
        this.馆藏 = 馆藏;
    }

    /**
     * 头标区 : -----nam0 22----- 450
     * ID 号 : 009811545
     * 通用数据 : 20180914d2018 em y0chiy50 ea
     * 题名与责任 : 经济政治与社会 [专著] = Economy, politics and society / 沈越，张可君主编
     * 版本项 : 4版
     * 出版项 : 北京 : 北京师范大学出版社, 2018
     * 载体形态项 : 144页 : 图 ; 26cm
     * 语言 : chi
     * 一般附注 : 中等职业教育课程改革国家规划新教材
     * 内容提要 : 本书主要介绍了当下我国的经济、政治与社会发展现状，内容包括五个部分：透视经济现象；投身经济建设；坚持中国特色社会主义政治制度；参与政治生活；共建文明社会，共享美好生活。
     * 题名 : Economy, politics and society
     * 主题 : ["中国经济 -- 经济建设 -- 中等专业教育 -- 教材","政治 -- 中国 -- 中等专业教育 -- 教材"]
     * 中图分类号 : ["F124","D6"]
     * 著者 : [{"系统号":"000180397","名称标目":["沈越 (1952~)","shen yue"],"标目附注":["北京师范大学教授，曾任经济与工商管理学院常务副院长、党委书记，兼任中华外国经济学说研究会学术委员会副主任、北京市经济学总会副会长、北京市外国经济学说研究会副会长。籍贯江苏溧阳，出生地四川成都。研究领域为中国经济改革与发展、经济体制比较、德国经济学说。主编有《计划经济向市场经济稳定转轨研究》等。","四川成都 籍贯：江苏溧阳","shenyue@bnu.edu.cn","经济学","北京师范大学"],"单纯参照":"Shen Yue (1952~)","更新标志":"N"},{"系统号":"000888837","名称标目":["张可君","zhang ke jun"],"标目附注":"高级讲师，安徽新华学院副院长。教育部《经济政治与社会教学大纲》起草小组负责人。","更新标志":"N"}]
     * 所有单册 : 查看所有馆藏单册信息
     * 馆藏 : [" 中文基藏"," 书刊保存本库"," 北区中文图书区"]
     */

    private String 头标区;
    @SerializedName(value = "ID 号")
    private String _ID;
    private String 通用数据;
    private String 题名与责任;
    private String 版本项;
    private Object 出版项;
    private String 载体形态项;
    private String 语言;
    private Object 一般附注;
    private Object 内容提要;
    private Object 题名;
    private String 所有单册;
    private Object 主题;
    private Object 中图分类号;
    private Object 著者;
    private Object 馆藏;

    @Override
    public String toString() {
        return "CrawlerJson{" +
                "头标区='" + 头标区 + '\'' +
                ", _ID='" + _ID + '\'' +
                ", 通用数据='" + 通用数据 + '\'' +
                ", 题名与责任='" + 题名与责任 + '\'' +
                ", 版本项='" + 版本项 + '\'' +
                ", 出版项=" + 出版项 +
                ", 载体形态项='" + 载体形态项 + '\'' +
                ", 语言='" + 语言 + '\'' +
                ", 一般附注=" + 一般附注 +
                ", 内容提要=" + 内容提要 +
                ", 题名=" + 题名 +
                ", 所有单册='" + 所有单册 + '\'' +
                ", 主题=" + 主题 +
                ", 中图分类号=" + 中图分类号 +
                ", 著者=" + 著者 +
                ", 馆藏=" + 馆藏 +
                '}';
    }

    public static class 著者Bean {
        /**
         * 系统号 : 000180397
         * 名称标目 : ["沈越 (1952~)","shen yue"]
         * 标目附注 : ["北京师范大学教授，曾任经济与工商管理学院常务副院长、党委书记，兼任中华外国经济学说研究会学术委员会副主任、北京市经济学总会副会长、北京市外国经济学说研究会副会长。籍贯江苏溧阳，出生地四川成都。研究领域为中国经济改革与发展、经济体制比较、德国经济学说。主编有《计划经济向市场经济稳定转轨研究》等。","四川成都 籍贯：江苏溧阳","shenyue@bnu.edu.cn","经济学","北京师范大学"]
         * 单纯参照 : Shen Yue (1952~)
         * 更新标志 : N
         */


        private String 系统号;
        private String 单纯参照;
        private String 更新标志;
        private String 名称标目;
        private String 标目附注;

        public String get系统号() {
            return 系统号;
        }

        public void set系统号(String 系统号) {
            this.系统号 = 系统号;
        }

        public String get单纯参照() {
            return 单纯参照;
        }

        public void set单纯参照(String 单纯参照) {
            this.单纯参照 = 单纯参照;
        }

        public String get更新标志() {
            return 更新标志;
        }

        public void set更新标志(String 更新标志) {
            this.更新标志 = 更新标志;
        }

        public String get名称标目() {
            return 名称标目;
        }

        public void set名称标目(String 名称标目) {
            this.名称标目 = 名称标目;
        }

        public String get标目附注() {
            return 标目附注;
        }

        public void set标目附注(String 标目附注) {
            this.标目附注 = 标目附注;
        }
    }
}
