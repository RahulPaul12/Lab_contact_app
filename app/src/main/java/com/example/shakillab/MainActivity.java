package com.example.shakillab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edittext_name, edittext_email, edittext_id;
    private  Button button_add,button_view, button_update,button_delete;
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edittext_name = findViewById(R.id.edittext_name);
        edittext_email = findViewById(R.id.edittext_email);
        edittext_id = findViewById(R.id.edittext_id);
        button_add = findViewById(R.id.button_add);
        button_view = findViewById(R.id.button_view);
        button_update = findViewById(R.id.button_update);
        button_delete = findViewById(R.id.button_delete);


        button_add.setOnClickListener(this);
        button_view.setOnClickListener(this);
        button_update.setOnClickListener(this);
        button_delete.setOnClickListener(this);
        myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View view) {
        String name = edittext_name.getText().toString();
        String number = edittext_email.getText().toString();
        String id = edittext_id.getText().toString();
      if(view.getId()==R.id.button_add){
        long rowId=  myDatabaseHelper.insertData(name,number);
        if(rowId>0){
            Toast.makeText(this, "Contact saved successfully!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
      }
      if(view.getId()==R.id.button_view){
          Cursor cursor = myDatabaseHelper.displayData();
          if(cursor.getCount()==0){
              showData("Error","No data Found!");
              return;
          }
          StringBuffer stringBuffer = new StringBuffer();
          while (cursor.moveToNext()){
              stringBuffer.append("ID :"+cursor.getString(0)+"\n");
              stringBuffer.append("NAME :"+cursor.getString(1)+"\n");
              stringBuffer.append("NUMBER :"+cursor.getString(2)+"\n");
          }
          showData("Contact List",stringBuffer.toString());
      }
      if(view.getId()==R.id.button_update){
       Boolean isUpdated=  myDatabaseHelper.updateData(id,name,number);
       if(isUpdated==true){
           Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
       }else{
           Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
       }
      }
      if(view.getId()==R.id.button_delete){
       int value=   myDatabaseHelper.deleteData(id);
       if(value>0){
           Toast.makeText(this, "Contact deleted successfully!", Toast.LENGTH_SHORT).show();
       }
       else{
           Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
       }
      }
    }
    public void showData(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();


    }
}