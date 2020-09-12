package com.example.clientlist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.clientlist.datebase.AppDataBase;
import com.example.clientlist.datebase.AppExecutor;
import com.example.clientlist.datebase.Client;
import com.example.clientlist.datebase.DataAdapter;
import com.example.clientlist.utils.Constans;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppDataBase myDb;
    private DataAdapter adapter;
    private List<Client> listClient;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    public  DataAdapter.AdapterOnItemClicked adapterOnItemClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawer_layout);

        adapterOnItemClicked = new DataAdapter.AdapterOnItemClicked() {
            @Override
            public void onAdapterItemClicked(int position) {

                Toast.makeText(MainActivity.this, "Position Item : "+ position, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra(Constans.NAME_KEY,listClient.get(position).getName());
                i.putExtra(Constans.SEC_NAME_KEY,listClient.get(position).getSec_name());
                i.putExtra(Constans.TEL_KEY,listClient.get(position).getNumber());
                i.putExtra(Constans.IMP_KEY,listClient.get(position).getImportance());
                i.putExtra(Constans.DESC_KEY,listClient.get(position).getDescription());
                i.putExtra(Constans.SP_KEY,listClient.get(position).getSpecial());
                i.putExtra(Constans.ID_KEY,listClient.get(position).getId());
                startActivity(i);

            }
        };
        adapter = new DataAdapter(listClient, adapterOnItemClicked);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();
        AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
            @Override
            public void run() {
                listClient = myDb.clientDAO().getClientList();
                AppExecutor.getInstance().getMainIO().execute(new Runnable()
                {
                    @Override
                    public void run()
                    {
                         if(adapter != null)
                         {
                             adapter.updateAdapter(listClient);
                         }
                    }
                });

            }
        });

    }

    private void goTo(String url)
    {
        Intent brIntent, chooser;
        brIntent = new Intent(Intent.ACTION_VIEW);
        brIntent.setData(Uri.parse(url));
        chooser = Intent.createChooser(brIntent,"Öfnnen mit ");
        if(brIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(chooser);
        }
    }

    private void init()
    {
        recyclerView = findViewById(R.id.rView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myDb = AppDataBase.getInstanceDb(getApplicationContext());
        listClient = new ArrayList<>();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.id_client)
        {
            Toast.makeText(this, "client pulsed", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.id_web)
        {
            goTo("https://ru.wikipedia.org");
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {

    }

}