package cn.edu.nju.kg_qa.domain.dto;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/2/18 22:42<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class AuthorBeanPatch {
    /**
     * 系统号 : 000180397
     * 名称标目 : ["沈越 (1952~)","shen yue"]
     * 标目附注 : ["北京师范大学教授，曾任经济与工商管理学院常务副院长、党委书记，兼任中华外国经济学说研究会学术委员会副主任、北京市经济学总会副会长、北京市外国经济学说研究会副会长。籍贯江苏溧阳，出生地四川成都。研究领域为中国经济改革与发展、经济体制比较、德国经济学说。主编有《计划经济向市场经济稳定转轨研究》等。","四川成都 籍贯：江苏溧阳","shenyue@bnu.edu.cn","经济学","北京师范大学"]
     * 单纯参照 : Shen Yue (1952~)
     * 更新标志 : N
     */


    private String 系统号;
    private List<String> 单纯参照;
    private List<String> 名称标目;
    private List<String> 标目附注;

    private String trueName;

    @Override
    public String toString() {
        return "AuthorBeanPatch{" +
                "系统号='" + 系统号 + '\'' +
                ", 单纯参照=" + 单纯参照 +
                ", 名称标目=" + 名称标目 +
                ", 标目附注=" + 标目附注 +
                ", trueName='" + trueName + '\'' +
                ", year='" + year + '\'' +
                '}';
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    private String year;

    public String get系统号() {
        return 系统号;
    }

    public void set系统号(String 系统号) {
        this.系统号 = 系统号;
    }

    public List<String> get单纯参照() {
        return 单纯参照;
    }

    public void set单纯参照(List<String> 单纯参照) {
        this.单纯参照 = 单纯参照;
    }

    public List<String> get名称标目() {
        return 名称标目;
    }

    public void set名称标目(List<String> 名称标目) {
        this.名称标目 = 名称标目;
    }

    public List<String> get标目附注() {
        return 标目附注;
    }

    public void set标目附注(List<String> 标目附注) {
        this.标目附注 = 标目附注;
    }

    public String trueOtherNames;

    public String getTrueOtherNames() {
        return trueOtherNames;
    }

    public void setTrueOtherNames(String trueOtherNames) {
        this.trueOtherNames = trueOtherNames;
    }
}
