package kentakodashima.com.bookrecord.ui.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import kentakodashima.com.bookrecord.R;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {

  private int space;
  private int topBottomSpace;
  private int leftRightSpace;


  public CustomItemDecoration(int space) {
    this.space = space;
  }

  public CustomItemDecoration(int topButtom, int leftRight) {
    this.topBottomSpace = topButtom;
    this.leftRightSpace = leftRight;
  }

  public static CustomItemDecoration generateGridCellSpaces(Context context) {

    int spacingInPixels = context.getResources().getDimensionPixelSize(R.dimen.recyclerview_margin);
    return new CustomItemDecoration(spacingInPixels);
  }

  public static CustomItemDecoration generateListCellSpaces(Context context) {

    int spacingInPixels = context.getResources().getDimensionPixelSize(R.dimen.recyclerview_margin);
    return new CustomItemDecoration(spacingInPixels, 0);
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

    outRect.set(leftRightSpace, topBottomSpace, leftRightSpace, topBottomSpace);

    outRect.set(space, space, space, space);
//    outRect.top = space;
//    outRect.left = space;
//    outRect.right = space;
//    outRect.bottom = space;
  }

}
