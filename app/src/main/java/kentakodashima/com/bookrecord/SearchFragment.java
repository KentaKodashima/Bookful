package kentakodashima.com.bookrecord;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmResults;

public class SearchFragment extends Fragment {

  private RecyclerView recordList;
  private RealmResults<Record> records;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_search, null);

    recordList = view.findViewById(R.id.record_list);

    Record recordGetter = new Record();
    records = recordGetter.getRecords(getActivity());

    // use a linear layout manager
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    recordList.setLayoutManager(layoutManager);

    RecyclerView.Adapter listAdapter = new SearchAdapter(getActivity(), records);
    recordList.setAdapter(listAdapter);

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
