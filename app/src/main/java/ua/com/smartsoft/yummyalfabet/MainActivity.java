package ua.com.smartsoft.yummyalfabet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// План
// + Нарисовать 4 экран - О приложении
// + Перерисовать первый экран
// + Гласные - красным, Согласные - синим, Ь - зеленым
// + Выделить изучаемую букву в соонете цветом
// + Карточку с Є перерисовать, что бы буква не сильно налазила на изображение
// + В конце (после Я) высести страницу со всеми буквами (реализовал иначе)
// + Запоминать с какой буквы продолжать (если был вход по нажатию)
// + Протестировать на Моторола Дроид
// + Прописать в манифесте только портретное расположение при использовании

public class MainActivity extends Activity {
    TextView textViewChar;
    TextView textViewSonet;
    Typeface faceChar;
    Typeface faceSonet;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    ImageView imageView;
    Button buttonRight;
    Button buttonLeft;
    String colorChar;
    public static final String APP_PREFERENCES = "mysettings"; // имя файла настроек
    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES_COUNTER = "numberChar";


    @Override
    protected void onPause() {
        super.onPause();
        // Запоминаем данные
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_COUNTER, SplashScreen.getInstance().nomberChar);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSettings.contains(APP_PREFERENCES_COUNTER)) {
            // Получаем число из настроек
            // if (SplashScreen.getInstance().nomberChar == 0)
            // SplashScreen.getInstance().nomberChar = mSettings.getInt(APP_PREFERENCES_COUNTER, 0);
            //this.onCreate(Bundle savedInstanceState);
            createScreen();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createScreen();
    }

    protected void createScreen() {
        //Log.d("MainScreen", "Вызов новой активности: **********************************************************************");
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        //Log.d("MainScreen", "Вызов экрана активности: **********************************************************************");
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(APP_PREFERENCES_COUNTER)) {
            // Получаем число из настроек
            if (SplashScreen.getInstance().nomberChar == 0)
                SplashScreen.getInstance().nomberChar = mSettings.getInt(APP_PREFERENCES_COUNTER, 0);
        }

        imageView = (ImageView) findViewById(R.id.imageViewMain);
        textViewChar = (TextView) findViewById(R.id.textViewChar);
        textViewSonet = (TextView) findViewById(R.id.textViewSonet);
        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        // ----------------------------------------------------------------------------------------
        faceChar = Typeface.createFromAsset(getAssets(), "fonts/Narbut_Abetka.ttf");
        textViewChar.setTypeface(faceChar);
        textViewChar.setShadowLayer(3f, 7f, 7f, 0xFF050505);
        faceSonet = Typeface.createFromAsset(getAssets(), "fonts/Abetka.ttf");
        textViewSonet.setTypeface(faceSonet);
        textViewSonet.setTextColor(Color.parseColor("#304FFE")); // Color.parseColor("#00C853")
        textViewChar.setPadding(0, SplashScreen.getInstance().sizeChar, 0, 0);
        textViewChar.setTextSize((int) (SplashScreen.getInstance().sizeChar));

        textViewSonet.setTextSize((int) (SplashScreen.getInstance().sizeChar) / 6);
        createPage();
        // -------------------------------Обрабатываем клик для смены буквы
        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SplashScreen.getInstance().nomberChar == 0) {
                    SplashScreen.getInstance().nomberChar = 32;
                } else {
                    SplashScreen.getInstance().nomberChar--;
                }
                createPage();
            }
        });

        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SplashScreen.getInstance().nomberChar == 32) {
                    SplashScreen.getInstance().nomberChar = 0;
                } else {
                    SplashScreen.getInstance().nomberChar++;
                }
                createPage();
            }
        });
        buttonRight = (Button) findViewById(R.id.buttonRight);
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
        buttonLeft = (Button) findViewById(R.id.buttonLeft);
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TableActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void createPage() {
        // создаем строку с названием изображения в ресурсе и устанавливаем его
        String str = "img" + String.format("%02d", SplashScreen.getInstance().nomberChar + 1);
        imageView.setImageResource(getResources().getIdentifier(str, "drawable", getPackageName()));
        // устанавливаем буквицу и задаем ей цвет как гласной или согласной
        textViewChar.setText(SplashScreen.getInstance().alfabetCharArray[SplashScreen.getInstance().nomberChar]);
        switch (SplashScreen.getInstance().nomberChar) {
            case 0:
            case 6:
            case 7:
            case 10:
            case 11:
            case 12:
            case 18:
            case 23:
            case 31:
            case 32:
                colorChar = "#D50000";  // червоний #FFEB3B - жовтий
                break;
            case 30:
                colorChar = "#00C853"; // A700 #00C853 - зелений
                break;
            default:
                colorChar = "#304FFE"; // A700 #304FFE - синій
                break;
        }
        textViewChar.setTextColor(Color.parseColor(colorChar));
        // Выделяем буквы в сонете цветом аналогичным буквице
        final Pattern p = Pattern.compile(
                SplashScreen.getInstance().alfabetCharArray[SplashScreen.getInstance().nomberChar],
                Pattern.CASE_INSENSITIVE);
        final Matcher matcher = p.matcher(SplashScreen.getInstance().alfabetSonetAttay[SplashScreen.getInstance().nomberChar]);
        final SpannableStringBuilder spannable = new SpannableStringBuilder(
                SplashScreen.getInstance().alfabetSonetAttay[SplashScreen.getInstance().nomberChar]);
        while (matcher.find()) {
            final ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor("#D50000"));
            spannable.setSpan(
                    span, matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE //Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }
        textViewSonet.setText(spannable);
    }
}

// Log.d("MainScreen", "файл изображения:"+ str +"********************************");
// Log.d("MainScreen", "Чевойто нашел:"+"-------------------------------------------------------");
// Log.d("SplashScreen", "metrics.density="+"********************************");
// Log.d("SplashScreen", "metrics.density="+"********************************");
// Log.d("MainScreen", "файл изображения:"+ str +"********************************");
// imageView.setImageDrawable(getResources().getDrawable(R.drawable.img02));
// String str = "img" + String.format("%02d",SplashScreen.getInstance().nomberChar+1);
// imageView.setImageResource(getResources().getIdentifier("img02", "drawable", getPackageName()));
// imageView.setImageResource(getResources().getIdentifier(str, "drawable", getPackageName()));
// textViewChar.setText(SplashScreen.getInstance().alfabetCharArray[SplashScreen.getInstance().nomberChar]);
// textViewSonet.setText(SplashScreen.getInstance().alfabetSonetAttay[SplashScreen.getInstance().nomberChar]);

// faceSonet = Typeface.createFromAsset(getAssets(), "fonts/Narbut_Classic.ttf");
// extends AppCompatActivity
// textViewChar.setTextColor(Color.parseColor("#B71C1C")); // #B71C1C
// textViewChar.setTextColor(Color.parseColor("#90B71C1C")); // #B71C1C

//    @Override
//    protected void onDestroy() {
//        System.gc();
//        super.onDestroy();
//        imageView.setImageBitmap(null);
//    }

// Log.d("MainScreen", "Вызов новой активности: ********************************");