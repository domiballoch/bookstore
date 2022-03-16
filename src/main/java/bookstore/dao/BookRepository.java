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

    //TODO:Not really needed - delete?
    @Query(value = "SELECT * FROM book b WHERE b.title = ':title' AND b.author = ':author'", nativeQuery=true)
    List<Book> findByTitleAndAuthor(@Param("title") final String title, @Param("author") final String author);

    List<Book> findByCategory(final Category category);

    @Query(value = "SELECT * FROM book b WHERE b.title LIKE CONCAT('%', :search, '%')", nativeQuery=true)
    List<Book> findBookBySearchTermIgnoreCase(@Param("search") final String search);

    //TODO:Redo query criteria
    @Query(value = "SELECT COUNT(*) FROM book b WHERE b.title = 'title' AND b.author = 'author'", nativeQuery=true)
    int getBookStock(final String title, final String author);

    @Query(value = "SELECT stock FROM book b WHERE b.isbn = isbn", nativeQuery = true)
    int getBookStockNew(final long isbn);

}
