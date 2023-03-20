
package com.backendMarch.librarymanagementsystem.Service;


import com.backendMarch.librarymanagementsystem.DTO.StudentRequestDto;
import com.backendMarch.librarymanagementsystem.DTO.StudentResponseDto;
import com.backendMarch.librarymanagementsystem.DTO.StudentUpdateEmailRequestDto;
import com.backendMarch.librarymanagementsystem.Entity.LibraryCard;
import com.backendMarch.librarymanagementsystem.Entity.Student;
import com.backendMarch.librarymanagementsystem.Enum.CardStatus;
import com.backendMarch.librarymanagementsystem.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

  /* public void addStudent(Student student){

        // set the value of card
        LibraryCard card = new LibraryCard();
        card.setStatus(CardStatus.ACTIVATED);
        card.setValidTill("03/2025");
        card.setStudent(student);

        // set the card attroubte in student
        student.setCard(card);

        studentRepository.save(student);
    }

   */
  public void addStudent(StudentRequestDto studentRequestDto){

      // set the value of card
      //create a student object
      Student student = new Student();
      student.setAge(studentRequestDto.getAge());
      student.setName(studentRequestDto.getName());
      student.setEmail(studentRequestDto.getEmail());
      student.setDepartment(studentRequestDto.getDepartment());

      //create a card object
      LibraryCard card = new LibraryCard();
      card.setStatus(CardStatus.ACTIVATED);
     // card.setValidTill("03/2025");
      card.setStudent(student);

      // set the card attroubte in student

      //set card in student
      student.setCard(card);

      //will save both student and card
      studentRepository.save(student);
  }

    public String findByEmail(String email){
        Student student=studentRepository.findByEmail(email);
        return student.getName();
    }
    public StudentResponseDto updateEmail(StudentUpdateEmailRequestDto studentUpdateEmailRequestDto){
        Student student = studentRepository.findById(studentUpdateEmailRequestDto.getId()).get();
        student.setEmail(studentUpdateEmailRequestDto.getEmail());
        //update step
        Student updatedStudent = studentRepository.save(student);
        //convert updated student to response Dto
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(updatedStudent.getId());
        studentResponseDto.setName(updatedStudent.getName());
        studentResponseDto.setEmail(updatedStudent.getEmail());
        return studentResponseDto;
    }


}
