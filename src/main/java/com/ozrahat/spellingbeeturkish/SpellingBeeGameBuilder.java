package com.ozrahat.spellingbeeturkish;

import com.ozrahat.spellingbeeturkish.exceptions.NoSuchWordListException;
import com.ozrahat.spellingbeeturkish.exceptions.NotValidQueryStringException;
import com.ozrahat.spellingbeeturkish.helpers.Dictionary;
import com.ozrahat.spellingbeeturkish.helpers.Helpers;
import com.ozrahat.spellingbeeturkish.model.GameModel;

import java.io.File;
import java.util.*;

public class SpellingBeeGameBuilder {

    private com.ozrahat.spellingbeeturkish.helpers.Dictionary dictionary;

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

        Character centerCharacter = getWordCountByCenterCharacter(newCharacters, dictionary.getWordList());

        TreeSet<String> wordList = getWordListForGame(dictionary.getWordList(), newCharacters, centerCharacter);

        newCharacters.remove(centerCharacter);

        GameModel gameModel = new GameModel();
        gameModel.setCharacters(newCharacters);
        gameModel.setCenterLetter(centerCharacter);
        gameModel.setWordList(new TreeSet<>(wordList));

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
        TreeSet<String> wordList;
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
                    if (isValidWord(word, characters)) {
                        validWords.add(word);
                    }
                }
                Character centerCharacter =  getWordCountByCenterCharacter(characters, validWords);
                characters.remove(centerCharacter);
                gameModel.setCharacters(characters);
                gameModel.setCenterLetter(centerCharacter);

                // Let's create our word list for the game!
                wordList = getWordListForGame(validWords, centerCharacter);
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
            if (canPangram(word))
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
     * Method for checking whether a word has 7 distinct characters.
     *
     * @param word Any given word.
     * @return true or false
     */
    public static boolean canPangram(String word) {
        if (word.length() < 7)
            return false;
        HashMap<Character, Integer> characters = new HashMap<>();
        for (Character character: word.toCharArray()) {
            characters.merge(character, 1, Integer::sum);
        }
        return characters.keySet().size() == 7;
    }

    /**
     * Method for finding whether a word is valid or not.
     * Rules are:
     * <ul>
     * <li>Each word must be at least four letters long</li>
     * <li>Each word must not contain any letters other than the seven letters</li>
     * </ul>
     *
     * @param word Any given word.
     * @param characters All 7 characters to create the game.
     * @return true or false
     */
    public static boolean isValidWord(String word, ArrayList<Character> characters) {
        // If the word is less than 4 characters long, return false.
        if (word.length() < 4)
            return false;
        // Check if word contains all the characters.
        // If not so, return false.
        for (Character character: word.toLowerCase().toCharArray()) {
            if (!characters.contains(character))
                return false;
        }
        return true;
    }

    /**
     * Method for finding the best character to put it into the center of the hive.
     * It finds the most frequent character in the array.
     *
     * @param characters The characters selected to start the game.
     * @param words Words made up with given characters.
     * @return Character that most frequent in all the given words.
     */
    public static Character getWordCountByCenterCharacter(ArrayList<Character> characters, ArrayList<String> words) {
        // First create a hash map to hold that
        // how many times each character found in the words array.
        HashMap<Character, Integer> letterCounts = new HashMap<>();
        // Initialize the hashmap with putting each character with a value of 0.
        for (Character letter: characters) {
            letterCounts.put(letter, 0);
        }

        // Check all characters for each word so that if they exist in the word or not.
        // If so increment the value of the character by one in the hashmap.
        for (String word: words) {
            for (Character letter: characters) {
                if (word.contains(String.valueOf(letter)))
                    letterCounts.put(letter, letterCounts.get(letter) + 1);
            }
        }
        // Find the most frequent character and return it.
        return Collections.max(letterCounts.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    /**
     * Method for checking all the possible words that contains
     * the given center character at least one.
     *
     * @param words Words made up with given characters.
     * @param centerCharacter The character that must be existed in every word at least one.
     * @return Array of the word list to start the game
     */
    public static TreeSet<String> getWordListForGame(ArrayList<String> words, Character centerCharacter) {
        // Initialize an array to hold the words.
        TreeSet<String> wordList = new TreeSet<>();
        // We need to get all the possible words which contains the center character.
        for (String word: words) {
            if (word.contains(String.valueOf(centerCharacter)))
                wordList.add(word.toLowerCase());
        }
        return wordList;
    }

    /**
     * Method for checking all the possible words that contains
     * the given center character at least one.
     *
     * @param words Words made up with given characters.
     * @param characters The character list that must be existed in every word.
     * @return Array of the word list to start the game
     */
    public static TreeSet<String> getWordListForGame(ArrayList<String> words, ArrayList<Character> characters, Character centerCharacter) {
        // Initialize an array to hold the words.
        TreeSet<String> wordList = new TreeSet<>();
        // We need to get all the possible words which contains the center character.
        for (String word: words) {
            if (isValidWord(word, characters) && word.contains(String.valueOf(centerCharacter)))
                wordList.add(word);
        }
        return wordList;
    }

    /**
     * Method for initializing the ArrayList with Turkish Alphabet.
     */
    private void initializeAlphabet() {
        alphabet = new ArrayList<>(List.of('a', 'b', 'c', 'ç', 'd', 'e', 'f', 'g', 'ğ', 'h', 'ı', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'ö', 'p', 'r', 's', 'ş', 't', 'u', 'ü', 'v', 'y', 'z'));
    }
}
