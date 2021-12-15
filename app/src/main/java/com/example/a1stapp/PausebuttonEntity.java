package com.example.a1stapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class PausebuttonEntity implements EntityBase{

    private Bitmap bmpP = null;
    private Bitmap bmpUP = null;

    private Bitmap ScaledbmpP = null;
    private Bitmap ScaledbmpUP = null;

    private float xPos = 0;
    private float yPos = 0;

    private boolean isDone = false;
    private boolean isInit = false;
    private boolean Paused = false;

    float ScreenWidth, ScreenHeight;

    private float buttonDelay = 0;

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

//        bmpP = ResourceManager.Instance.GetBitmap(R.drawable.pausebutton);
//        bmpUP = ResourceManager.Instance.GetBitmap(R.drawable.pausebutton);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        ScaledbmpP = Bitmap.createScaledBitmap(bmpP, (int) (ScreenWidth)/7, (int)(ScreenWidth)/7, true);
        ScaledbmpUP = Bitmap.createScaledBitmap(bmpUP, (int) (ScreenWidth)/7, (int)(ScreenWidth)/7, true);

        xPos = 400;
        yPos = 100;

        isInit = true;
    }

    @Override
    public void Update(float _dt) {
        buttonDelay += _dt;

        if(TouchManager.Instance.HasTouch())
        {
            if(TouchManager.Instance.IsDown() && !Paused)
            {
                // Check Collision of button here!!
                float imgRadius = ScaledbmpP.getHeight() * 0.5f;

                if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos,yPos, imgRadius) && buttonDelay >= 0.25)
                {
                    Paused = true;
                    StateManager.Instance.ChangeState("MainGame");
                    GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
                }
                buttonDelay = 0;

            }
            else
                Paused = false;
        }
    }

    @Override
    public void Render(Canvas _canvas) {

        if (Paused == false)
            _canvas.drawBitmap(ScaledbmpP,xPos - ScaledbmpP.getWidth() * 0.5f, yPos - ScaledbmpP.getHeight() * 0.5f, null);
        else
            _canvas.drawBitmap(ScaledbmpUP,xPos - ScaledbmpUP.getWidth() * 0.5f, yPos - ScaledbmpUP.getHeight() * 0.5f, null);
    }

    @Override
    public boolean IsInit() {

        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.PAUSEBUTTON_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_PAUSE;}

    public static PausebuttonEntity Create()
    {
        PausebuttonEntity result = new PausebuttonEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PAUSE);
        return result;
    }
}