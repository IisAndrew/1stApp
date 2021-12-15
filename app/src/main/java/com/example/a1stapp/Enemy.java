package com.example.a1stapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.SurfaceView;

import java.util.Random;

public class Enemy implements EntityBase, Collidable{

    private Bitmap bmp = null;

    private int xPos = 0;
    private float xStart = 0;
    private int yPos = 0;
    private float screenHeight = 0;
    private float speed = 0;
    private boolean isDone = false;
    private boolean isInit = false;
    private static float angleToPlayer = 0;

    //private Sprite enemysprite = null;

    private boolean hasTouched = false;

    int ScreenWidth, ScreenHeight;

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {

        //bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.ship2_1);
        bmp = ResourceManager.Instance.GetBitmap( R.drawable.enemysprite);

        // Using Sprite animation class to load our sprite sheet
        //enemysprite = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.enemysprite),4,4, 16 );

        // Define how we want the player to react or if it is enemy or obstacles, how it is going to appear as.
        // You should have this part!
        //Random ranGen = new Random();
        //xPos = ranGen.nextInt(_view.getWidth());
        xPos = 400;
        yPos = 500;

    }

    @Override
    public void Update(float _dt) {
        if(GameSystem.Instance.GetIsPaused())
            return;
        angleToPlayer = (float) (Math.atan((Ship.GetPosX() - xPos)/(Ship.GetPosY() - yPos))*(180/Math.PI));
        //enemysprite.Update(_dt);

        //yPos ++;
        //Bullet.Create(xPos, yPos);
    }

    @Override
    public void Render(Canvas _canvas) {

        Matrix transform = new Matrix();

        transform.setRotate(360 - angleToPlayer);
        transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);

        transform.postTranslate(xPos, yPos);
        _canvas.drawBitmap(bmp, transform, null);

        //enemysprite.Render(_canvas, xPos, yPos);  // Location can be changed!
        //_canvas.drawBitmap(bmp, xPos, yPos, null);
    }

    @Override
    public boolean IsInit() {

        return bmp != null;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.ENEMY_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_ENEMY;}

    public static Enemy Create()
    {
        Enemy result = new Enemy();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_ENEMY);
        return result;
    }

    @Override
    public String GetType() {
        return "Enemy";
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {
        return yPos;
    }

    @Override
    public float GetRadius() {
        return bmp.getWidth();
    }

    public static float GetAngle() {
        return angleToPlayer;
    }
    @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() !=  "Bullet") {  // Another entity
            SetIsDone(true);
        }
    }

}
