package iamutkarshtiwari.github.io.noos.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.ArrayList;
import java.util.Collections;

import iamutkarshtiwari.github.io.noos.R;
import iamutkarshtiwari.github.io.noos.adapters.RecyclerViewAdapter;
import iamutkarshtiwari.github.io.noos.models.News;
import iamutkarshtiwari.github.io.noos.utils.NewsLoader;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    private static final String NEWS_API_BASE_QUERY =
            "http://content.guardianapis.com/search";

    private static final int NEWS_LOADER = 1;
    private static final String SAVED_LAYOUT_MANAGER = "1";
    private static final String SEARCH_RESULTS = "newsSearchResults";


    private FloatingSearchView searchBarView;
    private String mLastQuery = "";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter newsAdapter;
    private LinearLayoutManager layoutManager;
    private View loadingIndicator;
    private Parcelable layoutManagerSavedState;

    /**
     * Returns context of this activity
     *
     * @return
     */
    public static Context getContext() {
        return getContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBarView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        searchBarView.setOnHomeActionClickListener(
                new FloatingSearchView.OnHomeActionClickListener() {
                    @Override
                    public void onHomeClicked() {
                        finish();
                    }
                });


        loadingIndicator = findViewById(R.id.loading_indicator);
        setLoadingIndicatorVisibilty(false);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        newsAdapter = new RecyclerViewAdapter(this, new ArrayList<News>(), new RecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(News news) {
                String url = news.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        layoutManager = new LinearLayoutManager(getApplicationContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newsAdapter);

        if (savedInstanceState != null) {
            News[] news = (News[]) savedInstanceState.getParcelableArray(SEARCH_RESULTS);
            ArrayList<News> list = new ArrayList<News>(news.length);
            Collections.addAll(list, news);
            newsAdapter.updateAdapterData(list);
            setSearchLabelVisibility(false);
        }

        setupSearchBar();


    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        setLoadingIndicatorVisibilty(true);
        setSearchLabelVisibility(false);
        return new NewsLoader(MainActivity.this, args.getString("request_url"));
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {
        setLoadingIndicatorVisibilty(false);
        if (data == null) {
            Toast.makeText(this, this.getResources().getString(R.string.bad_server), Toast.LENGTH_SHORT).show();
            setSearchLabelVisibility(true);
            return;
        }
        setSearchLabelVisibility(data.size() == 0);
        if (data.size() == 0) {
            Toast.makeText(this, this.getResources().getString(R.string.no_news_found), Toast.LENGTH_SHORT).show();

        } else {
            newsAdapter.updateAdapterData(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        newsAdapter.updateAdapterData(new ArrayList<News>());
        setSearchLabelVisibility(true);
        setLoadingIndicatorVisibilty(false);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Sets up listeners for search bar
     */
    public void setupSearchBar() {


        searchBarView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;
                newsAdapter.getAdapterData().clear();

                Uri baseUri = Uri.parse(NEWS_API_BASE_QUERY);
                Uri.Builder uriBuilder = baseUri.buildUpon();
                uriBuilder.appendQueryParameter("api-key", "31348b4c-ec34-4a51-98d4-dfc8471f2a6c");
                if (query != null)
                    uriBuilder.appendQueryParameter("q", query);

                Bundle args = new Bundle();
                args.putString("request_url", uriBuilder.toString());
                // Check if internet available
                if (!isNetworkAvailable()) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.network_issue), Toast.LENGTH_SHORT).show();
                    return;
                }
                getSupportLoaderManager().restartLoader(NEWS_LOADER, args, MainActivity.this).forceLoad();
            }
        });
    }


    /**
     * Updates search results in the recycler view
     *
     * @param searchResult
     */
    public void updateResults(ArrayList<News> searchResult) {
        RecyclerViewAdapter adapter = (RecyclerViewAdapter) recyclerView.getAdapter();
        adapter.updateAdapterData(searchResult);
        adapter.notifyDataSetChanged();

        searchBarView.clearSuggestions();
        searchBarView.clearQuery();
        searchBarView.clearSearchFocus();
    }

    /**
     * Sets visibility of search label
     *
     * @param flag
     */
    public void setSearchLabelVisibility(boolean flag) {
        RelativeLayout searchLabel = (RelativeLayout) findViewById(R.id.search_label);
        int id = flag ? View.VISIBLE : View.INVISIBLE;
        searchLabel.setVisibility(id);
    }

    /**
     * Show/hide loading indicator
     * @param flag
     */
    public void setLoadingIndicatorVisibilty(boolean flag) {
        int id = flag ? View.VISIBLE : View.INVISIBLE;
        loadingIndicator.setVisibility(id);
    }

    @Override
    public void onBackPressed() {
        if (searchBarView.isSearchBarFocused()) {
            searchBarView.clearSuggestions();
            searchBarView.clearQuery();
            searchBarView.clearSearchFocus();
            return;
        }
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<News> searchList = newsAdapter.getAdapterData();
        News[] books = new News[searchList.size()];
        for (int i = 0; i < books.length; i++) {
            books[i] = searchList.get(i);
        }
        outState.putParcelableArray(SEARCH_RESULTS, (Parcelable[]) books);
    }


}
