package ru.mirea.laricheva.thread;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;

import ru.mirea.laricheva.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        TextView infoTextView = binding.textViewAverage;
        Thread mainThread = Thread.currentThread();
        infoTextView.setText("Имя текущего потока: " + mainThread.getName());
        mainThread.setName("МОЙ НОМЕР ГРУППЫ: БСБО-01-20, НОМЕР ПО СПИСКУ: 17");
        infoTextView.append("\nНовое имя потока: " + mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(), "Stack: " + Arrays.toString(mainThread.getStackTrace()));

        binding.buttonCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        try
                        {
                            EditText edCountLesson = findViewById(R.id.editTextLessonCount);
                            float countLesson = parseFloat(String.valueOf(edCountLesson.getText()));
                            EditText edCountDays = findViewById(R.id.editTextDaysCount);
                            float countDays = parseFloat(String.valueOf(edCountDays.getText()));
                            TextView tv = findViewById(R.id.textView3);
                            tv.setText(format("Среднее количество пар в день: %f", countLesson / countDays));
                        }
                        finally {

                        }

                        int numberThread = counter++;
                        Log.d("ThreadProject", format("Запущен поток №%d студентом группы №%s номер по " +
                                "списку №%d ", numberThread, "БСБО-01-20", 17));
                        long endTime = System.currentTimeMillis() + 20 * 1000;
                        while (System.currentTimeMillis() < endTime) {
                            synchronized (this) {
                                try {
                                    wait(endTime - System.currentTimeMillis());
                                    Log.d(MainActivity.class.getSimpleName(), "Endtime: " + endTime);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            Log.d("ThreadProject", "Выполнен поток № " + numberThread);
                        }


                    }

                }).start();


            }
        });

        setContentView(binding.getRoot());
    }
}