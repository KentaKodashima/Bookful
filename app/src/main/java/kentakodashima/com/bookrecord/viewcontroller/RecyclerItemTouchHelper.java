package kentakodashima.com.bookrecord.viewcontroller;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import kentakodashima.com.bookrecord.ui.recyclerview.SearchAdapter;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

  private RecyclerItemTouchHelperListener listener;

  final ColorDrawable background = new ColorDrawable(Color.RED);

  public RecyclerItemTouchHelper(int dragDirection, int swipeDirection, RecyclerItemTouchHelperListener listener) {
    super(dragDirection, swipeDirection);
    this.listener = listener;
  }

  @Override
  public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
    return false;
  }

  @Override
  public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
  }

  @Override
  public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
    if (viewHolder != null) {
      final View foregroundView = ((SearchAdapter.ViewHolder) viewHolder).viewForeground;

      getDefaultUIUtil().onSelected(foregroundView);
    }
  }

  @Override
  public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                              RecyclerView.ViewHolder viewHolder, float dX, float dY,
                              int actionState, boolean isCurrentlyActive) {
    final View foregroundView = ((SearchAdapter.ViewHolder) viewHolder).viewForeground;
    getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
            actionState, isCurrentlyActive);
  }

  @Override
  public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    final View foregroundView = ((SearchAdapter.ViewHolder) viewHolder).viewForeground;
    getDefaultUIUtil().clearView(foregroundView);
  }

  @Override
  public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

    final View foregroundView = ((SearchAdapter.ViewHolder) viewHolder).viewForeground;

    background.setBounds(0, foregroundView.getTop(),   foregroundView.getLeft() + (int)dX, foregroundView.getBottom());

    background.draw(c);

    getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
  }

  @Override
  public int convertToAbsoluteDirection(int flags, int layoutDirection) {
    return super.convertToAbsoluteDirection(flags, layoutDirection);
  }

  public interface RecyclerItemTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
  }
}
