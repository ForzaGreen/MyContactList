package eurecom.fr.mycontactlist;

import android.os.AsyncTask;

public class ModifyTask extends AsyncTask<ResponseHandler, Void, String> {

    private ResponseHandler rh;

    @Override
    protected String doInBackground(ResponseHandler... rhs) {
        rh = rhs[0];
        return rh.start();
    }
    @Override
    protected void onPostExecute(String response) {
        rh.respond(response);
    }

}
