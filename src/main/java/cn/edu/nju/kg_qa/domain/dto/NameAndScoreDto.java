package cn.edu.nju.kg_qa.domain.dto;

/**
 * Description: <br/>
 * date: 2021/3/16 1:27<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public class NameAndScoreDto {
    String name;

    Double score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
