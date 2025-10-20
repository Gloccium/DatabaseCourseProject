package ru.sergeev.DatabaseCourseProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private int publicationYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    // Связь "Многие к одному": Много книг -> один жанр
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    public Book(String title, int publicationYear, Author author, Genre genre) {
        this.title = title;
        this.publicationYear = publicationYear;
        this.author = author;
        this.genre = genre;
    }
}
