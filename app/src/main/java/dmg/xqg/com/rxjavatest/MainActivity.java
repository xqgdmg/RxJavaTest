package dmg.xqg.com.rxjavatest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import dmg.xqg.com.rxjavatest.ui.ShowPicActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvShowPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initListener() {
        tvShowPic.setOnClickListener(this);
    }

    private void initView() {
        tvShowPic = (TextView) findViewById(R.id.tvShowPic);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvShowPic){
            startActivity(new Intent(MainActivity.this,ShowPicActivity.class));
        }else if (1 == 2){
            Log.e("chris","chris");
        }
    }
}
