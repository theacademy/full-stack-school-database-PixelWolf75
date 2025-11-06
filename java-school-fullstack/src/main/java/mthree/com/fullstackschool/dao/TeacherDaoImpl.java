package mthree.com.fullstackschool.dao;

import mthree.com.fullstackschool.dao.mappers.StudentMapper;
import mthree.com.fullstackschool.dao.mappers.TeacherMapper;
import mthree.com.fullstackschool.model.Teacher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TeacherDaoImpl implements TeacherDao {

    private final JdbcTemplate jdbcTemplate;

    public TeacherDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Teacher createNewTeacher(Teacher teacher) {
        //YOUR CODE STARTS HERE

        final String SQL = "INSERT INTO teacher (tFName, tLName, dept) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update( conn -> {
            PreparedStatement statement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, teacher.getTeacherFName());
            statement.setString(2, teacher.getTeacherLName());
            statement.setString(3, teacher.getDept());
            return statement;
        }, keyHolder);

        Number newId = keyHolder.getKey();
        if (newId != null)
        {
            teacher.setTeacherId(newId.intValue());
        }
        return teacher;

        //YOUR CODE ENDS HERE
    }

    @Override
    public List<Teacher> getAllTeachers() {
        //YOUR CODE STARTS HERE

        final String SQL = "SELECT * FROM teacher;";
        return jdbcTemplate.query(SQL, new TeacherMapper());

        //YOUR CODE ENDS HERE
    }

    @Override
    public Teacher findTeacherById(int id) {
        //YOUR CODE STARTS HERE

        final String SQL = "SELECT * FROM teacher WHERE tid = ? ;";
        return jdbcTemplate.queryForObject(SQL, new TeacherMapper(), id);

        //YOUR CODE ENDS HERE
    }

    @Override
    public void updateTeacher(Teacher t) {
        //YOUR CODE STARTS HERE

        final String SQL = "UPDATE teacher SET tFName = ?, tLName = ?, dept = ?;";
        jdbcTemplate.update(SQL,
                t.getTeacherFName(),
                t.getTeacherLName(),
                t.getDept());

        //YOUR CODE ENDS HERE
    }

    @Override
    public void deleteTeacher(int id) {
        //YOUR CODE STARTS HERE

        final String SQL_DELETE_COURSE = "DELETE FROM course "
                + "WHERE teacherId IN (SELECT tid FROM teacher WHERE tid = ?);";
        jdbcTemplate.update(SQL_DELETE_COURSE, id);
        final String SQL_DELETE_TEACHER = "DELETE FROM teacher WHERE tid = ?;";
        jdbcTemplate.update(SQL_DELETE_TEACHER, id);

        //YOUR CODE ENDS HERE
    }
}
