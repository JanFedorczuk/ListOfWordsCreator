package list.of.words.creator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuBar extends JMenuBar
{

    private MainMenuFrame mainMenuFrame;
    private Dimension screenSize;

    private int buttonFontSize;
    private int buttonWidth;
    private int buttonHeight;
    private String[] fileItems = new String[]{"Stwórz konfigurację", "Załaduj plik", "Załaduj pliki",
            "Stwórz plik", "Modyfikuj konfigurację", "Resetuj status", "Wyjście"};

    private String[] settingsItems = new String[]{"Ustaw plik startowy"};

    private JMenuItem configCreator = new JMenuItem(fileItems[0]);
    private JMenuItem fileUploader = new JMenuItem(fileItems[1]);
    private JMenuItem filesUploader = new JMenuItem(fileItems[2]);
    private JMenuItem fileCreator = new JMenuItem(fileItems[3]);
    private JMenuItem configModifier = new JMenuItem(fileItems[4]);
    private JMenuItem exitProgram = new JMenuItem(fileItems[6]);

    private JMenuItem settingsItem0 = new JMenuItem(settingsItems[0]);

    public MainMenuBar(MainMenuFrame mainMenuFrame, Dimension screenSize, int buttonFontSize, int buttonWidth, int buttonHeight)
    {
        this.mainMenuFrame = mainMenuFrame;
        this.screenSize = screenSize;
        this.buttonFontSize = buttonFontSize;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;

        configCreator.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));
        fileUploader.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));
        filesUploader.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));
        fileCreator.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));
        configModifier.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));
        exitProgram.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));

        JMenu fileMenu = new JMenu("Opcje");
        fileMenu.setFont(new Font("Arial", Font.BOLD, buttonFontSize));

        settingsItem0.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));

        JMenu settingsMenu = new JMenu("Ustawienia");
        settingsMenu.setFont(new Font("Arial", Font.BOLD, buttonFontSize));

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
        configCreator.addActionListener(mainMenuFrame.configCreator);
        fileMenu.add(configCreator);

        fileUploader.setEnabled(false);
        fileUploader.addActionListener(mainMenuFrame.fileUploader);
        fileMenu.add(fileUploader);

        filesUploader.setEnabled(false);
        filesUploader.addActionListener(mainMenuFrame.filesUploader);
        fileMenu.add(filesUploader);

        fileCreator.setEnabled(false);
        fileCreator.addActionListener(mainMenuFrame.fileCreator);
        fileMenu.add(fileCreator);

        configModifier.setEnabled(false);
        configModifier.addActionListener(mainMenuFrame.configModifier);
        fileMenu.add(configModifier);

        exitProgram.addActionListener(mainMenuFrame.exitProgram);
        fileMenu.add(exitProgram);
    }

    private void fillSettingsMenu(JMenu settingsMenu)
    {
        settingsItem0.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                SettingsFrame settingsFrame = new SettingsFrame(screenSize, buttonFontSize, buttonWidth, buttonHeight);
            }
        });
        settingsMenu.add(settingsItem0);
    }

    public void setFileItemsUploadFileAndFilesEnabled()
    {
        fileUploader.setEnabled(true);
        filesUploader.setEnabled(true);
    }

    public void setFilesItemsCreateAndModify()
    {
        fileCreator.setEnabled(true);
        configModifier.setEnabled(true);
    }
}

