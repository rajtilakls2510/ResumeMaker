package com.example.resumemaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditActivity extends AppCompatActivity {
    EditText name,email,phone,qualities,qualifications,experience,projects,skill1,skill2,skill3,achievements,language;
    TextView Skill,Heading;
    RatingBar rating1,rating2,rating3;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference myRef;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        name=findViewById(R.id.userNameEdit);
        email=findViewById(R.id.emailEdit);
        phone=findViewById(R.id.phoneEdit);
        qualities=findViewById(R.id.qualitiesEdit);
        qualifications=findViewById(R.id.qualificationEdit);
        experience=findViewById(R.id.experienceEdit);
        projects=findViewById(R.id.projectsEdit);
        skill1=findViewById(R.id.skill1Edit);
        skill2=findViewById(R.id.skill2Edit);
        skill3=findViewById(R.id.skill3Edit);
        achievements=findViewById(R.id.achievementsEdit);
        language=findViewById(R.id.languageEdit);
        Skill=findViewById(R.id.skillsEdit);
        Heading=findViewById(R.id.HeadingEdit);
        rating1=findViewById(R.id.rating1Edit);
        rating2=findViewById(R.id.rating2Edit);
        rating3=findViewById(R.id.rating3Edit);
        key=getIntent().getStringExtra("Key");
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        myRef=db.getReference().child("users").child(mAuth.getCurrentUser().getUid()).child(key);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name.setText(dataSnapshot.child("name").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                phone.setText(dataSnapshot.child("phone").getValue().toString());
                qualities.setText(dataSnapshot.child("qualities").getValue().toString());
                qualifications.setText(dataSnapshot.child("qualifications").getValue().toString());
                experience.setText(dataSnapshot.child("experience").getValue().toString());
                projects.setText(dataSnapshot.child("projects").getValue().toString());
                skill1.setText(dataSnapshot.child("skill1").getValue().toString());
                skill2.setText(dataSnapshot.child("skill2").getValue().toString());
                skill3.setText(dataSnapshot.child("skill3").getValue().toString());
                rating1.setRating(Float.parseFloat(dataSnapshot.child("rating1").getValue().toString()));
                rating2.setRating(Float.parseFloat(dataSnapshot.child("rating2").getValue().toString()));
                rating3.setRating(Float.parseFloat(dataSnapshot.child("rating3").getValue().toString()));
                achievements.setText(dataSnapshot.child("achievements").getValue().toString());
                language.setText(dataSnapshot.child("language").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.saveEdit){

            if((key.equals("")) || (name.getText().toString().equals("")) ) {
                Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
                return true;
            }
            myRef=db.getReference().child("users").child(mAuth.getCurrentUser().getUid()).child(key);

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
            if(!achievements.getText().toString().equals(""))
                myRef.child("achievements").setValue(achievements.getText().toString());
            myRef.child("language").setValue(language.getText().toString());
            Intent i=new Intent(EditActivity.this,ShowActivity.class);
            i.putExtra("Key",key);
            startActivity(i);
            Toast.makeText(this, "Resume Edited", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
