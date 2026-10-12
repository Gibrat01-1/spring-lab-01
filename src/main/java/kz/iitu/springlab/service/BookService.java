package kz.iitu.springlab.service;

import kz.iitu.springlab.model.Book;
import kz.iitu.springlab.web.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {

    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public BookService() {
        create(new Book(null, "Effective Java", "Joshua Bloch", 2018));
        create(new Book(null, "Clean Code", "Robert Martin", 2008));
    }

    public List<Book> findAll() {
        return books.values().stream()
                .sorted(Comparator.comparing(Book::id))
                .toList();
    }

    public Book findById(Long id) {
        Book book = books.get(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        return book;
    }

    public Book create(Book book) {
        long id = sequence.incrementAndGet();
        Book saved = new Book(id, book.title(), book.author(), book.year());
        books.put(id, saved);
        return saved;
    }
}
