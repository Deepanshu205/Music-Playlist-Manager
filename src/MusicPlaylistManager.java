import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class MusicPlaylistManager extends JFrame implements ActionListener {
    private Playlist playlist;
    private JTextArea displayArea;
    private JTextField titleField, artistField, durationField, searchField;
    private JButton addButton, removeButton, displayButton, searchButton, shuffleButton, sortButton, reverseButton;
    private JMenuItem saveMenuItem, loadMenuItem;

    public MusicPlaylistManager() {
        playlist = new Playlist();

        // Window settings
        setTitle("Music Playlist Manager");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        saveMenuItem = new JMenuItem("Save Playlist");
        saveMenuItem.addActionListener(this);
        loadMenuItem = new JMenuItem("Load Playlist");
        loadMenuItem.addActionListener(this);
        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Text area for displaying the playlist
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for user input and buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(10, 2, 10, 10));
        controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Adds padding

        controlPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        controlPanel.add(titleField);

        controlPanel.add(new JLabel("Artist:"));
        artistField = new JTextField();
        controlPanel.add(artistField);

        controlPanel.add(new JLabel("Duration (minutes.seconds):"));
        durationField = new JTextField();
        controlPanel.add(durationField);

        // Buttons
        addButton = new JButton("Add Song");
        addButton.addActionListener(this);
        controlPanel.add(addButton);

        removeButton = new JButton("Remove Song");
        removeButton.addActionListener(this);
        controlPanel.add(removeButton);

        displayButton = new JButton("Display Playlist");
        displayButton.addActionListener(this);
        controlPanel.add(displayButton);

        searchButton = new JButton("Search Song");
        searchButton.addActionListener(this);
        controlPanel.add(searchButton);

        searchField = new JTextField();
        controlPanel.add(searchField);

        shuffleButton = new JButton("Shuffle Playlist");
        shuffleButton.addActionListener(this);
        controlPanel.add(shuffleButton);

        sortButton = new JButton("Sort by Title");
        sortButton.addActionListener(this);
        controlPanel.add(sortButton);

        reverseButton = new JButton("Reverse Playlist");
        reverseButton.addActionListener(this);
        controlPanel.add(reverseButton);

        // Add the control panel to the bottom of the window
        add(controlPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == addButton) {
                String title = titleField.getText().trim();
                String artist = artistField.getText().trim();
                double duration = Double.parseDouble(durationField.getText().trim());
                if (title.isEmpty() || artist.isEmpty() || duration <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter valid details!");
                } else {
                    playlist.addSong(new Song(title, artist, duration));
                    titleField.setText("");
                    artistField.setText("");
                    durationField.setText("");
                    JOptionPane.showMessageDialog(this, "Song added successfully!");
                }
            } else if (e.getSource() == removeButton) {
                String title = titleField.getText().trim();
                if (title.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Enter a song title to remove!");
                } else {
                    playlist.removeSong(title);
                    JOptionPane.showMessageDialog(this, "Song removed!");
                }
            } else if (e.getSource() == displayButton) {
                playlist.displayPlaylist(displayArea);
            } else if (e.getSource() == searchButton) {
                String keyword = searchField.getText().trim();
                if (keyword.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Enter title/artist to search!");
                } else {
                    playlist.searchSong(keyword, displayArea);
                }
            } else if (e.getSource() == shuffleButton) {
                playlist.shufflePlaylist();
                playlist.displayPlaylist(displayArea);
            } else if (e.getSource() == sortButton) {
                playlist.sortPlaylistByTitle();
                playlist.displayPlaylist(displayArea);
            } else if (e.getSource() == reverseButton) {
                playlist.reversePlaylist();
                playlist.displayPlaylist(displayArea);
            } else if (e.getSource() == saveMenuItem) {
                playlist.savePlaylist("playlist.txt");
                JOptionPane.showMessageDialog(this, "Playlist saved to file!");
            } else if (e.getSource() == loadMenuItem) {
                playlist.loadPlaylist("playlist.txt");
                playlist.displayPlaylist(displayArea);
                JOptionPane.showMessageDialog(this, "Playlist loaded from file!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Duration must be a valid decimal number!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "File error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new MusicPlaylistManager();
    }
}
