package com.project.wei.tastyrecipes.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.project.wei.tastyrecipes.R;

public class OpinionActivity extends Activity {

    private EditText et_edit_opinion;
    private ImageButton ib_left_back;
    private Button btn_send_opinion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        et_edit_opinion = (EditText) findViewById(R.id.et_edit_opinion);
        ib_left_back = (ImageButton) findViewById(R.id.ib_left_back);
        btn_send_opinion = (Button) findViewById(R.id.btn_send_opinion);



        btn_send_opinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OpinionActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                et_edit_opinion.setText(null);
                //et_edit_opinion.setHint("继续提交您的建议");
            }
        });
        ib_left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
