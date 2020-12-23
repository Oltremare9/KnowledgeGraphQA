package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.domain.entity.AuthorNode;
import cn.edu.nju.kg_qa.service.AuthorService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description: <br/>
 * date: 2020/12/23 11:46<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Api(tags = "作者查询接口")
@RestController
@RequestMapping("/authorSearch")
public class AuthorSearchController {
    @Autowired
    AuthorService authorService;

    @ApiOperation(value = "作者名模糊查找作者")
    @GetMapping("/findAuthorByAuthorName/{authorName}")
    public String findAuthorByAuthorName(@PathVariable String authorName){
        List<AuthorNode> list=authorService.findAuthorByAuthorName(authorName);
        return JSON.toJSONString(list);
    }

    @ApiOperation(value = "书籍名模糊查找作者")
    @GetMapping("/findAuthorByBookName/{bookName}")
    public String findAuthorByBookName(@PathVariable String bookName){
        List<AuthorNode> list=authorService.findAuthorByBookName(bookName);
        return JSON.toJSONString(list);
    }

}
