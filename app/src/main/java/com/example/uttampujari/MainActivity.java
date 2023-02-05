package com.example.uttampujari;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Model>models;
    SqlDatabse db;
    String url = "https://reqres.in/api/users?page=1";
    RecyclerView recyclerView;
    Adapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Model> models = new ArrayList<>();

        db = new SqlDatabse(this);
        recyclerView = findViewById(R.id.rec);

        parseApiData();

        Cursor cursor = new SqlDatabse(this).readAllData();

        if (cursor.moveToFirst()) {
            do {

                Model model = new Model();

                model.setFIRST_NAME(cursor.getString(1).toString());
                model.setLAST_NAME(cursor.getString(2).toString());
                model.setEMAIL(cursor.getString(3).toString());
                model.setIMAGE(cursor.getString(4).toString());
                //model.setID(cursor.getInt(0);

                models.add(model);
                System.out.println(model);

            } while (cursor.moveToNext());
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new Adapter(getApplicationContext(), models);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((int) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void removeItem(int id) {
        db.deleteCourse(id);
    }

    private void parseApiData() {

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Res : ",response);
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length()>0)
                    {
                        for (int i = 0; i<jsonArray.length(); i++)
                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String firstName = jsonObject1.getString("first_name");
                            String lastName = jsonObject1.getString("last_name");
                            String email = jsonObject1.getString("email");
                            String photo = jsonObject1.getString("avatar");

                            db.insertData(firstName,lastName,email,photo);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }


        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);

    }
}