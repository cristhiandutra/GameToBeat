package com.cristhian.gametobeat.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    EditText mEmail;

    @BindView(R.id.et_password)
    EditText mPassword;

    @BindView(R.id.et_password_repeat)
    EditText mPasswordRepeat;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.bt_register)
    public void register() {

        // Reset errors
        mEmail.setError(null);
        mPassword.setError(null);
        mPasswordRepeat.setError(null);

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String passwordRepeat = mPasswordRepeat.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!LoginUtil.isPasswordValid(password)) {
            mPassword.setError(getString(R.string.password_invalid));
            cancel = true;
            focusView = mPassword;
        }

        if (!LoginUtil.isPasswordValid(passwordRepeat)) {
            mPasswordRepeat.setError(getString(R.string.password_invalid));
            cancel = true;
            focusView = mPasswordRepeat;
        }

        if (!LoginUtil.isEmailValid(email)) {
            mEmail.setError(getString(R.string.email_invalid));
            cancel = true;
            focusView = mEmail;
        }

        if (cancel) {
            focusView.requestFocus();
        } else if (!LoginUtil.isPasswordMatch(password, passwordRepeat)) {
            Toast.makeText(RegisterActivity.this, "Passwords must be the same", Toast.LENGTH_LONG).show();
        } else {

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, getString(R.string.register_error), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, getString(R.string.register_success), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this, GameListActivity.class);
                                startActivity(intent);

                                finish();
                            }
                        }
                    });
        }
    }
}
