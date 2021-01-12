package com.example.dteamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddPersonActivity extends AppCompatActivity {

    Button btnParents, btnKid, connWear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        btnParents = findViewById(R.id.btnParents);//부모 추가버튼
        btnKid = findViewById(R.id.btnKid);//아이 추가버튼
        connWear = findViewById(R.id.connWear);//웨어러블 연결


        //부모 추가 버튼 클릭시
        btnParents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPersonActivity.this, AddPersonActivity2.class);
                startActivity(intent);
                Toast.makeText(AddPersonActivity.this,
                        "친구(기기) 추가 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        //아이 추가 버튼 클릭시
        btnKid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPersonActivity.this, AddPersonActivity2.class);
                startActivity(intent);
                Toast.makeText(AddPersonActivity.this,
                        "친구(기기) 추가 화면으로 이동합니다.", Toast.LENGTH_SHORT).show();
            }
        });

        //웨어러블 연결 클릭시
        connWear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}