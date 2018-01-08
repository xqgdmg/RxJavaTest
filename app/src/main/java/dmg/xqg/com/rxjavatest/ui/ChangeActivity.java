package dmg.xqg.com.rxjavatest.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import dmg.xqg.com.rxjavatest.R;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class ChangeActivity extends AppCompatActivity {

    private ImageView ivShowPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        initView();
        initListener();
        initData();

        gotoSee();
    }

    /**
     * 模拟数据
     */
    private void initData() {
        InputStream cInputStream = getResources().openRawResource(R.raw.c);
        File cFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "c.png");
        getFileFromStream(cInputStream, cFile);
    }

    /**
     * 把流转为文件
     */
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

    /**
     * 使用 map ，转换
     */
    private void gotoSee() {
        String imgPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/c.png";

        Observable
                .just(imgPath) // 输入类型 String，最多是个参数
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String filePath) { // 参数类型 String
                        return getBitmapFromFile(filePath); // 返回类型 Bitmap
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) { // 参数类型 Bitmap
                        showBitmap(bitmap);
                    }
                });
    }

    /**
     * 显示图片
     */
    private void showBitmap(Bitmap bitmap) {
        ivShowPic.setImageBitmap(bitmap);
    }

    private Bitmap getBitmapFromFile(String filePath) {
        return BitmapFactory.decodeFile(filePath,getBitmapOption(1));
    }

    /**
     * 采样率设置为2，避免内存溢出（代价是图片变模糊了）
     */
    private BitmapFactory.Options getBitmapOption(int inSampleSize){
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    private void initListener() {

    }

    private void initView() {
        ivShowPic = (ImageView) findViewById(R.id.ivShowPic);
    }
}
