package town.food.com.foodintown;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    JSONArray userx = null;
    private EditText user, pass;
    private Button btn_login, btn_register;
    private ProgressDialog pDialog;
    String username, password;
    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = "http://foodintown.esy.es/index.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        user = (EditText)findViewById(R.id.user_bar);
        pass = (EditText)findViewById(R.id.pass_bar);
        btn_login= (Button)findViewById(R.id.btn_login);
        btn_register = (Button)findViewById(R.id.btn_register);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = user.getText().toString();
                password = pass.getText().toString();
                new AttemptLogin().execute();

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registro = new Intent(Login.this,Register.class);
                startActivity(registro);
            }
        });



    }


    class AttemptLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }



        @Override
        protected String doInBackground(String... args) {
            int success;

                try {
                    List params = new ArrayList();
                    params.add(new BasicNameValuePair("username", username));
                    params.add(new BasicNameValuePair("password", password));

                    Log.d("request!", "starting");
                    // getting product details by making HTTP request
                    JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST",
                            params);

                    success = json.getInt(TAG_SUCCESS);



                    if (success == 1) {

                        Intent i = new Intent(Login.this, Home.class);
                        finish();
                        startActivity(i);

                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(Login.this, "Intente mas tarde", Toast.LENGTH_LONG).show();
            }
        }
    }

}
