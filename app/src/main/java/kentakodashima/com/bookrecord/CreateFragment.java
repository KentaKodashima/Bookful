package kentakodashima.com.bookrecord;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

public class CreateFragment extends Fragment implements TextView.OnEditorActionListener {

  ImageView imageView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    EditText titleEdit = (EditText) getActivity().findViewById(R.id.book_title);
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
        Bitmap image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
        imageView = (ImageView) getActivity().findViewById(R.id.book_image);
        imageView.setImageBitmap(image);
      } catch (Exception e) {
        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
        e.printStackTrace();
      }
    }
  }

  @Override
  public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
    if (i == EditorInfo.IME_ACTION_DONE) {
      InputMethodManager imm = (InputMethodManager)textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
      return true;
    }
    return false;
  }
}