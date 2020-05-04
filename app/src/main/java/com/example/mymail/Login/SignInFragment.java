package com.example.mymail.Login;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mymail.MainActivity;
import com.example.mymail.R;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.mymail.Login.SingUpFragment.emailPattern;
import static com.example.mymail.ShareFunction.setFragment;



/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        getUI(view);
        setClick();
        return view;
    }

    private FirebaseAuth firebaseAuth;
    private EditText email, password;
    private TextView forget_pass, signUp;
    private Button btnSignIn;
    private FrameLayout frameLayout;
    private ProgressBar progressBar;

    private void getUI(View view) {
        frameLayout = getActivity().findViewById(R.id.frameLayout);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        forget_pass = view.findViewById(R.id.forget_accout);
        signUp = view.findViewById(R.id.signUp);
        btnSignIn = view.findViewById(R.id.btnSignIn);
        progressBar = view.findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void setClick() {
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signUp.setOnClickListener(v -> setFragment(new SingUpFragment(), frameLayout, getActivity(), true));
        btnSignIn.setOnClickListener(v -> {
            btnSignIn.setEnabled(false);
            checkEmailandPassWord();
        });
        forget_pass.setOnClickListener(v-> setFragment(new ForgotEmailFragment(),frameLayout,getActivity(),true));
    }

    private void checkInput() {
        if (!TextUtils.isEmpty(email.getText())) {
            if (!TextUtils.isEmpty(password.getText()) && password.length()>7) {
                btnSignIn.setEnabled(true);
                btnSignIn.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                btnSignIn.setEnabled(false);
                btnSignIn.setTextColor(Color.parseColor("#50FFFF"));
            }
        } else {
            btnSignIn.setEnabled(false);
            btnSignIn.setTextColor(Color.parseColor("#50FFFF"));
        }
    }

    private void checkEmailandPassWord() {
        if (email.getText().toString().matches(emailPattern)) {
            progressBar.setVisibility(View.VISIBLE);
            btnSignIn.setEnabled(false);
            firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Intent myIntent = new Intent(getActivity(), MainActivity.class);
                    startActivity(myIntent);
                    getActivity().finish();
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    btnSignIn.setEnabled(true);
                    Toast.makeText(getActivity(), "Incorrect email or password !", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Incorrect email or password !", Toast.LENGTH_SHORT).show();
        }
    }
}