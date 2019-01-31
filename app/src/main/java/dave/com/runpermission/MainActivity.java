package dave.com.runpermission;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private Button button;
String []permissions={Manifest.permission.CAMERA};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(isPermissionGranted()){
                        Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        requestPermission();
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isPermissionGranted(){
        for(String permission:permissions){
            if(checkSelfPermission(permission)!=PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission(){
        ArrayList<String> leftPer=new ArrayList<String>();
        for(String permission:permissions){
            if(checkSelfPermission(permission)!=PackageManager.PERMISSION_GRANTED){
             leftPer.add(permission);
            }
        }
        requestPermissions(leftPer.toArray(new String[leftPer.size()]),101);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==101){
            for(int i=0;i<grantResults.length;i++){
               if(shouldShowRequestPermissionRationale(permissions[i])){
                   new AlertDialog.Builder(this)
                           .setTitle("Permission Required")
                           .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                  requestPermission();
                               }
                           })
                           .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   dialog.dismiss();
                               }
                           })
                           .create()
                           .show();

               }
            }
        }
    }
}
