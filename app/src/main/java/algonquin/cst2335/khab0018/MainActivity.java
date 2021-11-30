package algonquin.cst2335.khab0018;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author Aar Khabar
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    /**
     * This holds the text to the centre of the screen
     */
    private TextView tv = null;
    /**
     * This holds the password to the centre of the screen
     */
    private EditText cityText = null;
    /**
     * This holds the button to the centre of the screen
     */
    private Button forecastBtn = null;
    /**
     * This string represents the address of the server we will connect to
     */
    private String stringURL;

    Bitmap image;

    ImageView iv = null;

    String description = null;
    String iconName = null;
    String current = null;
    String min = null;
    String max = null;
    String humidity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityText = findViewById(R.id.cityTextField);
        forecastBtn = findViewById(R.id.forecastButton);

        forecastBtn.setOnClickListener(clk -> {
            String cityName = cityText.getText().toString();

            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Getting forecast")
                    .setMessage("We're calling people in " + cityName + " to look outside their windows and tell us what's the weather like over there.")
                    .setView(new ProgressBar(MainActivity.this))
                    .show();

            Executor newThread = Executors.newSingleThreadExecutor();
            newThread.execute(() -> {
                try {
                    stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                            + URLEncoder.encode(cityName, "UTF-8")
                            + "&appid=7e943c97096a9784391a981c4d878b22&units=metric&mode=xml";

                    URL url = new URL(stringURL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(false);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput( in  , "UTF-8");

                    while (xpp.next() != XmlPullParser.END_DOCUMENT){
                        switch (xpp.getEventType()){
                            case XmlPullParser.START_TAG:
                                if(xpp.getName().equals("temperature")){
                                    current = xpp.getAttributeValue(null, "value");
                                    min = xpp.getAttributeValue(null, "min");
                                    max = xpp.getAttributeValue(null, "max");
                                }else if(xpp.getName().equals("weather")){
                                    description= xpp.getAttributeValue(null, "value");
                                    iconName = xpp.getAttributeValue(null, "icon");
                                }else if(xpp.getName().equals("humidity")){
                                    humidity = xpp.getAttributeValue(null, "humidity");
                                }
                                break;
                            case XmlPullParser.END_TAG:

                                break;
                            case XmlPullParser.TEXT:

                                break;
                        }
                    }


                    File file = new File(getFilesDir(), iconName + ".png");

                    if(file.exists()){
                        image = BitmapFactory.decodeFile(getFilesDir() + "/" + iconName + ".png");
                    }else{
                        URL imgUrl = new URL("https://openweathermap.org/img/w/" + iconName + ".png");
                        HttpURLConnection imgConnection = (HttpURLConnection) imgUrl.openConnection();
                        imgConnection.connect();
                        int responseCode = imgConnection.getResponseCode();
                        if (responseCode == 200) {
                            image = BitmapFactory.decodeStream(imgConnection.getInputStream());
                            image.compress(Bitmap.CompressFormat.PNG, 100, openFileOutput(iconName + ".png", Activity.MODE_PRIVATE));
                        }
                        FileOutputStream fOut = null;
                        try {
                            fOut = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
                            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                            fOut.flush();
                            fOut.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    runOnUiThread(( ) -> {
                        tv = findViewById(R.id.temp);
                        tv.setText("The current temperature is " + current+ " "+ iconName);
                        tv.setVisibility(View.VISIBLE);

                        tv = findViewById(R.id.maxTemp);
                        tv.setText("The max temperature is " + max);
                        tv.setVisibility(View.VISIBLE);

                        tv = findViewById(R.id.minTemp);
                        tv.setText("The min temperature is " + min);
                        tv.setVisibility(View.VISIBLE);

                        tv = findViewById(R.id.humitidy);
                        tv.setText("The humitidy is " + humidity + "%");
                        tv.setVisibility(View.VISIBLE);

                        iv = findViewById(R.id.icon);
                        iv.setImageBitmap(image);
                        iv.setVisibility(View.VISIBLE);

                        tv = findViewById(R.id.description);
                        tv.setText(description);
                        tv.setVisibility(View.VISIBLE);

                        dialog.hide();
                    });
                }

                catch (IOException | XmlPullParserException ioe) {
                    Log.e("Connection error:", ioe.getMessage());
                }
            });
        });
    }
}