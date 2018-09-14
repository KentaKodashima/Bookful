package kentakodashima.com.bookful.ui.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import kentakodashima.com.bookful.R;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {

  private int space;


  public CustomItemDecoration(int space) {
    this.space = space;
  }

  public static CustomItemDecoration generateGridCellSpaces(Context context) {

    int spaceInPixels = context.getResources().getDimensionPixelSize(R.dimen.recyclerview_margin);
    return new CustomItemDecoration(spaceInPixels);
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

    outRect.set(space, space, space, space);

  }

}
