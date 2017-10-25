package iamutkarshtiwari.github.io.noos.utils;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

import iamutkarshtiwari.github.io.noos.models.News;

/**
 * Created by utkarshtiwari on 14/10/17.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

    private String dataSource;

    public NewsLoader(Context context, String dataSource) {
        super(context);
        this.dataSource = dataSource;
    }

    @Override
    public ArrayList<News> loadInBackground() {
        JSONParser jsonParser = new JSONParser(this.dataSource);
        return jsonParser.getResponseData();
    }
}