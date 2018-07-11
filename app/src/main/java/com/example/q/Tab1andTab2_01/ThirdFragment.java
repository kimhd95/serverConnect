package com.example.q.Tab1andTab2_01;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ThirdFragment extends Fragment {
    public ThirdFragment(){
    }

    private Intent intent_add, intent_view;
    private ListView listView;
    private ListViewAdapter adapter;
    private ArrayList<ContentModel> contentModelArrayList;
    private SQLiteDatabase DB;
    private ConnectServer connectServer;
    private static final String url = "http://52.231.69.145:8080/";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        connectServer = new ConnectServer();
        View view = inflater.inflate(R.layout.fragment_third,null);
        listView = (ListView) view.findViewById(R.id.lv_LISTVIEW);          // 리스트뷰
        contentModelArrayList = new ArrayList<ContentModel>();  // 저장할 배열




        adapter = new ListViewAdapter(getActivity(), contentModelArrayList);
        listView.setAdapter(adapter);

        /** 2. 아이템 클릭 **/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String title = contentModelArrayList.get(position).getTitle();
                String author = contentModelArrayList.get(position).getAuthor();
                String text = contentModelArrayList.get(position).getText();

                intent_view = new Intent(getActivity(), viewDetailBoard.class);
                intent_view.putExtra("Title", title);
                intent_view.putExtra("Author", author);
                intent_view.putExtra("Text", text);

                startActivity(intent_view);
            }
        });



        FloatingActionButton add_FAB = (FloatingActionButton) view.findViewById(R.id.add);
        FloatingActionButton refresh_FAB = (FloatingActionButton) view.findViewById(R.id.refresh);
        /** 3. Add **/
        add_FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** 3-1-1. 새로운 intent 띄움 **/
                intent_add = new Intent(getActivity(), AddItemActivity.class);
                startActivityForResult(intent_add, 1111);
                /** 3-1-2. intent에서 서버에 request **/

                /** 3-3. 새로고침 **/
            }
        });
        /** 1. 새로고침 **/
        refresh_FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** 1-1. 서버에 request **/
                JSONObject json_refresh = new JSONObject();
                try {
                    json_refresh.put("_refresh", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                connectServer.requestPost(url, json_refresh);
                /** 1-3. response 받아서 띄우는 코드 **/




            }
        });


        //return layout;
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1111) {
            if(resultCode == 1234) {
                /** 3-3. 새로고침 (추가가 제대로 됐으면) **/
            }
        }
    }



}
