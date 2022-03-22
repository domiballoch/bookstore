package bookstore.dao;

import bookstore.domain.Book;
import bookstore.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByCategory(final Category category);

    @Query(value = "SELECT * FROM book b WHERE b.title LIKE CONCAT('%', :search, '%')", nativeQuery=true)
    List<Book> findBookBySearchTermIgnoreCase(@Param("search") final String search);

    @Query(value = "SELECT stock FROM book b WHERE b.isbn = :isbn", nativeQuery = true)
    int getBookStock(@Param("isbn") final long isbn);

    @Modifying
    @Query(value = "UPDATE Book b SET b.stock = stock", nativeQuery = true)
    void updateBookStock(final int stock);
}
