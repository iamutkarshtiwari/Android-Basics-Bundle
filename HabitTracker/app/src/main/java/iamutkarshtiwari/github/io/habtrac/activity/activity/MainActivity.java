package iamutkarshtiwari.github.io.habtrac.activity.activity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import iamutkarshtiwari.github.io.habtrac.R;
import iamutkarshtiwari.github.io.habtrac.activity.adapters.RecyclerViewAdapter;
import iamutkarshtiwari.github.io.habtrac.activity.fragment.AddHabitFragment;
import iamutkarshtiwari.github.io.habtrac.activity.models.Habit;
import iamutkarshtiwari.github.io.habtrac.activity.models.HabitContract;
import iamutkarshtiwari.github.io.habtrac.activity.utils.HabitDbHelper;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter listAdapter;
    private LinearLayoutManager layoutManager;
    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddProductDialog();
            }
        });

        mDbHelper = new HabitDbHelper(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        View listArea = findViewById(R.id.list_view);
        listAdapter = new RecyclerViewAdapter(this, getListFromDatabase(), listArea);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(listAdapter);

    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        updateListView();
    }

    private void showAddProductDialog() {
        AddHabitFragment newFragment = new AddHabitFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.add_product));
    }


    public void updateListView() {
        listAdapter.updateAdapterData(getListFromDatabase());
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

    private ArrayList<Habit> getListFromDatabase() {

        ArrayList<Habit> data = new ArrayList<Habit>();
        Cursor cursor = read();

        try {
            int idColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_NAME);
            int dateColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_START_DATE);
            int numberOfTimesColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_NUMBER_OF_TIMES);

            while (cursor.moveToNext()) {
                String currentID = Integer.toString(cursor.getInt(idColumnIndex));
                String currentName = cursor.getString(nameColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                String currentNumberOfTimes = Integer.toString(cursor.getInt(numberOfTimesColumnIndex));

                data.add(new Habit(currentID, currentName, currentDate, currentNumberOfTimes));

            }
        } finally {
            cursor.close();
        }
        return data;
    }

    private Cursor read() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_NAME,
                HabitContract.HabitEntry.COLUMN_START_DATE,
                HabitContract.HabitEntry.COLUMN_NUMBER_OF_TIMES
        };

        Cursor cursor = db.query(HabitContract.HabitEntry.TABLE_NAME, projection, null, null, null, null, null);
        return cursor;
    }
}
