package bookstore.controller;

import bookstore.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/web")
public class AdminWebController {
	
    @Autowired
    private AdminService adminService;

    //TODO: Admin must see all books and have option to add, remove or edit
    //TODO: Each action will redirect to another view / jsp page
    //TODO: Common headers should be visible for navigation
    
    @GetMapping(value = "/addNewBook")
    public ModelAndView addNewBookToBookstore() {
        return new ModelAndView("showAllBooks", "books",
                adminService.addNewBookToBookStore(0, null, null, null, null));
    }

    @DeleteMapping
    public ModelAndView deleteBookFromBookstore() {
        return new ModelAndView();
    }

}
