package dmg.xqg.com.rxjavatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

        }else if (1 == 2){

        }
    }
}
