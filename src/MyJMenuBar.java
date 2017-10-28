package list.of.words.creator;

import javax.swing.*;

public class MyJMenuBar extends JMenuBar 
{

    private MyFrame myFrame;
    private String[] fileItems = new String[]{"Stwórz konfigurację", "Załaduj plik", "Załaduj pliki",
            "Stwórz plik", "Modyfikuj konfigurację", "Resetuj status", "Wyjście"};

    private String[] settingsItems = new String[]{"Ustaw plik startowy"};

    private JMenuItem configCreator = new JMenuItem(fileItems[0]);
    private JMenuItem fileUploader = new JMenuItem(fileItems[1]);
    private JMenuItem filesUploader = new JMenuItem(fileItems[2]);
    private JMenuItem fileCreator = new JMenuItem(fileItems[3]);
    private JMenuItem configModifier = new JMenuItem(fileItems[4]);
    private JMenuItem resetStatus = new JMenuItem(fileItems[5]);
    private JMenuItem exitProgram = new JMenuItem(fileItems[6]);

    private JMenuItem settingsItem0 = new JMenuItem(settingsItems[0]);

    public MyJMenuBar(MyFrame myFrame)
    {
        this.myFrame = myFrame;

        JMenu fileMenu = new JMenu("Opcje");
        JMenu settingsMenu = new JMenu("Ustawienia");

        fillFileMenu(fileMenu);
        fillSettingsMenu(settingsMenu);

        fileMenu.insertSeparator(1);
        fileMenu.insertSeparator(4);
        fileMenu.insertSeparator(7);
        add(fileMenu);
        add(settingsMenu);

    }

    private void fillFileMenu(JMenu fileMenu)
    {
        configCreator.addActionListener(myFrame.configCreator);
        fileMenu.add(configCreator);

        fileUploader.setEnabled(false);
        fileUploader.addActionListener(myFrame.fileUploader);
        fileMenu.add(fileUploader);

        filesUploader.setEnabled(false);
        filesUploader.addActionListener(myFrame.filesUploader);
        fileMenu.add(filesUploader);

        fileCreator.setEnabled(false);
        fileCreator.addActionListener(myFrame.fileCreator);
        fileMenu.add(fileCreator);

        configModifier.setEnabled(false);
        configModifier.addActionListener(myFrame.configModifier);
        fileMenu.add(configModifier);

        resetStatus.setEnabled(false);
        resetStatus.addActionListener(myFrame.resetStatus);
        fileMenu.add(resetStatus);

        exitProgram.addActionListener(myFrame.exitProgram);
        fileMenu.add(exitProgram);
    }

    private void fillSettingsMenu(JMenu settingsMenu)
    {
        settingsItem0.addActionListener(myFrame.setOptions);
        settingsMenu.add(settingsItem0);
    }

    public void setFileItemsUploadFileAndFilesEnabled()
    {
        fileUploader.setEnabled(true);
        filesUploader.setEnabled(true);
        resetStatus.setEnabled(true);
    }

    public void setFilesItemsCreateAndModify()
    {
        fileCreator.setEnabled(true);
        configModifier.setEnabled(true);
    }

    public void setFilesItemsDisabled()
    {
        fileUploader.setEnabled(false);
        filesUploader.setEnabled(false);
        fileCreator.setEnabled(false);
        configModifier.setEnabled(false);
        resetStatus.setEnabled(false);
    }
}

