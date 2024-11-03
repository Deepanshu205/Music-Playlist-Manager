class Song {
    String title;
    String artist;
    double duration; // duration in decimal minutes

    public Song(String title, String artist, double duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return title + " by " + artist + " [" + String.format("%.2f", duration) + " mins]";
    }
}
