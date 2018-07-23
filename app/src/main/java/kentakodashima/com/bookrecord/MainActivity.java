package kentakodashima.com.bookrecord;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
          = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
        case R.id.navigation_home:
          MainFragment mainFragment = new MainFragment();
          FragmentTransaction mainTransaction = getFragmentManager().beginTransaction();
          mainTransaction.replace(R.id.fragmentContainer, mainFragment);
          mainTransaction.addToBackStack(null);
          mainTransaction.commit();
          return true;
        case R.id.navigation_create:
          CreateFragment createFragment = new CreateFragment();
          FragmentTransaction createTransaction = getFragmentManager().beginTransaction();
          createTransaction.replace(R.id.fragmentContainer, createFragment);
          createTransaction.addToBackStack(null);
          createTransaction.commit();
          return true;
        case R.id.navigation_search:
          SearchFragment searchFragment = new SearchFragment();
          FragmentTransaction searchTransaction = getFragmentManager().beginTransaction();
          searchTransaction.replace(R.id.fragmentContainer, searchFragment);
          searchTransaction.addToBackStack(null);
          searchTransaction.commit();
          return true;
      }
      return false;
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
  }

}
