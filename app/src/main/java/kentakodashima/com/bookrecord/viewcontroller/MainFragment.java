package kentakodashima.com.bookrecord.viewcontroller;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmResults;
import kentakodashima.com.bookrecord.R;
import kentakodashima.com.bookrecord.model.Record;
import kentakodashima.com.bookrecord.ui.recyclerview.MainAdapter;

public class  MainFragment extends Fragment {

  RecyclerView gridList;
  private RealmResults<Record> records;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_main, null);

    gridList = (RecyclerView) view.findViewById(R.id.grid_list);
    Record recordGetter = new Record();
    records = recordGetter.getRecords(getActivity());

    // use a linear layout manager
    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);

    gridList.setLayoutManager(layoutManager);

    RecyclerView.Adapter gridAdapter = new MainAdapter(getActivity(), records);
    gridList.setAdapter(gridAdapter);


    return view;
  }

  @Override
  public void onActivityCreated (Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
  }
}
