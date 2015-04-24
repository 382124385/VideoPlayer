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
        /*��������Surface��ά���Լ��Ļ����������ǵȴ���Ļ����Ⱦ���潫�������͵��û���ǰ*/
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setFixedSize(176, 144);	//���÷ֱ���
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
				case R.id.play://���Բ��Ű�ť
					mediaPlayer.reset();
					mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
					mediaPlayer.setDataSource("/sdcard/"+ filename);//������Ҫ���ŵ���Ƶ
					mediaPlayer.setDisplay(surfaceView.getHolder());//����Ƶ���������SurfaceView
					mediaPlayer.prepare();
					mediaPlayer.start();
					break;

				case R.id.pause://������ͣ��ť
					if(mediaPlayer.isPlaying()){
						mediaPlayer.pause();
					}else{
						mediaPlayer.start();
					}
					break;
					
				case R.id.reset://�������²��Ű�ť
					if(!mediaPlayer.isPlaying()){
						mediaPlayer.reset();
						mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
						mediaPlayer.setDataSource("/sdcard/"+ filename);//������Ҫ���ŵ���Ƶ
						mediaPlayer.setDisplay(surfaceView.getHolder());//����Ƶ���������SurfaceView
						mediaPlayer.prepare();
						mediaPlayer.start();
					}
					mediaPlayer.seekTo(0);
					break;
					
				case R.id.stop://����ֹͣ��ť
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