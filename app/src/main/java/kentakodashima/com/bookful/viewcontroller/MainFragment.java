package kentakodashima.com.bookful.viewcontroller;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmResults;
import kentakodashima.com.bookful.model.Record;
import kentakodashima.com.bookful.R;
import kentakodashima.com.bookful.ui.recyclerview.CustomItemDecoration;
import kentakodashima.com.bookful.ui.recyclerview.MainAdapter;

public class  MainFragment extends Fragment {

  RecyclerView gridList;
  private TextView emptyView;
  private RealmResults<Record> records;
  private ImageView gridImageView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_main, null);

    gridList = (RecyclerView) view.findViewById(R.id.grid_list);
    emptyView = (TextView) view.findViewById(R.id.empty_view);
    gridImageView = (ImageView) view.findViewById(R.id.grid_image);
    // TODO: Set imageView flexible height and width
//    gridImageView.getLayoutParams().height = view.getHeight() / 2;

    Record recordGetter = new Record();
    records = recordGetter.getRecords(getActivity());

    // use a linear layout manager
    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);

    gridList.setLayoutManager(layoutManager);
    gridList.addItemDecoration(CustomItemDecoration.generateGridCellSpaces(getActivity()));

    final RecyclerView.Adapter gridAdapter = new MainAdapter(getActivity(), records);
    gridList.setAdapter(gridAdapter);

    // Observe the changes in records
    records.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<Record>>() {
      @Override
      public void onChange(RealmResults<Record> records, OrderedCollectionChangeSet changeSet) {
        gridAdapter.notifyDataSetChanged();

        checkData(records);
      }
    });

    checkData(records);

    return view;
  }

  private void checkData(RealmResults<Record> records) {
    if (records.size() == 0) {
      gridList.setVisibility(View.GONE);
      emptyView.setVisibility(View.VISIBLE);
    } else {
      gridList.setVisibility(View.VISIBLE);
      emptyView.setVisibility(View.GONE);
    }
  }

  @Override
  public void onActivityCreated (Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
  }
}
