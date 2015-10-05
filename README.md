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


When command line argument has not provided at that time program will run it's by default unit test cases.
When user provides command line argument at that time output displays based on input string.

**Note :** Only one command line argument is allowed.

**Example :**
```
java HuffmanQuizMain "Hello how are you?"
```
Sample Output :

```
 === User Input Test Case === 
** Input Data : Hello how are you? 18 bytes

=> Frequency Table : 
     : 3
   a : 1
    : 1
   e : 2
   H : 1
   h : 1
   l : 2
   o : 3
   r : 1
   u : 1
   w : 1
   y : 1
   ? : 1

=> The encoding for each character : 
     : 000
   a : 1101
    : 00100
   e : 111
   H : 1000
   h : 0011
   l : 101
   o : 010
   r : 0110
   u : 00101
   w : 0111
   y : 1001
   ? : 1100

=> Here is the original data encoded : 
   10001111011010100000011010011100011010110111000100101000101110000100
   Compressed data fits in 9 bytes

=> Original Input string after decompression : 
   Hello how are you?

*** Compressed :      50%`
```
