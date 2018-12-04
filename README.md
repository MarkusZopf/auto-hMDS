# The auto-<i>h</i>MDS Corpus
The auto-<i>h</i>MDS corpus is a (a) large, (b) heterogeneous, (c) multilingual, (d) multi-document summarization corpus. The corpus is an automatically generated extension of the manually created <i>h</i>MDS corpus (https://github.com/AIPHES/hMDS).

## Size

| language | topics | uncompressed size | source documents |
| -------- | ------ | ----------------- | ---------------- |
| de       | 2,210  |  1,8 GB           | 10,454           |
| en       | 5,106  |  12,5 GB          | 54,290           |
| total    | 7,316  |  14,3 GB          | 64,744           |

## Reference
If you plan to refer to auto-<i>h</i>MDS Corpus in your publications, please cite [the corresponding LREC 2018 paper](http://www.lrec-conf.org/proceedings/lrec2018/pdf/1018.pdf):

```
@InProceedings{Zopf2018autohMDS,
  author    = {Zopf, Markus},
  title     = {auto-hMDS: Automatic Construction of a Large Heterogeneous Multilingual Multi-Document Summarization Corpus},
  booktitle = {Proceedings of the 11th International Conference on Language Resources and Evaluation (LREC 2018)},
  month     = {May},
  year      = {2018},
  address   = {Miyazaki, Japan},
  publisher = {Association for Computational Linguistics},
  pages     = {to appear},
  website = {https://github.com/AIPHES/auto-hMDS}
}
```

## Folder/File Hierarchy
Below the top-level folder, the language-specific parts of the corpus can be found. Language-specific folder names have the pattern "auto-hMDS-xx" where xx indicates the language. Currently, the corpus contains English "auto-hMDS-en" and German "auto-hMDS-de" topics.

In every language-specific folder, the topics can be found. The topic folders follow the pattern "x_y_z". 
	- x indicates the topic id which equals the Wikipedia pageid. For topic "25_799993248_Autism", the topic id is 25 (http://en.wikipedia.org/?curid=25).
	- y indicates the revision id of the Wikipedia article. For topic "25_799993248_Autism", we included revision 799993248 in our corpus (https://en.wikipedia.org/w/index.php?title=Autism&oldid=799993248).
	- z indicates the topic name. The topic name of topic "25_799993248_Autism" is therefore "Autism".

A list of all topics for every language can be found in the "topics-xx.txt" files where xx indicates the language.
	
We illustrate the corpus structure below.
```
- auto-hMDS corpus
	- auto-hMDS-de
		- 96_165707958_Argon
		- 97_168947548_Arsen
		- 101_167549035_Americium
		- 102_168948138_Atom
		- 140_168992757_Aristoteles
		- ...
	- auto-hMDS-en
		- 25_799993248_Autism
		- 621_798125813_Amphibian
		- 663_800770271_Apollo 8
		- 751_800460460_Aikido
		- 798_797054480_Aries (constellation)
		- ...
```
Every topic has 2 sub-folders "input" and "reference".

The reference folder contains the original summary in the file "reference.txt" and a sentence-segmented version in the file "reference-segmented.txt". Every line in a "reference-segmented.txt" file contains one sentence. For every line (i.e. sentence) in "reference-segmented.txt" source web pages have been retrieved. The URLs of the web pages can be found in the "sentence_x_urls.txt" files where x indicates the sentence index. The first sentence index in "reference-segmented.txt" has index 0. The first line in "sentence_x_urls.txt" contains the sentence text and the following lines contain one URL per line. 

Summaries, sentence-segmented summaries, and the URL lists for individual sentences can be found in the file auto-hMDS reference.zip.

The input folder contains the input documents which have to be summarized. The input files have the pattern "sentence_x_y.*" where x indicates the sentences id and y indicates the link id. Note that neither the sentences ids nor the link ids have to be consecutively numbered. Links were skipped if a web pages was not retrievable. Sentences where skipped if all links of a sentence were not retrievable.

We provide 2 versions of every input document. *.html files contain the HTML code of the retrieved web page. *.html.ke.txt files contain all visible content of the web pages. The visible content has been extracted with the Boilerpipe Keep Everything boilerplate removal tool and does not contain HTML tags anymore. The structure of the topic folders is illustrated below.

```
- 96_165707958_Argon
	-input
		- sentence_1_1.html
		- sentence_1_1.html.ke.txt
		- sentence_2_1.html
		- sentence_2_1.html.ke.txt
		- ...
	-reference
		- reference.txt
		- reference-segmented.txt
		- sentence_0_urls.txt
		- sentence_1_urls.txt
		- sentence_2_urls.txt
		- ...
```

## Obtaining the Corpus
To obtain the corpus, please contact one of the following persons:

* [Markus Zopf](https://www.aiphes.tu-darmstadt.de/de/aiphes/people/doctoral-researchers/markus-zopf)
