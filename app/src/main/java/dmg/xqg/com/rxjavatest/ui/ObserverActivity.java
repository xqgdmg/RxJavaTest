package dmg.xqg.com.rxjavatest.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import dmg.xqg.com.rxjavatest.R;
import rx.Observable;
import rx.functions.Action1;

public class ObserverActivity extends AppCompatActivity {

    private TextView tvLogStringArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);

        initView();
        initListener();

        logStringArray();
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
                        Log.d("chris","name==" + name);
                        tvLogStringArray.setText(name);
                    }
                });
    }

    private void initListener() {

    }

    private void initView() {
        tvLogStringArray = (TextView) findViewById(R.id.tvLogStringArray);
    }
}
