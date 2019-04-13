package com.lsm.androidx.tool.views;

import android.media.MediaRecorder;
import java.io.File;
import java.io.IOException;

public class MyMediaRecorder {
    public boolean isRecording = false;
    private MediaRecorder mMediaRecorder;
    public File myRecAudioFile;

    public float getMaxAmplitude() {
        if (this.mMediaRecorder == null) {
            return 5.0f;
        }
        try {
            return (float) this.mMediaRecorder.getMaxAmplitude();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public File getMyRecAudioFile() {
        return this.myRecAudioFile;
    }

    public void setMyRecAudioFile(File myRecAudioFile) {
        this.myRecAudioFile = myRecAudioFile;
    }

    public boolean startRecorder() {
        if (this.myRecAudioFile == null) {
            return false;
        }
        try {
            this.mMediaRecorder = new MediaRecorder();
            this.mMediaRecorder.setAudioSource(1);
            this.mMediaRecorder.setOutputFormat(1);
            this.mMediaRecorder.setAudioEncoder(1);
            this.mMediaRecorder.setOutputFile(this.myRecAudioFile.getAbsolutePath());
            this.mMediaRecorder.prepare();
            this.mMediaRecorder.start();
            this.isRecording = true;
            return true;
        } catch (IOException exception) {
            this.mMediaRecorder.reset();
            this.mMediaRecorder.release();
            this.mMediaRecorder = null;
            this.isRecording = false;
            exception.printStackTrace();
            return false;
        } catch (IllegalStateException e) {
            stopRecording();
            e.printStackTrace();
            this.isRecording = false;
            return false;
        }
    }

    public void stopRecording() {
        if (this.mMediaRecorder != null) {
            if (this.isRecording) {
                try {
                    this.mMediaRecorder.stop();
                    this.mMediaRecorder.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.mMediaRecorder = null;
            this.isRecording = false;
        }
    }

    public void delete() {
        stopRecording();
        if (this.myRecAudioFile != null) {
            this.myRecAudioFile.delete();
            this.myRecAudioFile = null;
        }
    }
}
