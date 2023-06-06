James Choe
CSC 172-Lab 2
hchoe4
32242362
hchoe4@u.rochester.edu

This Java program implements a custom trie data structure.
The trie stores binary strings and provides methods for insertion, search, and retrieval of the smallest and largest strings.
Additionally, the program generates a textual representation of the trie after each operation.

<=== Classes ===>
Lab4 - The main class that contains the main method and drives the execution of the program.
Trie - The main class for the Brie Tree implementation. It contains methods for insertion, search, and retrieval of the smallest and largest strings, among others.
Node - A nested class within Trie that represents a node in the Brie Tree. Each node contains a binary string value and two child nodes (left and right).
TrieInsertResult - A nested class within Trie that represents the result of an insertion operation, containing the updated node, whether the insertion was successful, and the height of the tree.

<=== Methods ===>
insert(String x) - Inserts a binary string x into the Brie Tree.
search(String x) - Searches for the string in the Brie Tree that has the longest prefix match with x.
trieToList() - Returns a list of all binary strings stored in the Brie Tree.
largest() - Returns the largest binary string stored in the Brie Tree.
smallest() - Returns the smallest binary string stored in the Brie Tree.
size() - Returns the number of binary strings stored in the Brie Tree.
height() - Returns the height of the Brie Tree.
trieToString() - Generates a textual representation of the Brie Tree.

<=== Usage ===>
Compile: javac Lab4.java
Run: java Lab4 commands.txt
    replace commands.txt with path to input file containing commands for trie operations.

<=== Output ===>
The output will be displayed in the console and will show the result of each operation.
Additionally, a file named graphics.txt will be generated, containing a textual representation of the Brie Tree after each operation.

<=== Graphics ===>
For each insert operation the inserted binary string and a graphical representation of the Trie is shown.
For each Trie:
    Each line is a level in the Trie
    Each node is represented by [value, left, right]
    Nodes that have no value (internal nodes) have "-"
        ex: [-, 0, 1]
    If a Node doesn't have a child, that direction is represented as "-"
        ex: [value, -, 1]