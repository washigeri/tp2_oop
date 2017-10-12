package com.malek.elouerghi;
import sdljava.SDLMain;
import sdljava.SDLException;
import sdljava.video.SDLSurface;
import sdljava.video.SDLVideo;
import sdljava.video.SDLVideoMode;

public class Init {



    public static void main(String[] args) throws SDLException, InterruptedException {
        SDLMain.init(SDLMain.SDL_INIT_VIDEO);
        SDLSurface screen = null;
        screen = SDLVideo.setVideoMode(720,480,32, SDLVideo.SDL_HWSURFACE | SDLVideo.SDL_DOUBLEBUF | SDLVideo.SDL_HWACCEL | SDLVideo.SDL_OPENGL);
        SDLVideo.wmSetCaption("Kek", null);
        Thread.sleep(20000);
        screen.freeSurface();
        SDLMain.quit();


    }

}
