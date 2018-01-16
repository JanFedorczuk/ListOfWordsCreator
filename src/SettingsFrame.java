package list.of.words.creator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class SettingsFrame extends JDialog
{
    private SettingsFrame settingsFrame = this;

    private Dimension screenSize;

    private int settingsFrameWidth;
    private int settingsFrameHeight;
    private int buttonFontSize;
    private int buttonWidth;
    private int buttonHeight;

    public String currentDirectoryPath = MainMenuFrame.currentDirectoryPath;

    public SettingsFrame(Dimension screenSize, int buttonFontSize, int buttonWidth, int buttonHeight)
    {
        this.screenSize = screenSize;
        this.buttonFontSize = buttonFontSize;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;

        createAndShowSettingsFrame();
    }

    private void setFrameSizes()
    {
        settingsFrameWidth = (int) (screenSize.width / 2.8);
        settingsFrameHeight = screenSize.width / 25;
    }

    private void createAndShowSettingsFrame()
    {
        setFrameSizes();

        settingsFrame.setLayout(new GridBagLayout());
        settingsFrame.setResizable(false);
        settingsFrame.setTitle("Ustawienia");

        GridBagConstraints gbc = new GridBagConstraints();
        JPanel jPanel = new JPanel(new GridBagLayout());
        jPanel.setPreferredSize(new Dimension(settingsFrameWidth, settingsFrameHeight));
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 1;
        gbc.weightx = 1;

        JLabel jLabel = new JLabel("Scieżka folderu:");
        jLabel.setFont(new Font("Arial", Font.BOLD, buttonFontSize));
        gbc.gridx = 0;
        gbc.gridy = 0;
        jPanel.add(jLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField jTextField = new JTextField(24);
        jTextField.setFont(new Font("Arial", Font.PLAIN, buttonFontSize));
        if (currentDirectoryPath != null)
        {
            jTextField.setText(currentDirectoryPath);
        }
        jPanel.add(jTextField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        JButton jButton = new JButton("Wybierz plik");
        jButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        jButton.setFont(new Font("Arial", Font.BOLD, buttonFontSize));
        addButtonListener(settingsFrame, jButton, jTextField);
        jPanel.add(jButton, gbc);

        settingsFrame.add(jPanel, gbc);
        addWindowListener(settingsFrame, jTextField);
        settingsFrame.pack();

        settingsFrame.setLocation(screenSize.width / 2 - settingsFrame.getBounds().getSize().width / 2,
                screenSize.height / 2 - 2 * settingsFrame.getBounds().getSize().height);

        settingsFrame.setModalityType(ModalityType.APPLICATION_MODAL);
        settingsFrame.setVisible(true);
    }

    private boolean checkIfFilePathIsValid(String pathString)
    {
        try
        {
            File newFile = new File(pathString);
            if(newFile.exists())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    private void showErrorPopUp(SettingsFrame settingsFrame)
    {
        JOptionPane.showMessageDialog(settingsFrame, "Ścieżka do danego folderu jest " +
                "nieprawidłowa.", "Nieprawidłowa ścieżka folderu", JOptionPane.ERROR_MESSAGE);
    }

    private void addWindowListener(SettingsFrame settingsFrame, JTextField jTextField)
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
                    MainMenuFrame.currentDirectoryPath = stringPath;
                }
                else
                {
                    showErrorPopUp(settingsFrame);
                }
            }
        });
    }

    private void addButtonListener(SettingsFrame settingsFrame, JButton jButton, JTextField jTextField)
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
