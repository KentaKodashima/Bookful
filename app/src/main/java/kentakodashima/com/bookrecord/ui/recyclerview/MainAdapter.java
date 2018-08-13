package kentakodashima.com.bookrecord.ui.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.RealmResults;
import kentakodashima.com.bookrecord.R;
import kentakodashima.com.bookrecord.model.Record;
import kentakodashima.com.bookrecord.viewcontroller.RecordDetailActivity;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

  private LayoutInflater layoutInflater;
  private RealmResults<Record> listContents;
  private Context context;

  public MainAdapter(Context context, RealmResults<Record> listContents) {
    layoutInflater = LayoutInflater.from(context);

    this.listContents = listContents;
    this.context = context;
  }

  @NonNull
  @Override
  public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    // create a new view
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.grid_cell_layout, parent, false);

    // set the view's size, margins, paddings and layout parameters
    final ViewHolder viewHolder = new ViewHolder(view);

    // Implement the listener
    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        final int position = viewHolder.getAdapterPosition();
        Intent intent = new Intent(context, RecordDetailActivity.class);
        Record record = listContents.get(position);
        String recordKey = record.getRecordKey();
        intent.putExtra("recordKey", recordKey);
        context.startActivity(intent);
      }
    });

    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {

    Record record = listContents.get(position);
    String bookTitle = record.getTitle();

    if (record.getImageName() != null) {
      Bitmap imageBitmap = BitmapFactory.decodeFile(record.getImageName());
      holder.imageView.setImageBitmap(imageBitmap);
    } else {
      holder.imageView.setImageResource(R.drawable.dummy);
    }
    holder.textView.setText(bookTitle);
  }

  @Override
  public int getItemCount() {
    return listContents.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    TextView textView;
    ImageView imageView;

    public ViewHolder(View itemView) {
      super(itemView);

      textView = (TextView) itemView.findViewById(R.id.grid_text);
      imageView = (ImageView) itemView.findViewById(R.id.grid_image);
    }
  }
}
