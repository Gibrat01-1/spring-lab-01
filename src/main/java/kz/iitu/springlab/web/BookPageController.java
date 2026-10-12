package kz.iitu.springlab.web;

import jakarta.validation.Valid;
import kz.iitu.springlab.model.Book;
import kz.iitu.springlab.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookPageController {

    private static final List<String> ALL_TAGS =
            List.of("classic", "bestseller", "textbook", "new");

    private final BookService service;

    public BookPageController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", service.findAll());
        return "books/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("form", new BookForm());
        fillReferenceData(model);
        return "books/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") BookForm form,
                         BindingResult binding,          // immediately after the form!
                         Model model,
                         RedirectAttributes redirect) {

        if (binding.hasErrors()) {
            fillReferenceData(model);                    // the model again
            return "books/form";
        }

        Book saved = service.create(new Book(null, form.getTitle(),
                form.getAuthor(), form.getYear()));

        redirect.addFlashAttribute("message",
                "Book «" + saved.title() + "» has been added");
        return "redirect:/books";
    }

    // Used for task 4.4: GET /books/999 must end in BookNotFoundException
    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("book", service.findById(id));
        return "books/detail";
    }

    private void fillReferenceData(Model model) {
        model.addAttribute("genres", Genre.values());
        model.addAttribute("allTags", ALL_TAGS);
    }
}
