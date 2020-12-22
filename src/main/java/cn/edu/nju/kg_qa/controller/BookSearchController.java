package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.domain.entity.BookNode;
import cn.edu.nju.kg_qa.service.BookService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description: 简单查询控制类<br/>
 * date: 2020/12/22 13:47<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Api(tags = "图书搜索结果接口")
@RestController
@RequestMapping("/bookSearch")
public class BookSearchController {
    @Autowired
    BookService bookService;

    @ApiOperation(value = "作者名模糊 查询 图书")
    @GetMapping("/findBooksByAuthorName/{authorName}")
    public String findBookByAuthorName(@PathVariable String authorName){
        List<BookNode> list=bookService.findBooksByAuthorName(authorName);
        String data=JSON.toJSONString(list);
        return data;
    }

    @ApiOperation(value = "查询所有图书")
    @GetMapping("/findAllBooks")
    public String findAllBooks(){
        List<BookNode> list=bookService.findAllBooks();
        return JSON.toJSONString(list);
    }

    @ApiOperation(value = "书名 模糊 查询图书")
    @GetMapping("/findBooksByBookName/{bookName}")
    public String findBooksByBookName(@PathVariable String bookName){
        List<BookNode> list=bookService.findBooksByBookName(bookName);
        return JSON.toJSONString(list);
    }

    @ApiOperation(value = "根据ISBN 精准查询图书")
    @GetMapping("/findBookByISBN/{isbn}")
    public String findBookByISBN(@PathVariable String isbn){
        List<BookNode> list=bookService.findBookByISBN(isbn);
        return JSON.toJSONString(list);
    }

    @ApiOperation(value = "根据概念名 精准查询图书")
    @GetMapping("/findBooksByConceptName/{conceptName}")
    public String findBooksByConceptName(@PathVariable String conceptName){
        List<BookNode> list=bookService.findBooksByConceptName(conceptName);
        return JSON.toJSONString(list);
    }
}
