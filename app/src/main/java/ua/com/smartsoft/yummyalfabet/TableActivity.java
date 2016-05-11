package ua.com.smartsoft.yummyalfabet;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by viy on 15.03.16.
 */
public class TableActivity extends Activity {
    private TableLayout table;
    private Typeface faceChar;
    private int screenFontSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Configuration configuration = this.getResources().getConfiguration();
        screenFontSize = (int) configuration.screenHeightDp/10;  // int screenFontSize = (int) (screenHeightDp*0.8)/10; // Новая формула
        table = (TableLayout) findViewById(R.id.tableLayout);
        table.setStretchAllColumns(true);
        for (int j = 0; j < 32; j = j + 4) {
            TableRow table_row = new TableRow(this);
            table_row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
            table_row.setGravity(Gravity.CENTER_HORIZONTAL);
            for (int i = 0; i < 4; i++) paintChar(j+i,table_row);
            table.addView(table_row);
        }
        TableRow table_row = new TableRow(this);
        table_row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        table_row.setGravity(Gravity.CENTER_HORIZONTAL);
        paintChar(32,table_row);
        table.addView(table_row);
    }

    protected void paintChar(final int n, TableRow table_row_loc) {
        TextView tv_name = new TextView(this);
        tv_name.setText(SplashScreen.getInstance().alfabetCharArray[n]);
        switch (n) {
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
                tv_name.setTextColor(Color.parseColor("#D50000")); // червоний #FFEB3B - жовтий
                break;
            case 30:
                tv_name.setTextColor(Color.parseColor("#00C853")); // A700 #00C853 - зелений
                break;
            default:
                tv_name.setTextColor(Color.parseColor("#304FFE")); // A700 #304FFE - синій
                break;
        }
        //textViewChar.setTextColor(Color.parseColor(colorChar));
        // tv_name.setTextColor(Color.parseColor("#304FFE")); // #304FFE синій
        tv_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,screenFontSize);
        tv_name.setGravity(Gravity.CENTER_HORIZONTAL);
        tv_name.setPadding(0,0,0,0);
        faceChar = Typeface.createFromAsset(getAssets(), "fonts/Narbut_Abetka.ttf");
        tv_name.setTypeface(faceChar);
        tv_name.setShadowLayer(2f, 5f, 5f, 0xFF050505);
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SplashScreen.getInstance().nomberChar =  n; // j+i;
                finish();
            }
        });
        table_row_loc.addView(tv_name);
    }
}

//                TextView tv_name = new TextView(this);
//                tv_name.setText(SplashScreen.getInstance().alfabetCharArray[j+i]);
//                tv_name.setTextColor(Color.parseColor("#304FFE")); // #304FFE синій
//                tv_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,screenFontSize);
//                // tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,46);
//                tv_name.setGravity(Gravity.CENTER_HORIZONTAL);
//                tv_name.setPadding(0,0,0,0);
//                faceChar = Typeface.createFromAsset(getAssets(), "fonts/Narbut_Abetka.ttf");
//                tv_name.setTypeface(faceChar);
//                //tv_name.setW
//                //tv_name.setTypeface(Typeface.DEFAULT_BOLD);
//                //tv_name.setWidth(150);
//                tv_name.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        SplashScreen.getInstance().nomberChar =  4; // j+i;
//                        finish();
//                    }
//                });
//                table_row.addView(tv_name);

//    private TextView[] textViews;

//The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.
// int screenHeightDp = configuration.screenHeightDp;
// int screenWidthDp = configuration.screenWidthDp;
//The smallest screen size an application will see in normal operation, corresponding to smallest screen width resource qualifier.
// int smallestScreenWidthDp = configuration.smallestScreenWidthDp;
// Log.d("SplashScreen", "screenWidthDp="+screenWidthDp+"********"+"smallestScreenWidthDp="+smallestScreenWidthDp+"********************************");
// Log.d("SplashScreen", "screenHeightDp="+screenHeightDp+"****************************************");

// table_row.setWeightSum(4);
// table_row.setPadding(0,0,0,0);
// table_row.setBackgroundColor(color_white);
// table_row.

//    TextView tv_name = new TextView(this);
//tv_name.setText(SplashScreen.getInstance().alfabetCharArray[32]);
//        tv_name.setTextColor(Color.parseColor("#304FFE")); // #304FFE синій
//        tv_name.setTextSize(screenFontSize);
//        tv_name.setGravity(Gravity.CENTER_HORIZONTAL);
//        table_row.addView(tv_name);
//        faceChar = Typeface.createFromAsset(getAssets(), "fonts/Narbut_Abetka.ttf");
//        tv_name.setTypeface(faceChar);

// tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,46);


