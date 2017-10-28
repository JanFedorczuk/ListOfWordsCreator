package list.of.words.creator;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MyFrame extends JFrame
{
    private Core core;

    private JPanel buttonPanel;
    private JPanel textPanePanel;
    private MyJTextPane jTextPane;
    private JButton button1 = new JButton("Załaduj plik");
    private JButton button2 = new JButton("Stwórz konfigurację");
    private JButton button3 = new JButton("Załaduj pliki");
    private JButton button4 = new JButton("Stwórz plik");
    private JButton button5 = new JButton("Resetuj");
    private JButton button6 = new JButton("Modyfikuj konfigurację");

    public configCreator configCreator = new configCreator();
    public fileUploader fileUploader = new fileUploader();
    public filesUploader filesUploader = new filesUploader();
    public fileCreator fileCreator = new fileCreator();
    public configModifier configModifier = new configModifier();
    public exitProgram exitProgram = new exitProgram();
    public resetStatus resetStatus = new resetStatus();
    public setOptions setOptions = new setOptions();

    private MyJMenuBar myJMenuBar = new MyJMenuBar(this);
    private String currentDirectoryPath;

    public MyFrame(Core core)
    {
        setOptions();
        this.core = core;
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(myJMenuBar);
        this.setJMenuBar(menuBar);

        GridBagConstraints gbc = new GridBagConstraints();
        buttonPanel = new JPanel(new GridBagLayout());
        this.setLayout(new GridBagLayout());
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weighty = 1;
        gbc.weightx = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        buttonPanel.add(button1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 0);
        buttonPanel.add(button2, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 0);
        buttonPanel.add(button3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        buttonPanel.add(button4, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 0, 0);
        buttonPanel.add(button5, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 0, 0);
        buttonPanel.add(button6, gbc);

        gbc.insets = new Insets(10, 20, 0, 20);
        add(buttonPanel, gbc);

        textPanePanel = new JPanel(new GridBagLayout());
        jTextPane = new MyJTextPane();
        jTextPane.setEnabled(false);
        JScrollPane scroller = new JScrollPane(jTextPane);
        jTextPane.setPreferredSize(new Dimension(350,100));
        scroller.setPreferredSize(new Dimension(350,100));
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 0, 10, 0);
        textPanePanel.add(scroller, gbc);
        add(textPanePanel, gbc);



        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        addActionListeners();
    }

    private void setFrameSize()
    {
        Toolkit kit = Toolkit.getDefaultToolkit();
        setSize(526, 264);
    }

    private void setButtonSizes()
    {
        button2.setPreferredSize(new Dimension(152, 26));
        button1.setPreferredSize(new Dimension(152, 26));
        button3.setPreferredSize(new Dimension(152, 26));
        button4.setPreferredSize(new Dimension(152, 26));
        button6.setPreferredSize(new Dimension(152, 26));
        button5.setPreferredSize(new Dimension(152, 26));
    }

    private void setButtonsDisabled()
    {
        button1.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        button6.setEnabled(false);
        button5.setEnabled(false);
    }

    private void setOptions()
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dimension.width / 2 - 263), (dimension.height / 2 - 264));
        this.setFrameSize();
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
                                core.setButtonEnabled(button1);
                                core.setButtonEnabled(button3);
                                core.setButtonEnabled(button5);
                                myJMenuBar.setFileItemsUploadFileAndFilesEnabled();
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
                                        core.setButtonEnabled(button1);
                                        core.setButtonEnabled(button3);
                                        core.setButtonEnabled(button5);
                                        myJMenuBar.setFileItemsUploadFileAndFilesEnabled();
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
                                            core.setButtonEnabled(button4);
                                            core.setButtonEnabled(button6);
                                            myJMenuBar.setFilesItemsCreateAndModify();
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

    public class configModifier implements ActionListener
    {
        public void actionPerformed(ActionEvent event) {
            core.modifyConfiguration(jTextPane);
        }
    }

    public class resetStatus implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            core.resetStatus();
            button1.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
            button6.setEnabled(false);
            button5.setEnabled(false);
            myJMenuBar.setFilesItemsDisabled();
            jTextPane.setText("");
        }
    }

    public class exitProgram implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            System.exit(0);
        }
    }

    public class setOptions implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            JFrame settingsFrame = new JFrame();
            settingsFrame.setLayout(new GridBagLayout());
            settingsFrame.setLocationByPlatform(true);
            settingsFrame.setSize(508,80);
            settingsFrame.setResizable(false);
            settingsFrame.setTitle("Ustawienia");

            GridBagConstraints gbc = new GridBagConstraints();
            JPanel jPanel = new JPanel(new GridBagLayout());
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.weighty = 1;
            gbc.weightx = 1;

            JLabel jLabel = new JLabel("Scieżka folderu:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(6, 0, 0, 0);
            jPanel.add(jLabel, gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.insets = new Insets(6, 10, 0, 0);
            JTextField jTextField = new JTextField(24);
            if (currentDirectoryPath != null)
            {
                jTextField.setText(currentDirectoryPath);
            }
            jPanel.add(jTextField, gbc);

            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 10, 0, 0);
            JButton jButton = new JButton("Wybierz plik");
            addButtonListener(settingsFrame, jButton, jTextField);
            jPanel.add(jButton, gbc);

            gbc.insets = new Insets(10, 10, 0, 0);
            settingsFrame.add(jPanel, gbc);
            addWindowListener(settingsFrame, jTextField);
            settingsFrame.setVisible(true);
        }

        private boolean checkIfFilePathIsValid(String pathString)
        {
           try
           {
               File newFile = new File(pathString + "\\test");
               newFile.createNewFile();
               newFile.delete();
               return true;
           }
           catch (Exception ex)
           {
               return false;
           }
        }

        private void showErrorPopUp(JFrame settingsFrame)
        {
            JOptionPane.showMessageDialog(settingsFrame, "Ścieżka do danego folderu jest " +
                    "nieprawidłowa.", "Nieprawidłowa ścieżka folderu", JOptionPane.ERROR_MESSAGE);
        }

        private void addWindowListener(JFrame settingsFrame, JTextField jTextField)
        {
            settingsFrame.addWindowListener(new java.awt.event.WindowAdapter()
            {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent)
                {
                    String stringPath = jTextField.getText();
                    if (checkIfFilePathIsValid(stringPath))
                    {
                        if(!jTextField.getText().equalsIgnoreCase(""))
                        {
                        }
                        currentDirectoryPath = stringPath;
                    }
                    else
                    {
                        showErrorPopUp(settingsFrame);
                    }
                }
            });
        }

        private void addButtonListener(JFrame settingsFrame, JButton jButton, JTextField jTextField)
        {
            jButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    JFileChooser jFileChooser = new JFileChooser();
                    jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int result = jFileChooser.showOpenDialog(settingsFrame);

                    if (result == JFileChooser.APPROVE_OPTION)
                    {
                        if (checkIfFilePathIsValid(jFileChooser.getSelectedFile().getAbsolutePath()))
                        {
                            jTextField.setText(jFileChooser.getSelectedFile().getAbsolutePath());
                        }
                        else
                        {
                            showErrorPopUp(settingsFrame);
                        }
                    }
                    else if (result == JFileChooser.CANCEL_OPTION)
                    {
                    }
                }
            });
        }
    }

    private void addActionListeners()
    {
        button1.addActionListener(fileUploader);
        button2.addActionListener(configCreator);
        button3.addActionListener(filesUploader);
        button4.addActionListener(fileCreator);
        button6.addActionListener(configModifier);
        button5.addActionListener(resetStatus);
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
                String[] options = new String[] {"Proceed", "Cancel"};
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
                String[] options = new String[] {"Proceed", "Cancel"};
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
                String[] options = new String[]{"Proceed", "Cancel"};
                int dialogResult = JOptionPane.showOptionDialog
                        (null, "Część plików jest pusta, kontynuować operacje na niepustych?",
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

                    core.setButtonEnabled(button3);
                    core.setButtonEnabled(button4);
                    myJMenuBar.setFilesItemsCreateAndModify();
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
                    core.setButtonEnabled(button3);
                    core.setButtonEnabled(button4);
                    myJMenuBar.setFilesItemsCreateAndModify();
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
                    core.setButtonEnabled(button4);
                    core.setButtonEnabled(button6);
                    myJMenuBar.setFilesItemsCreateAndModify();
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

