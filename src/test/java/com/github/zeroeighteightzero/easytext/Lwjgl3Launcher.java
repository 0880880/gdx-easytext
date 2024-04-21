package com.github.zeroeighteightzero.easytext;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        Game main = new Game();
        return new Lwjgl3Application(main, getDefaultConfiguration(main));
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration(Game main) {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("EasyText");
        configuration.disableAudio(true);
        configuration.useVsync(true);
        configuration.setMaximized(true);

        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
        configuration.setOpenGLEmulation(Lwjgl3ApplicationConfiguration.GLEmulation.GL30, 3, 3);
        return configuration;
    }
}
