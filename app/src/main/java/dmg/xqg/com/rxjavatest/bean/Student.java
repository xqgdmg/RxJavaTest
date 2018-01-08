package dmg.xqg.com.rxjavatest.bean;

/**
 * Created by qhsj on 2018/1/8.
 */

public class Student {

    Course[] course;

    public Student(Course[] course) {
        this.course = course;
    }

    public Course[] getCourses() {
        return course;
    }
}
