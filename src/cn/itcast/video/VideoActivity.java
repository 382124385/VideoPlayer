package cn.itcast.video;
//Download by http://www.codefans.net
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class VideoActivity extends Activity {
	private static final String TAG = "VideoActivity";
    private EditText filenameText;
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButtonClickListener listener = new ButtonClickListener();
        
        ImageButton playButton =(ImageButton) this.findViewById(R.id.play);
        playButton.setOnClickListener(listener);
        
        ImageButton pauseButton =(ImageButton) this.findViewById(R.id.pause);
        pauseButton.setOnClickListener(listener);
        
        ImageButton resetButton =(ImageButton) this.findViewById(R.id.reset);
        resetButton.setOnClickListener(listener);
        
        ImageButton stopButton =(ImageButton) this.findViewById(R.id.stop);
        stopButton.setOnClickListener(listener);
        
        filenameText =(EditText) this.findViewById(R.id.filename);
        surfaceView =(SurfaceView) this.findViewById(R.id.surfaceView);
        /*下面设置Surface不维护自己的缓冲区，而是等待屏幕的渲染引擎将内容推送到用户面前*/
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setFixedSize(176, 144);	//设置分辨率
        mediaPlayer = new MediaPlayer();
    }
    
    @Override
	protected void onDestroy() {
		if(mediaPlayer.isPlaying()) mediaPlayer.stop();
		mediaPlayer.release();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if(mediaPlayer.isPlaying()) mediaPlayer.pause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		if(!mediaPlayer.isPlaying()) mediaPlayer.start();
		super.onResume();
	}

	private class ButtonClickListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			try {
				String filename = filenameText.getText().toString();
				switch (v.getId()) {
				case R.id.play://来自播放按钮
					mediaPlayer.reset();
					mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
					mediaPlayer.setDataSource("/sdcard/"+ filename);//设置需要播放的视频
					mediaPlayer.setDisplay(surfaceView.getHolder());//把视频画面输出到SurfaceView
					mediaPlayer.prepare();
					mediaPlayer.start();
					break;

				case R.id.pause://来自暂停按钮
					if(mediaPlayer.isPlaying()){
						mediaPlayer.pause();
					}else{
						mediaPlayer.start();
					}
					break;
					
				case R.id.reset://来自重新播放按钮
					if(!mediaPlayer.isPlaying()){
						mediaPlayer.reset();
						mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
						mediaPlayer.setDataSource("/sdcard/"+ filename);//设置需要播放的视频
						mediaPlayer.setDisplay(surfaceView.getHolder());//把视频画面输出到SurfaceView
						mediaPlayer.prepare();
						mediaPlayer.start();
					}
					mediaPlayer.seekTo(0);
					break;
					
				case R.id.stop://来自停止按钮
					if(mediaPlayer.isPlaying()){
						mediaPlayer.stop();
					}
					break;	
					
				}
			} catch (Exception e) {
				Log.e(TAG, e.toString());
			}
		}
    }
}