package com.example.funtest;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>{

	private List<Shortcuts> mList;
	private Context mContext;
	//private LayoutInflater inflater;
	private OnIClickListener mIClickListener;

	public RecycleViewAdapter(List<Shortcuts> mList) {
		// TODO Auto-generated constructor stub
	//	this.mContext = context;
		this.mList = mList;
		//inflater = LayoutInflater.from(mContext);
	}

	public RecycleViewAdapter(Context context,List<Shortcuts> mList){
		this.mContext = context;
		this.mList = mList;
	}
	
	public void setOnIClickListener(OnIClickListener mIClickListener){
		this.mIClickListener = mIClickListener;
	}
	
	public interface OnIClickListener{
		void OnIClick(View view,int position,Shortcuts mShortcuts);
	}
	class MyViewHolder extends RecyclerView.ViewHolder{
		
		private ImageButton fun_reorder;
		private ImageView func_icon;
		private TextView func_name;
		private ImageButton func_edit;
		private ImageButton func_remove;

		public MyViewHolder(View itemView) {
			// TODO Auto-generated constructor stub
			super(itemView);
			fun_reorder = (ImageButton)itemView.findViewById(R.id.fun_reorder);
			func_icon = (ImageView)itemView.findViewById(R.id.func_icon);
			func_name = (TextView)itemView.findViewById(R.id.func_text);
			func_edit = (ImageButton)itemView.findViewById(R.id.edit_image);
			func_remove = (ImageButton)itemView.findViewById(R.id.remove_image);
		}

		public void setItemOnClickListener(int position,Shortcuts mShortcuts){
			fun_reorder.setOnClickListener(new ItemListener(position,mShortcuts));
			func_edit.setOnClickListener(new ItemListener(position,mShortcuts));
			func_remove.setOnClickListener(new ItemListener(position,mShortcuts));
		}
		private class ItemListener implements OnClickListener{

			private int mPosition;
			private Shortcuts mShortcuts;
			
			private ItemListener(int position,Shortcuts shortcuts){
				mPosition = position;
				mShortcuts = shortcuts;
				
			}
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.fun_reorder:
					mIClickListener.OnIClick(fun_reorder,mPosition,mShortcuts);
					break;
				case R.id.edit_image:
					mIClickListener.OnIClick(func_edit,mPosition,mShortcuts);
					break;
				case R.id.remove_image:
					mIClickListener.OnIClick(func_remove,mPosition,mShortcuts);
					break;
				default:
					break;
				}
			}
			
		}
	}

	public void remove(int position){
		notifyItemRemoved(position);
		Log.d("Funtest","--mList.size()--1->" + mList.size() + "-position--->"+position);
		if (position != mList.size()) {
			notifyItemRangeChanged(position, mList.size()-position);
		}
		mList.remove(position);
	}
	
	public void add(Shortcuts mShortcuts,int position){
		mList.add(position, mShortcuts);
		notifyItemInserted(position);
	}
	
	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mList == null ? 0:mList.size();
	}

	@Override
	public void onBindViewHolder(MyViewHolder arg0, int arg1) {
		// TODO Auto-generated method stub
	//	Log.d("Funtest","--onBindViewHolder->" + "arg1-->"+ arg1 +"--arg0.getAdapterPosition()->"+arg0.getAdapterPosition());
		Shortcuts mShortcuts = mList.get(arg1);
		arg0.func_name.setText(mShortcuts.getShortcutsName());
		arg0.func_icon.setImageResource(mShortcuts.getID_icon());
		arg0.setItemOnClickListener(arg1,mShortcuts);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		//View view = inflater.inflate(R.layout.onlocksecree_item, arg0);
	//	Log.d("Funtest","--onCreateViewHolder->" + "arg1-->"+ arg1);
		View view = LayoutInflater.from(arg0.getContext()).inflate(R.layout.onlocksecree_item, arg0,false);
		MyViewHolder myViewHolder = new MyViewHolder(view);
		return myViewHolder;
	}
	
	
	

}
