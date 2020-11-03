package mk.reu.flying.fish.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameOverActivity extends AppCompatActivity {

    private Button startGame;
    private TextView score;
    private String Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Score = getIntent().getExtras().get("totalscore").toString();

        startGame = findViewById(R.id.play_again);

        score =findViewById(R.id.tv_score);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(GameOverActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

        score.setText("Score: " +Score);
    }
}
