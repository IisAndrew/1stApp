package com.example.a1stapp;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;

public class PauseMenu extends Activity implements OnClickListener, StateBase{

    private Button btn_continue;
    private Button btn_quit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //setContentView(R.layout.pausepage);

        btn_continue = (Button)findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(this); //Set Listener to this button --> Start Button

        btn_quit = (Button)findViewById(R.id.btn_quit);
        btn_quit.setOnClickListener(this); //Set Listener to this button --> Back Button

        //StateManager.Instance.AddState(new Mainmenu());
    }

    @Override
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent

        Intent intent = new Intent();

        if (v == btn_continue)
        {
            // intent --> to set to another class which another page or screen that we are launching.
            intent.setClass(this, GamePage.class);
            StateManager.Instance.ChangeState("MainGame"); // Default is like a loading page

        }

        else if (v == btn_quit)
        {
            //intent.setClass(this, MainMenu.class);
            intent.setClass(this, MainMenu.class);
            StateManager.Instance.ChangeState("MainMenu");
        }
        startActivity(intent);
    }

    @Override
    public void Render(Canvas _canvas) {
    }

    @Override
    public void OnEnter(SurfaceView _view) {
    }

    @Override
    public void OnExit() {
    }

    @Override
    public void Update(float _dt) {
    }

    @Override public String GetName() {
        return "PauseMenu";
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
