package com.googlecode.mp4parser;

import com.coremedia.iso.IsoFile;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.builder.FragmentedMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class ReadWriteExample {


    public static void main(String[] args) throws IOException {
        MovieCreator mc = new MovieCreator();

        Movie video = mc.build(Channels.newChannel(ReadWriteExample.class.getResourceAsStream("/smoothstreaming/video-128h-75kbps.mp4")));
        //Movie video = mc.build(new FileInputStream("/media/scratch/qualitaetstest_cinovu_sherminfiles/abendlandinchristenhand_1039kps.mp4").getChannel());

        IsoFile out1 = new FragmentedMp4Builder().build(video);
        IsoFile out2 = new DefaultMp4Builder().build(video);


        long starttime1 = System.currentTimeMillis();
        FileChannel fc1 = new RandomAccessFile("video-128h-75kbps.fmp4", "rw").getChannel();
        fc1.position(0);
        out1.getBox(fc1);
        long size1 = fc1.size();
        fc1.truncate(fc1.position());
        fc1.close();
        System.err.println("Writing " + size1 / 1024 / 1024 + "MB took " + (System.currentTimeMillis() - starttime1));

        long starttime2 = System.currentTimeMillis();
        FileChannel fc2 = new RandomAccessFile("video-128h-75kbps.mp4", "rw").getChannel();
        fc2.position(0);
        out2.getBox(fc2);
        long size2 = fc2.size();
        fc2.truncate(fc2.position());
        fc2.close();
        System.err.println("Writing " + size2 / 1024 / 1024 + "MB took " + (System.currentTimeMillis() - starttime2));

    }

    /*
      public static void main(String[] args) throws IOException {
        MovieCreator mc = new MovieCreator();

        Movie video = mc.build(new FileInputStream("/media/scratch/qualitaetstest_cinovu_sherminfiles/abendlandinchristenhand_1039kps.mp4").getChannel());

        IsoFile out1 = new FragmentedMp4Builder().build(video);
        IsoFile out2 = new DefaultMp4Builder().build(video);


        FileChannel fc1 = new RandomAccessFile("output.fmp4", "rw").getChannel();
        fc1.position(0);
        out1.getBox(fc1);
        fc1.truncate(fc1.position());
        fc1.close();

        FileChannel fc2 = new RandomAccessFile("output.mp4", "rw").getChannel();
        fc2.position(0);
        out2.getBox(fc2);
        fc2.truncate(fc2.position());
        fc2.close();


    }


     */


}
