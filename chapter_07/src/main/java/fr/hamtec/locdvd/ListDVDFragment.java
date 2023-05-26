package fr.hamtec.locdvd;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListDVDFragment  extends Fragment {
    private ListView list;
    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView( @NonNull @NotNull LayoutInflater inflater,
                              @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                              @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState ) {
        
        View view = inflater.inflate( R.layout.fragment_listdvd, null );
        list = view.findViewById( R.id.main_list );
        
        list.setOnItemClickListener( new AdapterView.OnItemClickListener( ) {
            @Override
            public void onItemClick( AdapterView < ? > parent, View view, int position, long id ) {
                
                startViewDVDActivity( id );
                
            }
        } );
        
        return view;
    }
    
    @Override
    public void onResume( ) {
        super.onResume( );
        ArrayList <DVD> dvdList = DVD.getDVDList( getActivity() );
        
        DVDAdapter dvdAdapter = new DVDAdapter( getActivity(), dvdList );
        list.setAdapter( dvdAdapter );
        
    }
    
    private void startViewDVDActivity( long dvdId){
        
        Intent intent = new Intent(getActivity(), ViewDVDActivity.class);
        intent.putExtra( "dvdId", dvdId );
        startActivity( intent );
        
    }
}
