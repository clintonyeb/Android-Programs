package com.example.appvilleegg.sampleApp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.applicasa.ApplicasaManager.LiGCMPushMessage;
import com.applicasa.ApplicasaManager.LiSession;
import com.applicasa.User.User;
import com.appvilleegg.R;

import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiFileCacher;
import applicasa.LiCore.Push.LiCallbackPush;
import applicasa.LiCore.communication.LiCallback.LiCallbackGetCachedFile;
import applicasa.LiCore.communication.LiQuery;
import applicasa.LiJson.LiJSONException;

public class ChatActivity extends Activity implements OnClickListener {

	 ProgressBar bar;
     List<User> users;
     Button send; 
     EditText msg;
     String receipientId;
     
     ScrollView scroller;
     LinearLayout mMsgHolder;
     WeakHashMap<String,Bitmap> bitmaps = new WeakHashMap<String,Bitmap>();
     
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		
		Bundle extras = getIntent().getExtras();
		receipientId = extras.getString("id");
		mMsgHolder = (LinearLayout)findViewById(R.id.linearLyaout_msgHolder);
		scroller = (ScrollView)findViewById(R.id.scrollContainer);
		bar = (ProgressBar)findViewById(R.id.progressBar);
		bar.setVisibility(View.VISIBLE);
		LiSession.sessionStart(this);
		
		send = (Button)findViewById(R.id.btn_sendPush);
		send.setOnClickListener(this);
		msg = (EditText)findViewById(R.id.txt_pushMSG);
		refreshMessages();
		msg.clearFocus();
		
	}
	
	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//	        Log.d(this.getClass().getName(), "back button pressed");
//	        Intent i = new Intent(this, MainActivity.class);
//			 startActivity(i); 
//			 finish();
//	    }
//	    return super.onKeyDown(keyCode, event);
//	}
	

	public void onClick(View v) {
		// TODO Auto-generated method stub
		final String text = msg.getText().toString();
		if (text == null || text.equals(""))
			return;
		LiGCMPushMessage message = new LiGCMPushMessage();
			
			message.setMessage(text);
			try {
				// add the sender ID, to reply to
				message.addTag("id", User.getCurrentUser().UserID);
			} catch (LiJSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message.setBadge(1);
//			message.setSound("egg");
			message.addRecipient(receipientId);
			message.sendPush(new LiCallbackPush() {
				
				public void onFailure(LiErrorHandler arg0) {
					// TODO Auto-generated method stub
				}
				
				public void onComplete() {
					// TODO Auto-generated method stub
					Toast.makeText(ChatActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
					//Chat chat = new Chat();
					//chat.ChatSender = User.getCurrentUser();
					//chat.ChatReciepent = new User(receipientId); // Create an empty user because when we save we want to save the recipientsId, This doesn't affect the recipient Data
					//chat.ChatText = text;
					//chat.save(null);
					
					
					mMsgHolder.removeAllViews();
					msg.setText("");
					msg.clearFocus();
					
					View v = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chat_row_right, null, false);
					addMeesage(text,User.getCurrentUser(), v);
					updateUIThread();
				}
			});
	}


	protected void addMeesage(String text, User user, View v) {
		// TODO Auto-generated method stub
		TextView sender = (TextView)v.findViewById(R.id.txt_sender);
		TextView msg = (TextView)v.findViewById(R.id.txt_msg);
		ImageView image = (ImageView)v.findViewById(R.id.img_user);
		
		
		String name = (!user.UserName.equals(""))?user.UserName :user.UserFirstName+" "+user.UserLastName;
		sender.setText(name+":");
		msg.setText(text);
		msg.setTextColor(Color.BLACK);
		sender.setTextColor(Color.BLACK);
		
		if (bitmaps.containsKey(user.UserImage))
			image.setImageBitmap(bitmaps.get(user.UserImage));
		else
			setImage(user.UserImage, image);
		
		mMsgHolder.addView(v);
		
	}
	
	protected void updateUIThread()
	{
		runOnUiThread(new Runnable() {
            public void run() {

                // Scroll to bottom
                if (scroller.getChildAt(0) != null) {
                	scroller.scrollTo(scroller.getScrollX(), scroller.getChildAt(0).getHeight());
                }
                scroller.fullScroll(View.FOCUS_DOWN);
            }
        });
	}


	protected void refreshMessages() {
		// TODO Auto-generated method stub
		
		LiQuery query = new LiQuery();
		
		// current user sent message to recipient
		//LiFilters f1 = new LiFilters(LiFieldChat.ChatReciepent, Operation.EQUAL, receipientId);
		//LiFilters f2 = new LiFilters(LiFieldChat.ChatSender, Operation.EQUAL, User.getCurrentUser().UserID);
		//LiFilters f3 = new LiFilters(f1, Condition.AND, f2);
		
		//OR
		
		// recipient sent message to current  
		//LiFilters f4 = new LiFilters(LiFieldChat.ChatReciepent, Operation.EQUAL, User.getCurrentUser().UserID);
		//LiFilters f5 = new LiFilters(LiFieldChat.ChatSender, Operation.EQUAL, receipientId);
		//LiFilters f6 = new LiFilters(f5, Condition.AND, f4);
		
		//LiFilters f7 = new LiFilters(f6, Condition.OR, f3);
		//query.Lifilters = f7;
		
		// add order by last update
		//query.addOrderBy(LiFieldChat.ChatLastUpdate, SortType.ASCESNDING);
		
		/*Chat.getArrayWithQuery(query, QueryKind.LIGHT, new LiChatGetArrayCallback() {
			
			public void onGetChatFailure(LiErrorHandler error) {
				// TODO Auto-generated method stub
				bar.setVisibility(View.INVISIBLE);
				Toast.makeText(mActivity, "Failed receiving users", Toast.LENGTH_LONG).show();
			}
			
			public void onGetChatComplete(List<Chat> items) {
				// TODO Auto-generated method stub
				for(Chat chat:items)
				{
					View v;
					User user;
					if (chat.ChatSender.UserID.equals(User.getCurrentUser().UserID))
					{
						 v = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chat_row_right, null, false);
						 user = User.getCurrentUser();
					}
					else
					{
						 v = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chat_row_left, null, false);
						 user = chat.ChatSender;
					}
					addMeesage(chat.ChatText,  user,  v);
				}
				updateUIThread();
				bar.setVisibility(View.INVISIBLE);
			}
		});*/
	}
	
	private void setImage(final String url, final ImageView image) {
		// TODO Auto-generated method stub
		
		LiFileCacher.getBitmapFromCache(url, new LiCallbackGetCachedFile() {
			
			public void onSuccessfullBitmap(Bitmap bitmap) {
				// TODO Auto-generated method stub
				if (bitmap != null)
					{
						image.setImageBitmap(bitmap);
						bitmaps.put(url, bitmap);
					}
			}
			
			public void onSuccessfull(InputStream is) {
				// TODO Auto-generated method stub
				
			}
			
			public void onFailure(LiErrorHandler error) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	protected void onPause() {
		// TODO Auto-generated method stub
		LiSession.sessionEnd(this);
		super.onPause();
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		LiSession.sessionResume(this);
		super.onResume();
	}

}
