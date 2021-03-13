package com.example.futbolito;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class Futbolito extends View implements SensorEventListener {

    Paint pincel = new Paint();
    int alto, ancho;
    int tam = 5;
    int borde = 12;
    float ejex = 0, ejey = 0, ejez = 0;

    public Futbolito(Context context) {
        super(context);
        SensorManager administrador = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = administrador.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        administrador.registerListener(this,sensor,SensorManager.SENSOR_DELAY_FASTEST);
        Display pantalla = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        ancho = pantalla.getWidth();
        alto = pantalla.getHeight();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        ejex-=sensorEvent.values[0];
        if(ejex<(tam+borde))
        {
            ejex = (tam+borde);
        }
        else if(ejex>(ancho-(tam+borde)))
        {
            ejex = ancho-(tam+borde);
        }
        ejey+=sensorEvent.values[1];
        if(ejey<(tam+borde))
        {
            ejey = (tam+borde);
        }
        else if(ejey>(alto-tam-170))
        {
            ejey = alto-tam-170;
        }
        ejez=sensorEvent.values[2];
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onDraw(Canvas lienzo){
        Resources res = getResources();
        Bitmap imagen = BitmapFactory.decodeResource(res, R.drawable.cancha);
        Bitmap cancha = redimensionarImagenMaximo(imagen,ancho,alto-160);
        lienzo.drawBitmap( cancha, 0, 0, pincel);
        pincel.setColor(Color.BLACK);
        lienzo.drawCircle(ejex,ejey,ejez+tam,pincel);
    }

    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeigth / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }
    }

