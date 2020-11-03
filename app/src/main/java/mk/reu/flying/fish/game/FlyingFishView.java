package mk.reu.flying.fish.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class FlyingFishView extends View
{

    private Bitmap fish[] = new Bitmap[22];
    private Bitmap bgImage;
    private Paint scorePaint  = new Paint();

    private Paint powered = new Paint();

    private Bitmap life [] = new Bitmap[2];

    private  int fshX=10;
    private  int fishY;
    private int fishSpeed;

    private  int yellowX,yellowY,yellowSpeed = 20;
    private  Paint yellowPaint =new Paint();

    private  int greenX,greenY,greenSpeed =38;
    private  Paint greenPaint = new Paint();


    private  int redX,redY,redSpeed =53;
    private  Paint redPaint = new Paint();




    private  int canvasWidth,canvasHeight;

    private boolean touch =false;
    private int score,lifeCounter;

    public FlyingFishView(Context context)

    {
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(),R.drawable.fish2);

        bgImage=BitmapFactory.decodeResource(getResources(),R.drawable.background);
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);


        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);


        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(50);
        scorePaint.setTypeface(Typeface.DEFAULT);
        scorePaint.setAntiAlias(true);


        powered.setColor(Color.WHITE);
        powered.setTextSize(35);
        powered.setTypeface(Typeface.DEFAULT_BOLD);
        powered.setAntiAlias(true);







        life[0]= BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishY =550;
        score =0;
        lifeCounter = 3;




    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvasWidth =canvas.getWidth();
        canvasHeight=canvas.getHeight();


        canvas.drawBitmap(bgImage,0,0,null);

        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;
        fishY =fishY+fishSpeed;

        if (fishY <minFishY)
        {
            fishY = minFishY;
        }
        if (fishY >maxFishY)
        {
            fishY = maxFishY;
        }
        fishSpeed =fishSpeed +2;
        if (touch)
        {
            canvas.drawBitmap(fish[1],fshX,fishY,null);
            touch =false;
        }
        else
        {
            canvas.drawBitmap(fish[0],fshX,fishY,null);
        }

        yellowX = yellowX -yellowSpeed;

        if (hitBallChecker(yellowX,yellowY))
        {
            score = score +5;
            yellowX = -100;
        }

        if (yellowX< 0)
        {
            yellowX = canvasWidth +21;
            yellowY = (int) Math.floor(Math.random()*(maxFishY -minFishY)) + minFishY;
        }
        canvas.drawCircle( yellowX,yellowY,25,yellowPaint);


        greenX = greenX -greenSpeed;

        if (hitBallChecker(greenX,greenY))
        {
            score = score +20;
            greenX = -100;
        }

        if (greenX< 0)
        {
            greenX = canvasWidth +21;
            greenY = (int) Math.floor(Math.random()*(maxFishY -minFishY)) + minFishY;
        }
        canvas.drawCircle( greenX,greenY,25,greenPaint);


        redX = redX -redSpeed;

        if (hitBallChecker(redX,redY))
        {

            redX = -100;
            lifeCounter --;
            if (lifeCounter == 0)
            {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();
                Intent gameOverIntent = new Intent(getContext(),GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(getContext(), "Total Score "+score, Toast.LENGTH_SHORT).show();
                gameOverIntent.putExtra("totalscore",score);
                getContext().startActivity(gameOverIntent);
            }
        }

        if (redX< 0)
        {
            redX = canvasWidth +21;
            redY = (int) Math.floor(Math.random()*(maxFishY -minFishY)) + minFishY;
        }
        canvas.drawCircle( redX,redY,32,redPaint);

        canvas.drawText("Score:" +score ,20,60,scorePaint);


        canvas.drawText("Powered by mk_reuben" ,500,2200,powered);



        for (int i=0; i<3; i++)
        {
            int x = (int)(295+life[0].getWidth()*1.3 * i);
            int y = 30;

            if (i < lifeCounter)
            {

                canvas.drawBitmap(life[0] ,x,y ,null);
            }
            else
            {

                canvas.drawBitmap(life[1] ,x,y ,null);
            }
        }


    }


    public  boolean hitBallChecker(int x,int y)
    {
        if (fshX<x && x <(fshX + fish[0].getWidth()) && fishY <y && y <(fishY + fish [0].getHeight()))
        {
            return true;
        }
        return  false;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction()== MotionEvent.ACTION_DOWN)
        {
            touch=true;

            fishSpeed = -22;
        }
        return true;
    }
}
