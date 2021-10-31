package bookstore.controller.web;

import bookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
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
        return new ModelAndView("show-all-books", "books", bookService.findAllBooks());

    }

    @GetMapping(value = "/findAllBooksHtml")
    public String findAllBooksHtml(Model model) {
        log.info("in controller");
//		String name = getLoggedInUserName(model);
//		model.put("todos", repository.findByUser(name));
        // if user instanceOf user then hide admin buttons
//        ModelAndView mv = null;
//        try {
//            log.info("in try");
//            mv = new ModelAndView("show-all-books-th", "books", bookService.findAllBooks());
//        } catch(Exception e) {
//            log.info("Can't show page", e.getMessage());
//        }
        model.addAttribute("books", bookService.findAllBooks());
        return "show-all-books-th";
    }

    //findBookBySearch - first 3 letters
    //searchByCategory, author

    @GetMapping(value = "/hello-html")
    public String helloHtml() {
        return "hello";
    }

    @GetMapping(value = "/hello-jsp")
    public String helloJsp() {
        return "hello";
    }
}