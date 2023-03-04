package com.example.food_delivery_app_scholars_of_mars.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.food_delivery_app_scholars_of_mars.AcountActivity;
import com.example.food_delivery_app_scholars_of_mars.MainActivity;
import com.example.food_delivery_app_scholars_of_mars.R;
import com.example.food_delivery_app_scholars_of_mars.model.UserModel;
import com.example.food_delivery_app_scholars_of_mars.session.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class AccountLoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressDialog progressDialog;
    TextInputEditText pwdField ,emailField;



    public AccountLoginFragment() {
        // Required empty public constructor
    }




    public static AccountLoginFragment newInstance(String param1, String param2) {
        AccountLoginFragment fragment = new AccountLoginFragment();
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
        return inflater.inflate(R.layout.fragment_account_login, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button btn = (Button) getActivity().findViewById(R.id.login_btn);
        progressDialog = new ProgressDialog(getContext());
        pwdField = getActivity().findViewById(R.id.pwd_login);
        emailField = getActivity().findViewById(R.id.email_login);

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String email = Objects.requireNonNull(emailField.getText()).toString() ;
                String password = Objects.requireNonNull(pwdField.getText()).toString();
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                progressDialog.cancel();
                              String uid = Objects.requireNonNull(authResult.getUser()).getUid();
                                DocumentReference docRef = firebaseFirestore.collection("user")
                                        .document(uid);

                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                //Log.d("Database", "DocumentSnapshot data: " + document.getData());
                                                Map<String, Object> userData = document.getData();
                                                assert userData != null;
                                                UserModel user = new UserModel(
                                                        Objects.requireNonNull(userData.get("name")).toString(),
                                                        Objects.requireNonNull(userData.get("email")).toString(),
                                                        Objects.requireNonNull(userData.get("phoneNumber")).toString(),
                                                        Objects.requireNonNull(userData.get("address")).toString()
                                                );

                                                //Log.d("Database", "DocumentSnapshot data: " + user.toString());
                                                SessionManager sessionManager = new SessionManager(getContext());
                                                sessionManager.createLoginSession(
                                                        user.getName(),
                                                        user.getEmail(),
                                                        user.getPhoneNumber(),
                                                        user.getAddress());

                                            } else {
                                                Log.d("Database", "No such document");
                                            }
                                        } else {
                                            Log.d("Database", "get failed with ", task.getException());
                                        }


                                    }
                                });



                                Toast.makeText(getContext(), "login success", Toast.LENGTH_SHORT).show();
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