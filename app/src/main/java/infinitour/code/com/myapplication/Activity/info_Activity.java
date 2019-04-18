package infinitour.code.com.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import infinitour.code.com.myapplication.R;

public class info_Activity extends AppCompatActivity {
    TextView et_area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_);
        et_area=(TextView)findViewById(R.id.et_area);
        double area=getIntent().getDoubleExtra("area",20.0);
        et_area.setText(String.valueOf(area));
        
    }
}
