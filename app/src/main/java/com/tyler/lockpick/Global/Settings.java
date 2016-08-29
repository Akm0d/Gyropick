package com.tyler.lockpick.Global;

/**
 * Created by tyler on 8/29/16.
 */
public class Settings {
    private static Settings instance = new Settings();
    private Boolean vibrate_on;
    private Boolean sound_on;

    public static Settings getInstance() {
        return instance;
    }

    private Settings() {
        vibrate_on = true;
        sound_on = true;
    }

    /**
     * Enable or disable the phone's vibration
     */
    public void setVibrate(Boolean vibrate_on){
        this.vibrate_on = vibrate_on;
    }

    /**
     * Enable or disable the app's sound
     */
    public void setSound(Boolean sound_on){
        this.sound_on = sound_on;
    }
}
