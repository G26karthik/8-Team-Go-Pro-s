package additionalPrograms;

import java.util.*;

// Word Ladder Problem using BFS - Time: O(N * L^2), Space: O(N)
public class WordLadder {    public static List<String> findLadder(String beginWord, String endWord, List<String> wordList) {
        if (beginWord.equals(endWord)) {
            return Collections.singletonList(beginWord);
        }
        
        Set<String> wordSet = new HashSet<>(wordList);
        
        if (!wordSet.contains(endWord)) {
            return Collections.emptyList();
        }
        
        Map<String, String> parentMap = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        wordSet.remove(beginWord);
        
        boolean found = false;
        while (!queue.isEmpty() && !found) {
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                String currentWord = queue.poll();
                char[] wordArray = currentWord.toCharArray();
                
                for (int j = 0; j < wordArray.length; j++) {
                    char originalChar = wordArray[j];
                    
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == originalChar) continue;
                        
                        wordArray[j] = c;
                        String newWord = new String(wordArray);
                        
                        if (wordSet.contains(newWord)) {
                            queue.offer(newWord);
                            wordSet.remove(newWord);
                            parentMap.put(newWord, currentWord);
                            
                            if (newWord.equals(endWord)) {
                                found = true;
                                break;
                            }
                        }
                    }
                    
                    if (found) break;
                    wordArray[j] = originalChar;
                }
            }
        }
        
        if (!parentMap.containsKey(endWord)) {
            return Collections.emptyList();
        }
        
        List<String> path = new ArrayList<>();
        String current = endWord;
        while (current != null) {
            path.add(0, current);
            current = parentMap.get(current);
        }
        
        if (!path.isEmpty() && !path.get(0).equals(beginWord)) {
            path.add(0, beginWord);
        }
        
        return path;
    }// Display the word ladder transformation path
    public static void visualizePath(List<String> path) {
        if (path.isEmpty()) {
            System.out.println("No transformation path exists.");
            return;
        }
        
        if (path.size() == 1) {
            System.out.println("Words are identical: " + path.get(0));
            return;
        }
        
        System.out.println("Word Ladder Transformation Path:");
        
        for (int i = 0; i < path.size(); i++) {
            System.out.println(path.get(i));
            
            if (i < path.size() - 1) {
                String currWord = path.get(i);
                String nextWord = path.get(i+1);
                
                StringBuilder change = new StringBuilder();
                for (int j = 0; j < currWord.length(); j++) {
                    if (currWord.charAt(j) != nextWord.charAt(j)) {
                        change.append("  " + currWord.charAt(j) + " -> " + nextWord.charAt(j));
                    }
                }
                
                System.out.println("  |");
                System.out.println("  v Changed: " + change.toString());
            }
        }
        
        System.out.println("\nTransformation length: " + (path.size() - 1));
    }    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Word Ladder Problem");
        System.out.println("-------------------");
        
        System.out.print("Enter begin word: ");
        String beginWord = scanner.next();
        
        System.out.print("Enter end word: ");
        String endWord = scanner.next();
        
        if (beginWord.length() != endWord.length()) {
            System.out.println("Error: Begin word and end word must have the same length.");
            scanner.close();
            return;
        }
        
        System.out.print("Enter number of words in dictionary: ");
        int n = scanner.nextInt();
        
        List<String> wordList = new ArrayList<>();
        System.out.println("Enter " + n + " words (one per line):");
        for (int i = 0; i < n; i++) {
            wordList.add(scanner.next());
        }
        
        visualizePath(findLadder(beginWord, endWord, wordList));
        
        scanner.close();
    }
}