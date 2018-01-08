package dmg.xqg.com.rxjavatest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import dmg.xqg.com.rxjavatest.ui.ChangeActivity;
import dmg.xqg.com.rxjavatest.ui.ObserverActivity;
import dmg.xqg.com.rxjavatest.ui.ObserverWithThreadActivity;
import dmg.xqg.com.rxjavatest.ui.ShowPicActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvShowPic;
    private TextView tvObserverSimple;
    private TextView tvObserverWithThread;
    private TextView tvChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initListener() {
        tvShowPic.setOnClickListener(this);
        tvObserverSimple.setOnClickListener(this);
        tvObserverWithThread.setOnClickListener(this);
        tvChange.setOnClickListener(this);
    }

    private void initView() {
        tvShowPic = (TextView) findViewById(R.id.tvShowPic);
        tvObserverSimple = (TextView) findViewById(R.id.tvObserverSimple);
        tvObserverWithThread = (TextView) findViewById(R.id.tvObserverWithThread);
        tvChange = (TextView) findViewById(R.id.tvChange);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvShowPic){
            startActivity(new Intent(MainActivity.this,ShowPicActivity.class));
        }else if (v.getId() == R.id.tvObserverSimple){
            startActivity(new Intent(MainActivity.this,ObserverActivity.class));
        }else if (v.getId() == R.id.tvObserverWithThread){
            startActivity(new Intent(MainActivity.this,ObserverWithThreadActivity.class));
        }else if (v.getId() == R.id.tvChange){
            startActivity(new Intent(MainActivity.this,ChangeActivity.class));
        }
    }
}
