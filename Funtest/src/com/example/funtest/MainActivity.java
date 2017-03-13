package com.example.funtest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.funtest.AlternativeViewAdapter.OnAIClickListener;
import com.example.funtest.RecycleViewAdapter.OnIClickListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private int DEFAULT_ID = 0;
	private int ALTERNATIVE_ID = 1;
	private int OTHERAPP_ID = 2;
	private String FUNCSETTING = "FuncSetting";
	private String KEY_ONLOCKSCREEN = "key_onlockscreen";
	private String KEY_ALTERNATIVE = "key_alternative"; 
	private RecyclerView mOnLocScreenRV;
	private RecycleViewAdapter mAdapter;

	private List<Shortcuts> mList;
	private List<Shortcuts> mShowOnCList;
	private List<Shortcuts> mShowAltList;
	private ListView mListView;
	private AlternativeViewAdapter mAlternativeViewAdapter;
	private TextView noOpen_text;

	private String appName;
	private String appPakeName;
	private Drawable appIcon;

//	private static final String FilePath ="/data/user/0/com.android.systemui/file";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// initData();

		// mAdapter = new RecycleViewAdapter(MainActivity.this,mList);
		noOpen_text = (TextView)findViewById(R.id.text_noopen);
		/*if (isOpenFunc()) {
			startFunc();
		} else {
			stopFunc();
		}*/
		startFunc();
	}

	private void initAlternativeUI() {
		mListView = (ListView) findViewById(R.id.on_alternative);
		mAlternativeViewAdapter = new AlternativeViewAdapter(MainActivity.this, ShowAlternativeData());
		mAlternativeViewAdapter.setAOnIClickListener(new AlternativeOnClickListen());
		mListView.setAdapter(mAlternativeViewAdapter);
		setListViewHeightBasedOnChildren(mListView);
	}

	private void initOnlockscreenUI() {
		mOnLocScreenRV = (RecyclerView) findViewById(R.id.on_lockscreen);
		mOnLocScreenRV.setLayoutManager(new LinearLayoutManager(this));

		mAdapter = new RecycleViewAdapter(ShowOnLockData());

		mAdapter.setOnIClickListener(new RecycleViewOnClickListen());

		mOnLocScreenRV.setAdapter(mAdapter);
		mOnLocScreenRV.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));// 添加分割线

		ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

			@Override
			public void onSwiped(ViewHolder arg0, int arg1) {
				// TODO Auto-generated method stub
				// 侧滑事件
				// mList.remove(arg0.getAdapterPosition());
				// mAdapter.notifyItemRemoved(arg0.getAdapterPosition());
			}

			@Override
			public boolean onMove(RecyclerView arg0, ViewHolder arg1, ViewHolder arg2) {
				// TODO Auto-generated method stub
				// 滑动事件
				Collections.swap(mShowOnCList, arg1.getAdapterPosition(), arg2.getAdapterPosition());
				mAdapter.notifyItemMoved(arg1.getAdapterPosition(), arg2.getAdapterPosition());
				SaveAllData();
				return false;
			}

			@Override
			public int getMovementFlags(RecyclerView arg0, ViewHolder arg1) {
				// TODO Auto-generated method stub
				// 首先回调的方法 返回int表示是否监听该方向
				int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;// 拖拽
				// int swipeFlags =
				// ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;//侧滑删除
				return makeMovementFlags(dragFlags, 0);
			}

			@Override
			public boolean isLongPressDragEnabled() {
				// TODO Auto-generated method stub
				// return super.isLongPressDragEnabled();
				return true;// 是否可拖拽
			}
		});
		helper.attachToRecyclerView(mOnLocScreenRV);
	}


	private List<Shortcuts> OnLocScreenDefaultData() {
		mList = new ArrayList<>();
		mList.add(new Shortcuts("Search with Google voice", "com.google.android.googlequicksearchbox",R.drawable.func_cal,"com.google.android.apps.gsa.queryentry.QueryEntryActivity"));
		mList.add(new Shortcuts("Show recent calls", "com.android.dialer", R.drawable.func_music,"com.android.dialer.DialtactsActivity"));
		mList.add(new Shortcuts("Search with Yahoo", "com.android.browser", R.drawable.func_yahoo,"http://www.yahoo.com"));
		mList.add(new Shortcuts("Edit Func settings", "com.android.camera2", R.drawable.func_camera,""));
		mList.add(new Shortcuts("Start the camera", "com.android.camera2", R.drawable.func_alarm,""));
		return mList;
	}

	private List<Shortcuts> AlternativeDefaultData() {
		mList = new ArrayList<>();
		mList.add(new Shortcuts("Recognise a song", "com.android.music", R.drawable.func_alarm,""));
		mList.add(new Shortcuts("Set timer", "com.android.deskclock", R.drawable.func_alarm,""));
		mList.add(new Shortcuts("Edit Wallshuffle settings", "", R.drawable.func_alarm,""));
		mList.add(new Shortcuts("Take a selfie", "", R.drawable.func_alarm,""));
		mList.add(new Shortcuts("Start music playlist", "com.android.music", R.drawable.func_music,""));
		mList.add(new Shortcuts("Compose a message", "", R.drawable.func_alarm,""));
		mList.add(new Shortcuts("Compose an email", "com.android.email", R.drawable.func_alarm,""));
		mList.add(new Shortcuts("Add contact", "com.android.contacts", R.drawable.func_alarm,""));
		mList.add(new Shortcuts("Add event", "", R.drawable.func_alarm,""));
		mList.add(new Shortcuts("Start sound recording", "com.android.soundrecorder", R.drawable.func_alarm,""));
		mList.add(new Shortcuts("Navigate home", "com.android.browser", R.drawable.func_alarm,""));
		mList.add(new Shortcuts("Set alarm", "com.android.deskclock", R.drawable.func_alarm,""));
		mList.add(new Shortcuts("Open calculator", "com.android.calculator2", R.drawable.func_alarm,""));
		mList.add(new Shortcuts("Turn Torch on/off", "com.broadcom.torch", R.drawable.func_alarm,""));
		return mList;
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

	private class AlternativeOnClickListen implements OnAIClickListener {

		@Override
		public void OnIClick(View view, int position, Shortcuts mShortcuts) {
			// TODO Auto-generated method stub
			// Toast.makeText(MainActivity.this,"hello_ale-->" + position,
			// Toast.LENGTH_SHORT).show();

			AddOnLockData(position, mShortcuts);
			SaveAllData();
		}

	}

	private class RecycleViewOnClickListen implements OnIClickListener {

		@Override
		public void OnIClick(View view, int position, Shortcuts mShortcuts) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.fun_reorder:
				Toast.makeText(MainActivity.this, "fun_reorder", Toast.LENGTH_SHORT).show();
				break;
			case R.id.edit_image:
				Toast.makeText(MainActivity.this, "edit_image", Toast.LENGTH_SHORT).show();
				break;
			case R.id.remove_image:
				RemoveOnLockData(position, mShortcuts);
				SaveAllData();
				// mAdapter.notifyDataSetChanged();
				// Toast.makeText(MainActivity.this,"remove_image"+ position,
				// Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}

	}

	private void RemoveOnLockData(int position, Shortcuts mShortcuts) {
		mAdapter.remove(position);
		mAlternativeViewAdapter.add(mShortcuts, 0);
		Log.d("Funtest", "--mShowOnCList.size--->" + mShowOnCList.size() + "---mShowAltList-->"+ mShowAltList.size());
	}

	private void AddOnLockData(int position, Shortcuts mShortcuts) {
		Log.d("Funtest", "--mAdapter.getItemCount()->" + mAdapter.getItemCount());
		if (mAdapter.getItemCount() < 5) {
			mAlternativeViewAdapter.remove(position);
			mAdapter.add(mShortcuts, 0);
		} else {
			Toast.makeText(this, "Maximum of 5 shortcuts reached. Remove one from the lock screen first.",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void EditShortcuts(int position) {

	}
	
	private List<Shortcuts> ShowOnLockData() {

		mShowOnCList = new ArrayList<>();
		// List<Shortcuts> showList = new ArrayList<>();
		if (!(readDataList(KEY_ONLOCKSCREEN) == null)) {
			mShowOnCList = readDataList(KEY_ONLOCKSCREEN);
		} else {
			mShowOnCList = OnLocScreenDefaultData();
		}
		return mShowOnCList;
	}
	
	private List<Shortcuts> ShowAlternativeData(){
		mShowAltList = new ArrayList<>();
		if (!(readDataList(KEY_ALTERNATIVE) == null)) {
			mShowAltList = readDataList(KEY_ALTERNATIVE);
		} else {
			mShowAltList = AlternativeDefaultData();
		}
		return mShowAltList;	
	}

	public List<Shortcuts> readDataList(String keyStr) {

		Log.d("Funtest", "-- getPreferString(this, keyStr, null)--->" + getPreferString(this, keyStr, null));
		if (getPreferString(this, keyStr, null) == null) {
			return null;
		} else {
			return getShortcuts(keyStr,getPreferString(this, keyStr, null));
		}
	}

	public List<Shortcuts> getShortcuts(String keyStr,String jsonString) {

		Log.d("Funtest", "-- getShortcuts-->");
		List<Shortcuts> list = new ArrayList<Shortcuts>();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(jsonString);
			JSONArray mJsonArray = jsonObject.getJSONArray(keyStr);
			for (int i = 0; i < mJsonArray.length(); i++) {
				Shortcuts mShortcuts = new Shortcuts();
				jsonObject = mJsonArray.getJSONObject(i);
				mShortcuts.setPakeName(jsonObject.getString("pakeName"));
				mShortcuts.setShortcutsName(jsonObject.getString("shortcutsName"));
				mShortcuts.setID_icon(jsonObject.getInt("ID_icon"));
				mShortcuts.setExtras(jsonObject.getString("extras"));
			//	Log.d("Funtest", "-- jsonObject2-->" + jsonObject);
				list.add(mShortcuts);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	private String changeArrayDateToJsonString(String keyStr,List<Shortcuts> mShortcutsList) {
		JSONArray mJsonArray = new JSONArray();
		JSONObject mJsonObject = new JSONObject();
		for (int i = 0; i < mShortcutsList.size(); i++) {
			JSONObject mJsonObject2 = new JSONObject();
			try {
				mJsonObject2.put("shortcutsName", mShortcutsList.get(i).getShortcutsName());
				mJsonObject2.put("pakeName", mShortcutsList.get(i).getPakeName());
				mJsonObject2.put("ID_icon", mShortcutsList.get(i).getID_icon());
				mJsonObject2.put("extras", mShortcutsList.get(i).getExtras());
				mJsonArray.put(mJsonObject2);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			mJsonObject.put(keyStr, mJsonArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mJsonObject.toString();
	}
	
	
	public static void saveInfoToTxt(String filePath, String fileName, String fileContent) throws Exception {
		if (fileContent != null) {
			Log.d("Funtest", "--filePath->" + filePath+ "--fileName->"+fileName);
			File file = new File(filePath);
			//Log.d("Funtest", "--file.exists()->" + file.exists()+ "-file.mkdirs()-->"+file.mkdirs()+"--->"+file);
			if (!file.exists()) {
				file.mkdirs();
			}
			File mFile = new File(file, fileName);
			FileOutputStream outputStream = new FileOutputStream(mFile);
			outputStream.write(fileContent.getBytes());
			outputStream.close();
		}
	}

	private void SaveAllData(){
		Log.d("Funtest", "--mShowOnCList.size-AAA-->" + mShowOnCList.size() + "---mShowAltList-->"+ mShowAltList.size());
		setListViewHeightBasedOnChildren(mListView);
		saveData(KEY_ONLOCKSCREEN,mShowOnCList);
		saveData(KEY_ALTERNATIVE, mShowAltList);
	}
	
	public void saveData(String keyStr,List<Shortcuts> mList) {
		
	//	if (!mList.isEmpty()) {
			String jString = changeArrayDateToJsonString(keyStr,mList);
			Log.d("Funtest", "--jString->" + jString);
			setPreferString(this, keyStr, jString);
	//	}
		if (keyStr.equals(KEY_ONLOCKSCREEN)) {
			String filepath = Environment.getExternalStorageDirectory() +File.separator+"File"+File.separator;
			try {
				Log.d("Funtest", "--filepath->" + filepath);
				saveInfoToTxt(filepath,"Func.txt", jString);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private List<Shortcuts> AllApkInfo() {
		mList = new ArrayList<>();
		PackageManager pm = this.getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.PERMISSION_GRANTED);
		for (ResolveInfo rInfo : list) {
			// results.add(rInfo.activityInfo.applicationInfo.loadLabel(pm).toString());
			appName = pm.getApplicationLabel(rInfo.activityInfo.applicationInfo).toString();// 获得应用名
			appPakeName = rInfo.activityInfo.applicationInfo.packageName;// 获得应用包名
			appIcon = pm.getApplicationIcon(rInfo.activityInfo.applicationInfo);// 获得应用的图标
			mList.add(new Shortcuts(OTHERAPP_ID, appName, appPakeName, appIcon));
			Log.d("Funtest", "--apkInfo->" + "appName-->" + appName + "---appPakeName-->" + appPakeName
					+ "---appIcon-->" + appIcon);
		}
		return mList;
	}

	public static void setPreferString(Context context, String key, String defaultValue) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		settings.edit().putString(key, defaultValue).commit();
	}

	public static String getPreferString(Context context, String key, String defaultValue) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		return settings.getString(key, defaultValue);
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//saveData();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	public void setListViewHeightBasedOnChildren(ListView listView) {   
        // 获取ListView对应的Adapter   
        ListAdapter listAdapter = listView.getAdapter();   
        if (listAdapter == null) {   
            return;   
        }   
   
        int totalHeight = 0;   
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
            // listAdapter.getCount()返回数据项的数目   
            View listItem = listAdapter.getView(i, null, listView);   
            // 计算子项View 的宽高   
            listItem.measure(0, 0);    
            // 统计所有子项的总高度   
            totalHeight += listItem.getMeasuredHeight();    
        }   
   
        ViewGroup.LayoutParams params = listView.getLayoutParams();   
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
        // listView.getDividerHeight()获取子项间分隔符占用的高度   
        // params.height最后得到整个ListView完整显示需要的高度   
        listView.setLayoutParams(params);   
    }
	
	private boolean isOpenFunc(){
		int isOpen = getSharedPreferences(FUNCSETTING, Context.MODE_PRIVATE).getInt("isOpenFunc", 0);
		if (isOpen == 0) {
			return true;
		} else {
			return false;
		}
	}
	private void startFunc(){
		noOpen_text.setVisibility(View.GONE);
		initOnlockscreenUI();
		initAlternativeUI();
		//getSharedPreferences(FUNCSETTING, Context.MODE_PRIVATE).edit().putInt("isOpenFunc", 0).commit();
	}
	
	private void stopFunc(){
		//getSharedPreferences(FUNCSETTING, Context.MODE_PRIVATE).edit().putInt("isOpenFunc", 1).commit();
		noOpen_text.setVisibility(View.VISIBLE);
	}
	
	/*
        //public static final int recent_subject_list = 8;

         public String getFileName(String filePath,String filename) {
             File file = new File(filePath);
             if (!file.exists())
             file.mkdirs();
             String fileName = filename+"";
             return fileName;
         }


         *//**
         * 存储缓存文件：
         *//*
        public void saveRecentSubList(List<Shortcuts> list,String filePath) {
          //  int type = recent_subject_list;
           // String fileName = filePath+ File.separator + getFileNameById(studentId);
            File file = new File(fileName);
            try {
                if (!file.exists())
                    file.createNewFile();
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
                oos.writeObject(list);
                oos.flush();
                oos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        *//**
         * 读取缓存文件：
         *//* 

         public List<Shortcuts> getRecentSubList(String uid, String studentId) {
         //   int type = recent_subject_list;
            List<Shortcuts> resultList = new ArrayList<>();
          //  String fileName = Config.CACHE_DATA_PATH + File.separator + getFileNameById(type, uid, studentId);
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
                ArrayList<Shortcuts> list_ext = (ArrayList<Shortcuts>) ois.readObject();

                for (Shortcuts obj : list_ext) {
                	Shortcuts bean = obj;
                    if (bean != null) {
                        resultList.add(bean);
                    }
                }
                ois.close();
            } catch (Exception e) {
                return resultList;
            }
            return resultList;
        }
    */
	
}
