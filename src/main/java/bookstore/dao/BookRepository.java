package bookstore.dao;

import bookstore.domain.Book;
import bookstore.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM book b WHERE b.title = 'title' AND b.author = 'author'", nativeQuery=true)
    public List<Book> findByTitleAndAuthor(final String title, final String author);

    @Query(value = "SELECT * FROM book b WHERE b.category = ':category'", nativeQuery=true)
    public List<Book> findBooksByCategory(@Param("category") final Category category);

    @Query(value = "SELECT * FROM book b WHERE b.title LIKE CONCAT('%', :search, '%')", nativeQuery=true)
    public List<Book> findBookBySearchTermIgnoreCase(@Param("search") final String search);
    //'(\"[a-zA-Z]{3}\")%'"
    //LIKE SUBSTR('search%', 1, 4)"

    @Query(value = "SELECT COUNT(*) FROM book b WHERE b.title = 'title' AND b.author = 'author'", nativeQuery=true)
    public int getBookStock(final String title, final String author);

}
