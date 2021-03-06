package com.example.q.Tab1andTab2_01;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ConnectServer {

    OkHttpClient client = new OkHttpClient();


    public void requestGet(String url, String searchKey) {

        //URL에 포함할 Query문 작성 Name&Value
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();                                   /** 1. Client 초기화 **/
        urlBuilder.addEncodedQueryParameter("searchKey", searchKey);                     /** 2. URL Build **/
        String requestUrl = urlBuilder.build().toString();

        //Query문이 들어간 URL을 토대로 Request 생성
        Request request = new Request.Builder().url(requestUrl).build();                                /** 3. Request 생성 **/

        //만들어진 Request를 서버로 요청할 Client 생성
        //Callback을 통해 비동기 방식으로 통신을 하여 서버로부터 받은 응답을 어떻게 처리 할 지 정의함
        client.newCall(request).enqueue(new Callback() {                                                /** 4. Request를 붙이고 Response에 대한 처리를 담당하는 Callback 작성 **/
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("error", "Connect Server Error is " + e.toString());
        }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("aaaa", "Response Body is " + response.body().string());
            }
        });
    }



    public void requestPost(String url, JSONObject json) {
        //Request Body에 서버에 보낼 데이터 작성
        MediaType JSON = MediaType.parse("application.jsonl charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json.toString());

        //작성한 Request Body와 데이터를 보낼 url을 Request에 붙임
        Request request = new Request.Builder().url(url).post(requestBody).build();

        //request를 Client에 세팅하고 Server로 부터 온 Response를 처리할 Callback 작성
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("error", "Connect Server Error is " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("aaaa", "Response Body is " + response.body().string());
            }
        });
    }
}