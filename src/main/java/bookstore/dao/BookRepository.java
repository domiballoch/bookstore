package bookstore.dao;

import bookstore.domain.Book;
import bookstore.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM book b WHERE b.title = 'title' AND b.author = 'author'", nativeQuery=true)
    public List<Book> findByTitleAndAuthor(String title, String author);

    @Query(value = "SELECT * FROM book b WHERE b.category = 'category'", nativeQuery = true)
    public List<Book> findBooksByCategory(Category category);

//    @Query<value = "SELECT * FROM book b WHERE
//    public List<Book> findBookBySearchTerm(String search);

    @Query(value = "SELECT COUNT(*) FROM book b WHERE b.title = 'title' AND b.author = 'author'", nativeQuery = true)
    public int getBookStock(String title, String author);

}
