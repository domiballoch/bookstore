package bookstore.controller.web;

import bookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

}
