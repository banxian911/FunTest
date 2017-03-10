package com.example.funtest;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class AlternativeViewAdapter extends BaseAdapter {

	private List<Shortcuts> mList;
	private Context mContext;
	private OnAIClickListener mIClickListener;
	
	public AlternativeViewAdapter(Context mContext,List<Shortcuts> mList) {
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.mList = mList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList == null ? 0:mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHold mHold;
		final Shortcuts mShortcuts = mList.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.alternative_item, null);
			mHold = new ViewHold();
			mHold.mImageView = (ImageView)convertView.findViewById(R.id.alternative_icon);
			mHold.mTextView = (TextView)convertView.findViewById(R.id.alternative_text);
			mHold.Addbt = (ImageButton)convertView.findViewById(R.id.add_image);
			convertView.setTag(mHold);
		}else {
			mHold = (ViewHold)convertView.getTag();
		}
		mHold.mImageView.setBackgroundResource(mShortcuts.getID_icon());;
		mHold.mTextView.setText(mShortcuts.getShortcutsName());
		mHold.Addbt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mIClickListener != null) {
					mIClickListener.OnIClick(mHold.Addbt, position,mShortcuts);
				}
				
			}
		});
		return convertView;
	}
	
	
	public class ViewHold{
		private ImageView mImageView;
		private TextView mTextView;
		private ImageButton Addbt;
	}
	
	public void setAOnIClickListener(OnAIClickListener mIClickListener){
		this.mIClickListener = mIClickListener;
	}
	
	public interface OnAIClickListener{
		void OnIClick(View view,int position,Shortcuts mShortcuts);
	}

	public void remove(int position){
		mList.remove(position);
		notifyDataSetChanged();
	}
	
	public void add(Shortcuts mShortcuts,int position){
		mList.add(position, mShortcuts);
		notifyDataSetInvalidated();
	}
}
