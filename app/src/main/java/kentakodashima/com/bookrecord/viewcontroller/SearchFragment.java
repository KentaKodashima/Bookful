package kentakodashima.com.bookrecord.viewcontroller;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import kentakodashima.com.bookrecord.R;
import kentakodashima.com.bookrecord.model.Record;
import kentakodashima.com.bookrecord.ui.recyclerview.CustomDividerItemDecoration;
import kentakodashima.com.bookrecord.ui.recyclerview.SearchAdapter;

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

  private RecyclerView recordList;
  private SearchView searchView;
  private RealmResults<Record> records;
  private ArrayList<Record> filteredRecords;
  private Record recordGetter;
  private SearchAdapter listAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_search, null);

    recordList = view.findViewById(R.id.record_list);
    searchView = view.findViewById(R.id.searchView);

    recordGetter = new Record();
    records = recordGetter.getRecords(getActivity());
    filteredRecords = new ArrayList<>();

    // use a linear layout manager
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
    recordList.setLayoutManager(layoutManager);

    recordList.addItemDecoration(new CustomDividerItemDecoration(getActivity()));

    listAdapter = new SearchAdapter(getActivity(), records);
    recordList.setAdapter(listAdapter);

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
    new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recordList);

    // SearchViewにOnQueryChangeListenerを設定
    searchView.setOnQueryTextListener(this);

    return view;
  }

  @Override
  public void onActivityCreated (Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
  }

  @Override
  public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
    if (viewHolder instanceof SearchAdapter.ViewHolder) {
      // remove the item from recycler view
      listAdapter.removeItem(viewHolder.getAdapterPosition());
    }
  }

  @Override
  public boolean onQueryTextSubmit(String query) {
    return false;
  }

  @Override
  public boolean onQueryTextChange(String newText) {
    if (TextUtils.isEmpty(newText)) {
      records = recordGetter.getRecords(getActivity());
      RecyclerView.Adapter newListAdapter = new SearchAdapter(getActivity(), records);
      recordList.setAdapter(newListAdapter);
    } else {
      Realm.init(getActivity());
      Realm realm = Realm.getDefaultInstance();

      newText = newText.toLowerCase();
      records = realm.where(Record.class).contains("title", newText).findAll();

      RecyclerView.Adapter newListAdapter = new SearchAdapter(getActivity(), records);
      recordList.setAdapter(newListAdapter);
    }
    return true;
  }
}
