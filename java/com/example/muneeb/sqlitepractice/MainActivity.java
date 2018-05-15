package com.example.muneeb.sqlitepractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etID, etFName, etLName;
    Button btnCreate, btnUpdate, btnRead, btnDelete;
    ListView lvData;

    DB_Institute instituteDB;

    ArrayList<String> listDBData = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instituteDB = new DB_Institute(this);

        etID = (EditText) findViewById(R.id.et_ID);
        etFName = (EditText) findViewById(R.id.et_fName);
        etLName = (EditText) findViewById(R.id.et_lName);

        btnCreate = (Button) findViewById(R.id.btn_create);
        btnRead = (Button) findViewById(R.id.btn_view);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnUpdate = (Button) findViewById(R.id.btn_update);

        lvData = (ListView) findViewById(R.id.lv_DBData);

        arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                listDBData);
        lvData.setAdapter(arrayAdapter);
    }

    public void registerNewStudent(View view) {
        String FName = etFName.getText().toString();
        String LName = etLName.getText().toString();

        try {
            instituteDB.open();
            instituteDB.newStudent(FName, LName);
            Toast.makeText(this,
                    "Student Registered!",
                    Toast.LENGTH_SHORT).show();
            etFName.setText("");
            etLName.setText("");
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            instituteDB.close();
        }
    }

    public void showAllRecords(View view) {
        showList();
    }

    public void showList() {
        arrayAdapter.clear();
        try {
            instituteDB.open();
            arrayAdapter.add(instituteDB.getAllRecords());
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            instituteDB.close();
        }
    }

    public void updateRecords(View view) {
        int ID = Integer.parseInt(etID.getText().toString());
        String FName = etFName.getText().toString();
        String LName = etLName.getText().toString();

        try {
            instituteDB.open();
            instituteDB.updatingRecords(ID, FName, LName);
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            instituteDB.close();
        }
        showList();
    }

    public void deleteRecords(View view) {
        int ID = Integer.parseInt(etID.getText().toString());

        try {
            instituteDB.open();
            instituteDB.deletingRecords(ID);
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            instituteDB.close();
        }
        showList();
    }
}
