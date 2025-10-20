package ru.sergeev.DatabaseCourseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sergeev.DatabaseCourseProject.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByPublicationYearGreaterThan(int year);

    @Query("SELECT b FROM Book b WHERE b.author.name = :authorName AND LOWER(b.title) LIKE LOWER(CONCAT('%', :titleKeyword, '%'))")
    List<Book> findBooksByAuthorNameAndTitleKeyword(
            @Param("authorName") String authorName,
            @Param("titleKeyword") String titleKeyword
    );
}
