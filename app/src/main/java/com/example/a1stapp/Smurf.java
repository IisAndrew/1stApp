package com.example.a1stapp;

// Created by TanSiewLan20201
// Sample Entity 

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.SurfaceView;

import java.util.Random;

public class Smurf implements EntityBase, Collidable{

    private Bitmap bmp = null;

    private int xPos = 0;
    private float xStart = 0;
    private int yPos = 0;
    private float screenHeight = 0;
    private float speed = 0;
    private boolean isDone = false;
    private boolean isInit = false;

    private Sprite spritesmurf = null;   // New on Week 8

    private boolean hasTouched = false; // New to Week 8

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

        // New method using our own resource manager : Returns pre-loaded one if exists
        //bmp = ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite);

 

        //isInit = true;

        //bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.ship2_1);
        bmp = ResourceManager.Instance.GetBitmap( R.drawable.ship2_1);

        // New to Week 8
        // Using Sprite animation class to load our sprite sheet
        spritesmurf = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite),4,4, 16 );

        // Define how we want the player to react or if it is enemy or obstacles, how it is going to appear as.
        // You should have this part!
        Random ranGen = new Random();
        xPos = ranGen.nextInt() * _view.getWidth();
        yPos = ranGen.nextInt() * _view.getHeight();


    }

    @Override
    public void Update(float _dt) {

        spritesmurf.Update(_dt);

        if(TouchManager.Instance.IsDown()){   // Previous and it is for just a touch - useful for collision with a image (example button)

            // Check collision
            float imgRadius = bmp.getHeight() * 0.5f;
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos,yPos, imgRadius))
            {
                SetIsDone(true); // // <--- This part here or this code, meant when time is up, kill the items.
                // If it is a button --> Going to another state
            }

        }
    }

    @Override
    public void Render(Canvas _canvas) {

        //Matrix transform = new Matrix();
        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);

        //transform.postTranslate(xPos, yPos);
        //_canvas.drawBitmap(bmp, transform, null);

        // New to Week 8
        spritesmurf.Render(_canvas, xPos, yPos);  // Location can be changed!

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
    public boolean IsInit() {

        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.SMURF_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_SMURF;}

    public static Smurf Create()
    {
        Smurf result = new Smurf();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_SMURF);
        return result;
    }

    @Override
    public String GetType() {
        return "Smurf";
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
        if(_other.GetType() != this.GetType()
                && _other.GetType() !=  "Smurf") {  // Another entity
            SetIsDone(true);
        }
    }

}
