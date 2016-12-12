package com.example.tech.swipe;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.androidifygeeks.library.fragment.PageFragment;
import com.androidifygeeks.library.fragment.TabDialogFragment;
import com.androidifygeeks.library.iface.IFragmentListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements IFragmentListener {
    private Button button;
    final Context context = this;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1, time_limit, swipe_limit;
    private static final String TAG = MainActivity.class.getSimpleName();
    private final Set<Fragment> mMyScheduleFragments = new HashSet<>();
    private String userChoosenTask;
    private static ImageView ivImage;
    Bitmap bm = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button = (Button) findViewById(R.id.btn_custom);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                TabDialogFragment.createBuilder(MainActivity.this, getSupportFragmentManager())
                        .setTitle("Custom Games settings")
                        .setSubTitle("Custom Games")
                        .setTabButtonText(new CharSequence[]{"Timer", "Swipes", "Backgrounds"})
                        .show();
            }
        });
    }

    Intent game_screen;

    public void onClickStartBtn(View v) {
        game_screen = new Intent(getApplicationContext(), Game_Screen.class);
        //game_screen.putExtra("backgroundimage",bm);
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_level_selector);
        dialog.setTitle("How Brave are you? ");

        Button btn_easy = (Button) dialog.findViewById(R.id.btn_easy);
        btn_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level_selector(0);
                startActivity(game_screen);
            }
        });
        Button btn_medium = (Button) dialog.findViewById(R.id.btn_medium);
        btn_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level_selector(1);
                startActivity(game_screen);
            }
        });
        Button btn_hard = (Button) dialog.findViewById(R.id.btn_hard);
        btn_hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                level_selector(2);
                startActivity(game_screen);
            }
        });
        dialog.show();
    }

    private void level_selector(int level) {
        switch (level) {
            case 0:
                Game_Screen.swipe_area_sq_hieght = Game_Screen.swipe_area_sq_width = 900;
                break;
            case 1:
                Game_Screen.swipe_area_sq_hieght = Game_Screen.swipe_area_sq_width = 600;
                break;
            case 2:
                Game_Screen.swipe_area_sq_hieght = Game_Screen.swipe_area_sq_width = 300;
                break;
            default:
                break;
        }
    }


    //    public TextView txtView;
//    public void onClickCustomBtn() {
//
//        final Dialog levelDialog = new Dialog(context);
//        levelDialog.setContentView(R.layout.dialog_w_seekbar_for_timer);
//        final TextView txtView = (TextView) levelDialog.findViewById(R.id.Select_Seconds); // TextView
//        SeekBar seekbar = (SeekBar) levelDialog.findViewById(R.id.seekbar_set_seconds);
//        txtView.setText("0");
//
//        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
//
//            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
//
//            @Override
//
//            public void onProgressChanged(SeekBar seekBark, int progress, boolean fromUser) {
//                txtView.setText(Integer.toString(progress));
//                i = progress;
//            }
//        });
//          Button btn_Custom_play = (Button) levelDialog.findViewById(R.id.btn_Custom_play);
//         Button btn_Custom_Cancel = (Button) levelDialog.findViewById(R.id.btn_Custom_Cancel);
//
//         btn_Custom_play.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
//             Game_Screen.swipe_area_sq_hieght=Game_Screen.swipe_area_sq_width=300;
//            Game_Screen.count_down_timer=i*1000;
//             onClickStartBtn(null);
//
//         }});
//          btn_Custom_Cancel.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {levelDialog.dismiss();}});
//
//        levelDialog.show();
//    }


    @Override
    public void onFragmentViewCreated(Fragment fragment) {
        int selectedTabPosition = fragment.getArguments().getInt(PageFragment.ARG_DAY_INDEX, 0);
        View rootContainer = fragment.getView().findViewById(R.id.root_container);

        switch (selectedTabPosition) {
            case 0:
                selectedTabPositionZeroCase(rootContainer);
                break;
            case 1:
                selectedTabPositionOneCase(rootContainer);
                break;
            case 2:
                selectedTabPositionTwoCase(rootContainer);
                break;
            default:
                break;
        }
        Log.i(TAG, "Position: " + selectedTabPosition);


    }


    private void selectedTabPositionZeroCase(View rootContainer) {
        // add view in container for first tab
        View tabProductDetailLayout = getLayoutInflater().inflate(R.layout.dialog_w_seekbar_for_timer, (ViewGroup) rootContainer);

        final TextView txtView = (TextView) tabProductDetailLayout.findViewById(R.id.Select_Seconds); // TextView
        SeekBar seekbar = (SeekBar) tabProductDetailLayout.findViewById(R.id.seekbar_set_seconds);
        txtView.setText("0");
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBark, int progress, boolean fromUser) {
                txtView.setText(Integer.toString(progress));
                time_limit = progress;
            }
        });
        Button btn_Custom_play = (Button) tabProductDetailLayout.findViewById(R.id.btn_Custom_play);
        Button btn_Custom_Cancel = (Button) tabProductDetailLayout.findViewById(R.id.btn_Custom_Cancel);

        btn_Custom_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game_Screen.swipe_area_sq_hieght = Game_Screen.swipe_area_sq_width = 300;
                Game_Screen.count_down_timer = time_limit * 1000;
                onClickStartBtn(null);

            }
        });

    }


    private void selectedTabPositionOneCase(View rootContainer) {
        // add view in container for first tab
        View tabProductDetailLayout = getLayoutInflater().inflate(R.layout.dialog_w_seekbar_for_swipes, (ViewGroup) rootContainer);

        final TextView txtView = (TextView) tabProductDetailLayout.findViewById(R.id.Select_Swipes); // TextView
        SeekBar seekbar = (SeekBar) tabProductDetailLayout.findViewById(R.id.seekbar_set_swipes);
        txtView.setText("0");
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBark, int progress, boolean fromUser) {
                txtView.setText(Integer.toString(progress));
                swipe_limit = progress;
            }
        });
        Button btn_Custom_play = (Button) tabProductDetailLayout.findViewById(R.id.btn_Custom_play);

        btn_Custom_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game_Screen.swipe_limit = swipe_limit;
                Game_Screen.stop_watch = true;
                onClickStartBtn(null);

            }
        });
    }
    View tabProductDetailLayout;
    private void selectedTabPositionTwoCase(View rootContainer) {

        View view = (View) findViewById(R.id.myView);
        tabProductDetailLayout = getLayoutInflater().inflate(R.layout.dialog_w_image_selector, (ViewGroup) rootContainer);
        Button btn_select_photo = (Button) tabProductDetailLayout.findViewById(R.id.btn_Select_Photo);
        Button b1 = (Button) tabProductDetailLayout.findViewById(R.id.btn_start_game_w_image);
        ivImage = (ImageView) tabProductDetailLayout.findViewById(R.id.ivImage);


        btn_select_photo.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {selectImage();}});
        ivImage.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {selectImage();}});
//            @Override
//            public void onClick(View v) {
//                if(isImageFitToScreen) {
//                    isImageFitToScreen=false;
//                    ivImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    ivImage.setAdjustViewBounds(true);
//                }else{
//                    isImageFitToScreen=true;
//                    ivImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                    ivImage.setScaleType(ImageView.ScaleType.FIT_XY);
//                }
//            }
//        });

    }




    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from your Library","Choose from our Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(MainActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from your Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Choose from our Library")) {
                    userChoosenTask = "Choose from our Library";
                    //if (result)
                    Intent custom_gallery_viewer = new Intent(MainActivity.this, Custom_Gallery_Viewer.class);
                    startActivity(custom_gallery_viewer);

                }   else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }



    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
            afterImageselected();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }


    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                Game_Screen.bm=bm;
                afterImageselected();
                //view.setBackground(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ivImage.setImageBitmap(bm);
    }

    private void afterImageselected(){
        Button b = (Button) tabProductDetailLayout.findViewById(R.id.btn_Select_Photo);
        b.setVisibility(tabProductDetailLayout.GONE);
        Button b1 = (Button) tabProductDetailLayout.findViewById(R.id.btn_start_game_w_image);
        b1.setVisibility(tabProductDetailLayout.VISIBLE);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStartBtn(null);
                Game_Screen.set_backgroud = true;
                //bg.setBackgroundDrawable(drawable);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }


    @Override
    public void onFragmentAttached(Fragment fragment) {
        mMyScheduleFragments.add(fragment);

    }

    @Override
    public void onFragmentDetached(Fragment fragment) {
        mMyScheduleFragments.remove(fragment);
    }

}


