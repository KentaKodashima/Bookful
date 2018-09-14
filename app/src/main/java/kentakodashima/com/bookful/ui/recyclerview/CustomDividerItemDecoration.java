package kentakodashima.com.bookful.ui.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CustomDividerItemDecoration extends RecyclerView.ItemDecoration {

  private static final int[] ATTRS = new int[]{
          android.R.attr.listDivider
  };

  private Drawable mDivider;

  public CustomDividerItemDecoration(Context context) {
    final TypedArray a = context.obtainStyledAttributes(ATTRS);
    mDivider = a.getDrawable(0);
    a.recycle();
  }

  @Override
  public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
    drawDevider(canvas, parent);
  }

  public void drawDevider(Canvas canvas, RecyclerView parent) {
    final int left = parent.getPaddingLeft();
    final int right = parent.getWidth() - parent.getPaddingRight();

    final int childCount = parent.getChildCount();
    for (int i = 0; i < childCount; i++) {
      final View child = parent.getChildAt(i);
      final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
              .getLayoutParams();
      final int top = child.getBottom() + params.bottomMargin;
      final int bottom = top + mDivider.getIntrinsicHeight();
      mDivider.setBounds(left, top, right, bottom);
      mDivider.draw(canvas);
    }
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
  }
}
