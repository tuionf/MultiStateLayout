package com.example.tuionf.multistatelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MultiStateLayout multipleStatusView;
    private Button main,error,no_net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        multipleStatusView = (MultiStateLayout) findViewById(R.id.main_multiplestatusview);
        main = (Button) findViewById(R.id.main);
        error = (Button) findViewById(R.id.custom_error);
        no_net = (Button) findViewById(R.id.no_net);

        main.setOnClickListener(this);
        error.setOnClickListener(this);
        no_net.setOnClickListener(this);
        //显示加载中视图

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.main:
                multipleStatusView.showContent();
                break;
            case R.id.custom_error:
                multipleStatusView.showError();
                break;
            case R.id.no_net:
                multipleStatusView.showNoNetwork();
                break;
            default:
                break;
        }
    }
}
