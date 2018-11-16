package ksn7.cs262.calvin.edu.homework2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String> {

    private EditText playerInput;
    private TextView mOutputText;
    private Context context;

    /**
     * onCreate sets up the saved instance state
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        playerInput = (EditText) findViewById(R.id.playerInput);
        mOutputText = (TextView) findViewById(R.id.titleText);
        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    /**
     * searchPlayers handles in user input when the button is pressed.
     * Handles case of empty search and handles improper input cases
     *
     * @param view
     */
    public void searchPlayers(View view) {
        // Take input as string, check if negative input, mark if nonempty
        String mQueryString = playerInput.getText().toString();
        int queryNumber = -1;
        Boolean nonempty = false;
        if (mQueryString != "") {
            nonempty = true;
            queryNumber = Integer.parseInt(mQueryString);
        }

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (nonempty && queryNumber <= 0) {
            mOutputText.setText(getString(R.string.negative_input));
        } else {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", mQueryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            mOutputText.setText(getString(R.string.loading));
        }
    }

    /**
     * Creates PlayerLoader
     *
     * @param i
     * @param bundle
     * @return
     */
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new PlayerLoader(this, bundle.getString("queryString"));
    }

    /**
     * onLoadFinished turns the JSON response from the loader into displayable text
     *
     * @param loader
     * @param s
     */
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        try {
            // Convert the response into a JSON object.
            JSONObject jsonObject = new JSONObject(s);

            try {
                // Initialize iterator and results fields.
                int id = -1;
                String name = null;
                String email = null;

                // Set up JSON array and length info for loop
                JSONArray itemsArray = jsonObject.getJSONArray("items");
                int num_players = itemsArray.length();

                // Look for results in the items array, exiting when both the title and author
                // are found or when all items have been checked.
                for (int i = 0; i < num_players; i++) {
                    // Get the current item information.
                    JSONObject player = itemsArray.getJSONObject(i);

                    // Try to get the author and title from the current item,
                    // catch if either field is empty and move on.
                    id = player.getInt("id");
                    name = player.getString("name");
                    email = player.getString("emailAddress");

                }

                // If both are found, display the result.
                if (id != -1) {
                    mOutputText.setText(Integer.toString(id));
                    playerInput.setText("");
                } else {
                    // If none are found, update the UI to show failed results.
                    mOutputText.setText(R.string.no_results);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Toast.makeText(context, getString(R.string.inside_msg), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            mOutputText.setText(R.string.connection_failed);
        }
    }

    /**
     * Placeholder for resetting the loader
     *
     * @param loader
     */
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    /**
     * onCreateOptionsMenu
     *
     * @param menu
     * @return true to mark it worked
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Handler for settings when an item is selected. Doesn't do much at this point
     *
     * @param item
     * @return result of super-call
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
