package com.cristhian.gametobeat.ui.login;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cristhian.gametobeat.R;
import com.cristhian.gametobeat.ui.list.GameListActivity;
import com.cristhian.gametobeat.util.LoginUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    EditText mEmail;

    @BindView(R.id.et_password)
    EditText mPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, GameListActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.bt_login)
    public void login() {

        // Reset errors
        mEmail.setError(null);
        mPassword.setError(null);

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!LoginUtil.isPasswordValid(password)) {
            mPassword.setError(getString(R.string.password_invalid));
            cancel = true;
            focusView = mPassword;
        }

        if (!LoginUtil.isEmailValid(email)) {
            mEmail.setError(getString(R.string.email_invalid));
            cancel = true;
            focusView = mEmail;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, getString(R.string.login_error), Toast.LENGTH_LONG).show();
                    } else {

                        Intent intent = new Intent(LoginActivity.this, GameListActivity.class);
                        startActivity(intent);

                        finish();
                    }
                }
            });
        }
    }

    @OnClick(R.id.tv_forgot_password)
    public void forgotPassword() {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_register_now)
    public void registerNow() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
