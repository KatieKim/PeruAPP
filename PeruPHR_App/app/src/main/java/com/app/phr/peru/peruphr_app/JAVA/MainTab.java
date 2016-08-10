package com.app.phr.peru.peruphr_app.JAVA;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.app.phr.peru.peruphr_app.Fragment.FragmentMyInfo;
import com.app.phr.peru.peruphr_app.Fragment.FragmentPHR;
import com.app.phr.peru.peruphr_app.Fragment.FragmentEducationInfo;
import com.app.phr.peru.peruphr_app.R;

import java.util.ArrayList;
import java.util.List;

public class MainTab extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.icon1,
            R.drawable.icon1,
            R.drawable.icon1
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab);

        //action bar 대신 toolbar를 사용
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar 에서 action bar의 support를 받아옴
        setSupportActionBar(toolbar);
        //타이틀 변경
        getSupportActionBar().setTitle("Peru-PHR");
        //액션바 컬러 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xB9CDE5));
        //새로고침 아이콘 표시
        //getSupportActionBar().setDisplayShowCustomEnabled(true);
        // 홈 아이콘 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //tab레이아웃을 사용함. main_tab.xml과 연결되어 있음
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    //tab 아이콘 바꿀 수 있음
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    //메뉴 버튼 추가, 아이콘 연결 되어 있음
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }

    //메뉴 이벤트 처리
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //home 버튼 눌렀을때
        if (id == android.R.id.home) {
            Intent myAct1 = new Intent(getApplicationContext(), Login.class);
            Toast.makeText(this, "log out", Toast.LENGTH_SHORT).show();
            //버튼 눌렀을때 액티비티 초기화
            myAct1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(myAct1);
            return true;
        }

//        //refresh 눌렀을 때
//        if (id == R.id.action_button) {
//            Toast.makeText(this, "Refresh success", Toast.LENGTH_SHORT).show();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    //tab에서 보여줄 탭 fragment 이름
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentPHR(), "PHR");
        //adapter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        adapter.addFrag(new FragmentEducationInfo(), "Education Info");
        adapter.addFrag(new FragmentMyInfo(), "My Info");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        //fragment 초기화 안될 시에 추가 할 것
//        @Override
//        public int getItemPosition(Object item) {
//            return POSITION_NONE;
//        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
