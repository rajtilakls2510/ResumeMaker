package com.example.resumemaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewActivity extends AppCompatActivity {

    EditText nameFile,name,email,phone,qualities,qualifications,experience,projects,skill1,skill2,skill3,achievements,language;
    TextView Skill,Heading;
    RatingBar rating1,rating2,rating3;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        nameFile=findViewById(R.id.nameFile);
        name=findViewById(R.id.userNameNew);
        email=findViewById(R.id.emailNew);
        phone=findViewById(R.id.phoneNew);
        qualities=findViewById(R.id.qualitiesNew);
        qualifications=findViewById(R.id.qualificationNew);
        experience=findViewById(R.id.experienceNew);
        projects=findViewById(R.id.projectsNew);
        skill1=findViewById(R.id.skill1New);
        skill2=findViewById(R.id.skill2New);
        skill3=findViewById(R.id.skill3New);
        achievements=findViewById(R.id.achievementsNew);
        language=findViewById(R.id.languageNew);
        Skill=findViewById(R.id.skillsNew);
        Heading=findViewById(R.id.Heading);
        rating1=findViewById(R.id.rating1New);
        rating2=findViewById(R.id.rating2New);
        rating3=findViewById(R.id.rating3New);

        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.saveNew:

                if((nameFile.getText().toString().equals("")) || (name.getText().toString().equals("")) || (email.getText().toString().equals("")) || (phone.getText().toString().equals(""))|| (qualities.getText().toString().equals(""))|| (qualifications.getText().toString().equals(""))|| (projects.getText().toString().equals(""))|| (experience.getText().toString().equals(""))|| (achievements.getText().toString().equals(""))|| (language.getText().toString()).equals("")) {
                    Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
                    return true;
                }
                db=FirebaseDatabase.getInstance();
                myRef=db.getReference().child("users").child(mAuth.getCurrentUser().getUid()).child(nameFile.getText().toString());
                myRef.child("name").setValue(name.getText().toString());
                myRef.child("email").setValue(email.getText().toString());
                myRef.child("phone").setValue(phone.getText().toString());
                myRef.child("qualities").setValue(qualities.getText().toString());
                myRef.child("qualifications").setValue(qualifications.getText().toString());
                myRef.child("projects").setValue(projects.getText().toString());
                myRef.child("experience").setValue(experience.getText().toString());
                if(!skill1.getText().toString().equals("")){
                myRef.child("skill1").setValue(skill1.getText().toString());
                myRef.child("rating1").setValue(String.valueOf(rating1.getRating()));}
                if(!skill2.getText().toString().equals("")){
                    myRef.child("skill2").setValue(skill2.getText().toString());
                    myRef.child("rating2").setValue(String.valueOf(rating2.getRating()));}
                if(!skill3.getText().toString().equals("")){
                    myRef.child("skill3").setValue(skill3.getText().toString());
                    myRef.child("rating3").setValue(String.valueOf(rating3.getRating()));}
                myRef.child("achievements").setValue(achievements.getText().toString());
                myRef.child("language").setValue(language.getText().toString());


                Toast.makeText(this, "Resume Saved", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
