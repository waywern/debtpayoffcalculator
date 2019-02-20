package waywern.debtpayoffcalculator;

/**
 * Created by slisovtsov on 3/7/2017.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class main_screen extends AppCompatActivity {

    private EditText mBalance_EditText;
    private EditText mAPP_Per_Year_EditText;
    private EditText mNumber_Of_Month_EditText;
    private EditText editText_monthly_payment;
    private AlertDialog alert;
    private AlertDialog.Builder builder;
    private Button mCalculateButton;
    Activity activity;
    private AdView mAdView;
    private ScrollView main_screen_scroll_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        //getActionBar().hide();

        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        //builder = new AlertDialog.Builder(this);
        main_screen_scroll_view = (ScrollView) findViewById(R.id.main_screen_scroll_view);
        activity = this;
        MobileAds.initialize(this, "ca-app-pub-2285230333666171~1627861698");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });
        mBalance_EditText = (EditText)findViewById(R.id.editText_balance);
        mAPP_Per_Year_EditText = (EditText)findViewById(R.id.editText_apr_per_year);
        mNumber_Of_Month_EditText = (EditText)findViewById(R.id.editText_number_of_month);
        editText_monthly_payment = (EditText)findViewById(R.id.editText_monthly_payment);
        mCalculateButton = (Button)findViewById(R.id.button_calculate);
        mCalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( mBalance_EditText.getText().toString().isEmpty() || mAPP_Per_Year_EditText.getText().toString().isEmpty() || mNumber_Of_Month_EditText.getText().toString().isEmpty())
                {
                    String error_message = "";
                    if(mBalance_EditText.getText().toString().isEmpty()) error_message = "Balance*";
                    else if(mAPP_Per_Year_EditText.getText().toString().isEmpty()) error_message = "APR per year*";
                    else if(mNumber_Of_Month_EditText.getText().toString().isEmpty()) error_message = "Number of month*";
                    Toast.makeText(activity, "Please fill out "+ error_message, Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    double balance = Double.parseDouble(mBalance_EditText.getText().toString());
                    double apr_per_year = Double.parseDouble(mAPP_Per_Year_EditText.getText().toString());
                    double number_of_month = Double.parseDouble(mNumber_Of_Month_EditText.getText().toString());
                    //System.out.println("(apr_per_year/100)/12)" + ((apr_per_year / 100) / 12));
                    //System.out.println("(apr_per_year/100)/12)" + ((apr_per_year / 100) / 12));
                    double monthly_payment = balance * ((apr_per_year / 100) / 12) / (1 - 1 / Math.pow((1 + (apr_per_year / 100) / 12), (number_of_month)));
                    //System.out.println("monthly_payment" + monthly_payment);
                    editText_monthly_payment.setText(String.valueOf(monthly_payment));

                    double interestPaid, principalPaid, newBalance;
                    double monthlyInterestRate, monthlyPayment;
                    int month;
                    double total_interest_paid = 0;

                    monthlyInterestRate = apr_per_year / 12;

                    TableLayout main_screen_table = new TableLayout(activity);

                    TableRow main_screen_table_header = new TableRow(activity);
                    TextView tv0 = new TextView(activity);
                    tv0.setText(" # of month ");
                    tv0.setTextColor(Color.BLACK);
                    if (Build.VERSION.SDK_INT >= 17)
                        tv0.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    main_screen_table_header.addView(tv0);
                    TextView tv1 = new TextView(activity);
                    tv1.setText(" Principal ");
                    tv1.setTextColor(Color.BLACK);
                    if (Build.VERSION.SDK_INT >= 17)
                        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    main_screen_table_header.addView(tv1);
                    TextView tv2 = new TextView(activity);
                    tv2.setText(" Interest ");
                    tv2.setTextColor(Color.BLACK);
                    if (Build.VERSION.SDK_INT >= 17)
                        tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    main_screen_table_header.addView(tv2);
                    TextView tv3 = new TextView(activity);
                    tv3.setText(" Balance ");
                    tv3.setTextColor(Color.BLACK);
                    if (Build.VERSION.SDK_INT >= 17)
                        tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    main_screen_table_header.addView(tv3);
                    TextView tv4 = new TextView(activity);
                    tv4.setText(" Total interest ");
                    tv4.setTextColor(Color.BLACK);
                    if (Build.VERSION.SDK_INT >= 17)
                        tv4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    main_screen_table_header.addView(tv4);
                    main_screen_table.addView(main_screen_table_header);

                    for (month = 1; month <= number_of_month; month++) {

                        interestPaid  = (double) Math.round( (balance      * (monthlyInterestRate / 100) ) * 100) / 100;
                        principalPaid = (double) Math.round( (monthly_payment - interestPaid) * 100) / 100;
                        newBalance    = (double) Math.round( (balance      - principalPaid) * 100) / 100;
                        total_interest_paid = (double) Math.round( (total_interest_paid + interestPaid) * 100) / 100;

                        /*System.out.println("");
                        System.out.println("# of month :" + month);
                        System.out.println("# interestPaid :" + interestPaid);
                        System.out.println("# principalPaid :" + principalPaid);
                        System.out.println("# newBalance :" + newBalance);
                        System.out.println("# total_interest_paid :" + total_interest_paid);*/

                        TableRow row = new TableRow(activity);
                        tv0 = new TextView(activity);
                        tv0.setText(Integer.toString(month));
                        tv0.setTextColor(Color.BLACK);
                        if (Build.VERSION.SDK_INT >= 17)
                            tv0.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        row.addView(tv0);
                        tv1 = new TextView(activity);
                        tv1.setText(Double.toString(principalPaid));
                        tv1.setTextColor(Color.BLACK);
                        if (Build.VERSION.SDK_INT >= 17)
                            tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        row.addView(tv1);
                        tv2 = new TextView(activity);
                        tv2.setText(Double.toString(interestPaid));
                        tv2.setTextColor(Color.BLACK);
                        if (Build.VERSION.SDK_INT >= 17)
                            tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        row.addView(tv2);
                        tv2 = new TextView(activity);
                        tv2.setText(Double.toString(newBalance));
                        tv2.setTextColor(Color.BLACK);
                        row.addView(tv2);
                        tv2 = new TextView(activity);
                        tv2.setText(Double.toString(total_interest_paid));
                        tv2.setTextColor(Color.BLACK);
                        if (Build.VERSION.SDK_INT >= 17)
                            tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        row.addView(tv2);
                        if(month % 2 == 0)
                            row.setBackgroundColor(Color.parseColor("#C0C0C0"));
                        main_screen_table.addView(row);

                        balance = newBalance;
                    }
                    main_screen_scroll_view.removeAllViews();
                    main_screen_scroll_view.addView(main_screen_table);

                } catch(Exception ex){
                    Toast.makeText(activity, "Sorry, I can't make this calculation", Toast.LENGTH_LONG).show();
                    System.out.println("System error :" + ex.toString());
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}
