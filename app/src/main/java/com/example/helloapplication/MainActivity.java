package com.example.helloapplication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import Adapter.New_Adapter;
import Model.Model_class;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private New_Adapter newAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Model_class> grocery_list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Model_class a=new Model_class(1,"Bread",50);
        Model_class b=new Model_class(1,"Jam",40);
        Model_class c=new Model_class(1,"Butter",70);
        Model_class d=new Model_class(1,"Bun",40);
        Model_class e=new Model_class(1,"Mango Juice",60);
        grocery_list.add(a);
        grocery_list.add(b);
        grocery_list.add(c);
        grocery_list.add(d);
        grocery_list.add(e);
        mRecyclerView=findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        newAdapter=new New_Adapter(grocery_list);
        mRecyclerView.setAdapter(newAdapter);

    }
}