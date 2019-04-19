package infinitour.code.com.myapplication.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import infinitour.code.com.myapplication.R;

public class info_Activity extends AppCompatActivity {
    TextView et_area;
    ImageView sunny,spring,snow,autumn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_);
        et_area=(TextView)findViewById(R.id.et_area);
        final double area=getIntent().getDoubleExtra("area",20.0);
        int area2= ((int) area);
        et_area.setText(String.valueOf(area2));
        sunny=(ImageView)findViewById(R.id.iv_sunny);
        spring=(ImageView)findViewById(R.id.iv_spring);
        snow=(ImageView)findViewById(R.id.iv_snow);
        autumn=(ImageView)findViewById(R.id.iv_autumn);
        sunny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(info_Activity.this,plants.class);
                intent.putExtra("id",2);
                intent.putExtra("area",area);
                startActivity(intent);
            }
        });
        spring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(info_Activity.this,plants.class);
                intent.putExtra("id",1);
                intent.putExtra("area",area);
                startActivity(intent);
            }
        });
        snow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(info_Activity.this,plants.class);
                intent.putExtra("id",0);
                intent.putExtra("area",area);
                startActivity(intent);
            }
        });
        autumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(info_Activity.this,plants.class);
                intent.putExtra("id",3);
                intent.putExtra("area",area);
                startActivity(intent);
            }
        });

    }
}
