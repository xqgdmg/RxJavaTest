package dmg.xqg.com.rxjavatest.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import dmg.xqg.com.rxjavatest.R;
import dmg.xqg.com.rxjavatest.bean.Course;
import dmg.xqg.com.rxjavatest.bean.Student;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * flatMap() 的使用
 */
public class FlatMapActivity extends AppCompatActivity {

    private Student[] students;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_map);

        initView();
        initData();
        showStudentMap();
//        showStudentFlatMap();

    }

    /**
     * 使用 map()，需要遍历
     */
    private void showStudentMap() {
        Subscriber<Student> subscriber = new Subscriber<Student>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(Student student) {
                Course[] courses = student.getCourses();
                for (int i = 0; i < courses.length; i++) {
                    Course course = courses[i];
                    Log.e("chris", course.getName());
                    tv.setText(course.getName());
                }
            }
        };

        Observable
                .from(students)
                .subscribe(subscriber);
    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
    }

    private void initData() {
        students = new Student[]{
                new Student(new Course[]{
                        new Course("math01"),
                        new Course("english01"),
                        new Course("chinese01")
                }),
                new Student(new Course[]{
                        new Course("math02"),
                        new Course("english02"),
                        new Course("chinese02")
                }),
                new Student(new Course[]{
                        new Course("math03"),
                        new Course("english03"),
                        new Course("chinese03")
                })
        };
    }

    /**
     * 使用 flatMap()，不需要遍历
     */
    private void showStudentFlatMap() {

        // 观察者，获取到的不是 Student，而是 Course
        Subscriber<Course> subscriber = new Subscriber<Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Course course) {
                Log.e("chris", course.getName());
                tv.setText(course.getName());
            }
        };

        // 被观察者
        Observable
                .from(students)
                // 复杂变换，一对多
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        // 再次调用了 Observable.from(); 返回 Observable<Course>
                        return Observable.from(student.getCourses());
                    }
                })
                // 订阅，被观察者和观察者之间形成关系
                .subscribe(subscriber);

    }
}
