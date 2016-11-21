package br.com.projeto.projetolistaartistas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.gson.JsonObject;

import java.net.URL;
import java.util.Objects;

import br.com.projeto.projetolistaartistas.model.Usuario;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    private static final int RC_SIGN_IN = 9001;
    public final String USUARIO = "USUARIO";
    private UserLoginTask mAuthTask = null;
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private GoogleApiClient mGoogleApiClient;

    private String nomeUsuario;
    private String emailUsuario;
    private String imagemUsuario;
    private String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);

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

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;

                }
            }
        });

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            final GoogleSignInAccount acct = result.getSignInAccount();
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            Toast.makeText(this, acct.getDisplayName(), Toast.LENGTH_LONG).show();
            Usuario usuario = new Usuario();

            nomeUsuario = acct.getDisplayName();
            emailUsuario = acct.getEmail();
            imagemUsuario = acct.getPhotoUrl() != null ? acct.getPhotoUrl().toString() : "";
            idUsuario = acct.getId();

            usuario.setUsu_nome(nomeUsuario);
            usuario.setUsu_email(emailUsuario);
            usuario.setUsu_imagem(imagemUsuario);
            usuario.setUsu_id_google(idUsuario);
            mAuthTask = new UserLoginTask(usuario);
            mAuthTask.execute((Void) null);
        } else {
            Toast.makeText(this, "There was a problem while connecting to your accounts:" + result.getStatus(), Toast.LENGTH_LONG).show();
        }
    }

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
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 1;
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private String mEmail;
        private String mPassword;
        private Usuario usuario;
        private Bitmap btm;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        UserLoginTask(Usuario usuario) {
            this.usuario = usuario;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            try {
                if (mEmail != null) {
                    Request request = new Request.Builder().url("http://www.doocati.com.br/tcc/webservice/login/" + mEmail + "/" + mPassword).build();
                    Response response = client.newCall(request).execute();
                    String jsonString = response.body().string();
                    if (Objects.equals(jsonString, "false")) {
                        return false;
                    }
                } else if (usuario != null) {
                    //http://www.doocati.com.br/tcc/webservice/loginGoogle/106536105818535805159
                    Request request = new Request.Builder().url("http://www.doocati.com.br/tcc/webservice/loginGoogle/" + usuario.getUsu_id_google()).build();
                    Response response = client.newCall(request).execute();
                    String jsonString = response.body().string();
                    if (!usuario.getUsu_imagem().equals("")) {
                        URL url = new URL(usuario.getUsu_imagem());
                        btm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    }
                    if (jsonString.equals("false")) {
                        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                        JsonObject json = new JsonObject();
                        json.addProperty("usu_email", usuario.getUsu_email());
                        json.addProperty("usu_nome", usuario.getUsu_nome());
                        json.addProperty("usu_id_google", usuario.getUsu_id_google());
                        json.addProperty("usu_imagem", usuario.getUsu_imagem());
                        RequestBody body = RequestBody.create(JSON, json.toString());
                        Request post = new Request.Builder().url("http://www.doocati.com.br/tcc/webservice/usuario/google").method("POST", RequestBody.create(null, new byte[0])).post(body).build();
                        Response responses = client.newCall(post).execute();
                        Log.d("RESULTADO", responses.body().string());
                    } else {
                        return true;
                    }
                } else {
                    return false;
                }

            } catch (Exception e) {
                return false;
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;


            if (success) {
                Intent intent = new Intent(getApplicationContext(), PessoasActivity.class);
                if (usuario != null) {
                    intent.putExtra(USUARIO, usuario);
                    intent.putExtra("IMAGE", btm);
                }
                startActivity(intent);
                finish();
            } else {
                mEmailView.setError(getString(R.string.error_incorrect_email));
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }
}

