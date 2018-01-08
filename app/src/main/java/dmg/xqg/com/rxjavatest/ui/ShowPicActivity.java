package dmg.xqg.com.rxjavatest.ui;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

import dmg.xqg.com.rxjavatest.R;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ShowPicActivity extends AppCompatActivity {

    private ImageView iv01;
    private ImageView iv02;
    private ImageView iv03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);

        initView();
        initData();
        initListener();
        show();
    }

    private void initData() {
        File[] folders = new File[]{

        };
    }

    private void initListener() {

    }

    private void initView() {
        iv01 = (ImageView) findViewById(R.id.iv01);
        iv02 = (ImageView) findViewById(R.id.iv02);
        iv03 = (ImageView) findViewById(R.id.iv03);
    }

    private void show() {
        Observable.from(folders)
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        return Observable.from(file.listFiles());
                    }
                })
                // 过滤条件
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {
                        return file.getName().endsWith(".png");
                    }
                })
                // 耗时的操作
                .map(new Func1<File, Bitmap>() {
                    @Override
                    public Bitmap call(File file) {
                        return getBitmapFromFile(file);
                    }
                })
                //
                .subscribeOn(Schedulers.io())
                //
                .observeOn(AndroidSchedulers.mainThread())
                // 最后要执行的操作
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        imageCollectorView.addImage(bitmap);
                    }
                });
    }

    private Bitmap getBitmapFromFile(File file) {
        return null;
    }
}
