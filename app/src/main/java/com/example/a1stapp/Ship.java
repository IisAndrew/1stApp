package com.example.a1stapp;

// Created by TanSiewLan20201
// Sample Entity

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import java.util.Random;

public class Ship implements EntityBase {
    private boolean isDone = false;
    private Bitmap bmp = null, scaledbmp = null;
    int ScreenWidth, ScreenHeight;
    private static float xPos, yPos, offset;
    private SurfaceView view = null;
    Matrix tfx = new Matrix();
    DisplayMetrics metrics;

    private boolean isInit = false;

    private Sprite spritesmurf = null;   // New on Week 8

    private boolean hasTouched = false; // New to Week 8

    private float lifetime;

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

        // New method using our own resource manager : Returns pre-loaded one if exists
        //bmp = ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite);

        //isInit = true;

        //bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.heli);
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.heli);

        // New to Week 8
        // Using Sprite animation class to load our sprite sheet
        spritesmurf = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.heli), 1, 1, 16);

        //Find the surfaceview size or screensize
        metrics = _view.getResources().getDisplayMetrics();
        ScreenHeight = metrics.heightPixels/5;
        ScreenWidth = metrics.widthPixels/5;
        scaledbmp = Bitmap.createScaledBitmap(bmp, ScreenWidth, ScreenHeight, true);

        // Define how we want the player to react or if it is enemy or obstacles, how it is going to appear as.
        // You should have this part!
        Random ranGen = new Random();
        xPos = ranGen.nextInt() * _view.getWidth();
        yPos = ranGen.nextInt() * _view.getHeight();

        lifetime = 30.0f;
    }

    @Override
    public void Update(float _dt) {
        if(GameSystem.Instance.GetIsPaused())
            return;
        spritesmurf.Update(_dt);

        lifetime -= _dt;
        if (lifetime < 0.0f)
            //SetIsDone(true);

        /*if(TouchManager.Instance.IsDown()){   // Previous and it is for just a touch - useful for collision with a image (example button)

            // Check collision
            float imgRadius = bmp.getHeight() * 0.5f;
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos,yPos, imgRadius))
            {
                SetIsDone(true); // // <--- This part here or this code, meant when time is up, kill the items.
                // If it is a button --> Going to another state
            }
        }*/

            // New Week 8
            if (TouchManager.Instance.HasTouch())  // Touch and drag
            {
                // Check collision with the smurf sprite
                float imgRadius1 = spritesmurf.GetWidth() * 0.5f;
                if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos,yPos, imgRadius1) || hasTouched)
                {
                    hasTouched = true;
                    xPos = TouchManager.Instance.GetPosX();
                    yPos = TouchManager.Instance.GetPosY();
                }
            }
    }

    @Override
    public void Render(Canvas _canvas) {

        //Matrix transform = new Matrix();
        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);

        //transform.postTranslate(xPos, yPos);
        // New to Week 8
        spritesmurf.Render(_canvas, (int)xPos, (int)yPos);  // Location can be changed!

        _canvas.drawBitmap(bmp, 350, 1000, null);

    }

    @Override
    public boolean IsInit() {
        return bmp != null;
    }

    @Override
    public int GetRenderLayer() {return LayerConstants.SHIP_LAYER;}

    @Override
    public void SetRenderLayer(int _newLayer){
    }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_DEFAULT;}

    public static Ship Create() {
        Ship result = new Ship();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    public static float GetPosX() {
        return xPos;
    }

    public static float GetPosY() {
        return yPos;
    }

    /*@Override
    public String GetType() {
        return "Smurf";
    }


    @Override
    public float GetRadius() {
        return bmp.getWidth();
    }

    @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() != this.GetType()
                && _other.GetType() !=  "Smurf") {  // Another entity
            SetIsDone(true);
        }
    }*/
}

