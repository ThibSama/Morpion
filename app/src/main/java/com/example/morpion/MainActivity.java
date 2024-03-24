package com.example.morpion;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private int currentPlayer = 1; // 1 pour Joueur 1, 2 pour Joueur 2
    private final Button[][] buttons = new Button[3][3];
    private TextView currentPlayerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Récupérez la référence de la GridLayout
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        // Créez et ajoutez des boutons à la grille
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button(this);
                button.setLayoutParams(new GridLayout.LayoutParams(
                        GridLayout.spec(i, GridLayout.FILL, 1f),
                        GridLayout.spec(j, GridLayout.FILL, 1f)
                ));
                button.setTag(i * 3 + j);
                button.setOnClickListener(buttonClickListener);
                gridLayout.addView(button);
                buttons[i][j] = button;
            }
        }

        // Appliquez le padding pour les barres système
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Mettre à jour le TextView avec le joueur actuel
    @SuppressLint("SetTextI18n")
    private void updateCurrentPlayerText() {
        currentPlayerTextView.setText("Joueur actuel : " + currentPlayer);
    }
    private void checkWinner() {
        // Vérifiez les lignes
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][0].getText().toString().isEmpty() &&
                    buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][0].getText().equals(buttons[i][2].getText())) {
                // Un joueur a gagné
                announceWinner(buttons[i][0].getText().toString());
                return;
            }
        }

        // Vérifiez les colonnes
        for (int j = 0; j < 3; j++) {
            if (!buttons[0][j].getText().toString().isEmpty() &&
                    buttons[0][j].getText().equals(buttons[1][j].getText()) &&
                    buttons[0][j].getText().equals(buttons[2][j].getText())) {
                // Un joueur a gagné
                announceWinner(buttons[0][j].getText().toString());
                return;
            }
        }

        // Vérifiez les diagonales
        if (!buttons[0][0].getText().toString().isEmpty() &&
                buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[0][0].getText().equals(buttons[2][2].getText())) {
            // Un joueur a gagné
            announceWinner(buttons[0][0].getText().toString());
            return;
        }

        if (!buttons[0][2].getText().toString().isEmpty() &&
                buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                buttons[0][2].getText().equals(buttons[2][0].getText())) {
            // Un joueur a gagné
            announceWinner(buttons[0][2].getText().toString());
            return;
        }

        // Vérifiez s'il y a match nul
        boolean draw = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().isEmpty()) {
                    draw = false;
                    break;
                }
            }
        }
        if (draw) {
            announceWinner("Match nul");
        }
    }

    private void announceWinner(String winner) {
        // Affichez un message ou effectuez une action appropriée lorsque quelqu'un gagne
        // Ici, vous pouvez par exemple afficher un Toast
        Toast.makeText(this, "Le joueur " + winner + " a gagné!", Toast.LENGTH_SHORT).show();
        // Réinitialisez le jeu ou effectuez toute autre action que vous souhaitez après la fin de la partie
    }

    private final View.OnClickListener buttonClickListener = v -> {
        if (v instanceof Button) {
            Button button = (Button) v;
            int position = (int) button.getTag();

            if (button.getText().toString().isEmpty()) {
                if (currentPlayer == 1) {

                    button.setText("X");
                } else {
                    button.setText("O");
                }



                checkWinner(); // Vérifiez s'il y a un gagnant après chaque coup

                currentPlayer = (currentPlayer == 1) ? 2 : 1;
            }
        }
    };
}
