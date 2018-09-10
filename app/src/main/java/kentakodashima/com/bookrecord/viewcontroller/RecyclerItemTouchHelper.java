package kentakodashima.com.bookrecord.viewcontroller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import kentakodashima.com.bookrecord.R;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
  private Context context;
  private RecyclerItemTouchHelperListener listener;
  final ColorDrawable background = new ColorDrawable(Color.RED);

  public RecyclerItemTouchHelper(Context context, int dragDirection, int swipeDirection, RecyclerItemTouchHelperListener listener) {
    super(dragDirection, swipeDirection);
    this.listener = listener;
    this.context = context;
  }

  @Override
  public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
    return false;
  }

  @Override
  public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    View itemView = viewHolder.itemView;

    background.setBounds(itemView.getRight(), itemView.getTop(), itemView.getLeft(), itemView.getBottom());
    background.draw(c);

    Drawable icon = context.getResources().getDrawable(R.drawable.ic_delete_white_24dp);
    float itemHeight = itemView.getBottom() - itemView.getTop();
    float intrinsicWidth = icon.getIntrinsicWidth();
    float intrinsicHeight = icon.getIntrinsicHeight();

    float iconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
    float iconMargin = (itemHeight - intrinsicHeight) / 2;
    float iconLeft = itemView.getRight() - iconMargin - intrinsicWidth;
    float iconRight = itemView.getRight() - iconMargin;
    float iconBottom = iconTop + intrinsicHeight;

    icon.setBounds((int)iconLeft, (int)iconTop, (int)iconRight, (int)iconBottom);
    icon.draw(c);
  }


  @Override
  public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
  }

  @Override
  public int convertToAbsoluteDirection(int flags, int layoutDirection) {
    return super.convertToAbsoluteDirection(flags, layoutDirection);
  }

  public interface RecyclerItemTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
  }
}
