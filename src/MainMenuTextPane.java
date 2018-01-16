package list.of.words.creator;

import javax.swing.*;

public class MainMenuTextPane extends JTextPane
{
    public void printMessageThatFileDoesNotExist()
    {
         this.setText(this.getText() + "Wybrany plik nie istnieje." + System.lineSeparator());
    }

    public void printMessageThatFileHasBadExtension()
    {
         this.setText(this.getText() + "Wybrany plik ma niewłaściwe rozszerzenie." + System.lineSeparator());
    }

    public void printMessageThatThatFileIsEmpty()
    {
         this.setText(this.getText() + "Wybrany plik jest pusty." + System.lineSeparator());
    }

    public void printMessageThatDataInFilesAreTheSame()
    {
         this.setText(this.getText() + "Dane z plików są takie same." + System.lineSeparator());
    }

    public void printMessageThatConfigFileCannotBeASupersetOfUploadedFile()
    {
         this.setText(this.getText() + "Konfiguracja nie może zawierać wszystkich słów z pliku do filtracji." + System.lineSeparator());
    }

    public void printMessageThatErrorOccurredDuringCreatingConfiguration()
    {
         this.setText(this.getText() + "Podczas ładowania pliku konfiguracyjnego wystąpił błąd." + System.lineSeparator());
    }

    public void printMessageThatConfigurationHasBeenCreated()
    {
         this.setText(this.getText() + "Załadowano plik konfiguracyjny." + System.lineSeparator());
    }

    public void printMessageThatUploadedFileCannotBeASubsetOfConfigFile()
    {
        this.setText(this.getText() + "Wszystkie słowa z pliku do filtracji są zawarte w pliku konfiguracyjnym." + System.lineSeparator());
    }

    public void printMessageAllWordsFromCurrentFileHaveAlreadyBeenAdded()
    {
        this.setText(this.getText() + "Wszystkie słowa z pliku do filtracji są zawarte w pliku konfiguracyjnym / " +
            "poprzednim pliku" + System.lineSeparator());
    }

    public void printMessageThatFileHasBeenUploaded()
    {
         this.setText(this.getText() + "Załadowano plik do filtracji." + System.lineSeparator());
    }

    public void printMessageThatFileHasBeenCreated()
    {
         this.setText(this.getText() + "Utworzono plik ze słówkami o podanej nazwie." + System.lineSeparator());
    }

    public void printMessageThatFileHasBeenModified()
    {
         this.setText(this.getText() + "Plik został zmodyfikowany." + System.lineSeparator());
    }

    public void printMessageThatFileAlreadyContainsTheseWords()
    {
        this.setText(this.getText() + "Plik już zawiera te słowa." + System.lineSeparator());
    }

    public void printMessageThatFileHasBeenReplaced()
    {
         this.setText(this.getText() + "Plik został zastąpiony nowym plikiem." + System.lineSeparator());
    }

    public void printMessageThatConfigurationHasBeenModified()
    {
         this.setText(this.getText() + "Plik konfiguracyjny został zmodyfikowany." + System.lineSeparator());
    }

    public void printMessageThatConfigurationAlreadyContainsTheseWords()
    {
         this.setText(this.getText() + "Plik konfiguracyjny zawiera już te słowa." + System.lineSeparator());
    }

    public void printMessageThatOperationHasBeenCanceled()
    {
        this.setText(this.getText() + "Operacja została anulowana." + System.lineSeparator());
    }

    public void printMessageThatUploadedFilesDoNotExist()
    {
        this.setText(this.getText() + "Wybrane pliki nie istnieją." + System.lineSeparator());
    }

    public void printMessageThatUploadedFilesHaveBadExtensions()
    {
        this.setText(this.getText() + "Wybrane pliki mają niewłaściwe rozszerzenia." + System.lineSeparator());
    }

    public void printMessageThatUploadedFilesAreEmpty()
    {
        this.setText(this.getText() + "Wybrane pliki są puste." + System.lineSeparator());
    }

    public void printMessageThatErrorOccurred()
    {
        this.setText(this.getText() + "Wystąpił błąd." + System.lineSeparator());
    }
}
