package com.example.morpion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Morpion4x4Activity extends AppCompatActivity {

    private int[] tv = new int[16]; // État des cases
    private Button[] tb = new Button[16]; // Tableau des boutons
    private boolean isPlayerX = true; // Alternance entre joueur X et O

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morpion_4x4);

        // Initialisation des boutons
        for (int i = 0; i < 16; i++) {
            String buttonID = "pion" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            tb[i] = findViewById(resID);
            tb[i].setTag(String.valueOf(i));
            tb[i].setOnClickListener(this::clic);
        }

        // Configuration des boutons
        findViewById(R.id.buttonRejouer).setOnClickListener(view -> resetGame());
        findViewById(R.id.buttonMenu).setOnClickListener(view -> finish());

    }

    private void clic(View view) {
        String key = (String) view.getTag();
        int position = Integer.parseInt(key);

        if (tv[position] == 0) {
            tv[position] = isPlayerX ? 1 : 2;
            Button button = (Button) view;

            if (isPlayerX) {
                button.setBackgroundResource(R.drawable.player_x); // Image pour X
            } else {
                button.setBackgroundResource(R.drawable.player_o); // Image pour O
            }

            TextView status = findViewById(R.id.joueurStatus);
            status.setText(isPlayerX ? "Joueur 2 joue" : "Joueur 1 joue");

            isPlayerX = !isPlayerX;
            checkGameStatus();
        }
    }

    private void checkGameStatus() {
        int[][] winConditions = {
                {0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}, // Lignes
                {0, 4, 8, 12}, {1, 5, 9, 13}, {2, 6, 10, 14}, {3, 7, 11, 15}, // Colonnes
                {0, 5, 10, 15}, {3, 6, 9, 12} // Diagonales
        };

        for (int[] condition : winConditions) {
            if (tv[condition[0]] == tv[condition[1]] && tv[condition[1]] == tv[condition[2]] &&
                    tv[condition[2]] == tv[condition[3]] && tv[condition[0]] != 0) {
                String winner = tv[condition[0]] == 1 ? "Joueur 1 a gagné !" : "Joueur 2 a gagné !";
                showWinner(winner);
                return;
            }
        }

        // Vérification de l'égalité
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
        findViewById(R.id.joueurStatus).setVisibility(View.GONE);

        for (Button button : tb) {
            button.setEnabled(false);
        }

        findViewById(R.id.buttonRejouer).setVisibility(View.VISIBLE);
        findViewById(R.id.buttonMenu).setVisibility(View.VISIBLE);
    }

    private void resetGame() {
        for (int i = 0; i < tv.length; i++) {
            tv[i] = 0;
            tb[i].setEnabled(true);
            tb[i].setBackgroundResource(android.R.drawable.btn_default);
        }

        isPlayerX = true;
        findViewById(R.id.joueurStatus).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.joueurStatus)).setText("Joueur 1 joue");
        findViewById(R.id.buttonRejouer).setVisibility(View.GONE);
        findViewById(R.id.buttonMenu).setVisibility(View.GONE);
    }
}