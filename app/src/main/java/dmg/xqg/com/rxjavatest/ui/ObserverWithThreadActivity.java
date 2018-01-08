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
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ObserverWithThreadActivity extends AppCompatActivity {

    private TextView tvLogStringArray;
    private ImageView ivShowPicById;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer_with_thread);

        initView();
        initListener();

        logStringArray();
        showPicById();
    }

    /**
     * 加载图片将会发生在 IO 线程，而设置图片则被设定在了主线程
     */
    private void showPicById() {
        final int drawableRes = R.drawable.b;
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onNext(Drawable drawable) {
                        ivShowPicById.setImageDrawable(drawable);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ObserverWithThreadActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void logStringArray() {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer number) {
                        Log.e("chris", "number:" + number);
                        tvLogStringArray.setText(number + "");
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
