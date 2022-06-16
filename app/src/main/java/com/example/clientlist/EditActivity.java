package com.example.clientlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clientlist.datebase.AppDataBase;
import com.example.clientlist.datebase.AppExecutor;
import com.example.clientlist.datebase.Client;
import com.example.clientlist.utils.Constans;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditActivity extends AppCompatActivity {
    private EditText edName, edSecName, edTel, edNotes;
    private CheckBox checkBoxImp, checkBoxNormal, checkBoxNoImp, checkBoxSpecial;
    private AppDataBase myDb;
    private int importance = 1;
    private int special = 0;
    private FloatingActionButton fb;
    private CheckBox[] checkBoxes = new CheckBox[3];
    private boolean isEdit = false;
    private boolean new_user = false;
    private int id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layot);
        init();
        getMyIntent();
        fb = findViewById(R.id.fb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImportanceFromCheck();

                if (!TextUtils.isEmpty(edName.getText().toString()) && !TextUtils.isEmpty(edSecName.getText().toString()) &&
                        !TextUtils.isEmpty(edTel.getText().toString()) && !TextUtils.isEmpty(edNotes.getText().toString())) {

                    AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (isEdit) {
                                Client client = new Client(edName.getText().toString(), edSecName.getText().toString(),
                                        edTel.getText().toString(), importance, edNotes.getText().toString(), special);
                                client.setId(id);
                                myDb.clientDAO().updateClient(client);
                                finish();
                            } else {

                                Client client = new Client(edName.getText().toString(), edSecName.getText().toString(),
                                        edTel.getText().toString(), importance, edNotes.getText().toString(), special);
                                myDb.clientDAO().insertClient(client);
                                finish();
                            }

                        }
                    });

                }
            }
        });


    }

    private void init() {
        fb = findViewById(R.id.fb);
        myDb = AppDataBase.getInstanceDb(getApplicationContext());
        edName = findViewById(R.id.edName);
        edSecName = findViewById(R.id.edSecName);
        edTel = findViewById(R.id.edTel);
        edNotes = findViewById(R.id.edNoties);

        checkBoxImp = findViewById(R.id.chekBoxImp);
        checkBoxNormal = findViewById(R.id.chekBoxNor);
        checkBoxNoImp = findViewById(R.id.chekBoxNoImp);
        checkBoxes[0] = checkBoxImp;
        checkBoxes[1] = checkBoxNormal;
        checkBoxes[2] = checkBoxNoImp;
        checkBoxSpecial = findViewById(R.id.chekBoxSpecial);
    }

    private void getMyIntent() {
        Intent i = getIntent();
        if (i != null) {
            if (i.getStringExtra(Constans.NAME_KEY) != null) {
                setEditPressed(false);
                edName.setText(i.getStringExtra(Constans.NAME_KEY));
                edSecName.setText(i.getStringExtra(Constans.SEC_NAME_KEY));
                edTel.setText(i.getStringExtra(Constans.TEL_KEY));
                edNotes.setText(i.getStringExtra(Constans.DESC_KEY));
                checkBoxes[i.getIntExtra(Constans.IMP_KEY, 0)].setChecked(true);
                if (i.getIntExtra(Constans.SP_KEY, 0) == 1) checkBoxSpecial.setChecked(true);
                id = i.getIntExtra(Constans.ID_KEY, 0);
                new_user = false;

            } else {
                new_user = true;
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (!new_user) getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_menu) {
            setEditPressed(true);
        } else if (id == R.id.id_delete) {
            deleteMessage();
        }

        return true;
    }

    public void setEditPressed(boolean edit) {
        if (edit) {
            fb.show();
        } else {

            fb.hide();
        }

        this.isEdit = edit;
        checkBoxImp.setClickable(edit);
        checkBoxNormal.setClickable(edit);
        checkBoxNoImp.setClickable(edit);
        checkBoxSpecial.setClickable(edit);

        edName.setFocusable(edit);
        edSecName.setFocusable(edit);
        edTel.setFocusable(edit);
        edNotes.setFocusable(edit);

        edName.setClickable(edit);
        edSecName.setClickable(edit);
        edTel.setClickable(edit);
        edNotes.setClickable(edit);

        edName.setFocusableInTouchMode(edit);
        edSecName.setFocusableInTouchMode(edit);
        edTel.setFocusableInTouchMode(edit);
        edNotes.setFocusableInTouchMode(edit);

    }

    public void deleteMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_message);
        builder.setTitle(R.string.delete_title);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        Client client = new Client(edName.getText().toString(), edSecName.getText().toString(),
                                edTel.getText().toString(), importance, edNotes.getText().toString(), special);
                        client.setId(id);
                        myDb.clientDAO().deleteClient(client);
                        finish();

                    }


                });

            }

        });


        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();


    }


    public void onClickImp(View view) {
        checkBoxNormal.setChecked(false);
        checkBoxNoImp.setChecked(false);
    }

    public void onClickNor(View view) {
        checkBoxImp.setChecked(false);
        checkBoxNoImp.setChecked(false);
    }

    public void onClickNoImp(View view) {
        checkBoxImp.setChecked(false);
        checkBoxNormal.setChecked(false);
    }

    private void getImportanceFromCheck() {
        if (checkBoxImp.isChecked()) {
            importance = 0;
        } else if (checkBoxNormal.isChecked()) {
            importance = 1;
        } else if (checkBoxNoImp.isChecked()) {
            importance = 2;
        }


        if (checkBoxSpecial.isChecked()) special = 1;

    }

}
