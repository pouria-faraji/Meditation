package com.blacksite.meditation;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    public EditText fname, lname, email, password;
    RelativeLayout btn_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("ایجاد حساب");

        fname = (EditText)findViewById(R.id.input_fname);
        lname = (EditText)findViewById(R.id.input_lname);
        email = (EditText)findViewById(R.id.input_email);
        password = (EditText)findViewById(R.id.input_password);
        btn_sign_up = (RelativeLayout) findViewById(R.id.btn_sign_up);

        fname.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/B Yekan_p30download.com.ttf"));
        lname.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/B Yekan_p30download.com.ttf"));
        email.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/B Yekan_p30download.com.ttf"));
        password.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/B Yekan_p30download.com.ttf"));

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }
    public void signup() {
        Log.d("logger", "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btn_sign_up.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("ایجاد حساب ...");
        progressDialog.show();

        String f_fname = fname.getText().toString();
        String f_lname = lname.getText().toString();
        String f_email = email.getText().toString();
        String f_password = password.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }
    public void onSignupSuccess() {
        btn_sign_up.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Sign up failed", Toast.LENGTH_SHORT).show();

        btn_sign_up.setEnabled(true);
    }
    public boolean validate() {
        boolean valid = true;

        String _fname = fname.getText().toString();
        String _lname = lname.getText().toString();
        String _email = email.getText().toString();
        String _password = password.getText().toString();

        if (_fname.isEmpty() || _fname.length() < 3) {
            fname.setError("نام باید دارای حداقل 3 حرف باشد");
            valid = false;
        } else {
            fname.setError(null);
        }

        if (_lname.isEmpty() || _lname.length() < 3) {
            lname.setError("نام خانوادگی باید دارای حداقل 3 حرف باشد");
            valid = false;
        } else {
            lname.setError(null);
        }

        if (_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_email).matches()) {
            email.setError("آدرس ایمیل را به شکل صحیح وارد نمایید");
            valid = false;
        } else {
            email.setError(null);
        }

        if (_password.isEmpty() || password.length() < 4 || password.length() > 10) {
            password.setError("رمز عبور باید بین 4 تا 10 کاراکتر باشد");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
