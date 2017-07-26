package com.example.user.finaltess;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.opencv.imgproc.Imgproc.rectangle;

public class Zoominout extends AppCompatActivity implements TextToSpeech.OnInitListener {
    String jiostring;
    private static final String TAG = null;
    Point p = new Point(1, 2);
    ImageView i;
    MediaPlayer myplayer;
    int mycount=0;
    int tapcount=0;
    int count1s = 0;
    int count2s = 0;
    int count3s = 0;
    int length = 0;
    int fuck = 0;
    int a1, a2, a3, a4 = 0;
    int b1, b2, b3, b4 = 0;
    int c1, c2, c3, c4 = 0;
    Rect rect = null;
    int set1 = 0;
    int set2 = 0;
    int set3 = 0;
    String path;
    int temp = 0;
    int a = 0;
    int b = 0;
    int c = 0;
    int d = 0;
    Size s;
    Bitmap bmpOut0;
    Bitmap bmpOut2;
    ArrayList<Integer> num3;
    String recognizedText;
    TextToSpeech tts;
    private static final ScheduledExecutorService worker =
            Executors.newSingleThreadScheduledExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_zoominout);
        i = (ImageView) findViewById(R.id.imageView6);
        TextView t1 = (TextView) findViewById(R.id.trytext2);
        ScrollView s1 = (ScrollView) findViewById(R.id.scroll2);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        final Bitmap bp = BitmapFactory.decodeFile("/sdcard/Any Reader/patna2.jpg", options);
        Mat mymat = new Mat();
        tts = new TextToSpeech(this, this);
        num3 = new ArrayList<>();
        Utils.bitmapToMat(skewdenoise.cleanbip(bp), mymat);
        Mat rgb = new Mat();
        Mat crop = rgb.clone();
        Imgproc.pyrDown(mymat, rgb);
        Mat small = new Mat();
        Imgproc.cvtColor(rgb, small, Imgproc.COLOR_RGB2GRAY);
        Mat grad = new Mat();
        Mat morphkernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(7, 7));
        Imgproc.morphologyEx(small, grad, Imgproc.MORPH_GRADIENT, morphkernel);
        Mat bw = new Mat();
        Imgproc.threshold(grad, bw, 0.0, 255.0, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        Mat connected = new Mat();
        morphkernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(30, 10));
        Imgproc.morphologyEx(bw, connected, Imgproc.MORPH_CLOSE, morphkernel);
        Mat mask = Mat.zeros(bw.size(), CvType.CV_8UC1);
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(connected, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));
        for (int idx = 0; idx < contours.size(); idx++) {
            rect = Imgproc.boundingRect(contours.get(idx));
            Mat maskROI = new Mat(mask, rect);
            maskROI.setTo(new Scalar(0, 0, 0));
            Imgproc.drawContours(mask, contours, idx, new Scalar(255, 255, 255), Core.FILLED);
            double r = (double) Core.countNonZero(maskROI) / (rect.width * rect.height);

            if ((rect.height < 50 && rect.width < 300)) {
                Imgproc.rectangle(rgb, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 1);
                a1 = rect.x;
                a2 = rect.y;
                a3 = rect.width;
                a4 = rect.height;
                ArrayList<Bitmap> paraarray = new ArrayList<Bitmap>();
                bmpOut0 = Bitmap.createBitmap(rgb.cols(), rgb.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(rgb, bmpOut0);
                Bitmap bmpOut2 = Bitmap.createBitmap(bmpOut0, a1, a2, a3, a4);
                paraarray.add(bmpOut2);
                Log.d(TAG, "Headings :" + set1 + " with " + a2);
//                Toast.makeText(Zoominout.this, "Headings :"+set1, Toast.LENGTH_LONG).show();
//                Toast.makeText(Zoominout.this, "x axis::" + a1, Toast.LENGTH_LONG).show();
                File pictureFile = new File("/sdcard/Any Reader/Headings/h" + a2 + ".jpg");

                if (pictureFile == null) {
                    Toast.makeText(Zoominout.this, "null bitmap", Toast.LENGTH_LONG).show();
                }

                Bitmap image = paraarray.get(fuck);
                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    //fos.write(data);
                    image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();

                } catch (FileNotFoundException e) {
                    Toast.makeText(Zoominout.this, "FileNotFoundException", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(Zoominout.this, "IOException", Toast.LENGTH_LONG).show();
                }

                set1++;
            } else if ((rect.height < 50 && rect.width > 300)) {
                Imgproc.rectangle(rgb, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 1);
                a1 = rect.x;
                a2 = rect.y;
                a3 = rect.width;
                a4 = rect.height;
                ArrayList<Bitmap> paraarray = new ArrayList<Bitmap>();
                bmpOut0 = Bitmap.createBitmap(rgb.cols(), rgb.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(rgb, bmpOut0);
                Bitmap bmpOut2 = Bitmap.createBitmap(bmpOut0, a1, a2, a3, a4);
                paraarray.add(bmpOut2);
                Log.d(TAG, "Points :" + set2 + " with " + a2);
//                Toast.makeText(Zoominout.this, "Point :" +set2 , Toast.LENGTH_LONG).show();
//                Toast.makeText(Zoominout.this, "x axis::" + a1, Toast.LENGTH_LONG).show();
                File pictureFile = new File("/sdcard/Any Reader/Points/p" + a2 + ".jpg");
                if (pictureFile == null) {
                    Toast.makeText(Zoominout.this, "null bitmap", Toast.LENGTH_LONG).show();
                }

                Bitmap image = paraarray.get(fuck);
                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    //fos.write(data);
                    image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();

                } catch (FileNotFoundException e) {
                    Toast.makeText(Zoominout.this, "FileNotFoundException", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(Zoominout.this, "IOException", Toast.LENGTH_LONG).show();
                }

                set2++;
            } else if ((rect.height > 50 && rect.width > 300)) {
                Imgproc.rectangle(rgb, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 0, 0), 1);
                b1 = rect.x;
                b2 = rect.y;
                b3 = rect.width;
                b4 = rect.height;
                ArrayList<Bitmap> paraarray = new ArrayList<Bitmap>();
                bmpOut0 = Bitmap.createBitmap(rgb.cols(), rgb.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(rgb, bmpOut0);
                Log.d(TAG, "Para :" + set3 + " with " + b2);
                rect.contains(p);
                Toast.makeText(Zoominout.this, "Para :" + set3 + " with " + b2, Toast.LENGTH_LONG).show();
//                Toast.makeText(Zoominout.this, "x axis::" + b1, Toast.LENGTH_LONG).show();
                Bitmap bmpOut2 = Bitmap.createBitmap(bmpOut0, b1, b2, b3, b4);
                paraarray.add(bmpOut2);
                File pictureFile = new File("/sdcard/Any Reader/Para/b" + b2 + ".jpg");
                File txtFile = new File("/sdcard/Any Reader/Param/b" + b1 + "-" + b2 + ">" + b3 + "<" + b4 + ".txt");
                if (pictureFile == null) {
                    Toast.makeText(Zoominout.this, "null bitmap", Toast.LENGTH_LONG).show();
                }

                Bitmap image = paraarray.get(fuck);
                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    FileOutputStream fos1 = new FileOutputStream(txtFile);
                    image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                    fos1.close();

                } catch (FileNotFoundException e) {
                    Toast.makeText(Zoominout.this, "FileNotFoundException", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(Zoominout.this, "IOException", Toast.LENGTH_LONG).show();

                }

                set3++;


            }

        }
        Toast.makeText(Zoominout.this, set1 + " " + "headings" + " " + set2 + " " + "points" + " " + set3 + " " + "paragraphs", Toast.LENGTH_LONG).show();

        try {
            i.setImageBitmap(bmpOut0);
        } catch (Exception e) {
            Toast.makeText(Zoominout.this, "null bitmap", Toast.LENGTH_LONG).show();
        }

        Toast.makeText(Zoominout.this, "faulty start", Toast.LENGTH_LONG).show();
        Log.d(TAG, "faulty2 start");
        sort1.faulty2();
        Toast.makeText(Zoominout.this, "faulty stop", Toast.LENGTH_LONG).show();
        Log.d(TAG, "faulty2 stop");
        Toast.makeText(Zoominout.this, "faultydelete start", Toast.LENGTH_LONG).show();
        Log.d(TAG, "faultydelete start");
        sort1.faultydelete();
        Toast.makeText(Zoominout.this, "faultydelete stop", Toast.LENGTH_LONG).show();
        Log.d(TAG, "faultydelete stop");
        jiostring = String.valueOf(Headings.sortgeneral());
        Toast.makeText(Zoominout.this, "Length of shit:" + String.valueOf(sort1.checkarrayx()) + " & " + String.valueOf(sort1.checkarrayY()), Toast.LENGTH_LONG).show();
        Log.d(TAG, "Length of shit:" + String.valueOf(sort1.checkarrayx()) + " & " + String.valueOf(sort1.checkarrayY()));

        final Context context = this;
        final GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if(tapcount%2!=0){
                    mycount++;
                    if(mycount==1){
                        if(count1s==1){
                            loadmusic("/sdcard/Any Reader/Media/Heading1.mp3");
                        }

                        else if(count2s==1){
                            loadmusic("/sdcard/Any Reader/Media/Para.mp3");
                            Toast.makeText(Zoominout.this,"read full text"+tapcount,Toast.LENGTH_LONG).show();
                        }
                    }else {
                        resumemusic();
                    }


                }
                else {
                    pausemusic();
                    Toast.makeText(Zoominout.this,"else",Toast.LENGTH_LONG).show();

                }
                Toast.makeText(Zoominout.this,"count::"+tapcount,Toast.LENGTH_LONG).show();
                tapcount++;
                if(tapcount>=2){
                    endmusic();
                }

                return true;

            }

            @Override
            public void onLongPress(MotionEvent e) {
                speakOut1();
            }

        };
        final GestureDetector detector = new GestureDetector(listener);

        detector.setOnDoubleTapListener(listener);
        detector.setIsLongpressEnabled(true);
        getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return detector.onTouchEvent(event);
            }
        });

    }
    void endmusic(){
        int position=myplayer.getCurrentPosition();
        if( position==myplayer.getDuration()){
            myplayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mediaPlayer) {
                    promptSpeechInput();

                }
            });
        }}

    public void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak!");
        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(Zoominout.this, "Your device doesn't support this feature!", Toast.LENGTH_LONG).show();
        }
    }
    public void speakOut1() {
        tts.speak("Well,I can see " + set1 + " headings with " + set2 + " points " + set3 + " paragraphs to read.Would you like to hear the headings first?", TextToSpeech.QUEUE_FLUSH, null);
        Runnable task = new Runnable() {
            public void run() {
                promptSpeechInput();
            }
        };
        worker.schedule(task,8, TimeUnit.SECONDS);
    }
    public void loadaudio(String sop,String filename){
        HashMap<String, String> myHashRender = new HashMap<String, String>();
        myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,sop);
        tts.synthesizeToFile(sop, myHashRender,filename);
    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                tts.setSpeechRate((float) 0.80);
                 tts.speak("you are all set to go!\n long press on the screen",TextToSpeech.QUEUE_FLUSH,null);

            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }
    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            File file1 = new File("/sdcard/Any Reader/Media/Heading1.mp3");
            File file2 = new File("/sdcard/Any Reader/Media/Heading2.mp3");
            File file3 = new File("/sdcard/Any Reader/Media/Heading3.mp3");
            File file4 = new File("/sdcard/Any Reader/Media/Heading4.mp3");
            File file5 = new File("/sdcard/Any Reader/Media/Heading5.mp3");
            File file6 = new File("/sdcard/Any Reader/Media/Heading6.mp3");
            Boolean delete1=file1.delete();
            Boolean delete2=file2.delete();
            Boolean delete3=file3.delete();
            Boolean delete4=file4.delete();
            Boolean delete5=file5.delete();
            Boolean delete6=file6.delete();
        }
        try{ if(myplayer.isPlaying()){
            myplayer.stop();
            myplayer.release();
            File file1 = new File("/sdcard/Any Reader/Media/Heading1.mp3");
            File file2 = new File("/sdcard/Any Reader/Media/Heading2.mp3");
            File file3 = new File("/sdcard/Any Reader/Media/Heading3.mp3");
            File file4 = new File("/sdcard/Any Reader/Media/Heading4.mp3");
            File file5 = new File("/sdcard/Any Reader/Media/Heading5.mp3");
            File file6 = new File("/sdcard/Any Reader/Media/Heading6.mp3");
            Boolean delete1=file1.delete();
            Boolean delete2=file2.delete();
            Boolean delete3=file3.delete();
            Boolean delete4=file4.delete();
            Boolean delete5=file5.delete();
            Boolean delete6=file6.delete();

        }}
        catch (Exception e){
            Log.d(TAG,"error on destroy method");
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int request_code, int result_code, Intent intent) {

        super.onActivityResult(request_code, result_code, intent);

        switch (request_code) {
            case 100:
                if (result_code == RESULT_OK && intent != null) {
                    ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (result.contains("one")||result.contains("just one")||result.contains("only one")) {
                        tts.speak("I got you!.Double Tap on the screen when I ask so",TextToSpeech.QUEUE_FLUSH,null);
                        String destination="/sdcard/Any Reader/Media/Heading1.mp3";
                        loadaudio(String.valueOf(Headingsreadout.sortgeneral(0)),destination);
                        Runnable task1 = new Runnable() {
                            public void run() {
                                tts.speak("Ready.Double Tap to start reading",TextToSpeech.QUEUE_FLUSH,null);
                            }
                        };
                        worker.schedule(task1,50, TimeUnit.SECONDS);
                        count1s=1;
                        tapcount=0;
                        mycount=0;
                        count2s=0;
                     }
                     else if(result.contains("yes")||result.contains("sure")||result.contains("ok")) {
                        tts.speak(jiostring,TextToSpeech.QUEUE_FLUSH,null);
                        Runnable task = new Runnable() {
                            public void run() {
                                promptSpeechInput();
                            }
                        };
                        worker.schedule(task,15, TimeUnit.SECONDS);



                    }


                    else if(result.contains("no")||result.contains("not now")||result.contains("full page")) {
                        tts.speak("OK, I will let you know when I am ready with the entire text",TextToSpeech.QUEUE_FLUSH,null);
                        String destination="/sdcard/Any Reader/Media/Para.mp3";
                        loadaudio(String.valueOf(createaudio.sortgeneral()),destination);
                        count2s=1;
                        Runnable task2 = new Runnable() {
                            public void run() {
                                tts.speak("Ready"+"\n"+"Double tap on the screen to read",TextToSpeech.QUEUE_FLUSH,null);
                            }
                        };
                        worker.schedule(task2,50, TimeUnit.SECONDS);
                    }
                    tapcount=0;
                    mycount=0;
                    count1s=0;
                }
                break;
        }
        }
    private String scanImage(){
        TessBaseAPI baseApi = new TessBaseAPI();
        path = "/sdcard/Finaltess";
        baseApi.setDebug(true);
        baseApi.init(path,"eng");
        baseApi.setImage(bmpOut0);
        baseApi.setVariable(TessBaseAPI.VAR_CHAR_WHITELIST,
                ".,';:[]{}!@#$%^&*()_+=-?/0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        recognizedText = baseApi.getUTF8Text();
        baseApi.end();
    return recognizedText;}

    void loadmusic(String completePath1) {
        //String completePath1 = "/sdcard/AnyReader/Media/Heading1.mp3";
        File file = new File(completePath1);
        Uri myUri2 = Uri.fromFile(file);
        myplayer = new MediaPlayer();
        myplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            myplayer.setDataSource(getApplicationContext(), myUri2);
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), "1.You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            myplayer.prepare();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(),"2.You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        }
        catch (IOException e){
            Toast.makeText(getApplicationContext(), "3.You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        }
        myplayer.start();


    }
    void resumemusic() {
        myplayer.seekTo(length);
        myplayer.start();
    }
    void pausemusic(){
        try{  myplayer.pause();
            length=myplayer.getCurrentPosition();
        }catch (Exception e){
            Log.d(TAG,"no pause now!");}
    }




}
