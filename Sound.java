package project;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.*;

public class Sound{
    private Clip clipBGM;

    public void playBgm(String s){
        Thread thread = new Thread(() -> {
            clipBGM = makeClip(new File(s));

            clipBGM.loop(Clip.LOOP_CONTINUOUSLY);

        });

        thread.setDaemon(true);
        thread.start();

    }
    public void playSE(String s){
        Thread thread = new Thread(() -> {
            Clip clipSE = makeClip(new File("./sounds/"+s));
            clipSE.start();
            try{
            Thread.sleep(3000);
          }catch(Exception e){}

            clipSE.close();
        });

        thread.setDaemon(true);
        thread.start();

    }
    public void closeBGMClip(){
      clipBGM.close();
    }




    public Clip makeClip(File path) {
        try(AudioInputStream ais = AudioSystem.getAudioInputStream(path)){
            AudioFormat af = ais.getFormat();
            DataLine.Info dLine = new DataLine.Info(Clip.class,af);
            Clip c = (Clip)AudioSystem.getLine(dLine);
            c.open(ais);
            return c;
        }catch(UnsupportedAudioFileException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(LineUnavailableException e){
            e.printStackTrace();
        }
        return null;
    }
}
