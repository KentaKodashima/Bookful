package kentakodashima.com.bookrecord.ui.recyclerview;

import android.content.Context;
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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
  private LayoutInflater layoutInflater;
  private RealmResults<Record> listContents;

  public SearchAdapter(Context context, RealmResults<Record> listContents) {
    layoutInflater = LayoutInflater.from(context);

    this.listContents = listContents;
  }

  @NonNull
  @Override
  public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    // create a new view
    View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.list_cell_layout, parent, false);

    // set the view's size, margins, paddings and layout parameters
    SearchAdapter.ViewHolder viewHolder = new SearchAdapter.ViewHolder(view);

    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {

    Record record = listContents.get(position);
    String bookTitle = record.getTitle();
    String bookDescription = record.getDescription();
    Bitmap imageBitmap = BitmapFactory.decodeFile(record.getImageName());

    holder.bookTitle.setText(bookTitle);
    holder.bookDescription.setText(bookDescription);
    holder.imageView.setImageBitmap(imageBitmap);
  }

  @Override
  public int getItemCount() {
    return listContents.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    TextView bookTitle;
    TextView bookDescription;
    ImageView imageView;

    public ViewHolder(View itemView) {
      super(itemView);

      bookTitle = (TextView) itemView.findViewById(R.id.book_title);
      bookDescription = (TextView) itemView.findViewById(R.id.book_description);
      imageView = (ImageView) itemView.findViewById(R.id.icon_image);
    }
  }
}
