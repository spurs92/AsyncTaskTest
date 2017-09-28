package com.spurs.asynctasktest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    MyAdapter adapter;

    ArrayList<Bitmap> bitmaps=new ArrayList<>();

    ArrayList<URL> urls=new ArrayList<>(); //이미지 주소소

    ImageDownTask downTask;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.listview);
        adapter=new MyAdapter(getLayoutInflater(),bitmaps);
        listView.setAdapter(adapter);

       try {
           urls.add(new URL("http://cfile25.uf.tistory.com/image/2716604454C741182D5777"));
           urls.add(new URL("http://bike.chosun.com/site/data/img_dir/2014/09/23/2014092302303_14.jpg"));
           urls.add(new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvjrVwmnHbE33vo1Y3AVtz6foWdRZTd0u0fJBrKQhXAd491pBmeg"));
           urls.add(new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSak0I9z5gy8ovhjNmL-g5SixKoMygVNg-qTZqcaQVq1UVqcE3F"));
           urls.add(new URL("http://bike.chosun.com/site/data/img_dir/2016/04/08/2016040802184_0.jpg"));
           urls.add(new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2RKKMCRZjYiFuqEFBUVKw-hRKDVxE8V9cnC0GSMl3xuMwVO84lQ"));
           urls.add(new URL("http://cfile25.uf.tistory.com/image/2716604454C741182D5777"));
           urls.add(new URL("http://bike.chosun.com/site/data/img_dir/2014/09/23/2014092302303_14.jpg"));
           urls.add(new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvjrVwmnHbE33vo1Y3AVtz6foWdRZTd0u0fJBrKQhXAd491pBmeg"));
           urls.add(new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSak0I9z5gy8ovhjNmL-g5SixKoMygVNg-qTZqcaQVq1UVqcE3F"));
           urls.add(new URL("http://bike.chosun.com/site/data/img_dir/2016/04/08/2016040802184_0.jpg"));
           urls.add(new URL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2RKKMCRZjYiFuqEFBUVKw-hRKDVxE8V9cnC0GSMl3xuMwVO84lQ"));
       } catch (MalformedURLException e) {
           e.printStackTrace();
       }
       downTask=new ImageDownTask();

       //ArrayList를 배열로..
       URL[] arr=new URL[urls.size()];
       urls.toArray(arr);

       downTask.execute(arr);
   }

   //별도 Thread 클래스 설계(백그라운드 작업+UI작업이 모두 가능한 클래스)
    class ImageDownTask extends AsyncTask<URL,Bitmap,String>{

       //작업을 시작하기 전에 호출되는 메소드(UI Thread가 처리)
       @Override
       protected void onPreExecute()
       {
           super.onPreExecute();
       }

       //백그라운드 작업수행(이 메소드가 이 Thread가 처리하는 메소드(run()과 같은)
       //이 클래스 객체의 excute(URL... params) 메소드를 호출하면 실행됨
       @Override
       protected String doInBackground(URL... params) {

           String msg="";

           try {
                Bitmap bm=null;
               for(URL url:params){
                   bm= BitmapFactory.decodeStream(url.openStream());
                   publishProgress(bm);
               }
               msg="성공";
           }catch (Exception e){
               msg="실패";
           }
           return msg;
       }

       //백그라운드 작업의 중간중간 데이터를 UI에 반영하고자 할때 사용
       //doInBackground()메소드에서 publicProgress()를 호출하면 실행되는 메소드(ui Thread가 처리)
       @Override
       protected void onProgressUpdate(Bitmap... values) {
           bitmaps.add(values[0]);
           adapter.notifyDataSetChanged();
           super.onProgressUpdate(values);
       }

       //백그라운드 작업(doInBackground()메소드)이 종료되면 자동으로
       //호출되는 메소드
       //doInBackground()메소드가 리턴한 값을 파라미터 받게됨.
       //보통 스레드 작업의 최종결과를 출력하는 용도(ui Thread가 처리)
       @Override
       protected void onPostExecute(String s) {
           super.onPostExecute(s);
           Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
       }
   }

}
