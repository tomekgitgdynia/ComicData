package com.marvel.developer.comicdata.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;


import com.google.gson.Gson;
import com.marvel.developer.comicdata.MainActivity;
import com.marvel.developer.comicdata.network.parseJSON.Creators;
import com.marvel.developer.comicdata.network.parseJSON.Data;
import com.marvel.developer.comicdata.network.parseJSON.Date;
import com.marvel.developer.comicdata.network.parseJSON.Image;
import com.marvel.developer.comicdata.network.parseJSON.Item;
import com.marvel.developer.comicdata.network.parseJSON.JsonBody;
import com.marvel.developer.comicdata.network.parseJSON.Price;
import com.marvel.developer.comicdata.network.parseJSON.Result;


import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class GetBookIntentService extends IntentService {

    public static final String BOOK_RESPONSE_ACTION = "com.marvel.developer.comicdata.network.BOOK_RESPONSE_ACTION";
    private static final String TAG = "GetBookIntentService";
    private static final int PRINT_PRICE_OFFSET = 0;
    private static final int DIGITAL_PRICE_OFFSET = 1;

    private static final int ON_SALE_DATE_OFFSET = 0;

    public GetBookIntentService() {
        super("GetBookIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        int numberOfItems = 0;
        Intent broadcastIntent = new Intent();
        BookResponse bookResponse = null;
        if (intent != null) {
            String comicBookId = intent.getStringExtra(MainActivity.COMIC_BOOK_ID);
            bookResponse = handleActionStockResponse(comicBookId);
        }

        broadcastIntent.putExtra("book_response", bookResponse);

        broadcastIntent.setAction(BOOK_RESPONSE_ACTION);
        sendBroadcast(broadcastIntent);
    }

    private BookResponse handleActionStockResponse(String comicBookId) {
        BookResponse bookResponse = new BookResponse();

        String rawData = null;
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Util.isWiFiAvailable(conMan)) {
            ConnectClient connectClient = new ConnectClient();
            rawData = connectClient.getWebservice(comicBookId);
            if (rawData == null || rawData.length() < 1  ) {
                bookResponse.setResultString(MainActivity.ERROR_CONNECTING);
                return bookResponse;
            }

            Gson gson = new Gson();

            JsonBody body = gson.fromJson(rawData, JsonBody.class);
            Data data = body.getData();
            if (data == null)
            {
                bookResponse.setResultString(MainActivity.BOOK_NOT_FOUND);
                return bookResponse;
            }
            List<Result> listOfResults = data.getResults();
            Result result = listOfResults.get(0);

            // id
            bookResponse.setId(String.valueOf(result.getId()));

            // Image url
            String thumbnailImageUrl = result.getThumbnail().getPath();
            String imageExtension = result.getThumbnail().getExtension();
            String completePath = thumbnailImageUrl + "." + imageExtension;
            bookResponse.setImageUrl(completePath);

            // Images
            List<Image> previewImages = result.getImages();
            bookResponse.getPreviewImageUrls().clear();
            for (Image image : previewImages) {
                String url = image.getPath();
                String extension = image.getExtension();
                String path = url + "." + extension;
                bookResponse.getPreviewImageUrls().add(path);
            }
            // Title
            bookResponse.setTitle(result.getTitle());

            // Price
            Price price = null;
            if(result.getPrices() != null && result.getPrices().size() > 0)
            {
                if(result.getPrices().size() > 1)
                {
                    price = result.getPrices().get(DIGITAL_PRICE_OFFSET);
                } else if (result.getPrices().size() == 1)
                {
                    price = result.getPrices().get(PRINT_PRICE_OFFSET);
                }
                bookResponse.setPrice(String.valueOf(price.getPrice()));
            }


            // Creators
            Creators creators = result.getCreators();
            List<Item> items = creators.getItems();
            StringBuilder sBuilder = new StringBuilder();
            if(items != null && items.size() > 0)
            {
                for(Item item: items)
                {
                    sBuilder = sBuilder.append(item.getName() + ",");
                }
                sBuilder.deleteCharAt(sBuilder.lastIndexOf(","));
            }

            bookResponse.setWrittenBy(sBuilder.toString());

            // Release date
            Date releaseDate = result.getDates().get(ON_SALE_DATE_OFFSET);
            String stringDate = releaseDate.getDate();
            stringDate = stringDate.split("T")[0];
            bookResponse.setReleaseDate(stringDate);

            // Page count
            bookResponse.setNumberOfPages(result.getPageCount().toString());

            // Summary
            bookResponse.setDescription(result.getDescription());

            // Copyright
            bookResponse.setCopyRight(body.getCopyright());

            bookResponse.setResultString(MainActivity.NO_ERRORS);
        } else {
            bookResponse.setResultString(MainActivity.NO_WIFI);
            return bookResponse;
        }

        return bookResponse;
    }

}
