package ua.com.smartsoft.yummyalfabet;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by viy on 09.03.16.
 */

public class SplashScreen extends Activity {
    private static SplashScreen singleton;
    public int screenHeight;                        // высота экрана
    public int screenWidth;                         // ширина экрана
    public int sizeFont;                            // размер шрифта для большого символа
    public int sizeChar;                            // размер шрифта для Буквицы
    public String[] alfabetCharArray;               // массив где хранятся все символы алфавита
    public String[] alfabetSonetAttay;              // массив для хранения четверостишей
    public int nomberChar = 0;                      // номер буквы для показа
    Boolean flagMainActivity = false;
    TextView textView1;                             // первоя строка заголовка
    TextView textView2;                             // вторая строка заголовка
    LinearLayout linearLayout;                      // макет экрана
    Typeface face;                                  //

    // Возвращает экземпляр данного класса
    public static SplashScreen getInstance() {
        return singleton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singleton = this;
        setContentView(R.layout.activity_splashscreen);

        textView1 = (TextView) findViewById(R.id.textView1);
        face = Typeface.createFromAsset(getAssets(), "fonts/Narbut_Abetka.ttf");
        textView1.setTypeface(face);
        textView1.setShadowLayer(2f, 5f, 5f, 0xFF050505);

        textView2 = (TextView) findViewById(R.id.textView2);
        face = Typeface.createFromAsset(getAssets(), "fonts/Narbut_Whirl.ttf");
        textView2.setTypeface(face);
        textView2.setShadowLayer(3f, 5f, 5f, 0xFF050505);

        // Определяем ширину и высоту экрана для задания размера шрифта
        DisplayMetrics metrics = new DisplayMetrics();
        singleton.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        Log.d("SplashScreen", "screenHeight="+screenHeight+"********"+"screenWidth="+screenWidth+"********************************");
        Log.d("SplashScreen", "metrics.density="+metrics.density+"********************************");
        Log.d("SplashScreen", "HeightDP="+(screenHeight/metrics.density)+"********"+"WidthPD="+(screenWidth/metrics.density)+"********************************");
        Configuration configuration = this.getResources().getConfiguration();
        //sizeChar = (int) (((screenHeight - screenWidth) ) / metrics.density);
        sizeChar = (int) configuration.screenHeightDp/4;
        sizeFont = (int) (((screenHeight - screenWidth) / 3.5) / metrics.density);
        textView1.setTextSize(sizeFont);
        sizeFont = (int) (((screenHeight - screenWidth) / 3.3) / metrics.density);
        textView2.setTextSize(sizeFont);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,10,10,10);
        textView1.setLayoutParams(params);
        textView1.setTextColor(Color.parseColor("#FFEB3B"));
        textView2.setLayoutParams(params);
        textView2.setTextColor(Color.parseColor("#304FFE")); //
        // Обрабатываем клик на экране для прехода в основное активити
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Передаем управление в главное активити
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
                flagMainActivity = true;
            }
        });
        // Загружаем данные

        // загружаем алфавит в масив
        alfabetCharArray = getResources().getStringArray(R.array.alfabet);
        alfabetSonetAttay = getResources().getStringArray(R.array.textSonet);

        //Создаем новый поток для показа заставки:
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // Загружаем изображения в масив
                // nameAutoBrend = getResources().getStringArray(R.array.nameauto);
                // Передаем управление в главное активити
                if (flagMainActivity == false) {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3*1000);
    }


}
//          android:scaleType="fitEnd"
//          import android.widget.RelativeLayout;
//          import android.view.Window;

// public String[] nameAutoBrend;                  // массив где хранятся все имена автомобильных брендов
// public SoundPool sSoundPool;                    // плайер для литер
// public int[] soundLetterSource = new int[33];   // звуки литер
// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
// public String[] nameFonts;                      // массив имен доступных шрифтов
// public int sizeFullImage;                       // размер стороны (квадрат) для изображения логотипа
//RelativeLayout.LayoutParams paramsTextView = new RelativeLayout.LayoutParams(
//        Splash_A123A.getInstance().sizeFullImage,
//        Splash_A123A.getInstance().sizeFullImage);
// RelativeLayout.LayoutParams paramsTextView = new RelativeLayout.LayoutParams(720,720);
//textView01.setLayoutParams(paramsTextView);
//textView01.setTextSize((int) (Splash_A123A.getInstance().sizeFont / 1.5));
// textView1.setTextSize(40);
// textView1.setTextColor(Color.RED);
//sizeFullImage = (screenWidth < screenHeight) ? screenWidth : screenHeight;
//sizeFullImage = sizeFullImage - (sizeFullImage / 10);
//sizeFont = (int) ( sizeFullImage / metrics.density );
//LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
// textView1.setGravity(Gravity.CENTER_HORIZONTAL);
// Log.d("SplashScreen", "screenHeight="+screenHeight+"********"+"screenWidth="+screenWidth+"********************************");
// Log.d("SplashScreen", "sizefont="+sizeFont+"********************************");
// Log.d("SplashScreen", "metrics.density="+metrics.density+"********************************");
// проверяем результат для отладки
// Log.d("Splash_A123A", "screenHeight="+screenHeight+"********"+"screenWidth="+screenWidth+"********************************");
// Log.d("Splash_A123A", "sizeFullImage="+sizeFullImage+"****************************************");
// screenWidth = (int) (metrics.widthPixels / metrics.density);
// screenHeight = (int) (metrics.heightPixels / metrics.density);
// Загружаем имена шрифтов
// nameFonts = getResources().getStringArray(R.array.namefonts);
// Загружаем звуки
// A123A.getInstance().onLoadDate();