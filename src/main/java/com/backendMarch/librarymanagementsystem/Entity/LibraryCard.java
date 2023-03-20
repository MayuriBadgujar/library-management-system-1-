/*package com.backendMarch.librarymanagementsystem.Entity;

public class LibraryCard {
}*/
package com.backendMarch.librarymanagementsystem.Entity;

import com.backendMarch.librarymanagementsystem.Enum.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LibraryCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cardNo;

   // private String validTill;

   @CreationTimestamp
    private Date creationDate;

    @UpdateTimestamp
    private Date updationDate;


    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @OneToOne
    @JoinColumn
    Student student;

    @OneToMany(mappedBy = "card",cascade = CascadeType.ALL)
    List<Transaction> transactionList=new ArrayList<>();

    @OneToMany(mappedBy = "card",cascade =CascadeType.ALL)
    List<Book> booksIssued=new ArrayList<>();

}
