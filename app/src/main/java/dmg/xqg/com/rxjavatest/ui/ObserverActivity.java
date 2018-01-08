package dmg.xqg.com.rxjavatest.ui;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import dmg.xqg.com.rxjavatest.R;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * 在 RxJava 的默认规则中，事件的发出和消费都是在同一个线程的。也就是说，如果只用下面的方法，实现出来的只是一个同步的观察者模式。
 * 一般会使用到 线程控制 —— Scheduler 调度器
 */
public class ObserverActivity extends AppCompatActivity {

    private TextView tvLogStringArray;
    private ImageView ivShowPicById;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);

        initView();
        initListener();

        logStringArray();
        showPicById();
    }

    private void showPicById() {
        final int drawableRes = R.drawable.a;
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(drawableRes);
                subscriber.onNext(drawable);// 发送数据
                subscriber.onCompleted();// 发送完成
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onNext(Drawable drawable) {
                ivShowPicById.setImageDrawable(drawable);// 观察到数据
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(ObserverActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logStringArray() {
        String names[] = new String[]{
                "a",
                "b",
                "c"
        };
        Observable
                // 数据源
                .from(names)
                // 订阅
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String name) {
                        Log.e("chris","name==" + name);
                        tvLogStringArray.setText(name);
                    }
                });
    }

    private void initListener() {

    }

    private void initView() {
        tvLogStringArray = (TextView) findViewById(R.id.tvLogStringArray);
        ivShowPicById = (ImageView) findViewById(R.id.ivShowPicById);
    }
}
