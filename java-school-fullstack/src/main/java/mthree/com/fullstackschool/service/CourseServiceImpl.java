package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.CourseDao;
import mthree.com.fullstackschool.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseServiceInterface {

    //YOUR CODE STARTS HERE
    CourseDao courseDao;

    @Autowired
    public CourseServiceImpl(CourseDao courseDao)
    {
        this.courseDao = courseDao;
    }
    //YOUR CODE ENDS HERE

    public List<Course> getAllCourses() {
        //YOUR CODE STARTS HERE

        return this.courseDao.getAllCourses();

        //YOUR CODE ENDS HERE
    }

    public Course getCourseById(int id) {
        //YOUR CODE STARTS HERE

        Course course = new Course();
        try
        {
            course = this.courseDao.findCourseById(id);
        }
        catch (DataAccessException e)
        {
            course.setCourseName("Course Not Found");
            course.setCourseDesc("Course Not Found");
        }
        return course;

        //YOUR CODE ENDS HERE
    }

    public Course addNewCourse(Course course) {
        //YOUR CODE STARTS HERE

        boolean fieldIsEmpty = false;
        if (course.getCourseName().isEmpty())
        {
            fieldIsEmpty = true;
            course.setCourseName("Name blank, course NOT added");
        }

        if (course.getCourseDesc().isEmpty())
        {
            fieldIsEmpty = true;
            course.setCourseDesc("Description blank, course NOT added");
        }
        return fieldIsEmpty ? course : this.courseDao.createNewCourse(course);

        //YOUR CODE ENDS HERE
    }

    public Course updateCourseData(int id, Course course) {
        //YOUR CODE STARTS HERE

        /*
        Course courseToUpdate = this.courseDao.findCourseById(id);
        if(courseToUpdate == null)
        {
            return null;
        }*/
        if (id != course.getCourseId())
        {
            course.setCourseName("IDs do not match, course not updated");
            course.setCourseDesc("IDs do not match, course not updated");
            return course;
        }
        this.courseDao.updateCourse(course);
        return this.courseDao.findCourseById(id);

        //YOUR CODE ENDS HERE
    }

    public void deleteCourseById(int id) {
        //YOUR CODE STARTS HERE

        this.courseDao.deleteCourse(id);
        System.out.println("Course ID:" + id + " deleted");

        //YOUR CODE ENDS HERE
    }
}
