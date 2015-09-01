package com.example.testapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gilberto on 2015-08-31.
 */
public class ServerRequest {

    ProgressDialog pd;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://tripool.netai.net/";

    public ServerRequest(Context c) {
        pd = new ProgressDialog(c);
        pd.setCancelable(false);
        pd.setTitle("Processing");
        pd.setMessage("Please wait...");
    }

    public void storeUserDataBackground(User u, GetUserCallback ucb) {
        pd.show();
        new StoreUserDataAsyncTask(u, ucb).execute();
    }

    public void fetchUserDataBackground(User u, GetUserCallback cb) {
        pd.show();
        new fetchUserDataAsyncTask(u, cb).execute();
    }

    public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void> {
        User u;
        GetUserCallback ucb;

        public StoreUserDataAsyncTask(User u, GetUserCallback ucb) {
            this.u = u;
            this.ucb = ucb;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
            dataToSend.add(new BasicNameValuePair("name", u.name));
            dataToSend.add(new BasicNameValuePair("email", u.email));
            dataToSend.add(new BasicNameValuePair("password", u.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            pd.dismiss();
            ucb.done(null);
            super.onPostExecute(aVoid);
        }
    }

    public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, User> {
        User u;
        GetUserCallback ucb;

        public fetchUserDataAsyncTask(User u, GetUserCallback ucb) {
            this.u = u;
            this.ucb = ucb;
        }

        @Override
        protected User doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();
            dataToSend.add(new BasicNameValuePair("email", u.email));
            dataToSend.add(new BasicNameValuePair("password", u.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchUserData.php");

            User returnedUser = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObeject = new JSONObject(result);

                if(jObeject.length() == 0){
                    returnedUser = null;
                } else {
                    String name = jObeject.getString("name");
                    String email = jObeject.getString("email");
                    String password = jObeject.getString("password");

                    returnedUser = new User(name, email, password);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnedUser;
        }

        @Override
        protected void onPostExecute(User returnedUser) {
            pd.dismiss();
            ucb.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }
}
