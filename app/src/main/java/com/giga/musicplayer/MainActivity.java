package com.giga.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    //Array to store songs
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listViewSong);

        runtimePermission();
    }

    //Method for asking permission
    public void runtimePermission(){
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        displaySongs();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    //Method to find song
    public ArrayList<File> findSong(File file){

        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        for (File singlefile:
                files
             ) {
            if(singlefile.isDirectory() && !singlefile.isHidden()){
                arrayList.addAll(findSong(singlefile));
            }
            else{
                  if(singlefile.getName().endsWith(".mp3") || singlefile.getName().endsWith(".wav") || singlefile.getName().endsWith(".bp")){
                      //Add the file
                      arrayList.add(singlefile);
                  }
            }
        }
        return  arrayList;
    }

    void displaySongs(){

        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());

        items = new String[mySongs.size()];
        int i;

        for(i = 0; i < mySongs.size(); i++){
            items[i] = mySongs.get(i).getName().toString()
                    .replace(".mp3","")
                    .replace(".wav", "")
                    .replace(".bp","");
        }

        //ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        customadapter customadapter = new customadapter();
        listView.setAdapter(customadapter);
    }

    class customadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View myview = getLayoutInflater().inflate(R.layout.list_view, null);
            TextView mytextview = myview.findViewById(R.id.txtsongname);
            mytextview.setSelected(true);
            mytextview.setText(items[position]);

            return myview;
        }
    }
}