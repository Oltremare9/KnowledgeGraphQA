package cn.edu.nju.kg_qa.service;

import cn.edu.nju.kg_qa.domain.relation.WriteRelation;
import cn.edu.nju.kg_qa.repository.WriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: <br/>
 * date: 2020/12/24 23:23<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class WriteService {
    @Autowired
    WriteRepository writeRepository;

    /**
     * 根据作者名查询关系路径节点
     *
     * @param authorName
     * @return
     */
    public List<WriteRelation> findWriteRelationByAuthorName(String authorName){
        return writeRepository.findWriteRelationByAuthorName(authorName);
    }

    /**
     * 根据书籍名查询关系路径节点
     *
     * @param bookName
     * @return
     */
    public List<WriteRelation> findWriteRelationByBookName(String bookName){
        return writeRepository.findWriteRelationByBookName(bookName);
    }
}
