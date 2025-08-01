package com.example.library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(name = "usersIdSeq", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersIdSeq")
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL)
    private List<Book> borrowedBooks = new ArrayList<>();


    public void addBook(Book book) {
        borrowedBooks.add(book);
        book.setBorrower(this);
    }

    public void removeBook(Book book) {
        borrowedBooks.remove(book);
        book.setBorrower(null);
    }
}
