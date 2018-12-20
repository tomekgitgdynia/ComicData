package com.marvel.developer.comicdata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.marvel.developer.comicdata.data.AttributeListItem;
import com.marvel.developer.comicdata.data.BookResponse;
import com.marvel.developer.comicdata.network.GetBookIntentService;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String COMIC_BOOK_ID = "comicBookId";

    public static final String BOOK_NOT_FOUND = "Book Not Found";
    public static final String ERROR_CONNECTING = "Error Connecting";
    public static final String NO_WIFI = "No WiFi";
    public static final String NO_ERRORS = "No Errors";

    // Default comic allowing to show something if all else fails.  For demo app only.
    public static final String DEFAULT_COMIC = "40632";
    private Button priceButton = null;
    private TextView descriptionTextView = null;

    private RecyclerView attributesRecyclerView;
    private AttributesRVadapter attributesRVadapter;

    private BookResponseReceiver bookResponseReceiver = new BookResponseReceiver();
    private BookResponse bookResponse = null;

    ViewFlipper viewFlipper;
    Toolbar toolbar;
    Picasso picasso = null;

    private static int oldConfigInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        picasso = Picasso.with(MainActivity.this);
        picasso.setIndicatorsEnabled(true);

        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        if(width > height)
        {
            setContentView(R.layout.activity_main_horizontal);
        } else
        {
            setContentView(R.layout.activity_main);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Price button is placed overlapping image on purpose as part of a design.
        priceButton = findViewById(R.id.price_button);

        descriptionTextView = findViewById(R.id.descriptionTextView);
        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());

        attributesRecyclerView = findViewById(R.id.attributes_recycler_view);
        RecyclerView.LayoutManager recyclerLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        attributesRecyclerView.setLayoutManager(recyclerLayoutManager);
        attributesRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(MainActivity.this));
        attributesRecyclerView.setHasFixedSize(true);

        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);

        // Handling orientation changes and starting point (displaying default inage).
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


        viewFlipper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                viewFlipper.showNext();

            }
        });
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

        // Some temporary hardcoded book numbers.  For demo only as there is no
        // comic book list activity, only detail
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

            // Sets the title to the comic name as this is the comic detail activity
            // Placing title in the tool bar saves space in the body of the app
            toolbar.setTitle(bookResponse.getTitle());

            // Some quick editing of the purchase button.  The $ sign should be localized.
            priceButton.setText("$" + bookResponse.getPrice());

            // The description of the comic has its own text box
            descriptionTextView.setText(bookResponse.getDescription());

            // Set up the adapter to display attributes like dates, contributors etc.
            attributesRVadapter = new AttributesRVadapter(bookResponse.getAttributeItemList());
            attributesRecyclerView.setAdapter(attributesRVadapter);
            attributesRVadapter.notifyDataSetChanged();

            // Place images in the main image box on top.  Urls are retrieved from the
            // response and the Picasso handles each of the images loading and caching
            viewFlipper.removeAllViews();
            for (String urlString : bookResponse.getPreviewImageUrls()) {
                ImageView image = new ImageView(getApplicationContext());
                picasso.load(urlString).placeholder(R.drawable.marvel_icon).fit().into(image);
                viewFlipper.addView(image);
            }

        }
    }

    // This is to draw a divider between attributes rows
    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft() + 10;
            int right = parent.getWidth() - parent.getPaddingRight() - 10;
            int childCount = parent.getChildCount();

            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.setAlpha(100);
                mDivider.draw(c);
            }
        }
    }

    class AttributesRVadapter extends RecyclerView.Adapter<AttributesItemVH> {
        ArrayList<AttributeListItem> attributesList;
        private AttributesItemVH viewHolder;

        public AttributesRVadapter(ArrayList<AttributeListItem> attributesList) {

            this.attributesList = attributesList;
        }

        @Override
        public AttributesItemVH onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attribute_item, parent, false);
            return new AttributesItemVH(view);

        }

        @Override
        public void onBindViewHolder(AttributesItemVH holder, int position) {

            viewHolder = holder;

            viewHolder.attributeNameTV.setText(attributesList.get(position).getAttributeName());
            viewHolder.attributeValueTV.setText(attributesList.get(position).getAttributeValue());
        }

        @Override
        public int getItemCount() {
            return this.attributesList.size();

        }

        public void setItems(ArrayList<AttributeListItem> list) {
            this.attributesList = list;
        }

        public AttributesItemVH getViewHolder() {
            return viewHolder;
        }
    }

    /**
     *
     *
     */
    private class AttributesItemVH extends RecyclerView.ViewHolder {

        public String attributeName;
        public String attributeValue;

        TextView attributeNameTV = null;
        TextView attributeValueTV = null;

        public View itemView;

        public AttributesItemVH(View itemView) {
            super(itemView);

            this.itemView = itemView;

            attributeNameTV = (TextView) itemView.findViewById(R.id.attribute_name_text_view);
            attributeValueTV = (TextView) itemView.findViewById(R.id.attribute_value_text_view);

        }
    }

    // The Intent Service receiver with book response data passed inside Intent as Parcelable
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
