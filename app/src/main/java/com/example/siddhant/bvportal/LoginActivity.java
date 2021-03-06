package com.example.siddhant.bvportal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.signIn);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mPasswordView.addTextChangedListener(new TextWatcher() {

            TextView tv = (TextView)findViewById(R.id.showpassword);
            CheckBox cb = (CheckBox)findViewById(R.id.checkBox);
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cb.isChecked())
                    tv.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        //if(email.charAt(0)<'0' && email.charAt(0)>'9' )
        return true;
        //else
        //  return false;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            //mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    // mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                    //mLoginFormView.setVisibility(View.GONE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                    //mProgressView.setVisibility(View.GONE);
                }
            });

            TextView tv = (TextView)findViewById(R.id.textView5);
            //tv.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //     mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                    //mLoginFormView.setVisibility(View.GONE);
                }
            });


            tv = (TextView)findViewById(R.id.showpassword);
            //tv.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //   mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                    //mLoginFormView.setVisibility(View.GONE);
                }
            });

            tv = (TextView)findViewById(R.id.textView6);
            //tv.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //   mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                    //mLoginFormView.setVisibility(View.GONE);
                }
            });

            CheckBox cb = (CheckBox)findViewById(R.id.checkBox);
            //cb.setVisibility(show ? View.GONE : View.VISIBLE);
            cb.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                    //mLoginFormView.setVisibility(View.GONE);
                }
            });


            Button b = (Button)findViewById(R.id.button5);
            //b.setVisibility(show ? View.GONE : View.VISIBLE);
            b.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //  mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                    //mLoginFormView.setVisibility(View.GONE);
                }
            });

            b = (Button)findViewById(R.id.signIn);
            // b.setVisibility(show ? View.GONE : View.VISIBLE);
            b.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                    //mLoginFormView.setVisibility(View.GONE);
                }
            });

            b = (Button)findViewById(R.id.button7);
            //  b.setVisibility(show ? View.GONE : View.VISIBLE);
            b.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                    //mLoginFormView.setVisibility(View.GONE);
                }
            });

            View v = (View)findViewById(R.id.br);
            //v.setVisibility(show ? View.GONE : View.VISIBLE);
            v.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                    //mLoginFormView.setVisibility(View.GONE);
                }
            });


        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            //   mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            // mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        boolean v = true;
        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }
        @Override
        protected Boolean doInBackground(Void... params)
        {
            // TODO: attempt authentication against a network service.
            // Simulate network access.
            try {
                // Simulate network access.
                Firebase.setAndroidContext(getApplicationContext());
                Firebase ref = new Firebase("https://bvportal.firebaseio.com/");
                ref.authWithPassword(mEmail, mPassword, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Toast.makeText(getApplicationContext(), "User ID: " + authData.getUid() + ", Provider: " + authData.getProvider(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(getApplicationContext(),"There was an error",Toast.LENGTH_LONG).show();
                        v=false;
                    }
                });
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            return v;

        }
        // TODO: register the new account here


        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            //Toast.makeText(getApplicationContext(),"Holalua",Toast.LENGTH_LONG).show();

            if (success) {
                welcome();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

    }

    public void forgotPassword(View view)
    {
        Intent i = new Intent(this,ForgotPassword.class);
        startActivity(i);
    }

    public void welcome()
    {
        Intent i = new Intent(this,FrontPage.class);
        startActivity(i);
    }

    public void newAccount(View view)
    {
        Intent i = new Intent(this,NewAccount.class);
        startActivity(i);
    }
}
