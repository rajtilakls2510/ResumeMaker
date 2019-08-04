package com.example.resumemaker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ResumeActivity extends AppCompatActivity {

    ListView resumes;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference myRef;
    ArrayList<String> names = new ArrayList<>();
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        resumes = findViewById(R.id.resumes);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference().child("users").child(mAuth.getCurrentUser().getUid());
        final ListAdpater adapter=new ListAdpater();
        if(names.size()>0)
        names.clear();
        resumes.setAdapter(adapter);
        registerForContextMenu(resumes);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                names.add(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                names.remove(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        resumes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ResumeActivity.this, ShowActivity.class);
                intent.putExtra("Key", names.get(i));
                startActivity(intent);

            }
        });
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            Intent i = new Intent(ResumeActivity.this, MainActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.resume_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.newResume:
                Intent i = new Intent(ResumeActivity.this, NewActivity.class);
                startActivity(i);
                return true;
            case R.id.logout:
                mAuth.signOut();
                updateUI(mAuth.getCurrentUser());
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_resume_menu,menu);
            AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)menuInfo;
            key=((TextView)info.targetView.findViewById(R.id.NameList)).getText().toString();

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {


        switch(item.getItemId()){
            case R.id.delete:

                myRef=db.getReference().child("users").child(mAuth.getCurrentUser().getUid());
                myRef.child(key).removeValue();
                key="";
                Toast.makeText(this, key+" Resume deleted", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public class ListAdpater extends BaseAdapter{

        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=getLayoutInflater().inflate(R.layout.resume_list,null);
            TextView text=view.findViewById(R.id.NameList);
            text.setText(names.get(i));
            return view;
        }
    }

}
