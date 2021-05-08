package com.example.myapplication.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class TargetDetailsActivity extends AppCompatActivity {

    private Button btn_input_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        btn_input_save = findViewById(R.id.btn_details_save);

        btn_input_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 넘겨줄 값들 나중에 다시 적어야함 (e.g. 개인정보, 분야 등 입력받은 값)
//                str = editText.getText().toString();
//                Intent intent = new Intent(UserDetailsActivity.this, MainActivity.class);
////                intent.putExtra("str", str);
//                startActivity(intent);  //  activity 이동


                finish();
            }
        });
    }
}