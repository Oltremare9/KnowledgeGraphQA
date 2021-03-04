package cn.edu.nju.kg_qa.domain.response;

import cn.edu.nju.kg_qa.domain.base.Base;
import cn.edu.nju.kg_qa.domain.base.BaseNode;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/3/5 1:15<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class OneHopResponse {

    List<BaseNode> base;

    String valueType;

    public List<BaseNode> getBase() {
        return base;
    }

    public void setBase(List<BaseNode> base) {
        this.base = base;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }
}
