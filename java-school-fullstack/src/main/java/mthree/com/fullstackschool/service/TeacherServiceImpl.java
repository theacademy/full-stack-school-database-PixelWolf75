package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.TeacherDao;
import mthree.com.fullstackschool.model.Student;
import mthree.com.fullstackschool.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherServiceInterface {

    //YOUR CODE STARTS HERE
    TeacherDao teacherDao;

    @Autowired
    public TeacherServiceImpl(TeacherDao teacherDao)
    {
        this.teacherDao = teacherDao;
    }
    //YOUR CODE ENDS HERE

    public List<Teacher> getAllTeachers() {
        //YOUR CODE STARTS HERE

        return this.teacherDao.getAllTeachers();

        //YOUR CODE ENDS HERE
    }

    public Teacher getTeacherById(int id) {
        //YOUR CODE STARTS HERE

        Teacher teacher = new Teacher();
        try
        {
            teacher = this.teacherDao.findTeacherById(id);
        }
        catch (DataAccessException e)
        {
            teacher.setTeacherFName("Teacher Not Found");
            teacher.setTeacherLName("Teacher Not Found");
        }
        return teacher;

        //YOUR CODE ENDS HERE
    }

    public Teacher addNewTeacher(Teacher teacher) {
        //YOUR CODE STARTS HERE

        boolean fieldIsEmpty = false;
        if (teacher.getTeacherFName().isEmpty())
        {
            fieldIsEmpty = true;
            teacher.setTeacherFName("First Name blank, teacher NOT added");
        }

        if (teacher.getTeacherLName().isEmpty())
        {
            fieldIsEmpty = true;
            teacher.setTeacherLName("Last Name blank, teacher NOT added");
        }
        return fieldIsEmpty ? teacher : this.teacherDao.createNewTeacher(teacher);

        //YOUR CODE ENDS HERE
    }

    public Teacher updateTeacherData(int id, Teacher teacher) {
        //YOUR CODE STARTS HERE

        /*
        Teacher teacherToUpdate = this.teacherDao.findTeacherById(id);
        //No teacher found
        if (teacherToUpdate == null)
        {
            return null;
        }
         */
        if (id != teacher.getTeacherId())
        {
            teacher.setTeacherFName("IDs do not match, teacher not updated");
            teacher.setTeacherLName("IDs do not match, teacher not updated");
            return teacher;
        }
        this.teacherDao.updateTeacher(teacher);
        return this.teacherDao.findTeacherById(id);

        //YOUR CODE ENDS HERE
    }

    public void deleteTeacherById(int id) {
        //YOUR CODE STARTS HERE

        this.teacherDao.deleteTeacher(id);

        //YOUR CODE ENDS HERE
    }
}
