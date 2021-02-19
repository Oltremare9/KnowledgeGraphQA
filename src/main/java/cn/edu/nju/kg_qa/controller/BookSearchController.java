package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.common.CommonResult;
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
    public CommonResult<List<BookNode>> findBookByAuthorName(@PathVariable String authorName) {
        List<BookNode> list = bookService.findBooksByAuthorName(authorName);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "查询所有图书")
    @GetMapping("/findAllBooks")
    public CommonResult<List<BookNode>> findAllBooks() {
        List<BookNode> list = bookService.findAllBooks();
        return CommonResult.success(list);
    }

    @ApiOperation(value = "书名 模糊 查询图书")
    @GetMapping(value = {"/findBooksByBookName/{bookName}", "/findBooksByBookName"})
    public CommonResult<List<BookNode>> findBooksByBookName(@PathVariable(required = false) String bookName) {
        List<BookNode> list = bookService.findBooksByBookName(bookName);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "根据ISBN 精准查询图书")
    @GetMapping("/findBookByISBN/{isbn}")
    public CommonResult<List<BookNode>> findBookByISBN(@PathVariable String isbn) {
        List<BookNode> list = bookService.findBookByISBN(isbn);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "根据概念名 精准查询图书")
    @GetMapping("/findBooksByConceptName/{conceptName}")
    public CommonResult<List<BookNode>> findBooksByConceptName(@PathVariable String conceptName) {
        List<BookNode> list = bookService.findBooksByConceptName(conceptName);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "根据机构名 精准查询图书")
    @GetMapping("/findBooksByInstituteName/{instituteName}")
    public CommonResult<List<BookNode>> findBooksByInstituteName(@PathVariable String instituteName) {
        List<BookNode> list = bookService.findBooksByInstituteName(instituteName);
        return CommonResult.success(list);
    }
}
