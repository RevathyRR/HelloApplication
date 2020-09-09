package com.example.helloapplication;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import Adapter.New_Adapter;
import Database.DatabaseHelper;
import Model.Model_class;
import utils.MyDividerItemDecoration;
import utils.RecyclerTouchListner;


public class MainActivity extends AppCompatActivity {
    private New_Adapter newAdapter;
    private ArrayList<Model_class> grocery_list=new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView mRecyclerView;
    private TextView noGroceryView;
    private DatabaseHelper db;

    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //coordinatorLayout = findViewById(R.id.coordinator_layout);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        noGroceryView = findViewById(R.id.empty_items);
        db = new DatabaseHelper(this);


        grocery_list.addAll(db.getAllGroceries());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoteDialog(false, null, -1);
            }
        });

        newAdapter = new New_Adapter(grocery_list);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        mRecyclerView.setAdapter(newAdapter);

         /*Model_class a=new Model_class(1,"Bread",50);
        Model_class b=new Model_class(1,"Jam",40);
        Model_class c=new Model_class(1,"Butter",70);
        Model_class d=new Model_class(1,"Bun",40);
        Model_class e=new Model_class(1,"Mango Juice",60);
        grocery_list.add(a);
        grocery_list.add(b);
        grocery_list.add(c);
        grocery_list.add(d);
        grocery_list.add(e);

         */


        toggleEmptyGrocery();

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListner(this,
                mRecyclerView, new RecyclerTouchListner.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);


            }
        }));
    }

    private void createGrocery(String item, String price){
        long id = db.insertGrocery(item, price);
        Model_class n = db.getGrocery(id);

        if (n!=null){
            grocery_list.add(0, n);
            newAdapter.notifyDataSetChanged();
            toggleEmptyGrocery();

        }

    }

    private void deleteGrocery(int position){
        db.deleteGrocery(grocery_list.get(position));
        grocery_list.remove(position);
        newAdapter.notifyItemRemoved(position);
        toggleEmptyGrocery();
    }


   /* private void updateGrocery(String item, String price, int position){
        Model_class n = grocery_list.get(position);
        n.setItem(item);
        n.setPrice(price);
        db.updateGrocery(n);
        grocery_list.set(position,n);
        newAdapter.notifyItemChanged(position);
        toggleEmptyGrocery();


    }

    */

    private void showActionsDialog(final int position){
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showNoteDialog(true, grocery_list.get(position), position);
                } else {
                    deleteGrocery(position);
                }
            }
        });
        builder.show();

    }


    private void showNoteDialog(final boolean shouldUpdate, final Model_class grocery, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputItem = view.findViewById(R.id.item);
        final EditText inputPrice = view.findViewById(R.id.price);

        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_note_title) : getString(R.string.lbl_edit_note_title));

        if (shouldUpdate && grocery != null) {
            inputItem.setText(grocery.getItem());
            inputPrice.setText(grocery.getPrice());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(inputItem.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter item!", Toast.LENGTH_SHORT).show();
                    return;
                } else if(TextUtils.isEmpty(inputPrice.getText().toString())){
                    Toast.makeText(MainActivity.this, "Enter price!", Toast.LENGTH_SHORT).show();
                }
                else {
                    alertDialog.dismiss();
                }

            /*    // check if user updating note
                if (shouldUpdate && grocery != null) {
                    // update note by it's id
                    updateGrocery(inputItem.getText().toString(), inputPrice.getText().toString(), position); */

                    // create new note
                    createGrocery(inputItem.getText().toString(), inputPrice.getText().toString());
                


            }
        });
    }

    private void toggleEmptyGrocery() {
        // you can check notesList.size() > 0

        if (db.getGroceryCount() > 0) {
            noGroceryView.setVisibility(View.GONE);
        } else {
            noGroceryView.setVisibility(View.VISIBLE);
        }
    }
}