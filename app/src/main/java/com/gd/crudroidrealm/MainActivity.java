package com.gd.crudroidrealm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.gd.crudroidrealm.Modelo.Livro;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    ArrayAdapter<String> adapter;
    ArrayList<String> listagemTitulos;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        prepareUI();

        listagemTitulos = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listagemTitulos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, ReceiverBookDetail.class);
                intent.putExtra("livro_titulo", listagemTitulos.get(position).toString());
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.cadastro_livro,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Novo Livro").setView(v).setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        EditText txttitulo = (EditText) v.findViewById(R.id.titulo);
                        EditText txtautor = (EditText) v.findViewById(R.id.autor);
                        EditText txtisbn= (EditText) v.findViewById(R.id.isbn);


                        final Livro book = new Livro();
                        book.setTitulo(txttitulo.getText().toString());
                        book.setAutor(txtautor.getText().toString());
                        book.setIsbn(txtisbn.getText().toString());

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealm(book);
                            }
                        });

                        RealmResults<Livro> realmResults = realm.where(Livro.class).findAll();

                        if(!realmResults.isEmpty()){
                            listagemTitulos.clear();
                            for (Livro l: realmResults) {
                                listagemTitulos.add(l.getTitulo().toString());
                            }
                        }

                        adapter.notifyDataSetChanged();

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        RealmResults<Livro> realmResults = realm.where(Livro.class).findAll();
        if(!realmResults.isEmpty()){
            listagemTitulos.clear();
            for (Livro l: realmResults) {
                listagemTitulos.add(l.getTitulo().toString());
            }
        }

        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("------------------------------------------------------------onrest");

    }

    public void prepareUI(){
        listView = (ListView) findViewById(R.id.lista);
        searchView = (SearchView) findViewById(R.id.search);
    }


}
