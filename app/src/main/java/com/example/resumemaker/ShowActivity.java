package com.example.resumemaker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowActivity extends AppCompatActivity {

    TextView name,email,phone,qualities,qualifications,experience,projects,skills,skill1,skill2,skill3,achievement,language;
    RatingBar rating1,rating2,rating3;
    String key;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        name=findViewById(R.id.nameShow);
        email=findViewById(R.id.emailShow);
        phone=findViewById(R.id.phoneShow);
        qualities=findViewById(R.id.qualitiesShow);
        qualifications=findViewById(R.id.qualificationShow);
        experience=findViewById(R.id.experienceShow);
        projects=findViewById(R.id.projectsShow);
        skills=findViewById(R.id.skillsShow);
        skill1=findViewById(R.id.skill1Show);
        skill2=findViewById(R.id.skill2Show);
        skill3=findViewById(R.id.skill3Show);
        achievement=findViewById(R.id.achievementShow);
        language=findViewById(R.id.languageShow);
        rating1=findViewById(R.id.rating1Show);
        rating2=findViewById(R.id.rating2Show);
        rating3=findViewById(R.id.rating3Show);
        key=getIntent().getStringExtra("Key");
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
    }

    @Override
    protected void onResume() {

        myRef=db.getReference().child("users").child(mAuth.getCurrentUser().getUid()).child(key);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("name").getValue()!=null) {
                    name.setText(dataSnapshot.child("name").getValue().toString());
                    email.setText("Email: " + dataSnapshot.child("email").getValue().toString());
                    phone.setText("Phone: " + dataSnapshot.child("phone").getValue().toString());
                    qualities.setText("Qualities: \n\t" + dataSnapshot.child("qualities").getValue().toString());
                    qualifications.setText("Academic Qualifications: \n\t" + dataSnapshot.child("qualifications").getValue().toString());
                    experience.setText("Work Experience: \n\t" + dataSnapshot.child("experience").getValue().toString());
                    projects.setText("Projects: \n\t" + dataSnapshot.child("projects").getValue().toString());
                    if (dataSnapshot.child("skill1").getValue() != null) {
                        skill1.setText(dataSnapshot.child("skill1").getValue().toString());
                        rating1.setRating(Float.parseFloat(dataSnapshot.child("rating1").getValue().toString()));
                    }
                    if (dataSnapshot.child("skill2").getValue() != null) {
                        skill2.setText(dataSnapshot.child("skill2").getValue().toString());
                        rating2.setRating(Float.parseFloat(dataSnapshot.child("rating2").getValue().toString()));
                    }
                    if (dataSnapshot.child("skill3").getValue() != null) {
                        skill3.setText(dataSnapshot.child("skill3").getValue().toString());
                        rating3.setRating(Float.parseFloat(dataSnapshot.child("rating3").getValue().toString()));
                    }
                    if(dataSnapshot.child("achievements").getValue()!=null)
                    achievement.setText("Achievements: \n\t" + dataSnapshot.child("achievements").getValue().toString());
                    if(dataSnapshot.child("language").getValue()!=null)
                    language.setText("Languages spoken: " + dataSnapshot.child("language").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.editMenuItem:
                Intent i=new Intent(ShowActivity.this,EditActivity.class);
                i.putExtra("Key",key);
                startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }
}
