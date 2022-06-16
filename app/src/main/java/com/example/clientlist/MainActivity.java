package com.example.clientlist;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientlist.datebase.AppDataBase;
import com.example.clientlist.datebase.AppExecutor;
import com.example.clientlist.datebase.Client;
import com.example.clientlist.datebase.DataAdapter;
import com.example.clientlist.settings.SettingActivity;
import com.example.clientlist.utils.Constans;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppDataBase myDb;
    private DataAdapter adapter;
    private List<Client> listClient;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    public DataAdapter.AdapterOnItemClicked adapterOnItemClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                Toast.makeText(MainActivity.this, "Position Item : " + position, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra(Constans.NAME_KEY, listClient.get(position).getName());
                i.putExtra(Constans.SEC_NAME_KEY, listClient.get(position).getSec_name());
                i.putExtra(Constans.TEL_KEY, listClient.get(position).getNumber());
                i.putExtra(Constans.IMP_KEY, listClient.get(position).getImportance());
                i.putExtra(Constans.DESC_KEY, listClient.get(position).getDescription());
                i.putExtra(Constans.SP_KEY, listClient.get(position).getSpecial());
                i.putExtra(Constans.ID_KEY, listClient.get(position).getId());
                startActivity(i);

            }
        };
        adapter = new DataAdapter(listClient, adapterOnItemClicked, this);
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
                AppExecutor.getInstance().getMainIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (adapter != null) {
                            adapter.updateAdapter(listClient);
                        }
                    }
                });

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        SearchManager sManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        SearchView sView = (SearchView) menu.findItem(R.id.id_search).getActionView();
        assert sManager != null;
        sView.setSearchableInfo(sManager.getSearchableInfo(this.getComponentName()));
        sView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        listClient = myDb.clientDAO().getClientListName(s);
                        AppExecutor.getInstance().getMainIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                if (adapter != null) {
                                    adapter.updateAdapter(listClient);
                                }
                            }
                        });

                    }
                });
                return true;
            }
        });

        return true;
    }

    private void goTo(String url) {
        Intent brIntent, chooser;
        brIntent = new Intent(Intent.ACTION_VIEW);
        brIntent.setData(Uri.parse(url));
        chooser = Intent.createChooser(brIntent, "Öfnnen mit ");
        if (brIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }

    private void init() {
        recyclerView = findViewById(R.id.rView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myDb = AppDataBase.getInstanceDb(getApplicationContext());
        listClient = new ArrayList<>();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_client) {
            AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
                @Override
                public void run() {
                    listClient = myDb.clientDAO().getClientList();
                    AppExecutor.getInstance().getMainIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (adapter != null) {
                                adapter.updateAdapter(listClient);
                            }
                        }
                    });

                }
            });

        } else if (id == R.id.id_web) {
            goTo("https://ru.wikipedia.org");
        } else if (id == R.id.id_settings) {
            Intent i = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(i);

        } else if (id == R.id.id_special) {

            AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
                @Override
                public void run() {
                    listClient = myDb.clientDAO().getClientListSpecial();
                    AppExecutor.getInstance().getMainIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (adapter != null) {
                                adapter.updateAdapter(listClient);
                            }
                        }
                    });

                }
            });
        } else if (id == R.id.id_important) {
            AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
                @Override
                public void run() {
                    listClient = myDb.clientDAO().getClientListImportance(0);
                    AppExecutor.getInstance().getMainIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (adapter != null) {
                                adapter.updateAdapter(listClient);
                            }
                        }
                    });

                }
            });
        } else if (id == R.id.id_normal) {
            AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
                @Override
                public void run() {
                    listClient = myDb.clientDAO().getClientListImportance(1);
                    AppExecutor.getInstance().getMainIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (adapter != null) {
                                adapter.updateAdapter(listClient);
                            }
                        }
                    });

                }
            });
        } else if (id == R.id.id_no_important) {
            AppExecutor.getInstance().getDiscIO().execute(new Runnable() {
                @Override
                public void run() {
                    listClient = myDb.clientDAO().getClientListImportance(2);
                    AppExecutor.getInstance().getMainIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (adapter != null) {
                                adapter.updateAdapter(listClient);
                            }
                        }
                    });

                }
            });
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}