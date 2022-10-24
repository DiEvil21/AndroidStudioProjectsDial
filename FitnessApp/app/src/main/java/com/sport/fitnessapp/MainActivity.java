package com.sport.fitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sport.fitnessapp.list_classes.State;

import java.util.Calendar;
import java.util.Date;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    String fileName;
    ArrayList<State> states = new ArrayList<State>();
    EditText editTextName, editTextKkal;
    ArrayList<String> arr = new ArrayList<String>();

    public static final String LOG_TAG = "myLogs";


    CameraService[] myCameras = null;

    private CameraManager mCameraManager = null;
    private final int CAMERA1 = 0;
    private final int CAMERA2 = 1;

    private Button mButtonOpenCamera1 = null;
    private Button mButtonToMakeShot = null;
    private TextureView mImageView = null;
    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler = null;


    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getArrayList("arr") != null) {
            arr = getArrayList("arr");
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button228:
                String name = String.valueOf(editTextName.getText());
                String kkal = String.valueOf(editTextKkal.getText());

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                System.out.println("---------------------------------");
                System.out.println(prefs.getInt("imgName", 0));

                //arr.add("test2.jpg_11.01_fjdkskf_100");
                arr.add("test"+ prefs.getInt("imgName", 0)+".jpg" + "_" + Calendar.getInstance().getTime() + "_" + name + "_" + kkal);
                saveArrayList(arr,"arr");
                System.out.println("-------------------" + arr);
                editor.putInt("imgName", prefs.getInt("imgName", 0 ) + 1);
                editor.apply();
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
        }
    }


    public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();

    }

    public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextName = findViewById(R.id.editTextTextPersonName);
        editTextKkal = findViewById(R.id.editTextNumber);


        Log.d(LOG_TAG, "Запрашиваем разрешение");
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                ||
                (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        ) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }


        mButtonOpenCamera1 = findViewById(R.id.button1);

        mButtonToMakeShot = findViewById(R.id.button3);
        mImageView = findViewById(R.id.textureView);

        mButtonOpenCamera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myCameras[CAMERA2].isOpen()) {
                    myCameras[CAMERA2].closeCamera();
                }
                if (myCameras[CAMERA1] != null) {
                    if (!myCameras[CAMERA1].isOpen()) myCameras[CAMERA1].openCamera();
                }
            }
        });




        mButtonToMakeShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (myCameras[CAMERA1].isOpen()) myCameras[CAMERA1].makePhoto();
                if (myCameras[CAMERA2].isOpen()) myCameras[CAMERA2].makePhoto();

            }
        });


        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            // Получение списка камер с устройства


            myCameras = new CameraService[mCameraManager.getCameraIdList().length];


            for (String cameraID : mCameraManager.getCameraIdList()) {
                Log.i(LOG_TAG, "cameraID: " + cameraID);
                int id = Integer.parseInt(cameraID);


                // создаем обработчик для камеры
                myCameras[id] = new CameraService(mCameraManager, cameraID);


            }
        } catch (CameraAccessException e) {
            Log.e(LOG_TAG, e.getMessage());
            e.printStackTrace();
        }


    }


    public class CameraService {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        private File mFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                "test"+ prefs.getInt("imgName", 0)+".jpg");

        private String mCameraID;
        private CameraDevice mCameraDevice = null;
        private CameraCaptureSession mCaptureSession;
        private ImageReader mImageReader;


        public CameraService(CameraManager cameraManager, String cameraID) {

            mCameraManager = cameraManager;
            mCameraID = cameraID;

        }

        public void makePhoto() {


            try {
                // This is the CaptureRequest.Builder that we use to take a picture.
                final CaptureRequest.Builder captureBuilder =
                        mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                captureBuilder.addTarget(mImageReader.getSurface());
                CameraCaptureSession.CaptureCallback CaptureCallback = new CameraCaptureSession.CaptureCallback() {

                    @Override
                    public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                                   @NonNull CaptureRequest request,
                                                   @NonNull TotalCaptureResult result) {


                    }
                };

                mCaptureSession.stopRepeating();
                mCaptureSession.abortCaptures();
                mCaptureSession.capture(captureBuilder.build(), CaptureCallback, mBackgroundHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();


            }


        }


        private final ImageReader.OnImageAvailableListener mOnImageAvailableListener
                = new ImageReader.OnImageAvailableListener() {

            @Override
            public void onImageAvailable(ImageReader reader) {

                mBackgroundHandler.post(new ImageSaver(reader.acquireNextImage(), mFile));


            }

        };


        private CameraDevice.StateCallback mCameraCallback = new CameraDevice.StateCallback() {

            @Override
            public void onOpened(CameraDevice camera) {
                mCameraDevice = camera;
                Log.i(LOG_TAG, "Open camera  with id:" + mCameraDevice.getId());

                createCameraPreviewSession();
            }

            @Override
            public void onDisconnected(CameraDevice camera) {
                mCameraDevice.close();

                Log.i(LOG_TAG, "disconnect camera  with id:" + mCameraDevice.getId());
                mCameraDevice = null;
            }

            @Override
            public void onError(CameraDevice camera, int error) {
                Log.i(LOG_TAG, "error! camera id:" + camera.getId() + " error:" + error);
            }
        };


        private void createCameraPreviewSession() {

            mImageReader = ImageReader.newInstance(1920, 1080, ImageFormat.JPEG, 1);
            mImageReader.setOnImageAvailableListener(mOnImageAvailableListener, null);

            SurfaceTexture texture = mImageView.getSurfaceTexture();

            texture.setDefaultBufferSize(1920, 1080);
            Surface surface = new Surface(texture);

            try {
                final CaptureRequest.Builder builder =
                        mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

                builder.addTarget(surface);


                mCameraDevice.createCaptureSession(Arrays.asList(surface, mImageReader.getSurface()),
                        new CameraCaptureSession.StateCallback() {

                            @Override
                            public void onConfigured(CameraCaptureSession session) {
                                mCaptureSession = session;
                                try {
                                    mCaptureSession.setRepeatingRequest(builder.build(), null, mBackgroundHandler);
                                } catch (CameraAccessException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onConfigureFailed(CameraCaptureSession session) {
                            }
                        }, mBackgroundHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

        }


        public boolean isOpen() {
            if (mCameraDevice == null) {
                return false;
            } else {
                return true;
            }
        }

        public void openCamera() {
            try {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {


                        mCameraManager.openCamera(mCameraID, mCameraCallback, mBackgroundHandler);

                    }
                }


            } catch (CameraAccessException e) {
                Log.i(LOG_TAG, e.getMessage());

            }
        }

        public void closeCamera() {

            if (mCameraDevice != null) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
        }


    }


    @Override
    public void onPause() {
        if (myCameras[CAMERA1].isOpen()) {
            myCameras[CAMERA1].closeCamera();
        }
        if (myCameras[CAMERA2].isOpen()) {
            myCameras[CAMERA2].closeCamera();
        }
        stopBackgroundThread();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        startBackgroundThread();


    }


    private static class ImageSaver implements Runnable {

        /**
         * The JPEG image
         */
        private final Image mImage;
        /**
         * The file we save the image into.
         */
        private final File mFile;

        ImageSaver(Image image, File file) {
            mImage = image;
            mFile = file;
        }

        @Override
        public void run() {
            ByteBuffer buffer = mImage.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            FileOutputStream output = null;
            try {
                output = new FileOutputStream(mFile);
                output.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mImage.close();
                if (null != output) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}