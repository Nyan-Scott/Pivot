package com.example.pivot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlaySongActivity extends AppCompatActivity {

    public String title = "";
    public String artist = "";
    public String fileLink = "";
    public int drawable;
    public int currentIndex = -1;

    public MediaPlayer player = new MediaPlayer();
    public Button btnPlayPause = null;
    public SongCollection songCollection = new SongCollection();
    SeekBar seekBar, seekBarVolume;
    AudioManager audioManager;
    Handler handler = new Handler();


    SongCollection originalSongCollection = new SongCollection();
    List<Song> shuffleList = Arrays.asList(songCollection.songs);
    TextView currentTime;
    TextView totalTime;


    Button btnRepeat;
    Boolean repeatFlag = false;

    Button btnShuffle;
    Boolean shuffleFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        btnPlayPause = findViewById(R.id.btnPlayPause);
        Bundle songData = this.getIntent().getExtras();
        currentIndex = songData.getInt("index");
        Log.d("temasek", "retrieved Position is : " + currentIndex);
        displaySongBasedOnIndex();




        playSong(fileLink);
        currentTime = findViewById(R.id.currentTimer);
        totalTime = findViewById(R.id.totalTimer);

        seekBar = findViewById(R.id.seekBar1001);
        seekBarVolume = findViewById(R.id.volumeBar);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //get Max Volume

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        //get Current Volume

        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBarVolume.setMax(maxVolume);
        seekBarVolume.setProgress(curVolume);
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                if (player != null && player.isPlaying()) {
                    player.seekTo(seekBar.getProgress());

                }

            }


        });

        btnRepeat = findViewById(R.id.btnRepeat);

        btnShuffle = findViewById(R.id.btnShuffle);


    }

    Runnable p_bar = new Runnable() {
        @Override
        public void run() {
            if (player != null && player.isPlaying()) {
                seekBar.setProgress(player.getCurrentPosition());

            }
            handler.postDelayed(this, 1000);
        }


    };

    public void displaySongBasedOnIndex() {
        Song song = songCollection.getCurrentSong(currentIndex);
        title = song.getTitle();
        artist = song.getArtists();
        fileLink = song.getFileLink();
        drawable = song.getDrawable();

        TextView txtTitle = findViewById(R.id.txtSongTitle);
        txtTitle.setText(title);

        TextView txtArtist = findViewById(R.id.txtArtist);
        txtArtist.setText(artist);

        ImageView iCoverArt = findViewById(R.id.imgCoverArt);
        iCoverArt.setImageResource(drawable);


    }

    public void playSong(String songUrl) {
        try {
            player.reset();
            player.setDataSource(songUrl);
            player.prepare();
            player.start();
            gracefullyStopsWhenMusicEnds();

            btnPlayPause.setBackgroundResource(R.drawable.pause_btn);
            setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void playOrPauseMusic(View view) {
        if (player.isPlaying()) {
            player.pause();
            btnPlayPause.setBackgroundResource(R.drawable.pause_btn);
            seekBar.setMax(player.getDuration());
            handler.removeCallbacks(p_bar);
            handler.postDelayed(p_bar, 1000);


        } else {
            player.start();
            btnPlayPause.setBackgroundResource(R.drawable.play_btn);
        }
    }

    private void gracefullyStopsWhenMusicEnds() {
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // Toast.makeText(getBaseContext(), " The song has ended and the onComplete Listener is activated\n + The Button text is changed to 'PLAY' ", Toast.LENGTH_SHORT).show();
                if (repeatFlag) {
                    playOrPauseMusic(null);
                } else {
                    btnPlayPause.setBackgroundResource(R.drawable.play_btn);
                }

            }
        });
    }


    public void playNext(View view) {

        currentIndex = songCollection.getNextSong(currentIndex);
        //Toast.makeText(this, "After clicking playNext,\n the current index of this song\n" + "in the SongCollection array is now :" + currentIndex, Toast.LENGTH_LONG).show();

        Log.d("temasek", "After playNext, the index is now :" + currentIndex);
        displaySongBasedOnIndex();
        playSong(fileLink);
    }

    public void playPrevious(View view) {
        currentIndex = songCollection.getPrevSong(currentIndex);
        // Toast.makeText(this, "After clicking playPrevious, \n the current index of this song\n" + "in the SongCollection array is now :" + currentIndex, Toast.LENGTH_LONG).show();
        Log.d("temasek", "After playPrevious, the index is now : " + currentIndex);
        displaySongBasedOnIndex();
        playSong(fileLink);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (player != null) {
            handler.removeCallbacks(p_bar);
            player.stop();
            player.release();
        }

    }


    public void repeatSong(View view) {
        if (repeatFlag) {
            btnRepeat.setBackgroundResource(R.drawable.repeat_off);
        } else {
            btnRepeat.setBackgroundResource(R.drawable.repeat_on);
        }
        repeatFlag = !repeatFlag;
    }


    public void shuffleSong(View view) {
        if (shuffleFlag) {
            btnShuffle.setBackgroundResource(R.drawable.shuffle_off);
            songCollection = new SongCollection();
        } else {
            btnShuffle.setBackgroundResource(R.drawable.shuffle_on);
            Collections.shuffle(shuffleList);
            shuffleList.toArray(songCollection.songs);
        }
        shuffleFlag = !shuffleFlag;
    }




    public  String createTimerLabel (int duration){
        String timerLabel = "";
        int min = duration/ 1000/60;
        int sec = duration/1000%60;

        timerLabel +=min + " : ";

        if (sec<10) timerLabel += "0";
        timerLabel += sec;
        return timerLabel;

    }




}