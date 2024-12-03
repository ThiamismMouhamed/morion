package com.example.morpion;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

public class Morpion3x3Activity extends AppCompatActivity {

    private int[] tv = {0, 0, 0, 0, 0, 0, 0, 0, 0}; // État des cases
    private Button[] tb = new Button[9]; // Tableau des boutons
    private boolean isPlayerX = true; // Alternance entre joueur X et O

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morpion_3x3); // Nouveau layout pour 3x3

        // Initialiser les boutons
        tb[0] = findViewById(R.id.poin0);
        tb[1] = findViewById(R.id.pion1);
        tb[2] = findViewById(R.id.pion2);
        tb[3] = findViewById(R.id.pion3);
        tb[4] = findViewById(R.id.pion4);
        tb[5] = findViewById(R.id.pion5);
        tb[6] = findViewById(R.id.pion6);
        tb[7] = findViewById(R.id.pion7);
        tb[8] = findViewById(R.id.pion8);

        // Ajouter des tags et un onClickListener
        for (int i = 0; i < tb.length; i++) {
            tb[i].setTag(String.valueOf(i));
            tb[i].setOnClickListener(this::clic);
        }

        // Configurer les boutons "Rejouer" et "Quitter"
        Button buttonRejouer = findViewById(R.id.buttonRejouer);
        Button buttonQuitter = findViewById(R.id.buttonQuitter);

        buttonRejouer.setOnClickListener(view -> resetGame());
        buttonQuitter.setOnClickListener(view -> finish());
    }

    private void clic(View view) {
        String key = (String) view.getTag();
        int position = Integer.parseInt(key);

        if (tv[position] == 0) {
            tv[position] = isPlayerX ? 1 : 2;
            Button button = (Button) view;

            if (isPlayerX) {
                button.setBackgroundResource(R.drawable.player_x); // Image pour "X"
            } else {
                button.setBackgroundResource(R.drawable.player_o); // Image pour "O"
            }

            TextView status = findViewById(R.id.joueurStatus);
            if (isPlayerX) {
                status.setText("Joueur 2 joue");
            } else {
                status.setText("Joueur 1 joue");
            }

            isPlayerX = !isPlayerX;
            checkGameStatus();
        } else {
            Toast.makeText(this, "Case déjà prise !", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkGameStatus() {
        int[][] winConditions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Lignes
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Colonnes
                {0, 4, 8}, {2, 4, 6}             // Diagonales
        };

        for (int[] condition : winConditions) {
            if (tv[condition[0]] == tv[condition[1]] && tv[condition[1]] == tv[condition[2]] && tv[condition[0]] != 0) {
                String winner = (tv[condition[0]] == 1) ? "Joueur 1 a gagné !" : "Joueur 2 a gagné !";
                showWinner(winner);
                return;
            }
        }

        boolean isDraw = true;
        for (int value : tv) {
            if (value == 0) {
                isDraw = false;
                break;
            }
        }

        if (isDraw) {
            showWinner("Match nul !");
        }
    }

    private void showWinner(String winner) {
        TextView status = findViewById(R.id.idJoueur);
        status.setText(winner);
        status.setVisibility(View.VISIBLE);

        findViewById(R.id.joueurStatus).setVisibility(View.GONE);

        for (Button button : tb) {
            button.setEnabled(false);
        }

        findViewById(R.id.buttonRejouer).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonQuitter).setVisibility(View.VISIBLE);
    }

    private void resetGame() {
        for (int i = 0; i < tv.length; i++) {
            tv[i] = 0;
        }

        for (Button button : tb) {
            button.setText("");
            button.setEnabled(true);
            button.setBackgroundResource(android.R.drawable.btn_default);
        }

        isPlayerX = true;

        TextView status = findViewById(R.id.idJoueur);
        status.setVisibility(View.GONE);

        TextView joueurStatus = findViewById(R.id.joueurStatus);
        joueurStatus.setText("Joueur 1 joue");
        joueurStatus.setVisibility(View.VISIBLE);

        findViewById(R.id.buttonRejouer).setVisibility(View.GONE);
        findViewById(R.id.buttonQuitter).setVisibility(View.GONE);
    }

}
