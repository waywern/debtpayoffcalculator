package waywern.debtpayoffcalculator;

/**
 * Created by slisovtsov on 3/7/2017.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;

//import org.apache.http.client.methods.*;

public class main_screen extends AppCompatActivity {

    private EditText mBalance_EditText;
    private EditText mAPP_Per_Year_EditText;
    private EditText mNumber_Of_Month_EditText;
    private EditText editText_monthly_payment;
    private AlertDialog alert;
    private AlertDialog.Builder builder;
    private Button mCalculateButton;
    Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        //builder = new AlertDialog.Builder(this);

        activity = this;
        mBalance_EditText = (EditText)findViewById(R.id.editText_balance);
        mAPP_Per_Year_EditText = (EditText)findViewById(R.id.editText_apr_per_year);
        mNumber_Of_Month_EditText = (EditText)findViewById(R.id.editText_number_of_month);
        editText_monthly_payment = (EditText)findViewById(R.id.editText_monthly_payment);
        mCalculateButton = (Button)findViewById(R.id.button_calculate);
        mCalculateButton.setOnClickListener(new View.OnClickListener() {




            /*if(mLoginEditText.getText().toString().)
            {
                // TODO bring alert to view
            }
            if(mPasswordEditText.getText().toString().isEmpty())
            {
                // TODO bring alert to view
            }*/

            /* TODO CheckBox mCheckBoxRememberLogin; save somewhere check state to future */


            /*TODO Send request to server with credentials*/


            @Override
            public void onClick(View v) {
                if( mBalance_EditText.getText().toString().isEmpty() || mAPP_Per_Year_EditText.getText().toString().isEmpty() || mNumber_Of_Month_EditText.getText().toString().isEmpty())
                {
                    String error_message = "";
                    if(mBalance_EditText.getText().toString().isEmpty()) error_message = "Balance*";
                    else if(mAPP_Per_Year_EditText.getText().toString().isEmpty()) error_message = "APR per year*";
                    else if(mNumber_Of_Month_EditText.getText().toString().isEmpty()) error_message = "Number of month*";
                    builder.setMessage("Please fill out "+ error_message).setTitle("Error");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            System.out.println("OK clicked");
                        }
                    });

                    alert = builder.create();
                    alert.show();
                    return;
                }

                double balance = Double.parseDouble(mBalance_EditText.getText().toString());
                double apr_per_year = Double.parseDouble(mAPP_Per_Year_EditText.getText().toString());
                double number_of_month = Double.parseDouble(mNumber_Of_Month_EditText.getText().toString());
                System.out.println("(apr_per_year/100)/12)" + ((apr_per_year/100)/12));
                System.out.println("(apr_per_year/100)/12)" + ((apr_per_year/100)/12));
                double monthly_payment = balance * ((apr_per_year/100)/12)/(1-1/Math.pow((1+(apr_per_year/100)/12),(number_of_month)));
                System.out.println("monthly_payment" + monthly_payment);
                editText_monthly_payment.setText(String.valueOf(monthly_payment));

            }

        });

    }


/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

        if (requestCode == RC_READ) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                processRetrievedCredential(credential);
                goToContent(credential);
            } else {
                Log.e(TAG, "Credential Read: NOT OK");
                //setSignInEnabled(true);
            }
        } else if (requestCode == RC_SAVE) {
            Log.d(TAG, "Result code: " + resultCode);
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "Credential Save: OK");
            } else {
                Log.e(TAG, "Credential Save Failed");
            }
            goToContent(null);
        }
        mIsResolving = false;
    }
*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}
