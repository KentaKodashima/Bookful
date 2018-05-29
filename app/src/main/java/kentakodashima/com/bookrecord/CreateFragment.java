package kentakodashima.com.bookrecord;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

public class CreateFragment extends Fragment {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_create, null);
  }

  @Override
  public void onActivityCreated (Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    Button uploadButton = (Button) getActivity().findViewById(R.id.image_upload_button);
    uploadButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 0);
      }
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 0 && resultCode == RESULT_OK) {
      try {
        Uri uri = data.getData();
        Cursor c = getActivity().managedQuery(uri, null, null, null, null);
        c.moveToFirst();
        long id = c.getLong(c.getColumnIndexOrThrow("_id"));
        ContentResolver cr = getActivity().getContentResolver();
        Bitmap img = MediaStore.Images.Thumbnails.getThumbnail(cr, id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
      } catch (Exception e) {
        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
      }
    }
  }
//  public void imageButtonClicked(View view) {
//      Intent intent = new Intent();
//      intent.setType("image/*");
//      intent.setAction(Intent.ACTION_GET_CONTENT);
//      startActivityForResult(intent, 0);
//  }

}