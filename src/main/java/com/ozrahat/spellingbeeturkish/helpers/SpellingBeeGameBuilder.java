package com.ozrahat.spellingbeeturkish.helpers;

import com.ozrahat.spellingbeeturkish.exceptions.NoSuchWordListException;
import com.ozrahat.spellingbeeturkish.exceptions.NotValidQueryStringException;
import com.ozrahat.spellingbeeturkish.model.GameModel;

import java.io.File;
import java.util.*;

public class SpellingBeeGameBuilder {

    private Dictionary dictionary;

    private ArrayList<String> pangramWords;

    private ArrayList<Character> alphabet;
    private ArrayList<Character> characters;

    public SpellingBeeGameBuilder() {
        initializeDictionary();
        pangramWords = new ArrayList<>();
    }

    /**
     * Builds a SpellingBee GameModel object with auto generated word list.
     *
     * @return GameModel object to start the game.
     */
    public GameModel buildGame() {
        pangramWords = findPangramWords();

        Random random = new Random();
        int index = random.nextInt(pangramWords.size() - 1);

        String selectedWord = pangramWords.get(index);

        List<Character> characters = getCharacters(selectedWord);
        ArrayList<Character> newCharacters = new ArrayList<>(characters);

        Character centerCharacter = Helpers.getWordCountByCenterCharacter(newCharacters, dictionary.getWordList());

        List<String> wordList = Helpers.getWordListForGame(dictionary.getWordList(), newCharacters, centerCharacter);

        newCharacters.remove(centerCharacter);

        GameModel gameModel = new GameModel();
        gameModel.setCharacters(newCharacters);
        gameModel.setCenterLetter(centerCharacter);
        gameModel.setWordList(new ArrayList<>(wordList));

        return gameModel;
    }

    /**
     *
     * Builds a SpellingBee GameModel object with a given string of characters.
     *
     * @param query String with 7 distinct characters.
     * @return GameModel object to start the game.
     * @throws NotValidQueryStringException if query string is not properly formed.
     * @throws NoSuchWordListException if a game can not be created with the given query string.
     */
    public GameModel buildGame(String query) throws NotValidQueryStringException, NoSuchWordListException {
        initializeAlphabet();
        ArrayList<String> wordList;
        ArrayList<String> validWords = new ArrayList<>();
        characters = new ArrayList<>();

        if (isValidQuery(query)) {
            // Start the game.
            GameModel gameModel = new GameModel();
            for (Character character: query.toCharArray())
                characters.add(character);

            boolean canCreateWordList = tryToFindWordList();

            if (canCreateWordList) {
                for (String word: dictionary.getWordList()) {
                    if (Helpers.isValidWord(word, characters)) {
                        validWords.add(word);
                    }
                }
                Character centerCharacter =  Helpers.getWordCountByCenterCharacter(characters, validWords);
                characters.remove(centerCharacter);
                gameModel.setCharacters(characters);
                gameModel.setCenterLetter(centerCharacter);

                // Let's create our word list for the game!
                wordList = Helpers.getWordListForGame(validWords, centerCharacter);
                gameModel.setWordList(wordList);
                return gameModel;
            }else {
                throw new NoSuchWordListException();
            }
        }else {
            throw new NotValidQueryStringException();
        }
    }

    /**
     * Method for initializing the Dictionary object with absolute path of the dictionary file.
     */
    private void initializeDictionary() {
        File file = new File("C:\\Users\\ahmet\\IdeaProjects\\SpellingBeeTurkish\\src\\main\\java\\com\\ozrahat\\spellingbeeturkish\\assets\\dictionary.txt");
        dictionary = new Dictionary(file);
    }

    private boolean tryToFindWordList() {
        pangramWords.clear();
        for (String word: dictionary.getWordList()) {
            if (Helpers.isPangram(word, characters)) {
                pangramWords.add(word);
            }
        }
        return !pangramWords.isEmpty();
    }

    private boolean isValidQuery(String query) {
        if (query.length() != 7)
            return false;
        if (!isDistinctLetters(query))
            return false;
        return isAllCharactersBelongToAlphabet(query);
    }

    /**
     * Method for checking whether the given query string
     * contains a duplicate character or not.
     *
     * @param word The 7 character string entered by user.
     * @return true or false
     */
    public boolean isDistinctLetters(String word) {
        HashMap<Character, Integer> characters = new HashMap<>();
        for (Character character: word.toCharArray()) {
            characters.merge(character, 1, Integer::sum);
        }

        for (Character character: word.toCharArray()) {
            if (characters.get(character) > 1)
                return false;
        }
        return true;
    }

    /**
     * Method for checking whether all the characters of the query
     * belongs to the Turkish alphabet or not.
     *
     * @param query The 7 character string entered by user.
     * @return true or false
     */
    public boolean isAllCharactersBelongToAlphabet(String query) {
        for (Character character: query.toCharArray()) {
            if (!alphabet.contains(character))
                return false;
        }
        return true;
    }

    /**
     * Method for finding all possible pangram words in the word list.
     *
     * @return List of words that can be pangram.
     */
    private ArrayList<String> findPangramWords() {
        ArrayList<String> pangramWords = new ArrayList<>();
        for (String word: dictionary.getWordList()) {
            if (Helpers.canPangram(word))
                pangramWords.add(word);
        }
        return pangramWords;
    }

    /**
     * Method for getting characters of any given word.
     *
     * @param word Any given word.
     * @return List of characters.
     */
    private List<Character> getCharacters(String word) {
        // First create a hash map to hold that
        // how many times each character found in the word.
        Map<Character, Integer> characterCounts = new TreeMap<>();
        // Initialize the hashmap with putting each character with a value of 0.
        for (Character character: word.toCharArray()) {
            characterCounts.put(character, 0);
        }

        // Check all characters for the word.
        for (Character character: word.toCharArray()) {
            if (word.contains(String.valueOf(character)))
                characterCounts.put(character, characterCounts.get(character) + 1);
        }
        return characterCounts.keySet().stream().toList();
    }

    /**
     * Method for initializing the ArrayList with Turkish Alphabet.
     */
    private void initializeAlphabet() {
        alphabet = new ArrayList<>(List.of('a', 'b', 'c', 'ç', 'd', 'e', 'f', 'g', 'ğ', 'h', 'ı', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'ö', 'p', 'r', 's', 'ş', 't', 'u', 'ü', 'v', 'y', 'z'));
    }
}
