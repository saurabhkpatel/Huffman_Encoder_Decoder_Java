# Huffman In Java

This program is part of quiz.

Please read quiz description here : 
http://rubyquiz.com/quiz123.html

**Build Details**
-----------------

**For Compile:** Go to src directory where .java files reside. Below command will generate .class files for each java files.

`javac HuffmanQuizMain.java`


**For Run :** Where .class files generated from there run below command 

With Command Line argument : 
`java HuffmanQuizMain "Hello how are you"`

Without Command Line argument :
`java HuffmanQuizMain`

**Note** : Please use proper command line arguments as instructed. Only give one command line argument if you want to test userinput otherwise unit test cases will be executed.

When command line argument has not provided at that time program will run it's by default unit test cases.
When user provides command line argument at that time output displays based on input string.

**Note :** Only one command line argument is allowed.

**Example :**
```
java HuffmanQuizMain "How are you doing"
```
Sample Output :

```
 === User Input Test Case === 
** Input Data : How are you doing 17 bytes

=> Frequency Table : 
     : 3
   a : 1
    : 1
   d : 1
   e : 1
   g : 1
   H : 1
   i : 1
   n : 1
   o : 3
   r : 1
   u : 1
   w : 1
   y : 1

=> The encoding for each character : 
     : 000
   a : 1101
    : 1000
   d : 1110
   e : 1001
   g : 0111
   H : 0110
   i : 0100
   n : 1010
   o : 001
   r : 1011
   u : 0101
   w : 1111
   y : 1100

=> Here is the original data encoded : 
   011000111110001101101110010001100001010100011100010100101001111000
   Compressed data fits in 9 bytes

=> Original Input string after decompression : 
   How are you doing

*** Compressed :      47%

```
