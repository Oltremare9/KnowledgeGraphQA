package cn.edu.nju.kg_qa.domain.dto;

import cn.edu.nju.kg_qa.domain.base.BaseNode;

/**
 * Description: <br/>
 * date: 2021/3/13 20:53<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class EcharsNodesDto {

    private String id;

    private String name;

    private double x;

    private double y;

    private String value;

    private Integer category;

    double symbolSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public double getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(double symbolSize) {
        this.symbolSize = symbolSize;
    }

    public EcharsNodesDto(){
        x=Math.random()*1000-500;
        y=Math.random()*1000-500;
        symbolSize=30;
    }
}
