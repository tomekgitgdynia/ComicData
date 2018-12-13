package com.marvel.developer.comicdata.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectClient {

    //     https://gateway.marvel.com/v1/public/comics/40638?ts=1&apikey=f5fec7765f9b61c4ac20c7807d7725b2&hash=aa5bbfb39e1be4c26cc4c1e5c6dcd609
    private OkHttpClient client = new OkHttpClient();;
    private String result = null;
    private static final String TAG = "ConnectClient";

    public String getWebservice(String comicBookId) {


        HttpUrl httpUrl = new HttpUrl.Builder().scheme("https").host("gateway.marvel.com").addPathSegment("v1").addPathSegment("public").addPathSegment("comics").addPathSegment(comicBookId).addQueryParameter("ts", "1").addQueryParameter("apikey", "f5fec7765f9b61c4ac20c7807d7725b2").addQueryParameter("hash", "aa5bbfb39e1be4c26cc4c1e5c6dcd609").build();
        final Request request = new Request.Builder().url(httpUrl).build();
        Response response = null;
        String responseString = null;
        try {
            response = client.newCall(request).execute();
            responseString = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

//        responseString = "{\"code\":200,\"status\":\"Ok\",\"copyright\":\"© 2018 MARVEL\",\"attributionText\":\"Data provided by Marvel. © 2018 MARVEL\",\"attributionHTML\":\"<a href=\\\"http://marvel.com\\\">Data provided by Marvel. © 2018 MARVEL</a>\",\"etag\":\"7c30c9eee84e8d05a39643ba007a8e8bcd8c44dc\",\"data\":{\"offset\":0,\"limit\":20,\"total\":1,\"count\":1,\"results\":[{\"id\":40638,\"digitalId\":25934,\"title\":\"Hulk (2008) #50\",\"issueNumber\":50,\"variantDescription\":\"\",\"description\":\"HAUNTED HULK PART 1 Which devilish dark figure dares to haunt the Hulk? With appearances from some of Marvel's most powerful heroes, will might be a match for malevolent magic?\",\"modified\":\"2013-02-20T14:07:57-0500\",\"isbn\":\"\",\"upc\":\"5960605992-05011\",\"diamondCode\":\"FEB120598\",\"ean\":\"\",\"issn\":\"1941-2142\",\"format\":\"Comic\",\"pageCount\":48,\"textObjects\":[{\"type\":\"issue_solicit_text\",\"language\":\"en-us\",\"text\":\"&bull; Bonus-Sized! A Great Jumping On Point, As The Ghoulish Haunted Hulk Arc Begins!\\r\\n&bull; Who Or What Is The Dark Figure Hunting Red Hulk?\\r\\n&bull; Can Red Hulk&rsquo;s Might Match Malevolent Magic?\\r\\n&bull; Appearances By Dr. Strange, Red She-Hulk, Iron Man, Spider-Man, Daredevil And Others\\r\\nWe Aren&rsquo;t At Liberty To Divulge!\"},{\"type\":\"issue_preview_text\",\"language\":\"en-us\",\"text\":\"HAUNTED HULK PART 1 Which devilish dark figure dares to haunt the Hulk? With appearances from some of Marvel's most powerful heroes, will might be a match for malevolent magic?\"}],\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/40638\",\"urls\":[{\"type\":\"detail\",\"url\":\"http://marvel.com/comics/issue/40638/hulk_2008_50?utm_campaign=apiRef&utm_source=f5fec7765f9b61c4ac20c7807d7725b2\"},{\"type\":\"purchase\",\"url\":\"http://comicstore.marvel.com/Hulk-50/digital-comic/25934?utm_campaign=apiRef&utm_source=f5fec7765f9b61c4ac20c7807d7725b2\"},{\"type\":\"reader\",\"url\":\"http://marvel.com/digitalcomics/view.htm?iid=25934&utm_campaign=apiRef&utm_source=f5fec7765f9b61c4ac20c7807d7725b2\"},{\"type\":\"inAppLink\",\"url\":\"https://applink.marvel.com/issue/25934?utm_campaign=apiRef&utm_source=f5fec7765f9b61c4ac20c7807d7725b2\"}],\"series\":{\"resourceURI\":\"http://gateway.marvel.com/v1/public/series/3374\",\"name\":\"Hulk (2008 - 2012)\"},\"variants\":[{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/42331\",\"name\":\"Hulk (2008) #50 (Simonson Variant)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/comics/41939\",\"name\":\"Hulk (2008) #50 (Adams Variant)\"}],\"collections\":[],\"collectedIssues\":[],\"dates\":[{\"type\":\"onsaleDate\",\"date\":\"2012-04-04T00:00:00-0400\"},{\"type\":\"focDate\",\"date\":\"2012-03-21T00:00:00-0400\"},{\"type\":\"unlimitedDate\",\"date\":\"2013-04-01T00:00:00-0400\"},{\"type\":\"digitalPurchaseDate\",\"date\":\"2012-04-04T00:00:00-0400\"}],\"prices\":[{\"type\":\"printPrice\",\"price\":3.99},{\"type\":\"digitalPurchasePrice\",\"price\":1.99}],\"thumbnail\":{\"path\":\"http://i.annihil.us/u/prod/marvel/i/mg/f/20/5ba3a68f2ddb5\",\"extension\":\"jpg\"},\"images\":[{\"path\":\"http://i.annihil.us/u/prod/marvel/i/mg/f/20/5ba3a68f2ddb5\",\"extension\":\"jpg\"},{\"path\":\"http://i.annihil.us/u/prod/marvel/i/mg/e/d0/4f564bb0e1ebb\",\"extension\":\"jpg\"},{\"path\":\"http://i.annihil.us/u/prod/marvel/i/mg/4/30/4f56494174b9e\",\"extension\":\"jpg\"}],\"creators\":{\"available\":6,\"collectionURI\":\"http://gateway.marvel.com/v1/public/comics/40638/creators\",\"items\":[{\"resourceURI\":\"http://gateway.marvel.com/v1/public/creators/10172\",\"name\":\"Vc Clayton Cowles\",\"role\":\"letterer\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/creators/6806\",\"name\":\"Carlo Pagualyan\",\"role\":\"Penciller\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/creators/4600\",\"name\":\"Mark Paniccia\",\"role\":\"editor\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/creators/824\",\"name\":\"Jeff Parker\",\"role\":\"Writer\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/creators/8695\",\"name\":\"Jason Paz\",\"role\":\"Inker\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/creators/832\",\"name\":\"Val Staples\",\"role\":\"Colorist\"}],\"returned\":6},\"characters\":{\"available\":10,\"collectionURI\":\"http://gateway.marvel.com/v1/public/comics/40638/characters\",\"items\":[{\"resourceURI\":\"http://gateway.marvel.com/v1/public/characters/1016823\",\"name\":\"Abomination (Ultimate)\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/characters/1009548\",\"name\":\"Betty Ross\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/characters/1009262\",\"name\":\"Daredevil\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/characters/1009282\",\"name\":\"Doctor Strange\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/characters/1009368\",\"name\":\"Iron Man\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/characters/1010805\",\"name\":\"Machine Man\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/characters/1011360\",\"name\":\"Red Hulk\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/characters/1009610\",\"name\":\"Spider-Man\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/characters/1011003\",\"name\":\"Thaddeus Ross\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/characters/1009720\",\"name\":\"Wong\"}],\"returned\":10},\"stories\":{\"available\":2,\"collectionURI\":\"http://gateway.marvel.com/v1/public/comics/40638/stories\",\"items\":[{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/92098\",\"name\":\"Hulk (2008) #50\",\"type\":\"cover\"},{\"resourceURI\":\"http://gateway.marvel.com/v1/public/stories/92099\",\"name\":\"Interior #92099\",\"type\":\"interiorStory\"}],\"returned\":2},\"events\":{\"available\":0,\"collectionURI\":\"http://gateway.marvel.com/v1/public/comics/40638/events\",\"items\":[],\"returned\":0}}]}}";
        return responseString;
    }

}