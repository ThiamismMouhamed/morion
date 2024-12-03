package com.example.morpion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Lancer la version 3x3
    public void launchVersion3x3(View view) {
        Intent intent = new Intent(this, Morpion3x3Activity.class);
        startActivity(intent);
    }

    // Lancer la version 4x4
    public void launchVersion4x4(View view) {
        Intent intent = new Intent(this, Morpion4x4Activity.class);
        startActivity(intent);
    }

    // Quitter l'application
    public void quitApp(View view) {
        finish(); // Termine l'activité actuelle
        System.exit(0); // Quitte complètement l'application
    }
}