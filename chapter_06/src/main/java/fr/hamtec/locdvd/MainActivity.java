package fr.hamtec.locdvd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    
    ListView list;
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        list = findViewById( R.id.list );
        
        list.setOnItemClickListener( new AdapterView.OnItemClickListener( ) {
            @Override
            public void onItemClick( AdapterView < ? > parent, View view, int position, long id ) {
                
                startViewDVDActivity( id );
                
            }
        } );
        
        SharedPreferences sharedPreferences = getSharedPreferences("fr.hamtec.locdvd.prefs", Context.MODE_PRIVATE );
        
        if ( !sharedPreferences.getBoolean( "embeddedDataInserted", false ) ){
            readEmbbeddedData();
        }
        
    }
    private void startViewDVDActivity( long dvdId){
        
        Intent intent = new Intent(this, ViewDVDActivity.class);
        intent.putExtra( "dvdId", dvdId );
        startActivity( intent );
        
    }
    
    @Override
    protected void onPostResume( ) {
        super.onPostResume( );
        
        ArrayList<DVD> dvdList = DVD.getDVDList( MainActivity.this );
        
        DVDAdapter dvdAdapter = new DVDAdapter( this, dvdList );
        list.setAdapter( dvdAdapter );
    }
    
    /**
     * Lire les données et les enregistrer
     */
    private void readEmbbeddedData(){
        
        InputStream file = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        
        try {
            file = getAssets().open( "data.txt" );
            reader =new InputStreamReader( file );
            bufferedReader = new BufferedReader( reader );
            
            String line = null;
            
            while ( ( line = bufferedReader.readLine( ) ) != null ){
                
                String[] data = line.split( "\\|" );
                
                if ( data != null && data.length == 4 ){
                    DVD dvd = new DVD();
                    dvd.setTitre(data[0]);
                    dvd.setAnnee(Integer.decode(data[1]));
                    dvd.setActeurs(data[2].split(","));
                    dvd.setResume(data[3]);
                    
                    dvd.insert( this );
                    
                }
                
            }
            
        }catch ( IOException e ){
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
                reader.close();
                SharedPreferences sharedPreferences = getSharedPreferences("fr.hamtec.locDVD.prefs",Context.MODE_PRIVATE);//??
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("enbeddedDataInsered",true);
                editor.commit();
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
        
        
        
    }
}