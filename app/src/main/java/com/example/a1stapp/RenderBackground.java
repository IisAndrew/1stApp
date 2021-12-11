package com.example.a1stapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
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
        Ship = ResourceManager.Instance.GetBitmap(R.drawable.splashheli);

        // Finding the screen width & height to allow the images to scale according to it.
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        Scaledbmp = Bitmap.createScaledBitmap(bmp, ScreenWidth, ScreenHeight, true);
    }

    @Override
    public void Update(float _dt){
        xPos  -= _dt * 500; // How fast you want to pan the screen

        if (xPos < -ScreenWidth){
            xPos = 0;
        }
    }

    @Override
    public void Render(Canvas _canvas){
        _canvas.drawBitmap(Scaledbmp, xPos, yPos, null); // 1st image
        _canvas.drawBitmap(Scaledbmp, xPos + ScreenWidth, yPos, null);

        Matrix transform = new Matrix();
        //transform.postTranslate(200, 200);
        transform.postRotate(30);
        _canvas.drawBitmap(Ship, transform, null);
        _canvas.drawBitmap(Ship, 500, 500, null);
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
