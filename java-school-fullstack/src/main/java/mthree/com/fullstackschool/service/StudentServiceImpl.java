package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.StudentDao;
import mthree.com.fullstackschool.model.Course;
import mthree.com.fullstackschool.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentServiceInterface {

    //YOUR CODE STARTS HERE
    StudentDao studentDao;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao)
    {
        this.studentDao = studentDao;
    }
    //YOUR CODE ENDS HERE

    public List<Student> getAllStudents() {
        //YOUR CODE STARTS HERE

        return this.studentDao.getAllStudents();

        //YOUR CODE ENDS HERE
    }

    public Student getStudentById(int id) {
        //YOUR CODE STARTS HERE

        Student student = new Student();
        try
        {
            student = this.studentDao.findStudentById(id);
        }
        catch (DataAccessException e)
        {
            student.setStudentFirstName("Student Not Found");
            student.setStudentLastName("Student Not Found");
        }
        return student;

        //YOUR CODE ENDS HERE
    }

    public Student addNewStudent(Student student) {
        //YOUR CODE STARTS HERE

        boolean fieldIsEmpty = false;
        if (student.getStudentFirstName().isEmpty())
        {
            fieldIsEmpty = true;
            student.setStudentFirstName("First Name blank, student NOT added");
        }

        if (student.getStudentLastName().isEmpty())
        {
            fieldIsEmpty = true;
            student.setStudentLastName("Last Name blank, student NOT added");
        }
        return fieldIsEmpty ? student : this.studentDao.createNewStudent(student);

        //YOUR CODE ENDS HERE
    }

    public Student updateStudentData(int id, Student student) {
        //YOUR CODE STARTS HERE

        /*
        Student studentToUpdate = this.studentDao.findStudentById(id);
        if (studentToUpdate == null)
        {
            return null;
        }
         */

        if (id != student.getStudentId())
        {
            student.setStudentFirstName("IDs do not match, student not updated");
            student.setStudentLastName("IDs do not match, student not updated");
            return student;
        }
        this.studentDao.updateStudent(student);
        return this.studentDao.findStudentById(id);

        //YOUR CODE ENDS HERE
    }

    public void deleteStudentById(int id) {
        //YOUR CODE STARTS HERE

        this.studentDao.deleteStudent(id);

        //YOUR CODE ENDS HERE
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE

        Student student = studentDao.findStudentById(studentId);
        if (student.getStudentFirstName().equals("Student Not Found"))
        {
            System.out.println("Student Not Found");
        }
        else
        {
            this.studentDao.addStudentToCourse(studentId, courseId);
            System.out.println("Student:" + studentId + " deleted from course:" + courseId);
        }

        //YOUR CODE ENDS HERE
    }

    public void addStudentToCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE

        Student student = studentDao.findStudentById(studentId);
        if (student.getStudentFirstName().equals("Student Not Found"))
        {
            System.out.println("Student Not Found");
        }
        else
        {
            try
            {
                this.studentDao.deleteStudentFromCourse(studentId, courseId);
                System.out.println("Student:" + studentId + " added to course:" + courseId);
            }
            catch(Exception e)
            {
                System.out.println("Student:" + studentId + " already enrolled on course:" + courseId);
            }
        }

        //YOUR CODE ENDS HERE
    }
}
