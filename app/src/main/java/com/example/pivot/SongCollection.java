package com.example.pivot;

public class SongCollection {

    public Song songs[] = new Song[4];

    public SongCollection() {



        Song stressedOut = new Song("S1001",
                "Stressed Out",
                "Twenty One Pilot",
                "https://p.scdn.co/mp3-preview/0e0951b811f06fea9162eb7e95e4bae4802d97af?cid=2afe87a64b0042dabf51f37318616965",
                3.37,
                R.drawable.stressed_out);


        Song redbone = new Song("S1002",
                "Redbone",
                "Childish Gambino",
                "https://p.scdn.co/mp3-preview/0167089f98ed9b52156232cc17294c7676a88dd4?cid=2afe87a64b0042dabf51f37318616965",
                5.45,
                R.drawable.redbone);


        Song doIwannaKnow = new Song("S1003",
                "Do I Wanna Know?",
                "Arctic Monkeys",
                "https://p.scdn.co/mp3-preview/73e00a0a59c897b16d0fe30df43f7aeb2997079d?cid=2afe87a64b0042dabf51f37318616965",
                4.54,
                R.drawable.do_i_wanna_know);

        Song gone = new Song("S1004",
                "Gone",
                "ROSÉ",
                "https://p.scdn.co/mp3-preview/fa0669783e655baa57d009a7060618a96d8639f1?cid=2afe87a64b0042dabf51f37318616965",
                3.95, R.drawable.gone);




        songs[0] = stressedOut;
        songs[1] = redbone;
        songs[2] = doIwannaKnow;
        songs[3] = gone;



    }

    public Song getCurrentSong(int currentSongId) {

        return songs[currentSongId];
    }

    public int searchSongById(String id) {

        for (int index = 0; index < songs.length; index++) {
            Song tempSong = songs[index];
            if (tempSong.getId().equals(id)) {
                return index;
            }

        }
        return -1;
    }

    public int getNextSong(int currentSongIndex) {
        if (currentSongIndex >= songs.length - 1) {
            return currentSongIndex;
        } else {
            return currentSongIndex + 1;
        }
    }

    public int getPrevSong(int currentSongIndex){
       if (currentSongIndex <= 0){
           return currentSongIndex;
       }
    else{

        return currentSongIndex-1;
       }
    }

}


