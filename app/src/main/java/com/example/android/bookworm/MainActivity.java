package com.example.android.bookworm;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private EditText mBookInput;
    private TextView mTitleText,mAuthorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = (EditText)findViewById(R.id.bookInput);
        mTitleText = (TextView)findViewById(R.id.Title);
        mAuthorText = (TextView)findViewById(R.id.Author);
    }

    public void searchBooks(View view) {

        String queryString = mBookInput.getText().toString();
        //For hiding the keyboard after pressing search
        InputMethodManager inputManager =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        //For checking Network state and empty search field case
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected() && queryString.length()!=0){
            new Fetchbooks(mTitleText, mAuthorText).execute(queryString);
            mAuthorText.setText("");
            mTitleText.setText(R.string.Loading);
        }
        else{
            if(queryString.length() == 0){
                mAuthorText.setText("");
                mTitleText.setText(R.string.netOff);
            }
            else{
                mAuthorText.setText("");
                mTitleText.setText(R.string.netOff1);
            }
        }//        Log.i(LOG_TAG,"Searched: "+queryString);
//        if(queryString.length()!=0) {
//            new Fetchbooks(mTitleText, mAuthorText).execute(queryString);
//        }
//        else{
//            Toast.makeText(this,"Please Enter the name",Toast.LENGTH_SHORT).show();
//        }
    }
}