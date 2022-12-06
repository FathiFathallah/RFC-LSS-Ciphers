import java.util.*;
public class Main {
    //Rail Fence Cipher////////////////////////////////////////////////////////////////////
    private static String rfcEncrypt(String plainText, int key) {
        String encryptedText = "";
        char[][] array = new char[key][plainText.length()];
        int index = 0;
        int x = 1;
        for (int i = 0; i < plainText.length(); i++) {
            array[index][i] = plainText.charAt(i);
            index+=x;
            if(index == key - 1 || index ==0){
                x *= -1;
            }
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if(array[i][j] != '\u0000'){
                    encryptedText+=array[i][j];
                }
            }
        }
        return "Encrypted Text: " + encryptedText;
    }

    private static String rfcDecrypt(String encryptedText, int key) {
        String decryptedText = "";
        char[][] array = new char[key][encryptedText.length()];
        int index = 0;
        int x = 1;
        for (int i = 0; i < encryptedText.length(); i++) {
                array[index][i] = '*';
                index+=x;
                if(index == key - 1 || index ==0){
                    x *= -1;
                }
        }
        index = 0;
        for (int i = 0; i < array.length; i++) { //this equals to the row in our matrix.
            for (int j = 0; j < array[i].length; j++) { //this equals to the column in each row.
                if(array[i][j] == '*'){
                    array[i][j] = encryptedText.charAt(index++);
                }
                else{
                    array[i][j] = '*';
                }
            }
        }
        x = 1;
        index = 0;
        for (int i = 0; i < encryptedText.length(); i++) {
            decryptedText += array[index][i];
            index+=x;
            if(index == key - 1 || index ==0){
                x *= -1;
            }
        }
        return "Decrypted Text: " + decryptedText;
    }

    private static String rfcCrack(String encryptedText) {
        String crackaAalysis = "";
        int i=0;
        int k = 2;
        while(k<=11)
            crackaAalysis += "Test#" + (i++) + " | Key = "+ k + " => \t" + rfcDecrypt(encryptedText,k++)+ "\n";
        return crackaAalysis;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Least-Simple Substitution/////////////////////////////////////////////////////////////////////////////////////////////////
    private static String lssEncrypt(String plainText, char[] lookUpTable) {
        plainText = plainText.toUpperCase(); // JUST IN CASE
        String encryptedText = "";
        for(int i=0 ;i<plainText.length();i++){
            char character = plainText.charAt(i);
            if(Character.isAlphabetic(character)){
                encryptedText += lookUpTable[character-65];
            }
            else{
                encryptedText += character;
            }
        }
        return "Encrypted Text: " + encryptedText;
    }

    private static String lssAnalyze(String encryptedText,char[] englishLetterFrequency) {
        encryptedText = encryptedText.toLowerCase();
        String analyzedText = encryptedText;
        //this hashmap is for finding the max frequent letter each time and then replace it with the most english frequency letters
        HashMap<Character,Integer> hashmap = new HashMap<Character,Integer>();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for(int i=0;i<alphabet.length;i++){
            hashmap.put(alphabet[i],0);
        }
        //Increment the frequency by 1
        for(int i=0;i<encryptedText.length();i++){
            if(Character.isAlphabetic(encryptedText.charAt(i)))
                hashmap.replace(encryptedText.charAt(i),hashmap.get(encryptedText.charAt(i))+1);
        }
//        for (char i : hashmap.keySet()) {
//            System.out.println("character: " + i + " frequency: " + hashmap.get(i));
//        }
        int index = 0;
        int[] max = new int[2];
        for(int i=0;i<alphabet.length;i++){
            for (char j : hashmap.keySet()) {
                if(hashmap.get(j) > max[0]){
                    max[0] = hashmap.get(j);
                    max[1] = j;
                }
            }
            hashmap.remove((char)max[1]);
            max[0] = 0;
            analyzedText = analyzedText.replace(((char)max[1])+"", englishLetterFrequency[index++]+"");
        }
        return "Analyzed Text: " + analyzedText;
    }

    public static void main(String[] args) {
        char[] lookUpTable = {'J', 'I', 'C', 'A', 'X', 'S', 'E', 'Y', 'V', 'D', 'K', 'W', 'B', 'Q', 'T', 'Z', 'R', 'H', 'F', 'M', 'P', 'N', 'U', 'L', 'G', 'O'};
        char[] englishLetterFrequency = {'E','T', 'A','O','I','N','S','R','H','D','L','U','C','M','F','Y','W','G','P','B','V','K','X','Q','J','Z'};
        String PlainText = "";
        String EncryptedText = "";
        int rail = 0;
        Scanner scannerLine = new Scanner(System.in);
        Scanner scannerNumber = new Scanner(System.in);
        while(true){
            System.out.println("=========================================================================================");
            System.out.flush();
            System.out.println("1.Rail Fence Cipher\t2.Least-Simple Substitution\t3.Exit");
            int option = scannerNumber.nextInt();  // Read user input
            if(option == 3) break;
            //Rail Fence Cipher
            else if(option == 1){
                System.out.println("1.Encrypt\t2.Decrypt\t3.Crack\t4.Exit");
                int dycEnc = scannerNumber.nextInt();
                if(dycEnc == 1){
                    System.out.println("Enter The PlainText To Encrypt: ");
                    PlainText = scannerLine.nextLine().toUpperCase();
                    System.out.println("Enter The Key: ");
                    rail = scannerNumber.nextInt();
                    System.out.println(rfcEncrypt(PlainText,rail));
                }
                else if(dycEnc == 2){
                    System.out.println("Enter The Encrypted Text To Decrypt: ");
                    EncryptedText = scannerLine.nextLine();
                    System.out.println("Enter The Key: ");
                    rail = scannerNumber.nextInt();
                    System.out.println(rfcDecrypt(EncryptedText,rail));
                }
                else if(dycEnc == 3){
                    System.out.println("Enter The Encrypted Text To Crack: ");
                    EncryptedText = scannerLine.nextLine();
                    System.out.println(rfcCrack(EncryptedText));
                }
                else if(dycEnc == 4) continue;
                else System.out.println("Unknown Option");
            }

            else if(option == 2){
                System.out.println("1.Encrypt\t2.Analysis\t3.Exit");
                int dycAnalysis = scannerNumber.nextInt();
                if(dycAnalysis == 1){
                    System.out.println("Enter The PlainText To Encrypt: ");
                    PlainText = scannerLine.nextLine();
                    System.out.println(lssEncrypt(PlainText,lookUpTable));
                }
                else if(dycAnalysis == 2){
                    System.out.println("Enter The Encrypted Text To Analysis: ");
                    EncryptedText = scannerLine.nextLine();
                    System.out.println(lssAnalyze(EncryptedText,englishLetterFrequency));
                }
                else if(dycAnalysis == 3) continue;
                else System.out.println("Unknown Option");
            }
            else{
                System.out.println("Unknown Option");
            }
        }
    }
}