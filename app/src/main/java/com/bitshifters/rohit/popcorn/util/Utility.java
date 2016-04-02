package com.bitshifters.rohit.popcorn.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bitshifters.rohit.popcorn.R;
import com.bitshifters.rohit.popcorn.api.MovieDbOrgApiService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rohit on 29/3/16.
 */
public class Utility {
    private final static String TAG = Utility.class.getSimpleName();
    private final static String MY_PREFS = "MyPrefs";
    private final static String SORT_PREFERENCE = "sortPreference";
    private final static String YOUTUBE_VIDEO_THUMBNAIL_BASE_URL = "http://img.youtube.com/vi/";
    private final static int STANDARD_TEXT_EXCERPT_LENGTH = 150;

    public static String getFormattedDate(String dateString ) {
        String inputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.US);
        try {
            Date date = inputFormat.parse(dateString);
            return DateFormat.getDateInstance().format(date);
        } catch (ParseException e) {
            Log.e(TAG, "The Release data was not parsed successfully: " + dateString);
            // Return not formatted date
        }
        return dateString;
    }

    public static @MovieDbOrgApiService.SORT_BY String getSortPreference(Context context){
        SharedPreferences preferences = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        @MovieDbOrgApiService.SORT_BY String sortPreference =  preferences.getString(SORT_PREFERENCE, MovieDbOrgApiService.SORT_BY_POPULAR);
        return sortPreference;
    }

    public static void saveSortPreference(Context context, @MovieDbOrgApiService.SORT_BY String sortBy){
        SharedPreferences preferences = context.getSharedPreferences(MY_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SORT_PREFERENCE,sortBy);
        editor.commit();
    }

    public static String getPortraitPosterUrl(Context context, String relativeUrl){
        return MovieDbOrgApiService.IMAGE_BASE_URL
                + context.getResources().getString(R.string.portrait_poster_size_code)
                + relativeUrl;
    }

    public static String getLandscapePosterUrl(Context context, String relativeUrl){
        return MovieDbOrgApiService.IMAGE_BASE_URL
                + context.getResources().getString(R.string.landscape_poster_size_code)
                + relativeUrl;
    }

    //http://img.youtube.com/vi/Ay-ZN4uRZ-4/mqdefault.jpg
    public static String getYoutubeThumbnailUrl(String videoUrl){
        return YOUTUBE_VIDEO_THUMBNAIL_BASE_URL + videoUrl + "/1.jpg";
    }

    public static String getReviewExcerpt(String reviewText){
        if (reviewText.length() > STANDARD_TEXT_EXCERPT_LENGTH){
            return reviewText.substring(0,STANDARD_TEXT_EXCERPT_LENGTH)+"...Read More";
        }
        return reviewText;
    }
}
