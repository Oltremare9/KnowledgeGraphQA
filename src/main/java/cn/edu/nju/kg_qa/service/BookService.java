package cn.edu.nju.kg_qa.service;

import cn.edu.nju.kg_qa.domain.entity.BookNode;
import cn.edu.nju.kg_qa.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description: <br/>
 * date: 2020/12/22 20:25<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    /**
     * 作者名模糊查询书
     * @param authorName
     * @return
     */
    public List<BookNode> findBooksByAuthorName(String authorName){
        return bookRepository.findBooksByAuthorName(authorName);
    }

    /**
     * 查询所有图书
     * @return
     */
    public List<BookNode> findAllBooks(){
        Iterable<BookNode> iterator=bookRepository.findAll();
        List<BookNode> list=new ArrayList<>();
        for(BookNode bookNode:iterator){
            list.add(bookNode);
        }
        return list;
    }

    public List<BookNode> findBooksByName(String bookName){
        return bookRepository.findAllByNameContains(bookName);
    }
}
