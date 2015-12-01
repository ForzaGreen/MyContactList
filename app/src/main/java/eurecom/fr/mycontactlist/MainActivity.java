package eurecom.fr.mycontactlist;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Contact>>, AdapterView.OnItemClickListener {
    private ListView listView;
    private List<Contact> contacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //return netInfo != null && netInfo.isConnectedOrConnecting();

        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView1);

        //W:
        listView.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        // return true;
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("main", "onResume");
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<List<Contact>> onCreateLoader(int id, Bundle args) {

        Log.i("main", "creating loader");
        JsonLoader loader = new JsonLoader(this);
        loader.forceLoad();
        return loader;

    }

    @Override
    public void onLoadFinished(Loader<List<Contact>> arg0, List<Contact> arg1) {


        //contacts = arg1;
        //listView.setAdapter(new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1,arg1)); //new ContactAdapter(this, R.layout.contact, contacts));
        contacts = arg1;
        ContactAdapter Ca = new ContactAdapter(this, R.layout.contact, contacts);
        listView.setAdapter(Ca); //new ContactAdapter(this, R.layout.contact, contacts));


    }

    @Override
    public void onLoaderReset(Loader<List<Contact>> arg0) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //W:
        Log.isLoggable("id selectioné: ", id);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.i("Main:", "someone clicked on item: " + position);

        //W:
        //start ModifyContactActivity
        Contact contact = (Contact) parent.getAdapter().getItem(position);
        Intent intent = new Intent(this,ModifyContactActivity.class);
        intent.putExtra("contact", contact);
        startActivity(intent);


    }


}