package list.of.words.creator;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Core
{
    private ArrayList<String> configWords = new ArrayList<String>();
    private ArrayList<String> filteredWords = new ArrayList<String>();
    private ArrayList<String> copyOfFilteredWords = new ArrayList<String>();
    private ArrayList<String> unFilteredWords = new ArrayList<String>();
    private ArrayList<String> copyOfUnFilteredWords = new ArrayList<String>();
    private ArrayList<String> pathArrayList = new ArrayList<>();

    private Charset charset = Charset.forName("ISO8859_15");

    public ArrayList<String> getConfigWords() { return configWords; }
    public ArrayList<String> getFilteredWords()
    {
        return filteredWords;
    }
    public ArrayList<String> getCopyOfFilteredWords() { return copyOfFilteredWords; }
    public ArrayList<String> getUnFilteredWords() { return unFilteredWords; }
    public ArrayList<String> getCopyOfUnFilteredWords() { return copyOfUnFilteredWords; }
    public ArrayList<String> getPathArrayList()
    {
        return pathArrayList;
    }

    public void start()
    {
        MainMenuFrame mainMenuFrame = new MainMenuFrame(this);
        mainMenuFrame.setVisible(true);
    }
    
    public void fillConfigWords(Path path, MainMenuTextPane jTextPane)
    {
        String line = null;
        try (BufferedReader reader = Files.newBufferedReader(path, charset))
        {
            if (!configWords.isEmpty())
            {
                configWords.clear();
            }
            while ((line = reader.readLine()) != null)
            {
                String lines[] = line.split("[^a-zA-Z-']");
                for (String word : lines)
                {
                    if (configWords.isEmpty())
                    {
                        this.configWords.add(word.toLowerCase());
                    }
                    else
                    {
                        if (!configWords.contains(word))
                        {
                            this.configWords.add(word.toLowerCase());
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            jTextPane.printMessageThatErrorOccurred();
        }
    }

    public boolean fillFilteredWords(ArrayList<String> myArrayList, MainMenuTextPane jTextPane)
    {
        try
        {
            if (!filteredWords.isEmpty())
            {
                filteredWords.clear();
            }
            for (String word : myArrayList)
            {
                if (filteredWords.isEmpty())
                {
                    if (!configWords.contains(word))
                    {
                        this.filteredWords.add(word.toLowerCase());
                    }
                }
                else
                {
                    if ((!filteredWords.contains(word)) && (!(configWords.contains(word))))
                    {
                        this.filteredWords.add(word.toLowerCase());
                    }
                }
            }
            return true;
        }
        catch (Exception e)
        {
            jTextPane.printMessageThatErrorOccurred();
            return false;
        }
    }

    public void fillUnFilteredWords(Path path, MainMenuTextPane jTextPane)
    {
        String line = null;
        try (BufferedReader reader = Files.newBufferedReader(path, charset))
        {
            if (!unFilteredWords.isEmpty())
            {
                unFilteredWords.clear();
            }
            while ((line = reader.readLine()) != null)
            {
                String lines[] = line.split("[^a-zA-Z-']");
                for (String word : lines)
                {
                    if (unFilteredWords.isEmpty())
                    {
                        this.unFilteredWords.add(word.toLowerCase());
                    }
                    else
                    {
                        if (!unFilteredWords.contains(word))
                        {
                            this.unFilteredWords.add(word.toLowerCase());
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            jTextPane.printMessageThatErrorOccurred();
        }
    }

    public boolean checkAndAddDataToFilteredWords(ArrayList<String> unFilteredWords, MainMenuTextPane jTextPane)
    {
        try
        {
            ArrayList<String> helpArrayList = new ArrayList<>();

            for (String word : unFilteredWords)
            {
                if ((!filteredWords.contains(word)) && (!(configWords.contains(word))))
                {
                    this.filteredWords.add(word.toLowerCase());
                    helpArrayList.add(word);
                }
            }

            if (!helpArrayList.isEmpty())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(Exception e)
        {
            jTextPane.printMessageThatErrorOccurred();
            return false;
        }
    }

    public ArrayList<String> fillWordsFromFileArrayList(File file, ArrayList<String> wordsFromFileArrayList)
    {
        String line = null;
        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), charset))
        {
            if (!wordsFromFileArrayList.isEmpty())
            {
                wordsFromFileArrayList.clear();
            }
            while ((line = reader.readLine()) != null)
            {
                String lines[] = line.split("[^a-zA-Z-']");
                for (String word : lines)
                {
                    if (wordsFromFileArrayList.isEmpty())
                    {
                        wordsFromFileArrayList.add(word);
                    }
                    else
                    {
                        if (!wordsFromFileArrayList.contains(word))
                        {
                            wordsFromFileArrayList.add(word);
                        }
                    }
                }
            }
            return wordsFromFileArrayList;
        }
        catch (Exception e)
        {

            return null;
        }
    }

    public void createListOfWords(File file, MainMenuTextPane jTextPane)
    {
        try
        {
            FileWriter writer = new FileWriter(file);
            for (String word : filteredWords)
            {
                writer.write(word);
                writer.write(System.lineSeparator());
            }
            writer.close();
        }
        catch (Exception e)
        {
            jTextPane.printMessageThatErrorOccurred();
        }
    }

    public void modifyListOfWords(File file, MainMenuTextPane jTextPane)
    {
        try
        {
            ArrayList<String> wordsFromFileArrayList = new ArrayList<String>();
            fillWordsFromFileArrayList(file, wordsFromFileArrayList);
            FileWriter writer = new FileWriter(file.getAbsoluteFile(), true);
            if (!wordsFromFileArrayList.containsAll(filteredWords))
            {
                for (String word : filteredWords)
                {
                    if (!wordsFromFileArrayList.contains(word))
                    {
                        wordsFromFileArrayList.add(word);
                        writer.write(System.lineSeparator());
                        writer.write(word);
                    }
                }
                writer.close();
                jTextPane.printMessageThatFileHasBeenModified();
            }
            else
            {
                jTextPane.printMessageThatFileAlreadyContainsTheseWords();
            }
        }
        catch (Exception e)
        {
            jTextPane.printMessageThatErrorOccurred();
        }
    }
    
    public void modifyConfiguration(MainMenuTextPane jTextPane)
    {
        try
        {
            FileWriter writer = new FileWriter(getPathArrayList().get(0), true);
            if (!configWords.containsAll(filteredWords))
            {
                for (String fileArrayWord: this.filteredWords)
                {
                    if(!configWords.contains(fileArrayWord))
                    {
                        configWords.add(fileArrayWord);
                        writer.write(System.lineSeparator());
                        writer.write(fileArrayWord);
                    }
                }
                writer.close();
                jTextPane.printMessageThatConfigurationHasBeenModified();
            }
            else
            {
                jTextPane.printMessageThatConfigurationAlreadyContainsTheseWords();
            }
        }
        catch (Exception e)
        {
            jTextPane.printMessageThatErrorOccurred();
        }
    }

    public void addConfigPath(String configString)
    {
        if (pathArrayList.isEmpty())
        {
            pathArrayList.add(0, configString);
        }
        else
        {
            pathArrayList.set(0, configString);
        }
    }
    
    public String getFileExtension(File file)
    {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    public void setButtonEnabled(JButton button)
    {
        button.setEnabled(true);
    }
}


