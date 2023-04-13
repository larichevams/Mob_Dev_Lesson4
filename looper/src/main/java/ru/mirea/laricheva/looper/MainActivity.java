package ru.mirea.laricheva.looper;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import ru.mirea.laricheva.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.d(MainActivity.class.getSimpleName(), "Task execute. This is result: "
                        + msg.getData().getString("result"));
            }
        };

        MyLooper myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();

//        binding.editText.setText("Мой номер по списку №17");
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                String word = binding.editText.getText().toString();
                bundle.putString("KEY", word);
                msg.setData(bundle);
                myLooper.mHandler.sendMessage(msg);
            }
        });

        final Runnable runn = new Runnable() {
            public void run() {
                binding.textView3.setText(format("Ваше место работы: %s", String.valueOf(binding.editTextWork.getText())));
            }
        };


        binding.buttonThreadAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int age = parseInt(String.valueOf(binding.editTextAge.getText()));
                Log.d(MainActivity.class.getSimpleName(), "Ваш возраст: " + String.valueOf(binding.editTextAge.getText()));

                Thread t = new Thread(new Runnable() {
                    public void run() {
                        binding.editTextWork.postDelayed(runn, 1000 * age);
                    }
                });
                t.start();
            }
        });
        
    }
}