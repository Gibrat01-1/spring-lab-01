package kz.iitu.springlab.web;

// If lab 5 already has this class, keep yours and delete this file.
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Book with id " + id + " was not found");
    }
}
