package com.backendMarch.librarymanagementsystem.Service;

import com.backendMarch.librarymanagementsystem.DTO.BookRequestDto;
import com.backendMarch.librarymanagementsystem.DTO.BookResponseDto;
import com.backendMarch.librarymanagementsystem.Entity.Author;
import com.backendMarch.librarymanagementsystem.Entity.Book;
import com.backendMarch.librarymanagementsystem.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    AuthorRepository authorRepository;

   /* public String addBook(Book book) throws Exception {

        Author author;
        try{
            author = authorRepository.findById(book.getAuthor().getId()).get();
        }
        catch (Exception e){
            return "Book not added";
        }

        List<Book> booksWritten = author.getBooks();
        booksWritten.add(book);

        authorRepository.save(author);
        return "Book added";
    }

    */
   public BookResponseDto addBook(BookRequestDto bookRequestDto)throws Exception {
       //get the author object
       Author author = authorRepository.findById(bookRequestDto.getAuthorId()).get();
       Book book = new Book();
       book.setTitle(bookRequestDto.getTitle());
       book.setGenre(bookRequestDto.getGenre());
       book.setPrice(bookRequestDto.getPrice());
       book.setIssued(false);
       book.setAuthor(author);


       author.getBooks().add(book);
       authorRepository.save(author);  //will save both author and book
       //create a response else
       BookResponseDto bookResponseDto = new BookResponseDto();

       bookResponseDto.setTitle(book.getTitle());
       bookResponseDto.setPrice(book.getPrice());
       return bookResponseDto;
   }
}





