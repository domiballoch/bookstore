package bookstore.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bookstore.service.BookService;

@Controller
@RequestMapping(value = "/web")
public class BookWebController {

    @Autowired
    private BookService bookService;
    
    
    @GetMapping(value = "/findAllBooks")
    public ModelAndView findAllBooks() {
//		String name = getLoggedInUserName(model);
//		model.put("todos", repository.findByUser(name));
    	// if user instanceOf user then hide admin buttons
        return new ModelAndView("showAllBooks", "books", bookService.findAllBooks());

    }
    
    
    //findBookBySearch - first 3 letters
    //searchByCategory, author
    

    @GetMapping(value = "/hello-html")
    public String helloHtml() {
        return "hello.html";
    }

    @GetMapping(value = "/hello-jsp")
    public String helloJsp() {
        return "hello";
    }
}
