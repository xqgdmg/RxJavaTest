package dmg.xqg.com.rxjavatest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import dmg.xqg.com.rxjavatest.ui.ObserverActivity;
import dmg.xqg.com.rxjavatest.ui.ShowPicActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvShowPic;
    private TextView tvObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initListener() {
        tvShowPic.setOnClickListener(this);
        tvObserver.setOnClickListener(this);
    }

    private void initView() {
        tvShowPic = (TextView) findViewById(R.id.tvShowPic);
        tvObserver = (TextView) findViewById(R.id.tvObserver);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvShowPic){
            startActivity(new Intent(MainActivity.this,ShowPicActivity.class));
        }else if (v.getId() == R.id.tvObserver){
            startActivity(new Intent(MainActivity.this,ObserverActivity.class));
        }
    }
}
