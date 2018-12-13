package com.marvel.developer.comicdata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marvel.developer.comicdata.network.BookResponse;
import com.marvel.developer.comicdata.network.GetBookIntentService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String COMIC_BOOK_ID = "comicBookId";

    public static final String BOOK_NOT_FOUND = "Book Not Found";
    public static final String ERROR_CONNECTING = "Error Connecting";
    public static final String NO_WIFI = "No WiFi";
    public static final String NO_ERRORS = "No Errors";

    public static final String DEFAULT_COMIC = "40632";

    private ImageView thumbImageView = null;
    private TextView titleLabel = null;
    private TextView titleTextView = null;
    private TextView priceLabel = null;
    private TextView priceTextView = null;

    private TextView byLabel = null;
    private TextView byTextView = null;

    private TextView releasedLabel = null;
    private TextView releasedTextView = null;

    private TextView pagesLabel = null;
    private TextView pagesTextView = null;

    private TextView descriptionLabel = null;
    private TextView descriptionTextView = null;

    private RecyclerView recyclerView;
    private ImageListRVadapter imageListRVadapter;

    private BookResponseReceiver bookResponseReceiver = new BookResponseReceiver();
    private BookResponse bookResponse = null;

    private static int oldConfigInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        thumbImageView = findViewById(R.id.thumbImage);

        titleLabel = findViewById(R.id.titleLabel);
        titleTextView = findViewById(R.id.titleTextView);
        priceLabel = findViewById(R.id.priceLabel);
        priceTextView = findViewById(R.id.priceTextView);

        byLabel = findViewById(R.id.byLabel);
        byTextView = findViewById(R.id.byTextView);

        releasedLabel = findViewById(R.id.releasedLabel);
        releasedTextView = findViewById(R.id.releasedTextView);

        pagesLabel = findViewById(R.id.pagesLabel);
        pagesTextView = findViewById(R.id.pagesTextView);

        descriptionLabel = findViewById(R.id.descriptionLabel);
        descriptionTextView = findViewById(R.id.descriptionTextView);

        recyclerView = findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager recyclerLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        recyclerView.setHasFixedSize(true);

        if ((oldConfigInt & ActivityInfo.CONFIG_ORIENTATION) == ActivityInfo.CONFIG_ORIENTATION) {
            // Orientation changed
            if (savedInstanceState != null) {
                // Orientation changed and
                // Data in saved instance state

                bookResponse = savedInstanceState.getParcelable("book_response");
                refillFromBookResponse();

            } else {
                // Orientation changed. No data in saved instance state
                // If no rotation, and/or no data, that means we just started/restarted
                onItemSelected(DEFAULT_COMIC);
            }
        } else {
            if (savedInstanceState != null) {
                // Orientation not changed. First run or restarted by system. Data in saved instance state
                bookResponse = savedInstanceState.getParcelable("book_response");
                refillFromBookResponse();
            } else {
                // If no rotation, and/or no data, that means we just started/restarted
                // Orientation not changed. First run or restarted by system. No data in saved instance state
                onItemSelected(DEFAULT_COMIC);
            }
        }
    }


    public void onItemSelected(String selectedBook) {

        Intent getComicBookIntServ = new Intent(getApplicationContext(), GetBookIntentService.class);
        getComicBookIntServ.putExtra(COMIC_BOOK_ID, selectedBook);
        startService(getComicBookIntServ);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (this.bookResponse != null) {
            outState.putParcelable("book_response", bookResponse);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.someHero) {
            onItemSelected("40638");
            return true;
        } else if (id == R.id.hulk) {
            onItemSelected("40632");
            return true;
        } else if (id == R.id.venom) {
            onItemSelected("40633");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(bookResponseReceiver, new IntentFilter(GetBookIntentService.BOOK_RESPONSE_ACTION));

    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(bookResponseReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        oldConfigInt = getChangingConfigurations();
    }

    private void refillFromBookResponse() {
        if (bookResponse != null && bookResponse.getResultString().equals(MainActivity.NO_ERRORS)) {
            Uri imageUri = Uri.parse(bookResponse.getImageUrl());
            Picasso.with(MainActivity.this).load(imageUri).placeholder(R.drawable.marvel_icon).fit().centerCrop().into(thumbImageView);

            titleTextView.setText(bookResponse.getTitle());
            priceTextView.setText(bookResponse.getPrice());

            byTextView.setText(bookResponse.getWrittenBy());
            releasedTextView.setText(bookResponse.getReleaseDate());
            pagesTextView.setText(bookResponse.getNumberOfPages());

            descriptionTextView.setText(bookResponse.getDescription());
            if (imageListRVadapter == null) {
                imageListRVadapter = new ImageListRVadapter(bookResponse.getPreviewImageUrls());
                recyclerView.setAdapter(imageListRVadapter);
            } else {
                imageListRVadapter = new ImageListRVadapter(bookResponse.getPreviewImageUrls());
                recyclerView.setAdapter(imageListRVadapter);
                imageListRVadapter.notifyDataSetChanged();
            }
        }
    }

    // Adapter for the recycler view that displays the list of preview pages from the comic book
    class ImageListRVadapter extends RecyclerView.Adapter<ImageListItemVH> {
        ArrayList<String> imageList;
        private ImageListItemVH viewHolder;

        public ImageListRVadapter(ArrayList<String> list) {

            this.imageList = list;
        }

        @Override
        public ImageListItemVH onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_preview, parent, false);
            return new ImageListItemVH(view);

        }

        @Override
        public void onBindViewHolder(ImageListItemVH holder, int position) {

            viewHolder = holder;
            String imagePath = this.imageList.get(position);
            Uri uri = Uri.parse(imagePath);
            Context context = holder.itemView.getContext();
            Picasso.with(context).load(uri).into(viewHolder.previewImage);
        }

        @Override
        public int getItemCount() {
            return this.imageList.size();
        }

        public void setItems(ArrayList<String> list) {
            this.imageList = list;
        }

        public ImageListItemVH getViewHolder() {
            return viewHolder;
        }
    }

    /**
     *
     *
     */
    private class ImageListItemVH extends RecyclerView.ViewHolder {

        public ImageView previewImage;
        public View itemView;

        public ImageListItemVH(View itemView) {
            super(itemView);

            this.itemView = itemView;
            previewImage = itemView.findViewById(R.id.previewImage);
        }
    }


    public class BookResponseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(GetBookIntentService.BOOK_RESPONSE_ACTION)) {

                bookResponse = intent.getParcelableExtra("book_response");
                if (bookResponse.getResultString().equals(MainActivity.NO_ERRORS)) {
                    refillFromBookResponse();
                }
            }
        }
    }
}
