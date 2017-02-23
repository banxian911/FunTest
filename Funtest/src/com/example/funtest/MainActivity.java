package com.example.funtest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.funtest.RecycleViewAdapter.OnIClickListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnIClickListener{

	public String FUNCSETTING = "FuncSetting";
	public String ITEMPOSITION = "ItemPosition";
	private RecyclerView mOnLocScreenRV;
	private RecycleViewAdapter mAdapter;
	private List<Shortcuts> mList;
	
	private String appName;
	private String appPakeName;
	private Drawable appIcon;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//initData();
		mOnLocScreenRV = (RecyclerView)findViewById(R.id.on_lockscreen);
		mOnLocScreenRV.setLayoutManager(new LinearLayoutManager(this));
		//mAdapter = new RecycleViewAdapter(MainActivity.this,mList);
		mAdapter = new RecycleViewAdapter(AllApkInfo());
		mAdapter.setOnIClickListener(this);
		mOnLocScreenRV.setAdapter(mAdapter);
		mOnLocScreenRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));//添加分割线
		
		ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
			
			@Override
			public void onSwiped(ViewHolder arg0, int arg1) {
				// TODO Auto-generated method stub
				 //侧滑事件
             //   mList.remove(arg0.getAdapterPosition());
             //   mAdapter.notifyItemRemoved(arg0.getAdapterPosition());
			}
			
			@Override
			public boolean onMove(RecyclerView arg0, ViewHolder arg1, ViewHolder arg2) {
				// TODO Auto-generated method stub
				//滑动事件
				Collections.swap(mList, arg1.getAdapterPosition(), arg2.getAdapterPosition());
				mAdapter.notifyItemMoved(arg1.getAdapterPosition(), arg2.getAdapterPosition());
				//getSharedPreferences(FUNCSETTING, Context.MODE_PRIVATE).edit().putInt("", 1).commit();
				
				return false;
			}
			
			@Override
			public int getMovementFlags(RecyclerView arg0, ViewHolder arg1) {
				// TODO Auto-generated method stub
				//首先回调的方法 返回int表示是否监听该方向
				int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;//拖拽
				//int swipeFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;//侧滑删除 
				return makeMovementFlags(dragFlags, 0);
			}
			
			@Override
			public boolean isLongPressDragEnabled() {
				// TODO Auto-generated method stub
				//return super.isLongPressDragEnabled();
				return true;//是否可拖拽
			}
		});
		helper.attachToRecyclerView(mOnLocScreenRV);
    
	}
	
	private void initData(){
//		mList = new ArrayList<>();
//		mList.add(new Shortcuts("Alarm",R.drawable.func_alarm));
//		mList.add(new Shortcuts("Call", R.drawable.func_cal));
//		mList.add(new Shortcuts("Camera", R.drawable.func_camera));
//		mList.add(new Shortcuts("Contact",R.drawable.func_contact));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void OnIClick(View view, int position) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.fun_reorder:
			Toast.makeText(this,"fun_reorder", Toast.LENGTH_SHORT).show();
			break;
		case R.id.edit_image:
			Toast.makeText(this,"edit_image", Toast.LENGTH_SHORT).show();
			break;
		case R.id.remove_image:
			mAdapter.remove(position);
			//mAdapter.notifyDataSetChanged();
			Toast.makeText(this,"remove_image"+ position, Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	
	public void saveSwapList(List<Shortcuts> mList){
		//mOnLocScreenRV.get
		ArrayList<Shortcuts> mArrayList = new ArrayList<>();
		for (int i = 0; i < mList.size(); i++) {
			mArrayList.add(mList.get(i));
		}
	}
	
	private List<Shortcuts> AllApkInfo(){
		mList = new ArrayList<>();
		PackageManager pm = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.PERMISSION_GRANTED);
        for (ResolveInfo rInfo : list) {
           // results.add(rInfo.activityInfo.applicationInfo.loadLabel(pm).toString());
            appName = pm.getApplicationLabel(rInfo.activityInfo.applicationInfo).toString();//获得应用名
            appPakeName = rInfo.activityInfo.applicationInfo.packageName;//获得应用包名
            appIcon = pm.getApplicationIcon(rInfo.activityInfo.applicationInfo);//获得应用的图标
            mList.add(new Shortcuts(appName, appPakeName, appIcon));
            Log.d("Funtest","--apkInfo->" + "appName-->"+ appName + "---appPakeName-->"+ appPakeName + "---appIcon-->"+ appIcon);
        }
        return mList;
	}
}
