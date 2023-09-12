package com.example.reactive_webflux.service;

import com.example.reactive_webflux.domain.Book;
import com.example.reactive_webflux.domain.Review;
import com.example.reactive_webflux.exception.BookException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class BookService {
    private BookInfoService bookInfoService;
    private ReviewService reviewService;

    public BookService(BookInfoService bookInfoService, ReviewService reviewService) {
        this.bookInfoService = bookInfoService;
        this.reviewService = reviewService;
    }

    public Flux<Book> getBooks() {
        var allBooks = bookInfoService.getBooks();
        return allBooks
                .flatMap(bookInfo -> {
                    Mono<List<Review>> reviews =
                            reviewService.getReviews(bookInfo.getBookId()).collectList();
                    return reviews
                            .map(review -> new Book(bookInfo,review));
                }).onErrorMap(throwable -> {
                    log.error("Exception is :" + throwable);
                    return new BookException("Exception occurred while fetching Books");
                }).log();
    }
    public Mono<Book> getBookById(long bookId) {
        var book = bookInfoService.getBookById(bookId);
        var review = reviewService
                .getReviews(bookId)
                .collectList();

        return  book
                .zipWith(review,(b,r) -> new Book(b,r));

    }
}
