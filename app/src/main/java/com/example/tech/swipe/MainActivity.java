package com.example.tech.swipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.tech.swipe.MainMenuFragment.OnMainMenuButtonClicked;
public class MainActivity extends FragmentActivity implements OnMainMenuButtonClicked {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showMainMenu();

    }


    private void showMainMenu() {
        MainMenuFragment fragment = new MainMenuFragment();
        FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main, fragment, "MainMenu");
        transaction.addToBackStack("MainMenu");
        transaction.commit();
    }

//    public void onClickStartBtn(View v)
//    {
//        Intent i = new Intent(getApplicationContext(),Game_Screen.class);
//        startActivity(i);
//    }

    @Override
    public void onClickStartBtn() {
//        Game_Fragment gameFragment = new Game_Fragment();
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.activity_main, gameFragment, "Game");
//        transaction.addToBackStack("Game");
//        transaction.commit();
                Intent i = new Intent(getApplicationContext(),Game_Screen.class);
        startActivity(i);
    }


//    @Override
//    public void onGameOver(int score) {
//        ResultsFragment resultsFragment = new ResultsFragment();
//        resultsFragment.setScore(score);
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.activity_main, resultsFragment, "Results");
//        transaction.commit();
//    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag("Results") != null) {
            getSupportFragmentManager().popBackStack();
            showMainMenu();
        } else {
            super.onBackPressed();
        }
    }

}
