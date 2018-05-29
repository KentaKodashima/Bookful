package kentakodashima.com.bookrecord;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainAdapter extends BaseAdapter {

  private int resource;
  private LayoutInflater layoutInflater;
  private ArrayList<GridCell> listContents;

  public MainAdapter(Context context, int resource, ArrayList<GridCell> listContents) {
    layoutInflater = LayoutInflater.from(context);

    this.resource = resource;
    this.listContents = listContents;
  }

  static class ViewHolder {
    TextView textView;
    ImageView imageView;
  }

  @Override
  public int getCount() {
    return listContents.size();
  }

  @Override
  public Object getItem(int i) {
    return i;
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

    ViewHolder holder;

    if (convertView == null) {
      convertView = layoutInflater.inflate(resource, null);
      holder = new ViewHolder();
      holder.textView = convertView.findViewById(R.id.text_view);
      holder.imageView = convertView.findViewById(R.id.image_view);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    holder.textView.setText(listContents.get(position).getContent());
    holder.imageView.setImageResource(listContents.get(position).getImage());

    return convertView;
  }
}
