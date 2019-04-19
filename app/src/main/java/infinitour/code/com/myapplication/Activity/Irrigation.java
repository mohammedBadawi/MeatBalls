package infinitour.code.com.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import infinitour.code.com.myapplication.R;

public class Irrigation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irrigation);
        TextView et1,et2,et3,et4;
        double vol1,vol2,vol3,vol4;
        et1=(TextView)findViewById(R.id.et_vol_1);
        et2=(TextView)findViewById(R.id.et_vol_2);
        et3=(TextView)findViewById(R.id.et_vol_3);
        et4=(TextView)findViewById(R.id.et_vol_4);
        vol1=getIntent().getDoubleExtra("1",1000);
        vol2=getIntent().getDoubleExtra("2",1000);
        vol3=getIntent().getDoubleExtra("3",1000);
        vol4=getIntent().getDoubleExtra("4",1000);
        et1.setText(""+((int)vol1)+"m"+"m\u00b3");
        et2.setText(""+((int)vol2)+"m"+"m\u00b3");
        et3.setText(""+((int)vol3)+"m"+"m\u00b3");
        et4.setText(""+((int)vol4)+"m"+"m\u00b3");
    }
}
