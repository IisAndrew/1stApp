package com.example.a1stapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

import javax.xml.transform.Result;

public class RenderBackground implements EntityBase{

    private boolean isDone = false;
    private Bitmap bmp = null;
    private Bitmap Scaledbmp = null;
    private SurfaceView view = null;
    private float xPos, yPos;
    private int ScreenWidth, ScreenHeight;

    private Bitmap Ship = null;

    @Override
    public boolean IsDone(){
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone){
        isDone = _isDone;
    }

    @Override
    // TO initialise or load resources
    public void Init(SurfaceView _view) {
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.newbg);
        //Ship = ResourceManager.Instance.GetBitmap(R.drawable.heli);

        // Finding the screen width & height to allow the images to scale according to it.
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        Scaledbmp = Bitmap.createScaledBitmap(bmp, ScreenWidth, ScreenHeight, true);
        //Scaledbmp2 = Bitmap.createScaledBitmap(Ship, ScreenWidth, ScreenHeight, true);
    }

    @Override
    public void Update(float _dt){
        if(GameSystem.Instance.GetIsPaused())
            return;
        yPos  += _dt * 200; // How fast you want to pan the screen

        if (yPos > ScreenHeight){
            yPos = 0;
        }
    }

    @Override
    public void Render(Canvas _canvas){
        _canvas.drawBitmap(Scaledbmp, xPos, yPos, null); // 1st image
        _canvas.drawBitmap(Scaledbmp, xPos, yPos - ScreenHeight, null);

        Matrix transform = new Matrix();
        //transform.postTranslate(200, 200);
       // transform.postRotate(30);
        //_canvas.drawBitmap(Ship, transform, null);
        //_canvas.drawBitmap(Ship, 500, 500, null);
    }

    @Override
    public boolean IsInit(){
        return bmp != null;
    }

    @Override
    public int GetRenderLayer(){
        return LayerConstants.BACKGROUND_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer){
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_DEFAULT;
    }

    public static RenderBackground Create(){
        RenderBackground result = new RenderBackground();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

}
