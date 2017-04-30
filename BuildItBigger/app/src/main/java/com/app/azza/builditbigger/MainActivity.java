package com.app.azza.builditbigger;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;



public class MainActivity extends AppCompatActivity {
    private ProgressBar spinner;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
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


   /* public void tellJoke(View view) {

        String joke;
        JokesJava JokeTeller= new JokesJava();
        joke=JokeTeller.getJoke();
        Toast.makeText(this, joke, Toast.LENGTH_SHORT).show();
    }
*/
    //ba5od joke mn java library ab3atha lel android library 3lshan display
    public void launchJokeActivity(View view) {
//        Intent intent = new Intent(this, JokeActivity.class);
//         JokesJava jokeSource = new JokesJava();
//       String joke = jokeSource.getJoke();
//
//      intent.putExtra(JokeActivity.JOKE_KEY, joke);
//        startActivity(intent);

        spinner.setVisibility(View.VISIBLE);

        new EndpointsAsyncTask().execute(new Pair<Context, ProgressBar>(this,spinner));
    }

}
