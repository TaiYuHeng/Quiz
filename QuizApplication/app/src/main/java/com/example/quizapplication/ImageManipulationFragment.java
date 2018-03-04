package com.example.quizapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import static java.lang.Math.sqrt;

public class ImageManipulationFragment extends Fragment {

    public static final int PICK_IMAGE = 1;
    private LinearLayout mLinearLayout;
    private Context mContext;
    private Button button01;
    private Button button02;
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private  float mov_x;
    private  float mov_y;
    private  int sec_x;
    private  int sec_y;
    private boolean mIsDrag = false;
    private boolean mIsRotate_Zoom = false;
    private float mTransferX = 0f;
    private float mTransferY = 0f;
    private float mScale = 1.0f;
    private float oldRotation = 0;
    private float mRotation = 0;
    private float midX;
    private float midY;
    private Uri mFileUri;
    private float mRestorePosX = 0f;
    private float mRestorePosY = 0f;
    private float mRestoreMidX;
    private float mRestoreMidY;
    private float mRestoreScale = 1.0f;
    private float mRestoreRotation = 0;
    public ImageManipulationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMatrix = new Matrix();
        if (savedInstanceState != null) {
            mFileUri = savedInstanceState.getString("BitMap") != null ? Uri.parse(savedInstanceState.getString("BitMap")) : null;
            mRestorePosX = savedInstanceState.getFloat("Translate_X");
            mRestorePosY = savedInstanceState.getFloat("Translate_Y");
            mRestoreMidX = savedInstanceState.getFloat("Mid_X");
            mRestoreMidY = savedInstanceState.getFloat("Mid_Y");
            mRestoreScale = savedInstanceState.getFloat("Scale");
            mRestoreRotation = savedInstanceState.getFloat("Rotate");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_manipulation, container, false);
        mLinearLayout = view.findViewById(R.id.ImageManipulationView_layout);
        button01 = (Button) view.findViewById(R.id.load);
        button01.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
        button02 = (Button) view.findViewById(R.id.clear);
        button02.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                mLinearLayout.removeAllViews();
                mMatrix.reset();
                mRestorePosX = 0f;
                mRestorePosY = 0f;
                mRestoreMidX = 0f;
                mRestoreMidY = 0f;
                mRestoreScale = 1.0f;
                mRestoreRotation = 0;
                mFileUri = null;
            }
        });
        setImageView(mFileUri);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            mFileUri = data.getData();
            setImageView(mFileUri);
        }
    }

    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private float getScale(MotionEvent event){
        float x = mov_x - sec_x;
        float y = mov_y - sec_y;
        float first = (float)sqrt(x*x + y*y);
        x =  event.getX(0) - event.getX(1);
        y = event.getY(0) - event.getY(1);
        float second = (float)sqrt(x*x + y*y);
        return second/first;
    }

    private void setImageView(Uri uri){
        try {
            InputStream inputStream = mContext.getContentResolver().openInputStream(uri);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            mBitmap = BitmapFactory.decodeStream(bufferedInputStream);
            final ImageView mImageView = new ImageView(mContext);
            mImageView.setImageBitmap(mBitmap);
            mImageView.setScaleType(ImageView.ScaleType.MATRIX);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            mLinearLayout.addView(mImageView,params);
            mMatrix.postScale(mRestoreScale, mRestoreScale, mRestoreMidX , mRestoreMidY);
            mMatrix.postRotate(mRestoreRotation, mRestoreMidX , mRestoreMidY);
            mMatrix.postTranslate(mRestorePosX, mRestorePosY);
            mImageView.setImageMatrix(mMatrix);
            mBitmap= null;
            mImageView.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            mIsDrag = true ;
                            mIsRotate_Zoom = false;
                            mov_x = event.getX(0);
                            mov_y = event.getY(0);
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                            mIsDrag = false;
                            mIsRotate_Zoom = true;
                            sec_x = (int) event.getX(1);
                            sec_y = (int) event.getY(1);
                            midX = (event.getX(0) + event.getX(1))/2;
                            midY = (event.getY(0) + event.getY(1))/2;
                            mRestoreMidX = midX;
                            mRestoreMidX = midY;
                            oldRotation = rotation(event);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if(mIsDrag) {
                                mTransferX = (event.getX(0) - mov_x);
                                mTransferY = (event.getY(0) - mov_y);
                                mMatrix.postTranslate(mTransferX, mTransferY);
                                mRestorePosX = mRestorePosX + mTransferX;
                                mRestorePosY = mRestorePosY + mTransferY;
                                mov_x = event.getX(0);
                                mov_y = event.getY(0);
                                mImageView.setImageMatrix(mMatrix);
                            } else if (!mIsDrag && mIsRotate_Zoom){
                                mScale = getScale(event);
                                mMatrix.postScale(mScale, mScale, midX ,midY);
                                mRotation = rotation(event) - oldRotation;
                                mMatrix.postRotate(mRotation, midX , midY);
                                mRestoreScale = mRestoreScale * mScale;
                                mRestoreRotation = mRestoreRotation + mRotation;
                                mImageView.setImageMatrix(mMatrix);
                                mov_x = event.getX(0);
                                mov_y = event.getY(0);
                                sec_x = (int) event.getX(1);
                                sec_y = (int) event.getY(1);
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            mIsDrag = false ;
                            break;
                        case MotionEvent.ACTION_POINTER_UP:
                            mIsDrag = false ;
                            mIsRotate_Zoom = false ;
                            break;
                    }
                    return true;
                }
            });
        } catch (Exception e){
            Log.d("TAG",e.toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
            outState.putString("BitMap", mFileUri == null ? null : mFileUri.toString());
            outState.putFloat("Translate_X", mRestorePosX);
            outState.putFloat("Translate_Y", mRestorePosY);
            outState.putFloat("Mid_X", mRestoreMidX);
            outState.putFloat("Mid_Y", mRestoreMidX);
            outState.putFloat("Scale", mRestoreScale);
            outState.putFloat("Rotate", mRestoreRotation);
        }
}
