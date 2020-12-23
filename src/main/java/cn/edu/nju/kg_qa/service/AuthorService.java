package cn.edu.nju.kg_qa.service;

import cn.edu.nju.kg_qa.domain.entity.AuthorNode;
import cn.edu.nju.kg_qa.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: <br/>
 * date: 2020/12/23 11:41<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    /**
     * 作者名模糊查询作者
     * @param authorName
     * @return
     */
    public List<AuthorNode> findAuthorByAuthorName(String authorName){
        return authorRepository.findAllByNameContains(authorName);
    }

    /**
     * 图书名模糊查询作者
     * @param bookName
     * @return
     */
    public List<AuthorNode> findAuthorByBookName(String bookName){
        return authorRepository.findAuthorByBookName(bookName);
    }
}
