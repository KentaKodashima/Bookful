package kentakodashima.com.bookrecord.viewcontroller;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import io.realm.Realm;
import kentakodashima.com.bookrecord.R;
import kentakodashima.com.bookrecord.model.Record;

import static android.app.Activity.RESULT_OK;

public class CreateFragment extends Fragment implements TextView.OnEditorActionListener {

  private Button uploadButton;
  private ImageView bookImage;
  private EditText titleEdit;
  private EditText authorEdit;
  private EditText descriptionEdit;
  private EditText reviewEdit;
  private Button saveButton;

  private String titleString;
  private String authorString;
  private String descriptionString;
  private String reviewString;

  private Bitmap selectedImage;
  private String imageFilePathString;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Realm.init(getActivity());

    EditText titleEdit = (EditText) getActivity().findViewById(R.id.book_title_field);
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
    titleEdit = (EditText) getActivity().findViewById(R.id.book_title_field);
    authorEdit = (EditText) getActivity().findViewById(R.id.autor_field);
    descriptionEdit = (EditText) getActivity().findViewById(R.id.description_field);
    reviewEdit = (EditText) getActivity().findViewById(R.id.review_field);
    bookImage = (ImageView) getActivity().findViewById(R.id.book_image);

    uploadButton = (Button) getActivity().findViewById(R.id.image_upload_button);
    uploadButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 0);
      }
    });

    saveButton = (Button) getActivity().findViewById(R.id.save_button);
    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        saveButtonTapped();
      }
    });
  }

  private void saveButtonTapped() {
    if (!isFieldEmpty()) {
      Realm realm = Realm.getDefaultInstance();

      titleString = titleEdit.getText().toString();
      authorString = authorEdit.getText().toString();
      descriptionString = descriptionEdit.getText().toString();
      reviewString = reviewEdit.getText().toString();

      Record record = new Record(titleString, authorString, descriptionString, reviewString);

      if (bookImage.getDrawable().getConstantState() != getResources().getDrawable(R.drawable.dummy).getConstantState()) {
        saveImageData(selectedImage);
        record.setImageName(imageFilePathString);
      }

      realm.beginTransaction();
      final Record managedRecord = realm.copyToRealm(record);
      realm.commitTransaction();

      resetFields();

      MainFragment mainFragment = new MainFragment();
      FragmentTransaction mainTransaction = getFragmentManager().beginTransaction();
      mainTransaction.replace(R.id.fragmentContainer, mainFragment);
      mainTransaction.addToBackStack(null);
      mainTransaction.commit();
    } else {
      alertGenerator(R.string.empty_fields_alert_title, R.string.empty_fields_alert_message);
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == 0 && resultCode == RESULT_OK) {
      try {
        Uri uri = data.getData();
        selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

        bookImage.setImageBitmap(selectedImage);

      } catch (Exception e) {
        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
        e.printStackTrace();
      }
    }
  }

  private void saveImageData(Bitmap image) {
    // Generate random image name
    String fileName = UUID.randomUUID().toString() + ".png";
    File fileDirectory = getActivity().getFilesDir();

    File imageFile = new File(fileDirectory, fileName);
    String imageFilePath = imageFile.getAbsolutePath().toString();

    imageFilePathString = imageFilePath;

    try {
      OutputStream stream = new FileOutputStream(imageFile);
      image.compress(Bitmap.CompressFormat.PNG,100, stream);
      stream.flush();
      stream.close();
    } catch (IOException e) {
      e.printStackTrace();
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

  private void alertGenerator(int title, int message) {
    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
    builder.setTitle(title);
    builder.setMessage(message);
    builder.setPositiveButton("OK", null);
    builder.show();
  }

  public boolean isFieldEmpty() {

    boolean empty = false;
    if (titleEdit.getText().toString().isEmpty() ||
            authorEdit.getText().toString().isEmpty() ||
            descriptionEdit.getText().toString().isEmpty() ||
            reviewEdit.getText().toString().isEmpty()) {
      empty = true;
    }
    return empty;
  }

  public void resetFields() {
    titleEdit.setText("");
    authorEdit.setText("");
    descriptionEdit.setText("");
    reviewEdit.setText("");
    bookImage.setImageResource(R.drawable.dummy);
    selectedImage = null;
  }
}