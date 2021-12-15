package com.example.a1stapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.SurfaceView;

import java.util.Random;

public class Bullet implements EntityBase, Collidable{

    private static float xSpawnPos;
    private static float ySpawnPos;
    private Bitmap bmp = null;

    private float xPos = 0;
    private float xStart = 0;
    private float yPos = 0;
    private float screenHeight = 0;
    private float speed = 0;
    private boolean isDone = false;
    private boolean isInit = false;
    private float xIncr, yIncr , bulletSpeed;
    private Sprite bullet = null;

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
        bmp = ResourceManager.Instance.GetBitmap( R.drawable.bullet);

        // Using Sprite animation class to load our sprite sheet
        //bullet = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.bullet),4,4, 16 );

        // Define how we want the player to react or if it is enemy or obstacles, how it is going to appear as.
        // You should have this part!
        //Random ranGen = new Random();
        //xPos = ranGen.nextInt() * _view.getWidth();
        //yPos = ranGen.nextInt() * _view.getHeight();
        bulletSpeed = 50;
        yIncr = (float) (Math.acos(Enemy.GetAngle())*bulletSpeed);
        xIncr = (float) (Math.asin(Enemy.GetAngle())*bulletSpeed);

        xPos = xSpawnPos - 92;
        yPos = ySpawnPos;
    }

    @Override
    public void Update(float _dt) {
        if(GameSystem.Instance.GetIsPaused())
            return;
        //bullet.Update(_dt);

        //yPos += 50 *_dt;
        yPos += yIncr * _dt;
        xPos += xIncr * _dt;
    }

    @Override
    public void Render(Canvas _canvas) {

        Matrix transform = new Matrix();
        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);

        transform.postTranslate(xPos, yPos);
        _canvas.drawBitmap(bmp, transform, null);

        //bullet.Render(_canvas, (int)xPos, (int)yPos);  // Location can be changed!
    }

    @Override
    public boolean IsInit() {

        return bmp != null;
    }

    public static Bullet Create(float _xPos, float _yPos)
    {
        xSpawnPos = _xPos;
        ySpawnPos = _yPos;
        Bullet result = new Bullet();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_BULLET);
        return result;
    }
    @Override
    public int GetRenderLayer() {
        return LayerConstants.BULLET_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_BULLET;}

    @Override
    public String GetType() {
        return "Bullet";
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

    @Override
    public void OnHit(Collidable _other) {
//        if(_other.GetType() != this.GetType()) {  // Another entity
//            SetIsDone(true);
//        }
    }

}
