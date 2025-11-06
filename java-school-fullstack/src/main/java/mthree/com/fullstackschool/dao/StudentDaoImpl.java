package mthree.com.fullstackschool.dao;

import mthree.com.fullstackschool.dao.mappers.CourseMapper;
import mthree.com.fullstackschool.dao.mappers.StudentMapper;
import mthree.com.fullstackschool.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.sql.*;
import java.util.List;
import java.util.Objects;

@Repository
public class StudentDaoImpl implements StudentDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Student createNewStudent(Student student) {
        //YOUR CODE STARTS HERE

        final String SQL = "INSERT INTO student (fName, lName) VALUES (?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update( conn -> {
            PreparedStatement statement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, student.getStudentFirstName());
            statement.setString(2, student.getStudentLastName());
            return statement;
        }, keyHolder);

        Number newId = keyHolder.getKey();
        if (newId != null)
        {
            student.setStudentId(newId.intValue());
        }
        return student;

        //YOUR CODE ENDS HERE
    }

    @Override
    public List<Student> getAllStudents() {
        //YOUR CODE STARTS HERE

        final String SQL = "SELECT * FROM student;";
        return jdbcTemplate.query(SQL, new StudentMapper());

        //YOUR CODE ENDS HERE
    }

    @Override
    public Student findStudentById(int id) {
        //YOUR CODE STARTS HERE

        final String SQL = "SELECT * FROM student WHERE sid = ? ;";
        return jdbcTemplate.queryForObject(SQL, new StudentMapper(), id);

        //YOUR CODE ENDS HERE
    }

    @Override
    public void updateStudent(Student student) {
        //YOUR CODE STARTS HERE

        final String SQL = "UPDATE student SET fName = ?, lName = ?;";
        jdbcTemplate.update(SQL,
                student.getStudentFirstName(),
                student.getStudentFirstName());

        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteStudent(int id) {
        //YOUR CODE STARTS HERE

        final String SQL_DELETE_COURSE_STUDENT = "DELETE FROM course_student "
                + "WHERE course_id IN (SELECT cid FROM course) AND student_id = ?;";
        jdbcTemplate.update(SQL_DELETE_COURSE_STUDENT, id);
        final String SQL_DELETE_STUDENT = "DELETE FROM student WHERE sid = ?;";
        jdbcTemplate.update(SQL_DELETE_STUDENT, id);

        //YOUR CODE ENDS HERE
    }

    @Override
    public void addStudentToCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE

        final String SQL = "INSERT INTO course_student (student_id, course_id) VALUES (?, ?);";
        jdbcTemplate.update(SQL, studentId, courseId);

        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteStudentFromCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE

        final String SQL_DELETE_COURSE_STUDENT = "DELETE FROM course_student "
                + "WHERE course_id IN (SELECT cid FROM course WHERE cid = ?) AND student_id = ?;";
        jdbcTemplate.update(SQL_DELETE_COURSE_STUDENT, courseId, studentId);

        //YOUR CODE ENDS HERE
    }
}
