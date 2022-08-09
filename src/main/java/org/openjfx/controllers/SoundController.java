package org.openjfx.controllers;

import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;
import org.openjfx.config.Settings;

import java.util.concurrent.atomic.AtomicBoolean;

import static javafx.scene.media.AudioClip.INDEFINITE;

public class SoundController {
    private static AtomicBoolean soundOn = new AtomicBoolean();
    private static AtomicBoolean backgroundMusic = new AtomicBoolean(true);
    public static void setSound(boolean soundOn) {
        SoundController.soundOn.set(soundOn);
    }
    public static void startMenuSound() {
        if (!soundOn.get()) {
            return;
        }
        final Task task = new Task() {
            @Override
            protected Object call() throws InterruptedException {
                AudioClip audio = new AudioClip(Settings.getSoundUrl("menu_music"));
                audio.setVolume(0.5f);
                audio.setCycleCount(INDEFINITE);
                while (soundOn.get()) {
                    if (!backgroundMusic.get()) {
                        audio.stop();
                        audio.setVolume(0);
                        System.out.println("Stop background");
                    } else if (!audio.isPlaying()) {
                        audio.play();
                        audio.setVolume(0.5f);
                        System.out.println("Resume background");
                    }
                    wait(100);
                }
                audio.stop();
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public static void doorSound() {
        if (!soundOn.get()) {
            return;
        }
        final Task task = new Task() {
            @Override
            protected Object call() {
                AudioClip audio = new AudioClip(Settings.getSoundUrl("door"));
                audio.setVolume(0.5f);
                audio.setCycleCount(1);
                audio.play();
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public static void moveSound() {
        if (!soundOn.get()) {
            return;
        }
        final Task task = new Task() {
            @Override
            protected Object call() {
                AudioClip audio = new AudioClip(Settings.getSoundUrl("move"));
                audio.setVolume(0.5f);
                audio.setCycleCount(1);
                audio.play();
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public static void victorySound() {
        if (!soundOn.get()) {
            return;
        }
        final Task task = new Task() {
            @Override
            protected Object call() throws InterruptedException {
                AudioClip audio = new AudioClip(Settings.getSoundUrl("victory"));
                setBackgroundMusic(false);
                audio.setVolume(0.5f);
                audio.setCycleCount(1);
                audio.play();
                while (audio.isPlaying()) {
                    wait(100);
                }
                System.out.println("Victory Sound end");
                setBackgroundMusic(true);
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public static void setBackgroundMusic(boolean isOn) {
        System.out.println(isOn);
        backgroundMusic.set(isOn);
    }
}
