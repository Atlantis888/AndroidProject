package com.casper.testdrivendevelopment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTextActivity extends AppCompatActivity {

    private EditText editTextBookName;
    private Button buttonOk,buttonCancel;
    private int editPosition;
    @Override
    public void onBackPressed() {

        //super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("edit_position", editPosition);
        intent.putExtra("good_name", editTextBookName.getText().toString().trim());
        setResult(RESULT_OK, intent);
        EditTextActivity.this.finish();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        editPosition=getIntent().getIntExtra("edit_position",0);

        editTextBookName=(EditText)findViewById(R.id.edit_text_book_name);
        buttonCancel=(Button)findViewById(R.id.button_cancel);
        buttonOk=(Button)findViewById(R.id.button_ok);

        String goodName= getIntent().getStringExtra("good_name");
        if(goodName!=null) {
            editTextBookName.setText(goodName);
        }


        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("edit_position", editPosition);
                intent.putExtra("good_name", editTextBookName.getText().toString().trim());
                setResult(RESULT_OK, intent);
                EditTextActivity.this.finish();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextActivity.this.finish();
            }
        });

    }
}
