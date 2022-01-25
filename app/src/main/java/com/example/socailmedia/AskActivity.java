package com.example.socailmedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.Calendar;

public class AskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    String[] status = {"Choose category","Tech","Heath","Education","Food","Sports","News","Fashion","Beauty","Lifestyle"};

    EditText editText;
    Button button;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference AllQuestions,UserQuestions;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    QuestionMember member;
    String name,url,privacy,uid,cat_value;
    Spinner spinner;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserid = user.getUid();

        editText = findViewById(R.id.ask_et_question);
        button = findViewById(R.id.btn_submit);
        documentReference = db.collection("user").document(currentUserid);

        textView = findViewById(R.id.tv_cat);
        spinner = findViewById(R.id.spinner_cat);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,status);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        AllQuestions = database.getReference("AllQuestions");
        UserQuestions = database.getReference("UserQuestions").child(currentUserid);

        member = new QuestionMember();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String question = editText.getText().toString();

                Calendar cdate = Calendar.getInstance();
                SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMMM-yyyy");
                final  String savedate = currentdate.format(cdate.getTime());

                Calendar ctime = Calendar.getInstance();
                SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss");
                final String savetime = currenttime.format(ctime.getTime());


                String time = savedate +":"+ savetime;


                if ((question.length()==0) || cat_value.equals("Choose category")){
                    Toast.makeText(AskActivity.this, "Please ask a question", Toast.LENGTH_SHORT).show();

                }else {
                    member.setQuestion(question);
                    member.setName(name);
                    member.setPrivacy(privacy);
                    member.setUrl(url);
                    member.setUserid(uid);
                    member.setTime(time);
                    member.setCategory(cat_value.toLowerCase());

                    String id = UserQuestions.push().getKey();
                    UserQuestions.child(id).setValue(member);


                    String child = AllQuestions.push().getKey();
                    member.setKey(id);
                    AllQuestions.child(child).setValue(member);
                    Toast.makeText(AskActivity.this, "Submitted", Toast.LENGTH_SHORT).show();


                }


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();



        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.getResult().exists()){
                             name = task.getResult().getString("name");
                             url = task.getResult().getString("url");
                            privacy = task.getResult().getString("privacy");
                            uid = task.getResult().getString("uid");

                        }else {
                            Toast.makeText(AskActivity.this, "Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        cat_value = adapterView.getSelectedItem().toString();
         textView.setText(cat_value);


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();

    }
}