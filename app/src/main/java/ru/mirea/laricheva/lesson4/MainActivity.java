package ru.mirea.laricheva.lesson4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ru.mirea.laricheva.lesson4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    Fragment fragmentPlayer;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentPlayer = new PlayerFragment();
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MainActivity.class.getSimpleName(),"click mirea button");
            }
        });

        binding.buttonPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();
                Log.d(MainActivity.class.getSimpleName(),"click player button");
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragmentPlayer).commit();
            }
        });

        setContentView(binding.getRoot());
    }
}