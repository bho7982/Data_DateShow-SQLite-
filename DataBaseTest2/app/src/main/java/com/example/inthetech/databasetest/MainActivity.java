package com.example.inthetech.databasetest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView ShowValue_TestView, ShowValue_TestView2;
    EditText Value_EditText, Date_EditText;
    Button Save_Button, Show_Button, Reset_Button;
    Button Today_Button, Week_Button, Mount_Button;
    TestDB testDB;
    SQLiteDatabase sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testDB = new TestDB(this);

        ShowValue_TestView = (TextView)findViewById(R.id.textView);
        ShowValue_TestView.setMovementMethod(new ScrollingMovementMethod());

        ShowValue_TestView2 = (TextView)findViewById(R.id.textView3);
        ShowValue_TestView2.setMovementMethod(new ScrollingMovementMethod());

        Value_EditText = (EditText)findViewById(R.id.editText);
        Date_EditText = (EditText)findViewById(R.id.editText2);



        Save_Button = (Button)findViewById(R.id.button);
        Save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat fm1 = new SimpleDateFormat("yyyy-MM-dd");
                String date = fm1.format(new Date());
                sql = testDB.getWritableDatabase();
                String CheckDate = Date_EditText.getText().toString();
                if(CheckDate.matches(""))
                {
                    sql.execSQL("INSERT INTO member VALUES(null,'"
                            + Value_EditText.getText() + "','"
                            + date + "');"
                    );
                }else
                {
                    sql.execSQL("INSERT INTO member VALUES(null,'"
                            + Value_EditText.getText() + "','"
                            + Date_EditText.getText() + "');"
                    );
                }

                sql.close();
            }
        });

        Show_Button = (Button)findViewById(R.id.button2);
        Show_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql = testDB.getReadableDatabase();
                Cursor cursor;
                cursor = sql.rawQuery("SELECT * FROM MEMBER;", null);

                String Value2 = "Value" + "\r\n";

                while (cursor.moveToNext())
                {
                    Value2 += cursor.getString(2) + "         " + cursor.getString(1) + "\r\n";
                }
                ShowValue_TestView.setText(Value2);
                cursor.close();
                sql.close();
/*
                sql = testDB.getReadableDatabase();
                Cursor cursor2;
                cursor2 = sql.rawQuery("SELECT * FROM MEMBER2;", null);

                String Value3 = "Value" + "\r\n";

                while (cursor2.moveToNext())
                {
                    Value3 += cursor2.getString(2) + "         " + cursor2.getString(1) + "\r\n";
                }
                ShowValue_TestView2.setText(Value3);
                cursor2.close();
                sql.close();
*/
            }
        });

        Reset_Button = (Button)findViewById(R.id.button3);
        Reset_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql = testDB.getWritableDatabase();
                testDB.onUpgrade(sql, 1, 2);
                sql.close();
            }
        });

        Today_Button = (Button)findViewById(R.id.button6);
        Today_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowValue_TestView2.setText("Value");
                sql = testDB.getReadableDatabase();
                Cursor cursor;
                cursor = sql.rawQuery("SELECT * FROM MEMBER Where member.Testdate = date('now', 'localtime');", null);

                String Value2 = "Value" + "\r\n";

                while (cursor.moveToNext())
                {
                    Value2 += cursor.getString(2) + "         " + cursor.getString(1) + "\r\n";
                }
                ShowValue_TestView2.setText(Value2);
                cursor.close();
                sql.close();
                Log.v("일일 버튼","누름");
            }
        });

        Week_Button = (Button)findViewById(R.id.button7);
        Week_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowValue_TestView2.setText("Value");
                sql = testDB.getReadableDatabase();
                Cursor cursor;
                cursor = sql.rawQuery("SELECT member2.Testdate, IFNULL(AVG(Member.lux_val),0) "
                        + "FROM Member2 LEFT OUTER JOIN member ON Member.Testdate = member2.Testdate "
                        + "GROUP BY MEMBER2.TESTDATE "
                        + "HAVING member2.Testdate >= date('now','weekday 0', '-7 days', 'localtime') AND member2.Testdate <= date('now','weekday 0', '-1 days', 'localtime')"
                        + "ORDER BY MEMBER2.TESTDATE;", null);

                String Value2 = "Value" + "\r\n";

                while (cursor.moveToNext())
                {
                    Value2 += cursor.getString(0) + "         " + cursor.getString(1) + "\r\n";
                }
                ShowValue_TestView2.setText(Value2);
                cursor.close();
                sql.close();
                Log.v("주간 버튼","누름");
            }
        });

        Mount_Button = (Button)findViewById(R.id.button8);
        Mount_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowValue_TestView2.setText("");
                sql = testDB.getReadableDatabase();
                Cursor cursor;
                cursor = sql.rawQuery("SELECT member2.Testdate, IFNULL(AVG(Member.lux_val),0) "
                        + "FROM Member2 LEFT OUTER JOIN member ON Member.Testdate = member2.Testdate "
                        + "GROUP BY MEMBER2.TESTDATE "
                        + "HAVING member2.Testdate >= date('now','start of month','localtime') AND member2.Testdate <= date('now','start of month','+1 month','-1 day','localtime')"
                        + "ORDER BY MEMBER2.TESTDATE;", null);

                String Value2 = "Value" + "\r\n";

                while (cursor.moveToNext())
                {
                    Value2 += cursor.getString(0) + "         " + cursor.getString(1) + "\r\n";
                }
                ShowValue_TestView2.setText(Value2);
                cursor.close();
                sql.close();
                Log.v("월간 버튼","누름");
            }
        });

        SimpleDateFormat fm1 = new SimpleDateFormat("yyyy-MM");
        String date = fm1.format(new Date());
        Calendar calendar = Calendar.getInstance();

        Log.v(calendar.getActualMaximum(Calendar.DATE)+"","");
        sql = testDB.getWritableDatabase();
        sql.execSQL("DROP TABLE IF EXISTS member2");
        sql.execSQL("create table member2 (_id INTEGER PRIMARY KEY AUTOINCREMENT, lux_val char(20), Testdate date(10))");
        for(int i = 1; i <= calendar.getActualMaximum(Calendar.DATE); i++)
        {
            if(i < 10)
            {
                sql.execSQL("INSERT INTO member2 VALUES(null,0,'"
                        + date + "-0"+ i +"');"
                );
            }else
            {
                sql.execSQL("INSERT INTO member2 VALUES(null,0,'"
                        + date + "-" + i + "');"
                );
            }
        }


    }
}
