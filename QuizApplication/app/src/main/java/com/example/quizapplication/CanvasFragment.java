package com.example.quizapplication;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CanvasFragment extends Fragment {
    private Context mContext;
    private CanvasView mCanvasView;
    private LinearLayout mLinearLayout;
    private boolean isRotate = false;
    private String TAG = "CanvasFragment";
    private String TEMP_FILE_NAME = "temp";

    public CanvasFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            isRotate = savedInstanceState.getBoolean("IS_ROTATE");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_cavas, container, false);
        mLinearLayout = view.findViewById(R.id.canvas_layout);
        int mMargins = getResources().getDimensionPixelSize(R.dimen.padding_margins_value);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(mMargins,mMargins,mMargins,mMargins);
        mCanvasView = new CanvasView(mContext);
        mLinearLayout.addView(mCanvasView,params);
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onResume(){
        super.onResume();
        if (isRotate) {
            new getImage().execute();
        }
    }
    @Override
    public void onPause(){
        super.onPause();
    }

    //BEGIN Keep Bitmap in data/data/file  [Only use in Small Data]
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("IS_ROTATE",true);
        SaveBitmapToData(getActivity(), mCanvasView.getBitmap(), TEMP_FILE_NAME);
    }

    public boolean SaveBitmapToData(Activity act, Bitmap bmpToSave, String FileNameWithoutExtension) {
        try{
            FileOutputStream fos = act.openFileOutput(FileNameWithoutExtension + "." + "png", Context.MODE_PRIVATE);
            bmpToSave.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return true;
        }
        catch (Exception e)
        {
            if (e.getMessage() != null)
                Log.w(TAG, e.getMessage());
            else
                e.printStackTrace();
            return false;
        }
    }
    public Bitmap getBitmapFromData(Activity act, String FileName) {
        FileInputStream fis = null;
        try {
            fis = act.openFileInput(FileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedInputStream bis = new BufferedInputStream(fis);
        Bitmap bmpRet = BitmapFactory.decodeStream(bis);
        try {
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpRet;
    }
    //END Keep Bitmap in data/data/file  [Only use in Small Data]

    private class getImage extends AsyncTask<String , Integer , Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return getBitmapFromData(getActivity(), TEMP_FILE_NAME + ".png");
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mCanvasView.setBitmap(bitmap);
        }
    }

    private class saveImage extends AsyncTask<String , Integer , Bitmap> {

        Bundle mBundle;

        public saveImage(Bundle bundle) {
            mBundle = bundle;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            SaveBitmapToData(getActivity(), mCanvasView.getBitmap(), TEMP_FILE_NAME);
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mBundle.putBoolean("IS_ROTATE",true);
        }
    }
}
