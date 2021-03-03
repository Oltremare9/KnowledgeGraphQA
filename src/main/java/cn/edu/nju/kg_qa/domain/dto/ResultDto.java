package cn.edu.nju.kg_qa.domain.dto;

import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/3/4 1:10<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@QueryResult
public class ResultDto {


    /**
     * keys : ["CASE\n  WHEN (n1)-[*1..3]-(n2)\n    THEN \"1\"\n  ELSE \"0\"\nEND"]
     * length : 1
     * _fields : ["1"]
     * _fieldLookup : {"CASE\n  WHEN (n1)-[*1..3]-(n2)\n    THEN \"1\"\n  ELSE \"0\"\nEND":0}
     */

    private int length;
    private List<String> keys;
    private List<String> _fields;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<String> get_fields() {
        return _fields;
    }

    public void set_fields(List<String> _fields) {
        this._fields = _fields;
    }

}
