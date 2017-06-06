package com.atreya.myapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.atreya.myapp.CustomAdapters.PicBoxListAdapter;
import com.atreya.myapp.DataClasses.BeanClass;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.File;
import java.util.ArrayList;

import static com.atreya.myapp.SharedPref.REMIND_ME_SHARED_PREF;

public class PicBox extends AppCompatActivity {
RecyclerView imgList; public  static ArrayList<BeanClass> productList = new ArrayList<>();
    public static ArrayList<String> img_name = new ArrayList<>();
    public static ArrayList<String> img = new ArrayList<>();
    Bitmap bitmap;
    public  static  int picCount=0;
    SharedPreferences sharedPreferences;
    PicBoxListAdapter adapter;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE
    };
    String picName;
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;
RelativeLayout main,imgLay;
    ImageView imga,close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_box);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initViews();
        addPermission();
        initListeners();
        getSharedPrefData();
        populateList();
        initListners();
        Log.e("count",""+ String.valueOf(picCount));
    }

    private void getSharedPrefData() {

    }

    private void populateList() {

         adapter= new PicBoxListAdapter(PicBox.this,productList);

        imgList.setAdapter(adapter);

    }

    private void initViews() {
        imgList=(RecyclerView)findViewById(R.id.picBoxList);
        imgList.setHasFixedSize(true);

        sharedPreferences = getSharedPreferences(REMIND_ME_SHARED_PREF, 0);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        imgList.setLayoutManager(layoutManager);
        main=(RelativeLayout)findViewById(R.id.main);
        imgLay=(RelativeLayout)findViewById(R.id.image);
        imgLay.setVisibility(View.GONE);
        imga=(ImageView)findViewById(R.id.imageFul);
        close=(ImageView)findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                imgLay.setVisibility(View.GONE);
            }
        });

//        productList.add(listItems)

    }

    private void initListeners() {
        final FloatingActionsMenu rightLabels = (FloatingActionsMenu) findViewById(R.id.right_labels);
        FloatingActionButton renewFAB = new FloatingActionButton(PicBox.this);
        renewFAB.setTitle("Open Camera");
        renewFAB.setStrokeVisible(true);
        renewFAB.setSize(FloatingActionButton.SIZE_MINI);
        renewFAB.setColorNormalResId(R.color.StatusBarColor);
        renewFAB.setColorPressedResId(R.color.StatusBarColor);
        renewFAB.setIcon(R.drawable.camera_img);

        rightLabels.addButton(renewFAB);

        FloatingActionButton callFAB = new FloatingActionButton(PicBox.this);
        callFAB.setTitle("Open Gallery");
        callFAB.setStrokeVisible(true);
        callFAB.setSize(FloatingActionButton.SIZE_MINI);
        callFAB.setColorNormalResId(R.color.StatusBarColor);
        callFAB.setColorPressedResId(R.color.StatusBarColor);
        callFAB.setIcon(R.drawable.gallery);
        rightLabels.addButton(callFAB);

        renewFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PicBox.this,"Camera",Toast.LENGTH_SHORT).show();
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    builder.detectFileUriExposure();
                }

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(intent, 1);


            }
        });

        callFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(PicBox.this,"Gallery",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });


    }
    private void initListners() {
        imgList .addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(PicBox.this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);

                    Toast.makeText(PicBox.this, ""+position, Toast.LENGTH_SHORT).show();

                    String Selected = PicBoxListAdapter.products.get(position).getLabel();
                    Bitmap bit= BirmapConversion.decodeToBase64(Selected);
//
                    imga.setImageBitmap(bit);
                    imgLay.setVisibility(View.VISIBLE);


                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_right);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);



        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
//                imagePath= f.getPath();

                Log.e("file=",""+f);
                String filePath = f.getAbsolutePath();
                try
                {
                    bitmap = ImageResizer.decodeSampledBitmapFromFile(f.getAbsolutePath(), 1500, 800);
                    takePicName(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                bitmap = ImageResizer.decodeSampledBitmapFromFile(picturePath, 1500, 800);
//
//                imagePath=picturePath;
                Log.w("path of image from", picturePath + "");

                takePicName(bitmap);

            }
        }

    }


    private void takePicName(final Bitmap bitmap) {
          picName = "";


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Name");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                picName = input.getText().toString();

                String bits= BirmapConversion.encodeToBase64(bitmap);
                int count= sharedPreferences.getInt("picCount",0);

                count=count+1;
//        SharedPreferences.Editor sEdit = sharedPreferences.edit();
//        sEdit.putString("pic"+count,bits);
//        sEdit.commit();
                picCount++;
//             img_name.add(picCount,picname);
//              img.add(picCount,bits);
                BeanClass onj=new BeanClass();
                onj.setValue(picName);
                onj.setLabel(bits);
                productList.add(onj);
                Log.e("count",""+ String.valueOf(picCount));
                Log.e("pic name"," "+ picName);
                Log.e("\"pic\"+count",""+ String.valueOf("pic"+count));


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

          Log.e("name","dds"+picName);

    }

    private void addPermission()
    {
        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);

        if(ActivityCompat.checkSelfPermission(PicBox.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(PicBox.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(PicBox.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(PicBox.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(PicBox.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(PicBox.this,permissionsRequired[2])){
                //Show Information about why you need the permission
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PicBox.this);
                builder.setTitle("need_multiple_permission");
                builder.setMessage("camera_permission");
                builder.setPositiveButton("grant_permission", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(PicBox.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
            else {
                //just request the permission
                ActivityCompat.requestPermissions(PicBox.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
            }

//            txtPermissions.setText("Permissions Required");

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0],true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }
    }


        private void proceedAfterPermission()
        {
//        txtPermissions.setText("We've got all permissions");
            Toast.makeText(getBaseContext(), "We_got_All_Permissions", Toast.LENGTH_LONG).show();
        }

}
