package eurecom.fr.mycontactlist;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class ResponseHandler {

    private final String url = "http://1-dot-high-producer-109012.appspot.com/"; //"YOUR GAE LINK" // contactslist?respType=json

    private Context context;
    private String request;
    private String format;
    private Handler handler;
    private Activity activity;
    private Map<String,String> data;
    private int statusCode;

    public Activity getActivity() {
        return activity;
    }

    public ResponseHandler(Context context, String request, String format, Handler handler, Activity activity, Map<String,String> data)
    {
        this.context = context;
        this.request = url + request;
        this.format = format;
        this.handler = handler;
        this.activity = activity;
        this.data = data;
    }

    public void finish()
    {
        handler.post(new Runnable() {
            public void run() {
                activity.finish();
            }
        });
    }

    private void popUp(String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public String start()
    {
        String out = null;
        if(format.compareTo("POST") == 0)
            try {
                out = postData();
                Log.i("Response==", out);
                respond(out); //W
            } catch (IOException e) {
                e.printStackTrace();
            }
        else{
            try {
                deleteData();
            }catch (Exception e){


            }
        }
        return out;
    }

    public void respond(String response)
    {
        /* To be implemented
        * If the statusCode is ok?
        * Notify the user about the successful operation
        * If not simply state that a problem has occurred
        * Remember to conclude the activity
        * */

        //W:
        if(response == "OK") {
            popUp("Operation succeded ;) !");
        }
        finish(); //W
    }

    private String convertParameters(Map <String,String> data){
        String result = "";
        for (Map.Entry<String,String> e: data.entrySet()){
            result += e.getKey()+"="+e.getValue()+"&";
        }
        return result.substring(0,result.length()-1);
    }

    public String postData() throws IOException {
        URL urlToRequest = new URL(request);
        HttpURLConnection urlConnection = null;
        String postParameters = convertParameters(data);
        Log.d(ResponseHandler.class.toString(), postParameters);
        urlConnection = (HttpURLConnection) urlToRequest.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        urlConnection.setFixedLengthStreamingMode(postParameters.getBytes().length);
        PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
        out.print(postParameters);
        out.close();
        return urlConnection.getResponseMessage();
    }

    private void deleteData() throws MalformedURLException, IOException {
        Log.i("Request:",request);
        URL page = new URL(request);
        HttpURLConnection conn = null;
        conn = (HttpURLConnection) page.openConnection();
        //TODO: delete
        conn.connect();
        InputStreamReader in = null;
        String message = conn.getResponseMessage();
        return;
    }
}
