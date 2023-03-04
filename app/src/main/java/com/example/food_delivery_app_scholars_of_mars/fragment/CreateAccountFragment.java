package com.example.food_delivery_app_scholars_of_mars.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.food_delivery_app_scholars_of_mars.MainActivity;
import com.example.food_delivery_app_scholars_of_mars.R;
import com.example.food_delivery_app_scholars_of_mars.model.UserModel;
import com.example.food_delivery_app_scholars_of_mars.session.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    TextInputEditText name, email,numberPhone,password,passwordConfirmation;

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateAccountFragment newInstance(String param1, String param2) {
        CreateAccountFragment fragment = new CreateAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }
    public void onStart() {
        super.onStart();
        Button btn = (Button) getActivity().findViewById(R.id.sign_up_btn);
        name = getActivity().findViewById(R.id.name);
        email = getActivity().findViewById(R.id.email);
        numberPhone = getActivity().findViewById(R.id.phone_number);
        password = getActivity().findViewById(R.id.password);
        passwordConfirmation = getActivity().findViewById(R.id.password_confirm);

            progressDialog = new ProgressDialog(getContext());
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog.show();
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(Objects.requireNonNull(email.getText()).toString(),password.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    progressDialog.cancel();
                                    UserModel user = new UserModel(Objects.requireNonNull(name.getText()).toString(),
                                            email.getText().toString(),
                                            Objects.requireNonNull(numberPhone.getText()).toString(),
                                            "NA");
                                    Toast.makeText(getContext(), "login success", Toast.LENGTH_SHORT).show();
                                    firebaseFirestore.collection("user")
                                            .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                            .set(user);
                                    SessionManager sessionManager = new SessionManager(getContext());
                                    sessionManager.createLoginSession(
                                            user.getName(),
                                            user.getEmail(),
                                            user.getPhoneNumber(),
                                            user.getAddress());

                                    startActivity(new Intent(getContext(), MainActivity.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            });

    }
}