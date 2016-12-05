//package com.example.tech.swipe.ui;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.tech.swipe.R;
//import com.example.tech.swipe.SwipeListener1;
//
//import static com.example.tech.swipe.R.layout.game_activity;
//
//
//public class Game_Fragment extends Fragment {
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(game_activity);
//        View myView = (View) findViewById(R.id.myView);
//        myView.setOnTouchListener(new SwipeListener1(this) {
//            @Override
//            public boolean onSwipe(Direction d) {
//
//                if (d == d.up) {
//                    cancelTimer();
//                    Log.d(DEBUG_TAG, "up " + d);
//                    i++;
//                } else if (d == d.down) {
//                    cancelTimer();
//                    Log.d(DEBUG_TAG, "down " + d);
//                    i++;
//                }
//                startTimer(i);
//
//                return false;
//            }
//
//        });
//        // mDetector = new GestureDetectorCompat(this, new MyGestureListener());
//    }
//
////    private GameOver gameOverListener;
////
////    public interface GameOver {
////        public void onGameOver(int score);
////    }
////
////    @Override
////    public View onCreateView(LayoutInflater inflater, ViewGroup container,
////                             Bundle savedInstanceState) {
////        View v = inflater.inflate(R.layout.fragment_game_, null);
//////
//////        Game_UI gameui = (Game_UI) v.findViewById(R.id.gamescreen);
//////        gameui.setGameOverListener(gameOverListener);
////
////        return v;
////    }
//
//
//
//}
