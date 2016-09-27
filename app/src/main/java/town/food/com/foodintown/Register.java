package town.food.com.foodintown;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Register extends Activity {
    private EditText user, pass, name;
    private String getuser, getpass, getname;

    private Button  btn_register;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    private static final String REGISTER_URL = "http://foodintown.esy.es/register.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        user = (EditText)findViewById(R.id.user_bar);
        pass = (EditText)findViewById(R.id.pass_bar);
        name = (EditText)findViewById(R.id.name_bar);

        btn_register= (Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                getuser = user.getText().toString();
                getpass = pass.getText().toString();
                getname = name.getText().toString();

                new CreateUser().execute();

            }
        });

    }

    class CreateUser extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {

            int success;


            try {
                List params = new ArrayList();
                params.add(new BasicNameValuePair("username", getuser));
                params.add(new BasicNameValuePair("name", getname));
                params.add(new BasicNameValuePair("password", getpass));



                JSONObject json = jsonParser.makeHttpRequest(
                        REGISTER_URL, "POST", params);

                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                          Intent a = new Intent(Register.this, Login.class);
                          startActivity(a);

                    return json.getString(TAG_MESSAGE);
                }else{
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(Register.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }
}