package bookstore.controller;

import bookstore.dao.BookRepository;
import bookstore.domain.Book;
import bookstore.domain.Category;
import bookstore.service.AdminService;
import bookstore.service.BookService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@Controller
@RequestMapping(value = "/web")
public class AdminWebController {
	
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private BookService bookService; //Combine these into 1 webService/webController
    								 //Change views to lower-case and call then views instead of forms?
    
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Date - dd/MM/yyyy
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(LocalDate.class, new CustomDateEditor(
				dateFormat, false));
	}

    //TODO: Admin must see all books and have option to add, remove or edit
    //TODO: Each action will redirect to another view / jsp page
    //TODO: Common headers should be visible for navigation
    
    @GetMapping(value = "/addNewBook")
    public String addNewBookToBookstore(final Model model, final Book book) {
    	model.addAttribute("addNewBook", book);
    	return "addNewBook";
    }
    
    @PostMapping(value = "/addNewBook")
    public String saveNewBookToBookStore(final @Valid @ModelAttribute("book") Book book, BindingResult result) {
		if (result.hasErrors()) {
			return "book";
		}
		try {
			adminService.addNewBookToBookStoreWeb(book);
    	}catch(Exception e) {
    		e.getMessage(); //make this better...
    	}
    	return "redirect:/web/findAllBooks";
    }

    @GetMapping(value = "/deleteBook")
    public String deleteBookFromBookstore(final @RequestParam Long isbn) {
    	adminService.deleteSingleBookFromBookstoreByIsbn(isbn);
    	//finish exception handling 
        return "redirect:/web/findAllBooks";
    }
    
    //deleted book success?
    
	@GetMapping(value = "/updateBook")
	public String updateBook(@RequestParam Long isbn, ModelMap model) {
		Optional<Book> book = bookService.findBookByIsbn(isbn);
		if(!book.isPresent()) {
			log.warn("Book not present: {}", book);
			return "/findAllBooks";
		} else {
			model.put("book", book);
		}
		return "addNewBook";
	}

	@PostMapping(value = "/updateBook")
	public String saveUpdatedBook(ModelMap model, @Valid Book book, BindingResult result) {
		if (result.hasErrors()) {
			return "book";
		}
		adminService.updateBook(book);
		return "redirect:/web/findAllBooks";
	}

}
