package infinitour.code.com.myapplication.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.master.glideimageview.GlideImageView;

import java.util.ArrayList;

import infinitour.code.com.myapplication.R;

public class plants extends AppCompatActivity {
  /*  //winter
    String wheat ="https://image.flaticon.com/icons/svg/979/979585.svg?fbclid=IwAR3OrrfK5ZE0JscNpei3to2pP1yvIXCWTRrS50JK39k4TolTmiL7lMfLbGI";
    String onion ="https://image.flaticon.com/icons/svg/979/979585.svg?fbclid=IwAR3OrrfK5ZE0JscNpei3to2pP1yvIXCWTRrS50JK39k4TolTmiL7lMfLbGI";
    String garlic ="https://image.flaticon.com/icons/svg/979/979585.svg?fbclid=IwAR3OrrfK5ZE0JscNpei3to2pP1yvIXCWTRrS50JK39k4TolTmiL7lMfLbGI";
   //summer
    String watermelon="https://www.flaticon.com/free-icon/watermelon_1653139#term=watermelon&page=1&position=15";
    String corn="https://www.flaticon.com/free-icon/corn_1656390#term=corn&page=1&position=13";
    String pumpkin ="https://www.flaticon.com/free-icon/pumpkin_267888#term=pumpkin&page=1&position=44";
   //spring
    String rice ="https://www.flaticon.com/free-icon/rice_616594";
    String strawberry ="https://www.flaticon.com/free-icon/strawberry_135717#term=strawberry&page=1&position=2";
    String kiwi ="https://www.flaticon.com/free-icon/kiwi_1135602#term=kiwi%20fruit&page=1&position=4";
    //autumn
    String carrot ="https://www.flaticon.com/free-icon/carrot_1593668#term=carrot&page=1&position=5";
    String Broccoli ="https://www.flaticon.com/free-icon/broccoli_579118#term=broccoli&page=1&position=16";
    String radish ="https://www.flaticon.com/free-icon/radish_1515017#term=radish&page=1&position=15";
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);
        final double area=getIntent().getDoubleExtra("area",1000);
        ImageView iv_1=(ImageView)findViewById(R.id.iv_plant_1);
        ImageView iv_2=(ImageView)findViewById(R.id.iv_plant_2);
        ImageView iv_3=(ImageView)findViewById(R.id.iv_plant_3);
        final ArrayList<Double>volume_of_water=new ArrayList<>();
        final int index=getIntent().getIntExtra("id",0);
        if(index==0){
            iv_1.setImageResource(R.drawable.wheat);
            iv_2.setImageResource(R.drawable.onion);
            iv_3.setImageResource(R.drawable.garlic);
        }
        else if(index==1){
            iv_1.setImageResource(R.drawable.watermelon);
            iv_2.setImageResource(R.drawable.corn);
            iv_3.setImageResource(R.drawable.pumpkin);

        }
        else if(index==2){
            iv_1.setImageResource(R.drawable.cucumber);
            iv_2.setImageResource(R.drawable.strawberry);
            iv_3.setImageResource(R.drawable.kiwi);

        }
        else if(index==3){
            iv_1.setImageResource(R.drawable.carrot);
            iv_2.setImageResource(R.drawable.broccoli);
            iv_3.setImageResource(R.drawable.radish);

        }
        iv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(index==0){
                        double coef[]={1.8,3.2,5.6,4.7};
                        float seeds=120;
                        calc_water(((int) area),coef,seeds,volume_of_water);}

                else if(index==1){
                    double coef[]={5.7,6,6.9,5.4};
                    float seeds=1;
                    calc_water(((int) area),coef,seeds,volume_of_water);}
                    else if(index==2){
                        double coef[]={2.4,4.8,8,6.9};
                        float seeds=80;
                        calc_water(((int) area),coef,seeds,volume_of_water);}
                    else if(index==3){
                        double coef[]={2.4,4.8,8,6.9};
                        float seeds=80;
                        calc_water(((int) area),coef,seeds,volume_of_water);}
                Intent intent=new Intent(plants.this,Irrigation.class);
                    intent.putExtra("1",volume_of_water.get(0));
                    intent.putExtra("2",volume_of_water.get(1));
                    intent.putExtra("3",volume_of_water.get(2));
                    intent.putExtra("4",volume_of_water.get(3));
                    startActivity(intent);


            }
        });
        iv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==0){
                    double coef[]={2.4,5.6,9.9,8.3};
                    float seeds=20;
                    calc_water(((int) area),coef,seeds,volume_of_water);}

                else if(index==1){
                    double coef[]={3,3.6,5.6,9.2};
                    float seeds=9;
                    calc_water(((int) area),coef,seeds,volume_of_water);}
                else if(index==2){
                    double coef[]={1.6,3.2,5.1,4.1};
                    float seeds=1;
                    calc_water(((int) area),coef,seeds,volume_of_water);}
                else if(index==3){
                    double coef[]={1.9,3,4.1,3.9};
                    float seeds=3;
                    calc_water(((int) area),coef,seeds,volume_of_water);}
                Intent intent=new Intent(plants.this,Irrigation.class);
                intent.putExtra("1",volume_of_water.get(0));
                intent.putExtra("2",volume_of_water.get(1));
                intent.putExtra("3",volume_of_water.get(2));
                intent.putExtra("4",volume_of_water.get(3));
                startActivity(intent);
            }
        });
        iv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index==0){
                    double coef[]={2.3,3.8,5.6,3.7};
                    float seeds=13.4f;
                    calc_water(((int) area),coef,seeds,volume_of_water);}

                else if(index==1){
                    double coef[]={1.9,3,4.1,3.9};
                    float seeds=1;
                    calc_water(((int) area),coef,seeds,volume_of_water);}
                else if(index==2){
                    double coef[]={1.8,4.4,7.9,5.6};
                    float seeds=0.047f;
                    calc_water(((int) area),coef,seeds,volume_of_water);}
                else if(index==3){
                    double coef[]={3,3.6,5.6,4.2};
                    float seeds=55;
                    calc_water(((int) area),coef,seeds,volume_of_water);}
                Intent intent=new Intent(plants.this,Irrigation.class);
                intent.putExtra("1",volume_of_water.get(0));
                intent.putExtra("2",volume_of_water.get(1));
                intent.putExtra("3",volume_of_water.get(2));
                intent.putExtra("4",volume_of_water.get(3));
                startActivity(intent);
            }
        });
    }

    public void calc_water(int Area,double[]coef,float seeds_number, ArrayList<Double> volume_of_water){
        double first_stage,second_stage,third_stage,fourth_stage;
        first_stage=Area*coef[0]*seeds_number;
        second_stage=Area*coef[1]*seeds_number;
        third_stage=Area*coef[2]*seeds_number;
        fourth_stage=Area*coef[3]*seeds_number;
        volume_of_water.add(first_stage);
        volume_of_water.add(second_stage);
        volume_of_water.add(third_stage);
        volume_of_water.add(fourth_stage);
    }
}
