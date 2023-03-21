package com.backendMarch.librarymanagementsystem.Service;

import com.backendMarch.librarymanagementsystem.DTO.IssueBookRequestDto;
import com.backendMarch.librarymanagementsystem.DTO.IssueBookResponseDto;
import com.backendMarch.librarymanagementsystem.Entity.Book;
import com.backendMarch.librarymanagementsystem.Entity.LibraryCard;
import com.backendMarch.librarymanagementsystem.Entity.Transaction;
import com.backendMarch.librarymanagementsystem.Enum.CardStatus;
import com.backendMarch.librarymanagementsystem.Enum.TransactionStatus;
import com.backendMarch.librarymanagementsystem.Repository.BookRepository;
import com.backendMarch.librarymanagementsystem.Repository.CardRepository;
import com.backendMarch.librarymanagementsystem.Repository.TransactionRepository;
import com.sun.jdi.event.ExceptionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
;

import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    BookRepository bookRepository;
    public IssueBookResponseDto issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception {

        //create transaction object
        Transaction transaction=new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));
        transaction.setIssuedOperation(true);

        //fist step
        LibraryCard card;
        try{
            card=cardRepository.findById(issueBookRequestDto.getCardId()).get();
        }
        catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED );
           // transactionRepository.save(transaction);
            transaction.setMessage( "Invalid Card Id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid Card Id");
        }

        Book book;
        try{
            book=bookRepository.findById(issueBookRequestDto.getBookId()).get();
        }
        catch(Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage( "Invalid Book Id");
            transactionRepository.save(transaction);
            throw  new Exception("Invalid Book Id");
        }

        //both card and book are valid
        transaction.setBook(book);
        transaction.setCard(card);
        if(card.getStatus()!= CardStatus.ACTIVATED){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage( "Your card is not activated");
            transactionRepository.save(transaction);
            throw new Exception("Your card is not activated");
        }
       if(book.isIssued()==true){
           transaction.setTransactionStatus(TransactionStatus.FAILED);
           transaction.setMessage( "Sorry! book is already issued.");
           transactionRepository.save(transaction);
           throw new Exception("Sorry! book is already issued.");
       }

       //i can issue the book
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setMessage("Transaction was succesfull");
        book.setIssued(true);
       book.setCard(card);
       book.getTransaction().add(transaction);
       card.getTransactionList().add(transaction);
       card.getBooksIssued().add(book);

       //will save book and transaction also
       cardRepository.save(card);

       //prepare response dto
        IssueBookResponseDto issueBookResponseDto=new IssueBookResponseDto();
        issueBookResponseDto.setTransactionId(transaction.getTransactionNumber());
        issueBookResponseDto.setTransactionStatus(TransactionStatus.SUCCESS);
        issueBookResponseDto.setBookName(book.getTitle());

        // send an email
        String text = "Congrats !!." + card.getStudent().getName()+ "You have been issued "+book.getTitle()+" book.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mayuribadgujar1998@gmail.com");
        message.setTo(card.getStudent().getEmail());
        message.setSubject("Issue Book Notification");
        message.setText(text);
        emailSender.send(message);

       return  issueBookResponseDto;

    }
}
