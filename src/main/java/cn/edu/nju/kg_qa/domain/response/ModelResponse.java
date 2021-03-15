package cn.edu.nju.kg_qa.domain.response;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/3/5 0:05<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class ModelResponse {
    List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
