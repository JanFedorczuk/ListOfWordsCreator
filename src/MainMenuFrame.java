package list.of.words.creator;

import javafx.event.EventHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MainMenuFrame extends JFrame
{
    private Core core;

    private JPanel buttonPanel;
    private JPanel textPanePanel;
    private MainMenuTextPane jTextPane;

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenWidth = screenSize.width;

    private int isetsValue;

    private int textPaneWidth;
    private int textPaneHeight;

    private int buttonWidth;
    private int buttonHeight;
    private int buttonFontSize;

    private JButton loadFileButton = new JButton("Załaduj plik");
    private JButton createConfigurationButton = new JButton("Stwórz konfigurację");
    private JButton loadFilesButton = new JButton("Załaduj pliki");
    private JButton createFileButton = new JButton("Stwórz plik");
    private JButton setDefaultFileButton = new JButton("Ustaw plik startowy");
    private JButton modifyConfigurationButton = new JButton("Modyfikuj konfigurację");

    public configCreator configCreator = new configCreator();
    public fileUploader fileUploader = new fileUploader();
    public filesUploader filesUploader = new filesUploader();
    public fileCreator fileCreator = new fileCreator();
    public configModifier configModifier = new configModifier();
    public exitProgram exitProgram = new exitProgram();
    public defaultFileSetter defaultFileSetter = new defaultFileSetter();

    MainMenuFrame mainMenuFrame = this;
    private MainMenuBar mainMenuBar;
    public static String currentDirectoryPath;

    public MainMenuFrame(Core core)
    {
        setOptions();
        this.core = core;
        JMenuBar menuBar = new JMenuBar();
        mainMenuBar = new MainMenuBar(mainMenuFrame, screenSize, buttonFontSize, buttonWidth, buttonHeight);
        menuBar.add(mainMenuBar);
        this.setJMenuBar(menuBar);

        GridBagConstraints gbc = new GridBagConstraints();
        buttonPanel = new JPanel(new GridBagLayout());
        this.setLayout(new GridBagLayout());
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1;
        gbc.weightx = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        buttonPanel.add(loadFileButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, isetsValue, 0, 0);
        buttonPanel.add(createConfigurationButton, gbc);


        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, isetsValue, 0, 0);
        buttonPanel.add(loadFilesButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(isetsValue, 0, 0, 0);
        buttonPanel.add(createFileButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(isetsValue, isetsValue, 0, 0);
        buttonPanel.add(setDefaultFileButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(isetsValue, isetsValue , 0, 0);
        buttonPanel.add(modifyConfigurationButton, gbc);

        gbc.insets = new Insets(isetsValue, isetsValue, 0, isetsValue);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(buttonPanel, gbc);
        
        this.addButtonsListeners();
        
        textPanePanel = new JPanel(new GridBagLayout());
        jTextPane = new MainMenuTextPane();
        jTextPane.setEnabled(false);
        JScrollPane scroller = new JScrollPane(jTextPane);

        jTextPane.setPreferredSize(new Dimension(textPaneWidth,textPaneHeight));
        scroller.setPreferredSize(new Dimension(textPaneWidth,textPaneHeight));
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(isetsValue, 0, isetsValue, 0);
        textPanePanel.add(scroller, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(textPanePanel, gbc);
        pack();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(screenSize.width / 2 - this.getBounds().getSize().width / 2,
                screenSize.height / 2 - this.getBounds().getSize().height);
        addActionListeners();

        this.setVisible(true);
    }

    private void setButtonSizes()
    {
        createConfigurationButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        loadFileButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        loadFilesButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        createFileButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        modifyConfigurationButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        setDefaultFileButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        createConfigurationButton.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));
        loadFileButton.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));
        loadFilesButton.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));
        createFileButton.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));
        modifyConfigurationButton.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));
        setDefaultFileButton.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));
    }

    private void setButtonsDisabled()
    {
        loadFileButton.setEnabled(false);
        loadFilesButton.setEnabled(false);
        createFileButton.setEnabled(false);
        modifyConfigurationButton.setEnabled(false);
    }

    private void addButtonsListeners()
    {
        loadFileButton.setFocusable(true);
        createConfigurationButton.setFocusable(true);
        loadFilesButton.setFocusable(true);
        createFileButton.setFocusable(true);
        setDefaultFileButton.setFocusable(true);
        modifyConfigurationButton.setFocusable(true);

        loadFileButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
            }

            @Override
            public void keyReleased(KeyEvent e)
            {

                if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
                {
                    createConfigurationButton.requestFocus();
                }

                if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
                {
                    createFileButton.requestFocus();
                }

                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    uploadFile();
                }
            }
        });
        createConfigurationButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
                {
                    loadFileButton.requestFocus();
                }

                if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
                {
                    loadFilesButton.requestFocus();
                }

                if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
                {
                    setDefaultFileButton.requestFocus();
                }

                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    createConfiguration();
                }
            }
        });
        loadFilesButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
                {
                    createConfigurationButton.requestFocus();
                }


                if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
                {
                    modifyConfigurationButton.requestFocus();
                }

                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    uploadFiles();
                }
            }
        });
        createFileButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
            }

            @Override
            public void keyReleased(KeyEvent e)
            {

                if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
                {
                    setDefaultFileButton.requestFocus();
                }

                if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
                {
                    loadFileButton.requestFocus();
                }

                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    createFile();
                }
            }
        });
        setDefaultFileButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
                {
                    createFileButton.requestFocus();
                }

                if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
                {
                    modifyConfigurationButton.requestFocus();
                }

                if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
                {
                    createConfigurationButton.requestFocus();
                }

                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    setDefaultFile();
                }
            }
        });
        modifyConfigurationButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
                {
                    setDefaultFileButton.requestFocus();
                }

                if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
                {
                    loadFilesButton.requestFocus();
                }

                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    core.modifyConfiguration(jTextPane);
                }
            }
        });
    }

    private void setElementsSize()
    {
        if ((screenSize.width / 133) < 12)
        {
            screenWidth = 1600;
        }

        isetsValue     = (screenWidth/ 160);
        textPaneWidth  = (int) (screenWidth / 4.57);
        textPaneHeight = (screenWidth / 16);

        buttonWidth    = (int) (screenWidth / 9.3);
        buttonHeight   = (int) (screenWidth/ 61.5);
        buttonFontSize = (screenWidth / 133);

    }

    private void setOptions()
    {
        setElementsSize();
        this.setButtonSizes();
        this.setTitle("Kreator list słówek");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setButtonsDisabled();
    }
    
    public class configCreator implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            try
            {
                JFileChooser fileChooser = new JFileChooser(currentDirectoryPath);
                int result = fileChooser.showSaveDialog(buttonPanel);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    if(fileChooser.getSelectedFile().exists())
                    {
                        if(core.getFileExtension(fileChooser.getSelectedFile()).equals("txt")
                                || core.getFileExtension(fileChooser.getSelectedFile()).equals("srt"))
                        {
                            String configString = fileChooser.getSelectedFile().getAbsolutePath();
                            Path configFile = Paths.get(configString);
                            ArrayList<String> copyArrayList = new ArrayList<>();
                            copyArrayList.addAll(core.getConfigWords());
                            try
                            {
                                core.fillConfigWords(configFile, jTextPane);
                            }
                            catch(Exception e)
                            {
                                jTextPane.printMessageThatErrorOccurredDuringCreatingConfiguration();
                            }
                            if (core.getUnFilteredWords().isEmpty())
                            {
                                core.setButtonEnabled(loadFileButton);
                                core.setButtonEnabled(loadFilesButton);
                                core.setButtonEnabled(setDefaultFileButton);
                                mainMenuBar.setFileItemsUploadFileAndFilesEnabled();
                                jTextPane.printMessageThatConfigurationHasBeenCreated();
                                core.addConfigPath(configString);
                            }
                            else if (!core.getUnFilteredWords().isEmpty())
                            {
                                if ((core.getConfigWords().containsAll(core.getUnFilteredWords())) &&
                                        (core.getUnFilteredWords().containsAll(core.getConfigWords())))
                                {
                                    jTextPane.printMessageThatDataInFilesAreTheSame();
                                    core.getConfigWords().clear();
                                    if (!copyArrayList.isEmpty())
                                    {
                                        core.getConfigWords().addAll(copyArrayList);
                                    }
                                }
                                else
                                {
                                    if ((core.getConfigWords().containsAll(core.getUnFilteredWords())) &&
                                            (!core.getUnFilteredWords().isEmpty()))
                                    {
                                        jTextPane.printMessageThatConfigFileCannotBeASupersetOfUploadedFile();
                                        core.getConfigWords().clear();
                                        if (!copyArrayList.isEmpty())
                                        {
                                            core.getConfigWords().addAll(copyArrayList);
                                        }
                                    }
                                    else
                                    {
                                        core.fillFilteredWords(core.getUnFilteredWords(), jTextPane);
                                        core.setButtonEnabled(loadFileButton);
                                        core.setButtonEnabled(loadFilesButton);
                                        core.setButtonEnabled(setDefaultFileButton);
                                        mainMenuBar.setFileItemsUploadFileAndFilesEnabled();
                                        core.addConfigPath(configString);
                                        jTextPane.printMessageThatConfigurationHasBeenCreated();
                                    }
                                }
                            }

                        }
                        else
                        {
                            jTextPane.printMessageThatFileHasBadExtension();
                        }
                    }
                    else
                    {
                        jTextPane.printMessageThatFileDoesNotExist();
                    }
                }
                else if (result == JFileChooser.CANCEL_OPTION)
                {
                }
            }
            catch (Exception e)
            {
            }
        }
    }

    private void createConfiguration()
    {
        try
        {
            JFileChooser fileChooser = new JFileChooser(currentDirectoryPath);
            int result = fileChooser.showSaveDialog(buttonPanel);
            if (result == JFileChooser.APPROVE_OPTION)
            {
                if(fileChooser.getSelectedFile().exists())
                {
                    if(core.getFileExtension(fileChooser.getSelectedFile()).equals("txt")
                            || core.getFileExtension(fileChooser.getSelectedFile()).equals("srt"))
                    {
                        String configString = fileChooser.getSelectedFile().getAbsolutePath();
                        Path configFile = Paths.get(configString);
                        ArrayList<String> copyArrayList = new ArrayList<>();
                        copyArrayList.addAll(core.getConfigWords());
                        try
                        {
                            core.fillConfigWords(configFile, jTextPane);
                        }
                        catch(Exception e)
                        {
                            jTextPane.printMessageThatErrorOccurredDuringCreatingConfiguration();
                        }
                        if (core.getUnFilteredWords().isEmpty())
                        {
                            core.setButtonEnabled(loadFileButton);
                            core.setButtonEnabled(loadFilesButton);
                            core.setButtonEnabled(setDefaultFileButton);
                            mainMenuBar.setFileItemsUploadFileAndFilesEnabled();
                            jTextPane.printMessageThatConfigurationHasBeenCreated();
                            core.addConfigPath(configString);
                        }
                        else if (!core.getUnFilteredWords().isEmpty())
                        {
                            if ((core.getConfigWords().containsAll(core.getUnFilteredWords())) &&
                                    (core.getUnFilteredWords().containsAll(core.getConfigWords())))
                            {
                                jTextPane.printMessageThatDataInFilesAreTheSame();
                                core.getConfigWords().clear();
                                if (!copyArrayList.isEmpty())
                                {
                                    core.getConfigWords().addAll(copyArrayList);
                                }
                            }
                            else
                            {
                                if ((core.getConfigWords().containsAll(core.getUnFilteredWords())) &&
                                        (!core.getUnFilteredWords().isEmpty()))
                                {
                                    jTextPane.printMessageThatConfigFileCannotBeASupersetOfUploadedFile();
                                    core.getConfigWords().clear();
                                    if (!copyArrayList.isEmpty())
                                    {
                                        core.getConfigWords().addAll(copyArrayList);
                                    }
                                }
                                else
                                {
                                    core.fillFilteredWords(core.getUnFilteredWords(), jTextPane);
                                    core.setButtonEnabled(loadFileButton);
                                    core.setButtonEnabled(loadFilesButton);
                                    core.setButtonEnabled(setDefaultFileButton);
                                    mainMenuBar.setFileItemsUploadFileAndFilesEnabled();
                                    core.addConfigPath(configString);
                                    jTextPane.printMessageThatConfigurationHasBeenCreated();
                                }
                            }
                        }

                    }
                    else
                    {
                        jTextPane.printMessageThatFileHasBadExtension();
                    }
                }
                else
                {
                    jTextPane.printMessageThatFileDoesNotExist();
                }
            }
            else if (result == JFileChooser.CANCEL_OPTION)
            {
            }
        }
        catch (Exception e)
        {
        }
    }

    public class fileUploader implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            try
            {
                JFileChooser fileChooser = new JFileChooser(currentDirectoryPath);
                int result = fileChooser.showSaveDialog(buttonPanel);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    if (fileChooser.getSelectedFile().exists())
                    {
                        if (core.getFileExtension(fileChooser.getSelectedFile()).equals("txt") ||
                                core.getFileExtension(fileChooser.getSelectedFile()).equals("srt") &&
                                        fileChooser.getSelectedFile().exists())
                        {
                            String fileString = fileChooser.getSelectedFile().getAbsolutePath();
                            Path filePath = Paths.get(fileString);
                            ArrayList<String> copyArrayList = new ArrayList<>();
                            copyArrayList.addAll(core.getUnFilteredWords());
                            core.fillUnFilteredWords(filePath, jTextPane);
                            if (!core.getUnFilteredWords().isEmpty())
                            {
                                if ((core.getConfigWords().containsAll(core.getUnFilteredWords())) &&
                                        (core.getUnFilteredWords().containsAll(core.getConfigWords())))
                                {
                                    jTextPane.printMessageThatDataInFilesAreTheSame();
                                    if(!core.getUnFilteredWords().isEmpty())
                                    {
                                        core.getUnFilteredWords().clear();
                                        core.getUnFilteredWords().addAll(copyArrayList);
                                    }
                                    else
                                    {
                                        core.getUnFilteredWords().clear();
                                    }
                                }
                                else
                                {
                                    if (core.getConfigWords().containsAll(core.getUnFilteredWords()))
                                    {
                                        jTextPane.printMessageThatUploadedFileCannotBeASubsetOfConfigFile();
                                        if(!core.getUnFilteredWords().isEmpty())
                                        {
                                            core.getUnFilteredWords().clear();
                                            core.getUnFilteredWords().addAll(copyArrayList);
                                        }
                                        else
                                        {
                                            core.getUnFilteredWords().clear();
                                        }
                                    }
                                    else
                                    {
                                        if (core.fillFilteredWords(core.getUnFilteredWords(), jTextPane))
                                        {
                                            core.setButtonEnabled(createFileButton);
                                            core.setButtonEnabled(modifyConfigurationButton);
                                            mainMenuBar.setFilesItemsCreateAndModify();
                                            jTextPane.printMessageThatFileHasBeenUploaded();
                                        }
                                    }
                                }
                            }
                            else
                            {
                                jTextPane.printMessageThatThatFileIsEmpty();
                                core.getUnFilteredWords().clear();
                                core.getUnFilteredWords().addAll(copyArrayList);
                            }
                        }
                        else
                        {
                            jTextPane.printMessageThatFileHasBadExtension();
                        }
                    }
                    else
                    {
                        jTextPane.printMessageThatFileDoesNotExist();
                    }
                }
                else if (result == JFileChooser.CANCEL_OPTION)
                {
                }
            }
            catch (Exception e)
            {
            }
        }
    }

    private void uploadFile()
    {
        try
        {
            JFileChooser fileChooser = new JFileChooser(currentDirectoryPath);
            int result = fileChooser.showSaveDialog(buttonPanel);
            if (result == JFileChooser.APPROVE_OPTION)
            {
                if (fileChooser.getSelectedFile().exists())
                {
                    if (core.getFileExtension(fileChooser.getSelectedFile()).equals("txt") ||
                            core.getFileExtension(fileChooser.getSelectedFile()).equals("srt") &&
                                    fileChooser.getSelectedFile().exists())
                    {
                        String fileString = fileChooser.getSelectedFile().getAbsolutePath();
                        Path filePath = Paths.get(fileString);
                        ArrayList<String> copyArrayList = new ArrayList<>();
                        copyArrayList.addAll(core.getUnFilteredWords());
                        core.fillUnFilteredWords(filePath, jTextPane);
                        if (!core.getUnFilteredWords().isEmpty())
                        {
                            if ((core.getConfigWords().containsAll(core.getUnFilteredWords())) &&
                                    (core.getUnFilteredWords().containsAll(core.getConfigWords())))
                            {
                                jTextPane.printMessageThatDataInFilesAreTheSame();
                                if(!core.getUnFilteredWords().isEmpty())
                                {
                                    core.getUnFilteredWords().clear();
                                    core.getUnFilteredWords().addAll(copyArrayList);
                                }
                                else
                                {
                                    core.getUnFilteredWords().clear();
                                }
                            }
                            else
                            {
                                if (core.getConfigWords().containsAll(core.getUnFilteredWords()))
                                {
                                    jTextPane.printMessageThatUploadedFileCannotBeASubsetOfConfigFile();
                                    if(!core.getUnFilteredWords().isEmpty())
                                    {
                                        core.getUnFilteredWords().clear();
                                        core.getUnFilteredWords().addAll(copyArrayList);
                                    }
                                    else
                                    {
                                        core.getUnFilteredWords().clear();
                                    }
                                }
                                else
                                {
                                    if (core.fillFilteredWords(core.getUnFilteredWords(), jTextPane))
                                    {
                                        core.setButtonEnabled(createFileButton);
                                        core.setButtonEnabled(modifyConfigurationButton);
                                        mainMenuBar.setFilesItemsCreateAndModify();
                                        jTextPane.printMessageThatFileHasBeenUploaded();
                                    }
                                }
                            }
                        }
                        else
                        {
                            jTextPane.printMessageThatThatFileIsEmpty();
                            core.getUnFilteredWords().clear();
                            core.getUnFilteredWords().addAll(copyArrayList);
                        }
                    }
                    else
                    {
                        jTextPane.printMessageThatFileHasBadExtension();
                    }
                }
                else
                {
                    jTextPane.printMessageThatFileDoesNotExist();
                }
            }
            else if (result == JFileChooser.CANCEL_OPTION)
            {
            }
        }
        catch (Exception e)
        {
        }
    }

    public class filesUploader implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            try
            {
                JFileChooser fileChooser = new JFileChooser(currentDirectoryPath);
                fileChooser.setMultiSelectionEnabled(true);
                int result = fileChooser.showSaveDialog(buttonPanel);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    File[] filesArray = fileChooser.getSelectedFiles();
                    ArrayList<File> filteredWords = new ArrayList<>();

                    for (File file : filesArray)
                    {
                        filteredWords.add(file);
                    }
                    checkIfUploadedFilesExist(filteredWords);
                }
                else if (result == JFileChooser.CANCEL_OPTION)
                {
                }
            }
            catch (Exception e)
            {
            }
        }
    }

    private void uploadFiles()
    {
        try
        {
            JFileChooser fileChooser = new JFileChooser(currentDirectoryPath);
            fileChooser.setMultiSelectionEnabled(true);
            int result = fileChooser.showSaveDialog(buttonPanel);
            if (result == JFileChooser.APPROVE_OPTION)
            {
                File[] filesArray = fileChooser.getSelectedFiles();
                ArrayList<File> filteredWords = new ArrayList<>();

                for (File file : filesArray)
                {
                    filteredWords.add(file);
                }
                checkIfUploadedFilesExist(filteredWords);
            }
            else if (result == JFileChooser.CANCEL_OPTION)
            {
            }
        }
        catch (Exception e)
        {
        }
    }

    public class fileCreator implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            try
            {
                JFileChooser fileChooser = new JFileChooser(currentDirectoryPath);
                int result = fileChooser.showSaveDialog(buttonPanel);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileChooser.getSelectedFile();
                    if (!fileChooser.getSelectedFile().exists()) {
                        core.createListOfWords(file, jTextPane);
                        jTextPane.printMessageThatFileHasBeenCreated();
                    } else {
                        String[] options = new String[]{"Modify", "Replace", "Cancel"};
                        int dialogResult = JOptionPane.showOptionDialog
                                (null, "Plik o podanej nazwie już istnieje, proszę wybrać jedną z opcji.",
                                        "Zapis pliku", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                        null, options, options);
                        if (dialogResult == 0) {
                            core.modifyListOfWords(file, jTextPane);
                        } else if (dialogResult == 1) {
                            File plik = fileChooser.getSelectedFile();
                            plik.delete();
                            core.createListOfWords(file, jTextPane);
                            jTextPane.printMessageThatFileHasBeenReplaced();
                        } else {
                            jTextPane.printMessageThatOperationHasBeenCanceled();
                        }
                    }
                }
                else if (result == JFileChooser.CANCEL_OPTION)
                {
                }
            }
            catch (Exception e)
            {
            }
        }
    }

    private void createFile()
    {
        try
        {
            JFileChooser fileChooser = new JFileChooser(currentDirectoryPath);
            int result = fileChooser.showSaveDialog(buttonPanel);
            if (result == JFileChooser.APPROVE_OPTION)
            {
                File file = fileChooser.getSelectedFile();
                if (!fileChooser.getSelectedFile().exists()) {
                    core.createListOfWords(file, jTextPane);
                    jTextPane.printMessageThatFileHasBeenCreated();
                } else {
                    String[] options = new String[]{"Modify", "Replace", "Cancel"};
                    int dialogResult = JOptionPane.showOptionDialog
                            (null, "Plik o podanej nazwie już istnieje, proszę wybrać jedną z opcji.",
                                    "Zapis pliku", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                    null, options, options);
                    if (dialogResult == 0) {
                        core.modifyListOfWords(file, jTextPane);
                    } else if (dialogResult == 1) {
                        File plik = fileChooser.getSelectedFile();
                        plik.delete();
                        core.createListOfWords(file, jTextPane);
                        jTextPane.printMessageThatFileHasBeenReplaced();
                    } else {
                        jTextPane.printMessageThatOperationHasBeenCanceled();
                    }
                }
            }
            else if (result == JFileChooser.CANCEL_OPTION)
            {
            }
        }
        catch (Exception e)
        {
        }
    }

    public class configModifier implements ActionListener
    {
        public void actionPerformed(ActionEvent event) {
            core.modifyConfiguration(jTextPane);
        }
    }

    public class defaultFileSetter implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            SettingsFrame settingsFrame = new SettingsFrame(screenSize, buttonFontSize, buttonWidth, buttonHeight);
        }
    }

    private void setDefaultFile()
    {
        SettingsFrame settingsFrame = new SettingsFrame(screenSize, buttonFontSize, buttonWidth, buttonHeight);
    }

    public class exitProgram implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            System.exit(0);
        }
    }

    private void addActionListeners()
    {
        loadFileButton.addActionListener(fileUploader);
        createConfigurationButton.addActionListener(configCreator);
        loadFilesButton.addActionListener(filesUploader);
        createFileButton.addActionListener(fileCreator);
        modifyConfigurationButton.addActionListener(configModifier);
        setDefaultFileButton.addActionListener(defaultFileSetter);
    }

    private void checkIfUploadedFilesExist(ArrayList<File> filesArrayList)
    {
        try
        {
            ArrayList<File> temporaryFilesArrayList = temporaryFilesArrayList = new ArrayList<>();
            temporaryFilesArrayList.addAll(filesArrayList);
            ArrayList<Boolean> booleanArrayList = new ArrayList<>();

            for (File file : filesArrayList)
            {
                if (file.exists())
                {
                    booleanArrayList.add(true);
                }
                else
                {
                    booleanArrayList.add(false);
                    temporaryFilesArrayList.remove(file);
                }
            }
            filesArrayList.clear();
            filesArrayList.addAll(temporaryFilesArrayList);

            if(!booleanArrayList.contains(false))
            {
                checkIfUploadedFilesHaveProperExtension(filesArrayList);
            }
            else if(!booleanArrayList.contains(true))
            {
                jTextPane.printMessageThatUploadedFilesDoNotExist();
            }
            else
            {
                String[] options = new String[] {"Ok", "Anuluj"};
                int dialogResult = JOptionPane.showOptionDialog
                        (null, "Część plików nie istnieje, kontynuować operacje na istniejących plikach?",
                                "Część plików nie istnieje", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, options, options);
                if(dialogResult == 0)
                {
                    checkIfUploadedFilesHaveProperExtension(filesArrayList);
                }
                else
                {
                    jTextPane.printMessageThatOperationHasBeenCanceled();
                }

            }
        }
        catch (Exception e)
        {
        }
    }

    private void checkIfUploadedFilesHaveProperExtension(ArrayList<File> filesArrayList)
    {
        try
        {
            ArrayList<File> temporaryFilesArrayList = temporaryFilesArrayList = new ArrayList<>();
            temporaryFilesArrayList.addAll(filesArrayList);
            ArrayList<Boolean> booleanArrayList = new ArrayList<>();

            for (File file : filesArrayList)
            {
                if (core.getFileExtension(file).equals("txt") ||
                    core.getFileExtension(file).equals("srt"))
                {
                    booleanArrayList.add(true);
                }
                else
                {
                    booleanArrayList.add(false);
                    temporaryFilesArrayList.remove(file);
                }
            }
            filesArrayList.clear();
            filesArrayList.addAll(temporaryFilesArrayList);
            if(!booleanArrayList.contains(false))
            {
                checkIfUploadedFilesAreEmpty(filesArrayList);
            }
            else if(!booleanArrayList.contains(true))
            {
                jTextPane.printMessageThatUploadedFilesHaveBadExtensions();
            }
            else
            {
                String[] options = new String[] {"Ok", "Anuluj"};
                int dialogResult = JOptionPane.showOptionDialog
                        (null, "Część plików ma niewłaściwe rozszerzenie, kontynuować operacje na poprawnych plikach?",
                                "Część plików ma niewłaściwe rozszerzenie", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, options, options);
                if(dialogResult == 0)
                {
                    checkIfUploadedFilesAreEmpty(filesArrayList);
                }
                else
                {
                    jTextPane.printMessageThatOperationHasBeenCanceled();
                }
            }
        }
        catch (Exception e)
        {
        }
    }

    private void checkIfUploadedFilesAreEmpty(ArrayList<File> filesArrayList)
    {
        try
        {
            ArrayList<File> temporaryFilesArrayList = temporaryFilesArrayList = new ArrayList<>();
            temporaryFilesArrayList.addAll(filesArrayList);
            ArrayList<Boolean> booleanArrayList = new ArrayList<>();

            for (File file : filesArrayList)
            {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file.toString()));
                if (!(bufferedReader.readLine() == null))
                {
                    booleanArrayList.add(true);
                }
                else
                {
                    booleanArrayList.add(false);
                    temporaryFilesArrayList.remove(file);
                }
            }
            filesArrayList.clear();
            filesArrayList.addAll(temporaryFilesArrayList);

            if (!booleanArrayList.contains(false))
            {
                checkEachFileSeparately(filesArrayList);
            }
            else if (!booleanArrayList.contains(true))
            {
                jTextPane.printMessageThatUploadedFilesAreEmpty();
            }
            else
            {
                String[] options = new String[]{"Ok", "Anuluj"};
                int dialogResult = JOptionPane.showOptionDialog
                        (null, "Część plików jest pusta, kontynuować operacje na niepustych"
                                        + "plikach?",
                                "Część plików jest pusta", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                                null, options, options);
                if (dialogResult == 0)
                {
                    checkEachFileSeparately(filesArrayList);
                }
                else
                {
                    jTextPane.printMessageThatOperationHasBeenCanceled();
                }
            }
        }
        catch (Exception e)
        {
        }
    }

    private void checkEachFileSeparately(ArrayList<File> filteredWords)
    {
        for(int i = 0; i < filteredWords.size(); i++)
        {
            if (filteredWords.get(i).equals(filteredWords.get(filteredWords.size() - 1)))
            {
                handleLastFile(filteredWords.get(filteredWords.size() - 1));
            }
            else if (filteredWords.get(i).equals(filteredWords.get(0)))
            {
                handleFirstFile(filteredWords.get(0));
            }
            else
            {
                handleFile(filteredWords.get(i));
            }
        }
    }

    private void handleFirstFile(File file)
    {
        try
        {
            Path filePath = Paths.get(file.toString());
            core.getCopyOfFilteredWords().addAll(core.getFilteredWords());
            core.getCopyOfUnFilteredWords().addAll(core.getUnFilteredWords());
            core.getFilteredWords().clear();
            core.fillUnFilteredWords(filePath, jTextPane);
            if (core.getConfigWords().containsAll(core.getUnFilteredWords()) &&
                core.getUnFilteredWords().containsAll(core.getConfigWords()))
            {
                core.getUnFilteredWords().clear();
                jTextPane.printMessageThatDataInFilesAreTheSame();
            }
            else
            {
                if (core.getConfigWords().containsAll(core.getUnFilteredWords()) &&
                    !core.getUnFilteredWords().isEmpty())
                {
                    core.getUnFilteredWords().clear();
                    jTextPane.printMessageThatUploadedFileCannotBeASubsetOfConfigFile();
                } else {
                    core.fillFilteredWords(core.getUnFilteredWords(), jTextPane);
                    jTextPane.printMessageThatFileHasBeenUploaded();

                    core.setButtonEnabled(loadFilesButton);
                    core.setButtonEnabled(createFileButton);
                    mainMenuBar.setFilesItemsCreateAndModify();
                }
            }
        }
        catch (Exception e)
        {
        }
    }

    private void handleFile(File file)
    {
        try
        {
            Path filePath = Paths.get(file.toString());
            core.fillUnFilteredWords(filePath,jTextPane);
            if (((core.getConfigWords().containsAll(core.getUnFilteredWords())) &&
                    (core.getUnFilteredWords().containsAll(core.getConfigWords()))) ||
                    ((core.getUnFilteredWords().containsAll(core.getFilteredWords())) &&
                    (core.getFilteredWords().containsAll(core.getUnFilteredWords()))))
            {
                jTextPane.printMessageThatDataInFilesAreTheSame();
                core.getUnFilteredWords().clear();
            }
            else
            {
                if (core.checkAndAddDataToFilteredWords(core.getUnFilteredWords(), jTextPane)) {
                    jTextPane.printMessageThatFileHasBeenUploaded();
                    core.setButtonEnabled(loadFilesButton);
                    core.setButtonEnabled(createFileButton);
                    mainMenuBar.setFilesItemsCreateAndModify();
                }
                else
                {
                    jTextPane.printMessageAllWordsFromCurrentFileHaveAlreadyBeenAdded();
                    core.getUnFilteredWords().clear();
                }

            }
        }
        catch (Exception e)
        {
        }
    }

    private void handleLastFile(File file)
    {
        try
        {
            Path filePath = Paths.get(file.toString());
            core.fillUnFilteredWords(filePath, jTextPane);
            if(((core.getConfigWords().containsAll(core.getUnFilteredWords()))  &&
                (core.getUnFilteredWords().containsAll(core.getConfigWords())))  ||
               ((core.getUnFilteredWords().containsAll(core.getFilteredWords())) &&
                (core.getFilteredWords().containsAll(core.getUnFilteredWords()))))
            {
                jTextPane.printMessageThatDataInFilesAreTheSame();
            }
            else
            {
                if (core.checkAndAddDataToFilteredWords(core.getUnFilteredWords(), jTextPane))
                {
                    jTextPane.printMessageThatFileHasBeenUploaded();
                    core.setButtonEnabled(createFileButton);
                    core.setButtonEnabled(modifyConfigurationButton);
                    mainMenuBar.setFilesItemsCreateAndModify();
                }
                else
                {
                    jTextPane.printMessageAllWordsFromCurrentFileHaveAlreadyBeenAdded();
                    core.getUnFilteredWords().clear();
                }

            }
            if (!core.getFilteredWords().isEmpty())
            {
                core.getUnFilteredWords().clear();
                core.getUnFilteredWords().addAll(core.getFilteredWords());
                core.getCopyOfFilteredWords().clear();
                core.getCopyOfUnFilteredWords().clear();
            }
            else
            {
                core.getUnFilteredWords().clear();
                core.getUnFilteredWords().addAll(core.getCopyOfUnFilteredWords());
                core.getFilteredWords().addAll(core.getCopyOfFilteredWords());
                core.getCopyOfFilteredWords().clear();
                core.getCopyOfUnFilteredWords().clear();
            }
        }
        catch (Exception e)
        {
        }
    }
}

