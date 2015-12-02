package model;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Olivier Van Bulck
 * Date: 22/11/12
 */
public class Muziek {
    private AudioStream audioStream;
    private AudioPlayer audioPlayer = AudioPlayer.player;
    public enum Status{PLAYING, STOPPED, ERROR}
    private Status status = Status.STOPPED;
    private String bestand;

    public Muziek(String bestand) {
        this.bestand = bestand;
    }

    public void start() {
        if(status != Status.ERROR) {
            File f = new File(bestand);
            if(f.exists()) { //bestaat opgegeven bestand?
                if(new MimetypesFileTypeMap().getContentType(f).equals("application/octet-stream")) { //is geluidsbestand wel een wav?
                    try{audioStream = new AudioStream(new FileInputStream(bestand));}catch(IOException IOE){status = Status.ERROR;System.out.println(IOE);}
                }
                else {
                    status = Status.ERROR;
                }
            }
            else {
                status = Status.ERROR;
            }
            audioPlayer.start(audioStream);
            status = Status.PLAYING;
        }
    }

    public void stop() {
        if(status != Status.ERROR) {
            audioPlayer.stop(audioStream);
            status = Status.STOPPED;
        }
    }

    public boolean isMuziekSpelend() {
        if(status != Status.ERROR) {
            int stream = 0;
            if(audioStream != null) {
                try {stream = audioStream.available();}catch(IOException IOE){status = Status.ERROR;}
            }
            return status == Status.PLAYING && stream != 0;
        }
        else {
            return false;
        }
    }

    public String getBestand() {
        return bestand;
    }

    public boolean hasError() {
        return status == Status.ERROR;
    }
}