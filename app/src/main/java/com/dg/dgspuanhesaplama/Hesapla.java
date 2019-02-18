package com.dg.dgspuanhesaplama;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Hesapla extends AppCompatActivity implements View.OnClickListener{
    final float sayisalHam = (float) 145.378;
    final float sozelHam = (float) 122.382;
    final float esitHam = (float) 133.880;
    int gun,saat,dakika,saniye;
    SimpleDateFormat format;
    Button b,t;
    private AdView mAdView;
    CheckBox check;
    EditText say1, say2, soz1, soz2, obp;
    float sayD,sayY,sozD,sozY,sayNet,sozNet,sayPuan,obpF,ekPuan,sozPuan,esitP;
    TextView trN,sayisalN,sayP,sozP,esitPuan;
    //kalan zaman için textler
    TextView day,dayst,hours,hoursst,minute,minutest,second,secondst;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hesapla);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        b = (Button) findViewById(R.id.hesap);
        b.setOnClickListener(this);
        t = (Button) findViewById(R.id.temiz);
        t.setOnClickListener(this);
        check = (CheckBox)findViewById(R.id.onceki);

        say1 = (EditText)findViewById(R.id.sayDogru);
        say2 = (EditText)findViewById(R.id.sayYanlis);
        soz1 = (EditText)findViewById(R.id.sozDogru);
        soz2 = (EditText)findViewById(R.id.sozYanlis);
        obp = (EditText)findViewById(R.id.obp);
        trN = (TextView)findViewById(R.id.trNet);
        sayisalN = (TextView)findViewById(R.id.matNet);
        sayP = (TextView)findViewById(R.id.sayPuan);
        sozP = (TextView)findViewById(R.id.sozPuan);
        esitPuan = (TextView)findViewById(R.id.esitPuan);

        //kalan zaman textviewlar
        day = (TextView)findViewById(R.id.gun);
        dayst = (TextView)findViewById(R.id.gunst);
        hours = (TextView)findViewById(R.id.saat);
        hoursst = (TextView)findViewById(R.id.saatst);
        minute = (TextView)findViewById(R.id.dakika);
        minutest = (TextView)findViewById(R.id.dakikast);
        second = (TextView)findViewById(R.id.saniye);
        secondst = (TextView)findViewById(R.id.saniyest);


        kalanZaman();

    }

    @Override
    public void onClick(View v){
        if(v.getId() == b.getId()){

            if(TextUtils.isEmpty(say1.getText().toString())){
                sayD = 0;
            }
            else{
                sayD=Float.parseFloat(say1.getText().toString());
            }
            if(TextUtils.isEmpty(say2.getText().toString())){
                sayY = 0;
            }
            else{
                sayY=Float.parseFloat(say2.getText().toString());
            }
            if(TextUtils.isEmpty(soz1.getText().toString())){
                sozD = 0;
            }
            else{
                sozD=Float.parseFloat(soz1.getText().toString());
            }
            if(TextUtils.isEmpty(soz2.getText().toString())){
                sozY = 0;
            }
            else{
                sozY=Float.parseFloat(soz2.getText().toString());
            }
            if(TextUtils.isEmpty(obp.getText().toString())){
                obpF = 0;
            }
            else{
                if(80 < Float.parseFloat(obp.getText().toString())){
                    obp.setText("80");
                    obpF = 80;
                }else if(40 > Float.parseFloat(obp.getText().toString())){
                    obp.setText("40");
                    obpF = 40;
                }else{
                    obpF=Float.parseFloat(obp.getText().toString());
                }
            }




            if(check.isChecked() == false){
                ekPuan = obpF*(float)0.6;
            }else{
                ekPuan = obpF*(float)0.45;
            }



            sayNet=sayD-((float)sayY/4);
            sozNet=sozD-((float)sozY/4);


            sayPuan=(float)ekPuan + (float)sayisalHam + (float)(sayNet*(float)(3.177))+ (float)(sozNet*(float)(0.442));
            sozPuan=(float)ekPuan + (float)sozelHam + (float)(sayNet*(float)(0.635))+ (float)(sozNet*(float)(2.208));
            esitP=(float)ekPuan + (float)esitHam + (float)(sayNet*(float)(1.906))+ (float)(sozNet*(float)(1.325));



            trN.setText(String.valueOf(sozNet) + " Net");
            sayisalN.setText(String.valueOf(sayNet) + " Net");


            sayP.setText(String.valueOf(String.format("%.3f",sayPuan)));
            sozP.setText(String.valueOf(String.format("%.3f",sozPuan)));
            esitPuan.setText(String.valueOf(String.format("%.3f",esitP)));
        }
        else if(v.getId() == t.getId()){
            say1.setText("");
            say2.setText("");
            soz1.setText("");
            soz2.setText("");
            obp.setText("");
            trN.setText("");
            sayisalN.setText("");
            sayP.setText("");
            sozP.setText("");
            esitPuan.setText("");
        }
    }

    public void kalanZaman(){

        format = new SimpleDateFormat("MM-dd-HH-mm-ss");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                Date dt1 = null;
                Date dt2 = Calendar.getInstance().getTime();
                String dt = format.format(dt2);
                try {
                    dt1 = format.parse("07-08-09-30-00");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    dt2 = format.parse(dt);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                DateTime d1 = new DateTime(dt1);
                DateTime d2 = new DateTime(dt2);

                saniye = Seconds.secondsBetween(d2,d1).getSeconds();
                dakika = saniye / 60;
                saat = saniye / 3600;
                gun = saniye / 86400;
                saniye = saniye % 60;
                dakika = dakika % 60;
                saat = saat % 24;
                day.setText(String.valueOf(gun) + " ");
                hours.setText(" " + String.valueOf(saat) + " ");
                minute.setText(" " + String.valueOf(dakika) + " ");
                second.setText(" " + String.valueOf(saniye) + " ");
                handler.postDelayed(this, 1000);
            }
        }, 1);
    }
}
