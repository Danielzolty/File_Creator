# File_Creator
Produced search engine that stores text or pdf files in memory, utilizing several built-from-scratch data structures, including Stack, Trie, Heap, and B-Tree.

Using PDF Box external library, the application can return or insert any document as a pdf or a text.

User can limit how many documents can be stored in memory.

If the limit is reached, the least recently used document is stored on disk in JSON format by using Gson library.

Programmed to keep an O(1) dictionary lookup which maps words/prefixes to all documents containing that word/prefix.

System tracks all actions sequentially, allowing for undo capability.
