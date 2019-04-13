package com.lsm.androidx.tool.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.shenfei.tools.views.FileUtil;
import com.example.shenfei.tools.views.MyMediaRecorder;
import com.example.shenfei.tools.views.SoundDiscView;
import com.example.shenfei.tools.views.World;
import com.oda_tools.R;
import java.io.File;

public class ToolSoundMeter extends BroadcastActivity {
    private static final int msgWhat = 4097;
    private static final int refreshTime = 100;
    private ImageButton btnBack;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!hasMessages(4097)) {
                ToolSoundMeter.this.volume = ToolSoundMeter.this.mRecorder.getMaxAmplitude();
                if (ToolSoundMeter.this.volume > 0.0f && ToolSoundMeter.this.volume < 1000000.0f) {
                    World.setDbCount(20.0f * ((float) Math.log10((double) ToolSoundMeter.this.volume)));
                    ToolSoundMeter.this.soundDiscView.refresh();
                    ToolSoundMeter.this.refresh();
                }
                ToolSoundMeter.this.handler.sendEmptyMessageDelayed(4097, 100);
            }
        }
    };
    private MyMediaRecorder mRecorder;
    private int max = 0;
    private int min = 0;
    private SoundDiscView soundDiscView;
    private TextView title;
    private TextView txt_center;
    private TextView txt_left;
    private TextView txt_right;
    float volume = 10000.0f;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mini_tool_soundmeter);
        this.mRecorder = new MyMediaRecorder();
        this.title = (TextView) findViewById(R.id.title);
        this.title.setText("分贝仪");
        this.btnBack = (ImageButton) findViewById(R.id.btnBack);
        this.btnBack.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ToolSoundMeter.this.finish();
            }
        });
        init();
    }

    private void init() {
        this.txt_left = (TextView) findViewById(R.id.txt_left);
        this.txt_center = (TextView) findViewById(R.id.txt_center);
        this.txt_right = (TextView) findViewById(R.id.txt_right);
    }

    private void refresh() {
        if (((int) World.dbCount) > this.max) {
            this.max = (int) World.dbCount;
        }
        this.min = (((int) World.dbCount) + this.max) / 2;
        this.txt_center.setText(((int) World.dbCount) + "");
        this.txt_right.setText(this.min + "");
        this.txt_left.setText(this.max + "");
    }

    private void startListenAudio() {
        this.handler.sendEmptyMessageDelayed(4097, 100);
    }

    public void startRecord(File fFile) {
        try {
            this.mRecorder.setMyRecAudioFile(fFile);
            if (this.mRecorder.startRecorder()) {
                startListenAudio();
            } else {
                Toast.makeText(this, "启动录音失败", 0).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "录音机已被占用或录音权限被禁止", 0).show();
            e.printStackTrace();
        }
    }

    protected void onResume() {
        super.onResume();
        this.soundDiscView = (SoundDiscView) findViewById(R.id.soundDiscView);
        File file = FileUtil.createFile("temp.amr");
        if (file != null) {
            Log.v("file", "file =" + file.getAbsolutePath());
            startRecord(file);
            return;
        }
        Toast.makeText(getApplicationContext(), "创建文件失败", 1).show();
    }

    protected void onPause() {
        super.onPause();
        this.mRecorder.delete();
        this.handler.removeMessages(4097);
    }

    protected void onDestroy() {
        this.handler.removeMessages(4097);
        this.mRecorder.delete();
        super.onDestroy();
    }
}
