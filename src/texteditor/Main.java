package texteditor;

import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

class Main {
  JTextArea textarea;
  JPopupMenu popup;

  public static void main(String args[]){
    new Main();
  }

  public Main() {
    startup();
  }

  private void startup() {
    JWindow window = new JWindow();
    window.getContentPane().add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("resources/load.gif")), SwingConstants.CENTER));
    window.setSize(640, 300);
    window.addWindowListener(null);
    window.setLocationRelativeTo(null);
    window.setVisible(true);
  
    try {
      Thread.sleep(2500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      FileWriter writefile = new FileWriter("./README.txt", false);
      PrintWriter print = new PrintWriter(new BufferedWriter(writefile));

      print.println("特に書くことないからうんち\nわら");
      print.close();

      System.out.println("READMEの出力完了");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    
    window.setVisible(false);
    mainEditor();
  }
  
  private void mainEditor() {
    JFrame frame = new JFrame("Text Editor");
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    frame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("resources/icon.png")).getImage());
    
    frame.setSize(400,370);
    frame.addWindowListener(null);
    frame.setLocationRelativeTo(null);
    
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosed(WindowEvent e) {
        System.exit(0);
      }
    });
    
    JMenuBar menubar = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenuItem newfile = new JMenuItem("New...");
    JMenuItem open = new JMenuItem("Open...");
    JMenuItem save = new JMenuItem("Save...");
    JMenuItem exit = new JMenuItem("Exit...");
    JMenu edit = new JMenu("Edit");
    JMenuItem selectall = new JMenuItem("SelectAll...");
    JMenuItem copy = new JMenuItem("Copy...");
    JMenuItem cut = new JMenuItem("Cut...");
    JMenuItem paste = new JMenuItem("Paste...");
    JMenu help = new JMenu("Help");
    JMenuItem about = new JMenuItem("About...");

    textarea = new JTextArea();

    frame.setJMenuBar(menubar);
    menubar.add(file);
    file.add(newfile);
    file.add(open);
    file.add(save);
    file.add(exit);
    menubar.add(edit);
    edit.add(selectall);
    edit.add(copy);
    edit.add(cut);
    edit.add(paste);
    menubar.add(help);
    help.add(about);

    textarea.setForeground(Color.black);
    textarea.setFont(new Font("Arai", Font.ITALIC, 16));
    textarea.setEditable(true);
    textarea.setBounds(0,0, 400,370);
  
    newfile.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.setTitle("Text Editor - No File");
        textarea.setText("");
      }
    });
  
    open.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser loaddialog = new JFileChooser();
        FileNameExtensionFilter FileType_TEXT_Load = new FileNameExtensionFilter("TextFile (*.text *.txt *.yml *.java)", "text", "txt", "yml", "java");
        loaddialog.setFileFilter(FileType_TEXT_Load);
        
        int Result = loaddialog.showOpenDialog(frame);
        
        if(Result == JFileChooser.APPROVE_OPTION) {
          File LoadedFile = loaddialog.getSelectedFile();
          frame.setTitle("Text Editor - " + LoadedFile.getName());
          try{
            textarea.read(new FileReader(LoadedFile),null);
          }catch(IOException ioe){
            ioe.printStackTrace();
          }
        }
      }
    });
  
    save.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser savedialog = new JFileChooser();
        FileNameExtensionFilter FileType_TEXT_Save = new FileNameExtensionFilter("TextFile (*.text *.txt *.yml *.java)", "text", "txt", "yml", "java");
        savedialog.setFileFilter(FileType_TEXT_Save);
  
        int Result = savedialog.showSaveDialog(frame);
  
        try {
          if (Result == JFileChooser.APPROVE_OPTION) {
            FileWriter writer = new FileWriter(savedialog.getSelectedFile());
            writer.write(textarea.getText());
            writer.close();
          }
        } catch (FileNotFoundException ioe) {
          ioe.printStackTrace();
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }
      }
    });
    
    exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    selectall.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        textarea.selectAll();
      }
    });

    copy.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        textarea.copy();
      }
    });

    cut.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        textarea.cut();
      }
    });

    paste.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        textarea.paste();
      }
    });

    about.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource("resources/icon.png"));
        ImageIcon icon = new ImageIcon(originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));

        JOptionPane.showMessageDialog(
          frame,
          "Made by. Ced(Zel)",
          "About",
          JOptionPane.INFORMATION_MESSAGE,
          icon
        );
      }
    });
    
    JScrollPane scrollpane = new JScrollPane(textarea);
    scrollpane.setPreferredSize(new Dimension(100, 100));

    JPanel panel = new JPanel();
    frame.add(panel);  
    panel.add(scrollpane);
    frame.getContentPane().add(scrollpane, BorderLayout.CENTER);
    frame.setVisible(true);
  };

  public void mouseReleased(MouseEvent e){
    showPopup(e);
  }

  public void mousePressed(MouseEvent e){
    showPopup(e);
  }

  private void showPopup(MouseEvent e){
    if(e.isPopupTrigger()) {
      popup = new JPopupMenu();
      JMenuItem copy = new JMenuItem("Copy");
      popup.add(copy);
      textarea.add(popup);
      popup.show(e.getComponent(), e.getX(), e.getY());
    }
  }
}

/*
textarea.getSelectedText()
textarea.selectAll()
textarea.copy()
textarea.cut()
textarea.paste()
*/