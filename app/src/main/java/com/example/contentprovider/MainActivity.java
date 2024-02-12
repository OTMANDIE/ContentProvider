package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.Manifest;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lst);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.getNoms());
        lv.setAdapter(adapter);


    }

    public ArrayList<String> getContacts() {
        ArrayList<String> liste = new ArrayList<String>();
        ContentResolver cr = this.getContentResolver();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String nom = ContactsContract.Contacts.DISPLAY_NAME;
        Cursor c = cr.query(uri, new String[]{nom}, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            int indice = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            nom = c.getString(indice);
            liste.add(nom);
            c.moveToNext();
        }
        c.close();
        return liste;
    }

    private ArrayList<String> getNoms() {
        ArrayList<String> liste = new ArrayList<>();
        Uri URI = ContactsContract.Contacts.CONTENT_URI;
        final String COLUMN_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            ContentResolver cr = getContentResolver();
            Cursor cx = cr.query(URI, new String[]{COLUMN_NAME}, null, null, null);
            cx.moveToFirst();
            int indice = cx.getColumnIndex(COLUMN_NAME);
            while (!cx.isAfterLast()) {
                liste.add(cx.getString(indice));
                cx.moveToNext();
            }
        } else {
            Toast.makeText(this, "Vous n'avez pas le droit d'acceder aux contacts",
                    Toast.LENGTH_SHORT).show();
        }
        return liste;
    }
}