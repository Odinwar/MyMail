package com.example.mymail.Login;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mymail.R;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.mymail.Login.SingUpFragment.emailPattern;
import static com.example.mymail.ShareFunction.setFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForgotEmailFragment extends Fragment {

    public ForgotEmailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_email, container, false);
        setRetainInstance(true);
        getUI(view);
        setClick();
        return view;
    }

    private FirebaseAuth firebaseAuth;
    private TextView textComplete, goback;
    private EditText email;
    private ProgressBar progressBar;
    private ImageView imageComplete, imageSend;
    private FrameLayout frameLayout;
    private Button btnSend;

    private void getUI(View view) {
        textComplete = view.findViewById(R.id.textComplete);
        goback = view.findViewById(R.id.goback);
        email = view.findViewById(R.id.email);
        frameLayout = getActivity().findViewById(R.id.frameLayout);
        progressBar = view.findViewById(R.id.progressBar);
        imageComplete = view.findViewById(R.id.imageComplete);
        imageSend = view.findViewById(R.id.imageSend);
        btnSend = view.findViewById(R.id.btnSend);
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
        goback.setOnClickListener(v -> setFragment(new SignInFragment(), frameLayout, getActivity(), false));
        btnSend.setOnClickListener(v -> sendEmail());
    }

    private void checkInput() {
        if (!TextUtils.isEmpty(email.getText())) {
            if (email.getText().toString().matches(emailPattern)) {
                btnSend.setEnabled(true);
                btnSend.setTextColor(Color.parseColor("#FFFFFF"));
            }
        }
    }

    private void sendEmail() {
        imageSend.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(task ->
        {
            imageSend.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {
                imageComplete.setVisibility(View.VISIBLE);
                textComplete.setVisibility(View.VISIBLE);
            } else {
                String error = task.getException().getMessage();
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
