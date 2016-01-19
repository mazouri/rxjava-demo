package com.mazouri.rxjava;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.mazouri.rxjava.model.Course;
import com.mazouri.rxjava.model.Student;

import java.util.ArrayList;
import java.util.Observer;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //1
        observable.subscribe(subscriber);

        //2
        Observable observable = Observable.just("Hello", "Hi", "Aloha");

        //3
        String[] words = {"Hello", "Hi", "Aloha"};
        Observable.from(words);

        //4 打印字符串数组
        String[] strs = {"text 1", "text 2", "text 3", "text 4", "text 5"};
        Observable.from(strs).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, "from : " + s);
            }
        });

        //5 由 id 取得图片并显示
        final int drawableRes = R.mipmap.ic_launcher;
        final ImageView img = (ImageView) findViewById(R.id.img);

        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new rx.Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Drawable drawable) {
                img.setImageDrawable(drawable);
            }
        });

        //6 线程控制，使用Scheduler
        Observable.just(1,2,3,4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "number:" + integer);
                    }
                });

        //7 （重要）变换 map()
        Observable.just("images/logo.png")
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String filePath) {
                        return getBitmapFromPath(filePath);
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        showBitmap(bitmap);
                    }
                });

        //8 (重要) 1对多变换， flatMap()
        ArrayList<Course> coursesZ = new ArrayList<>();
        coursesZ.add(new Course("english"));
        coursesZ.add(new Course("math"));
        coursesZ.add(new Course("Chinese"));

        ArrayList<Course> coursesL = new ArrayList<>();
        coursesL.add(new Course("computer"));
        coursesL.add(new Course("math"));

        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("zhangsan", coursesZ));
        students.add(new Student("lisi", coursesL));

        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCourses());
                    }
                })
                .subscribe(new Subscriber<Course>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Course course) {
                        Log.d(TAG, "course : " + course.getCourseName());
                    }
                });
    }

    private void showBitmap(Bitmap bitmap) {

    }

    private Bitmap getBitmapFromPath(String filePath) {
        return null;
    }

    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            Log.d(TAG, "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.d(TAG, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "Error!");
        }
    };

    Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Aloha");
            subscriber.onCompleted();
        }
    });
}
