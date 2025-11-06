package mthree.com.fullstackschool.dao;

import mthree.com.fullstackschool.dao.mappers.CourseMapper;
import mthree.com.fullstackschool.model.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CourseDaoImpl implements CourseDao {

    private final JdbcTemplate jdbcTemplate;

    public CourseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Course createNewCourse(Course course) {
        //YOUR CODE STARTS HERE

        final String SQL = "INSERT INTO course (courseCode, courseDesc, teacherId) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update( conn -> {
            PreparedStatement statement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getCourseDesc());
            statement.setInt(3, course.getTeacherId());
            return statement;
        }, keyHolder);

        Number newId = keyHolder.getKey();
        if (newId != null)
        {
            course.setCourseId(newId.intValue());
        }
        return course;

        //YOUR CODE ENDS HERE
    }

    @Override
    public List<Course> getAllCourses() {
        //YOUR CODE STARTS HERE

        final String SQL = "SELECT * FROM course;";
        return jdbcTemplate.query(SQL, new CourseMapper());

        //YOUR CODE ENDS HERE
    }

    @Override
    public Course findCourseById(int id) {
        //YOUR CODE STARTS HERE

        final String SQL = "SELECT * FROM course WHERE cid = ? ;";
        return jdbcTemplate.queryForObject(SQL, new CourseMapper(), id);

        //YOUR CODE ENDS HERE
    }

    @Override
    public void updateCourse(Course course) {
        //YOUR CODE STARTS HERE

        final String SQL = "UPDATE course SET courseCode = ?, courseDesc = ?, teacherId = ?;";
        jdbcTemplate.update(SQL,
                course.getCourseName(),
                course.getCourseDesc(),
                course.getTeacherId());

        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteCourse(int id) {
        //YOUR CODE STARTS HERE

        final String SQL_DELETE_COURSE_STUDENT = "DELETE FROM course_student "
                + "WHERE student_id IN (SELECT sid FROM student) AND course_id = ?;";
        jdbcTemplate.update(SQL_DELETE_COURSE_STUDENT, id);
        final String SQL_DELETE_COURSE = "DELETE FROM course WHERE cid = ?;";
        jdbcTemplate.update(SQL_DELETE_COURSE, id);

        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteAllStudentsFromCourse(int courseId) {
        //YOUR CODE STARTS HERE

        final String SQL_DELETE_STUDENTS = "DELETE FROM student WHERE sid IN "
                + "(SELECT cs.student_id FROM course_student cs WHERE cs.course_id = ?);";
        jdbcTemplate.update(SQL_DELETE_STUDENTS, courseId);
        final String SQL_DELETE_COURSE_STUDENT = "DELETE FROM course_student "
                + "WHERE course_id IN (SELECT cid FROM course WHERE cid = ?);";
        jdbcTemplate.update(SQL_DELETE_COURSE_STUDENT, courseId);

        //YOUR CODE ENDS HERE
    }
}
