package dmg.xqg.com.rxjavatest.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import dmg.xqg.com.rxjavatest.R;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ShowPicActivity extends AppCompatActivity {

    // 这是一个文件夹数组
    private File[] folders;
    private ArrayList<Bitmap> bitmaps;
    private LinearLayout llRoot;

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
        InputStream aInputStream = getResources().openRawResource(R.raw.a);
        File aFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "a.png");
        InputStream bInputStream = getResources().openRawResource(R.raw.b);
        File bFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "b.png");
        InputStream cInputStream = getResources().openRawResource(R.raw.c);
        File cFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "c.png");

        getFileFromStream(aInputStream, aFile);
        getFileFromStream(bInputStream, bFile);
        getFileFromStream(cInputStream, cFile);

        folders = new File[]{
                new File(Environment.getExternalStorageDirectory().getAbsolutePath())
        };
    }

    private void getFileFromStream(InputStream inputStream, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initListener() {

    }

    private void initView() {
        llRoot = (LinearLayout) findViewById(R.id.llRoot);
    }

    private void show() {
        // 方式1，传统的方法
//        showByOldWay();

        // 方式2，rxjava
        showByRxJava();
    }

    private void showByRxJava() {
        Observable
                // 数据源
                .from(folders)
                // 返回合并的结果（该文件夹下的所有文件）
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        return Observable.from(file.listFiles());
                    }
                })
                // 过滤条件（以flatMap合并后的结果为基础）
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {
                        return file.getName().endsWith(".png");
                    }
                })
                // 返回一个特定功能的的 Observable，这个和 subscribe 有关
                .map(new Func1<File, Bitmap>() {
                    @Override
                    public Bitmap call(File file) {
                        return getBitmapFromFile(file);
                    }
                })
                // Observable 在哪个线程发射
                .subscribeOn(Schedulers.io())
                // Observer 在哪个线程接收？
                .observeOn(AndroidSchedulers.mainThread())
                // Observer 要执行的操作
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        addAndShowImage(bitmap);
                    }
                });
    }

    private void showByOldWay() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (File folder : folders) {
                    File[] files = folder.listFiles();
                    for (File file : files) {
                        if (file.getName().endsWith(".png")) {
                            final Bitmap bitmap = getBitmapFromFile(file);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    addAndShowImage(bitmap);
                                }
                            });
                        }
                    }
                }
            }
        }.start();
    }

    /**
     * 添加图片并显示
     */
    private void addAndShowImage(Bitmap bitmap) {
        bitmaps = new ArrayList<>();
        bitmaps.add(bitmap);

        // 显示所有图片
        for (int i = 0; i < bitmaps.size(); i++) {
            ImageView iv = new ImageView(ShowPicActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100,100);
            lp.setMargins(30,30,30,30);
            iv.setLayoutParams(lp);
            iv.setImageBitmap(bitmaps.get(i));
            llRoot.addView(iv);
        }
    }

    private Bitmap getBitmapFromFile(File file) {
        return BitmapFactory.decodeFile(file.getAbsolutePath(),getBitmapOption(2));
    }

    /**
     * 采样率设置为2，避免内存溢出
     */
    private BitmapFactory.Options getBitmapOption(int inSampleSize){
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }
}
