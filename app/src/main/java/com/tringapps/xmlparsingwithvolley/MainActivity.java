package com.tringapps.xmlparsingwithvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ListView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<ChannelItem> itemsArrayList = new ArrayList<>();
    String url = "http://www.javaworld.com/index.rss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_item);


        CustomVolleyRequestQueue queue = CustomVolleyRequestQueue.getInstance(this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    private XmlPullParserFactory xmlFactoryObject;


                    @Override
                    public void onResponse(String response) {

                        try {

                            xmlFactoryObject = XmlPullParserFactory.newInstance();
                            XmlPullParser myParser;
                            myParser = xmlFactoryObject.newPullParser();
                            myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                            InputStream in = new ByteArrayInputStream(response.getBytes());
                            myParser.setInput(in, "UTF-8");
                            itemsArrayList = xmlParser(myParser);
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "That didn't work!");
            }
        });

        queue.add(stringRequest);

        CustomAdapter adapter = new CustomAdapter(MainActivity.this, itemsArrayList);
        listView.setAdapter(adapter);

    }

    private ArrayList<ChannelItem> xmlParser(XmlPullParser myParser) throws XmlPullParserException, IOException {

        String text = null;
        int event = myParser.getEventType();
        ChannelItem item = null;

        while (event != XmlPullParser.END_DOCUMENT) {

            String name = myParser.getName();

            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if (name.equalsIgnoreCase("item")) {
                        item = new ChannelItem();
                    }
                case XmlPullParser.TEXT:
                    text = myParser.getText();

                    break;
                case XmlPullParser.END_TAG:

                    if (item != null) {
                        if (name.equalsIgnoreCase("title")) {

                            item.title = text;
                        } else if (name.equalsIgnoreCase("description")) {
                            text = Html.fromHtml(text).toString();
                            item.description = text;
                        } else if (name.equalsIgnoreCase("author")) {
                            text = Html.fromHtml(text).toString();
                            item.author = text;
                        } else if (name.equalsIgnoreCase("media:thumbnail")) {
                            item.url = myParser.getAttributeValue(null, "url");
                        } else if (name.equalsIgnoreCase("pubDate")) {
                            item.pubDate = text;
                            itemsArrayList.add(item);
                        }
                    }

                    break;

            }
            event = myParser.next();
        }
        return itemsArrayList;
    }



}

   /* String url = "http://my-json-feed";

    JsonObjectRequest jsObjRequest = new JsonObjectRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    mTxtDisplay.setText("Response: " + response.toString());
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub

                }
            });

// Access the RequestQueue through your singleton class.
MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);*/