package tech.artcoded.boost.book.service;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import tech.artcoded.boost.book.dto.BookDto;
import tech.artcoded.boost.book.entity.Book;
import tech.artcoded.boost.book.repository.BookRepository;
import tech.artcoded.boost.common.service.CrudService;
import tech.artcoded.boost.upload.entity.Upload;
import tech.artcoded.boost.upload.service.UploadService;
import tech.artcoded.boost.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface BookService extends CrudService<Long, Book> {

    UploadService getUploadService();
    BookRepository getRepository();

    default List<Book> findTop3OrOrderByCreatedDateDesc(){
        return getRepository().findTop3ByOrderByCreatedDateDesc();
    }

    default Optional<Book> findByIdAndTitle(Long id, String title) {
        return getRepository().findByIdAndTitle(id,title);
    }

    @Cacheable(cacheNames = "book",key = "#title")
    default Page<Book> findByTitleLike(String title) {
        System.out.println("from method");
        Pageable pr = PageRequest.of(0, 5);
        return getRepository().findByTitleContainingIgnoreCase(StringUtils.trimToEmpty(title),pr);
    }

    @SneakyThrows
    @CacheEvict(cacheNames = "book")
    default Book saveBookWithCover(BookDto book, User user) {
        produceEvent("_SAVE_WITH_COVER", "Title: " + book.getTitle() + ", ContentType: " + book.getContentType());
        Optional<Book> optionalBook = Optional.ofNullable(book.getId()).flatMap(this::findById);

        final Book.BookBuilder bookE = optionalBook.
                map(Book::toBuilder)
                .orElseGet(Book::builder)
                .id(optionalBook.map(Book::getId).orElse(null));
        Optional.ofNullable(book.getCover()).ifPresent(c -> {
            try {
                if (c.length > 0) {
                    Upload upload = getUploadService().upload(c, book.getContentType(), book.getFileName());
                    bookE.cover(upload);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return getRepository().save(
                bookE.author(book.getAuthor())
                        .title(book.getTitle())
                        .user(user)
                        .category(book.getCategory())
                        .description(book.getDescription())
                        .build()
        );

    }
}
