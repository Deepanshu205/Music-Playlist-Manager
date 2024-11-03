import javax.swing.*;
import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

class Playlist {
    private LinkedList<Song> songs;

    public Playlist() {
        songs = new LinkedList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(String title) {
        boolean removed = songs.removeIf(song -> song.title.equalsIgnoreCase(title));
        if (!removed) {
            JOptionPane.showMessageDialog(null, "Song not found in the playlist!");
        }
    }

    public void displayPlaylist(JTextArea textArea) {
        textArea.setText(""); // Clear the previous content
        if (songs.isEmpty()) {
            textArea.append("Playlist is empty.\n");
            return;
        }
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            textArea.append((i + 1) + ". " + song + "\n");
        }
    }

    public void reorderSongs(int fromIndex, int toIndex) {
        if (fromIndex >= 0 && fromIndex < songs.size() && toIndex >= 0 && toIndex < songs.size()) {
            Song song = songs.remove(fromIndex);
            songs.add(toIndex, song);
        }
    }

    public void reversePlaylist() {
        Collections.reverse(songs);
    }

    public void searchSong(String keyword, JTextArea textArea) {
        textArea.setText("");
        boolean found = false;
        System.out.println("Searching for keyword: " + keyword); // Debugging line
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            System.out.println("Checking song: " + song.title + " by " + song.artist); // Debugging line
            if (song.title.toLowerCase().contains(keyword.toLowerCase()) || 
                song.artist.toLowerCase().contains(keyword.toLowerCase())) {
                textArea.append((i + 1) + ". " + song + "\n");
                found = true;
            }
        }
        if (!found) {
            textArea.append("No songs found for: " + keyword + "\n");
        }
    }
    
    public void savePlaylist(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Song song : songs) {
            writer.write(song.title + "," + song.artist + "," + song.duration + "\n");
        }
        writer.close();
    }

    public void loadPlaylist(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        songs.clear(); // Clear the current playlist
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 3) {
                songs.add(new Song(data[0], data[1], Double.parseDouble(data[2])));
            } else {
                JOptionPane.showMessageDialog(null, "Error loading playlist. Invalid format.");
                return;
            }
        }
        reader.close();
    }

    public void shufflePlaylist() {
        Collections.shuffle(songs, new Random());
    }

    public void sortPlaylistByTitle() {
        songs.sort((a, b) -> a.title.compareToIgnoreCase(b.title));
    }
}