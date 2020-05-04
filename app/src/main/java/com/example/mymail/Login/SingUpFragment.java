package com.example.mymail.Login;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.example.mymail.ShareFunction.setFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingUpFragment extends Fragment {

    public SingUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        getUI(view);
        setClick();
        return view;
    }

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private EditText email, fullname, password, confirm_pass;
    private TextView signIn;
    private Button btnSignUp;
    private FrameLayout frameLayout;
    private ProgressBar progressBar;
    static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private void getUI(View view) {

        email = view.findViewById(R.id.email);
        fullname = view.findViewById(R.id.fullName);
        password = view.findViewById(R.id.password);
        confirm_pass = view.findViewById(R.id.confirm_pass);
        frameLayout = getActivity().findViewById(R.id.frameLayout);
        signIn = view.findViewById(R.id.signIn);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        progressBar = view.findViewById(R.id.progressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void setClick() {
        signIn.setOnClickListener(v -> setFragment(new SignInFragment(), frameLayout, getActivity(), false));
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
        fullname.addTextChangedListener(new TextWatcher() {
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
        confirm_pass.addTextChangedListener(new TextWatcher() {
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
        btnSignUp.setOnClickListener(v -> checkEmailandPassword());
    }

    private void checkInput() {
        if (!TextUtils.isEmpty(email.getText())) {
            if (!TextUtils.isEmpty(fullname.getText())) {

                if (!TextUtils.isEmpty(password.getText()) && password.length() > 7) {

                    if (!TextUtils.isEmpty(confirm_pass.getText()) && confirm_pass.length() > 7) {
                        btnSignUp.setEnabled(true);
                        btnSignUp.setTextColor(Color.parseColor("#FFFFFF"));
                    } else {
                        btnSignUp.setEnabled(false);
                        btnSignUp.setTextColor(Color.parseColor("#50FFFF"));
                    }
                } else {
                    btnSignUp.setEnabled(false);
                    btnSignUp.setTextColor(Color.parseColor("#50FFFF"));
                }
            } else {
                btnSignUp.setEnabled(false);
                btnSignUp.setTextColor(Color.parseColor("#50FFFF"));
            }
        } else {
            btnSignUp.setEnabled(false);
            btnSignUp.setTextColor(Color.parseColor("#50FFFF"));
        }
    }

    private void checkEmailandPassword() {
        Drawable icon_error = getResources().getDrawable(R.mipmap.custom_error_icon);
        icon_error.setBounds(0, 0, icon_error.getIntrinsicWidth(), icon_error.getIntrinsicHeight());

        if (email.getText().toString().matches(emailPattern)) {
            if (password.getText().toString().trim().equals(confirm_pass.getText().toString().trim())) {
                progressBar.setVisibility(View.VISIBLE);
                btnSignUp.setEnabled(false);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Map<Object, String> userdata = new HashMap<>();
                                userdata.put("fullname", fullname.getText().toString());
                                firebaseFirestore.collection("USERS").add(userdata).addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                                startActivity(intent);
                                                getActivity().finish();
                                            }
                                        });
                            } else {
                                btnSignUp.setEnabled(true);
                                progressBar.setVisibility(View.INVISIBLE);
                                String error = task.getException().getMessage();
                                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                confirm_pass.setError("Password don't matched!", icon_error);
            }

        } else {
            email.setError("Invaild Email", icon_error);
        }
    }
}
