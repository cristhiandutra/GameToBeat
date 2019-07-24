package com.cristhian.gametobeat.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cristhian.gametobeat.R;
import com.cristhian.gametobeat.util.LoginUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    EditText mEmail;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.bt_reset)
    public void resetPassword() {

        // Reset errors
        mEmail.setError(null);

        String email = mEmail.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!LoginUtil.isEmailValid(email)) {
            mEmail.setError(getString(R.string.email_invalid));
            cancel = true;
            focusView = mEmail;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(ResetPasswordActivity.this, getString(R.string.email_reset_invalid), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ResetPasswordActivity.this, getString(R.string.reset_success), Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    });
        }

    }
}
