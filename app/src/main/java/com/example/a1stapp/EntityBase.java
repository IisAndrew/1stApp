package com.example.a1stapp;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public interface EntityBase
{

    //used for entities such as background
    enum ENTITY_TYPE{
        //ENT_PLAYER,
        //ENT_SMURF,
        ENT_PAUSE,
         ENT_SHIP,
        //ENT_TEXT,
        //ENT_NEXT,
        ENT_DEFAULT,
        ENT_ENEMY,
        ENT_BULLET,
    }

    boolean IsDone();
    void SetIsDone(boolean _isDone);

    void Init(SurfaceView _view);
    void Update(float _dt);
    void Render(Canvas _canvas);

    boolean IsInit();

    int GetRenderLayer();
    void SetRenderLayer(int _newLayer);

	ENTITY_TYPE GetEntityType();
}
