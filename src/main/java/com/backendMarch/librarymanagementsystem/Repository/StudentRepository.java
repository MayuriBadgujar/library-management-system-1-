/*package com.backendMarch.librarymanagementsystem.Repository;

public interface StudentRepository {
}*/
package com.backendMarch.librarymanagementsystem.Repository;

import com.backendMarch.librarymanagementsystem.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Integer> {
   //findBy(attributename)
    //student search by email
    Student findByEmail(String email);

    //student search by age
    List<Student> findByAge(int age);

}
