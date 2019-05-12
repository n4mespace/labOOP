class Letter {
    private char letter;

    public Letter(char letter) {
        this.letter = letter;
    }

    @Override
    public String toString() {
        return Character.toString(letter);
    }
}

class Word extends SentenceMember {
    private Letter[] letters;

    public Word(String s) {
        char[] chars = s.toCharArray();
        this.letters = new Letter[s.length()];
        for (int i = 0; i < chars.length; i++) {
            this.letters[i] = new Letter(chars[i]);
        }
    }

    public static Word subword(Word word, int from) {
        return new Word(word.toString().substring(from));
    }

    public int getLength() {
        return this.letters.length;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Letter letter : this.letters) {
            result.append(letter.toString());
        }
        return result.toString();
    }
}

abstract class SentenceMember {}

class Sentence {
    private SentenceMember[] sentenceMembers;

    public Sentence(String s) {
        String[] split = s.split("(?=[,.!?])|\\s");
        sentenceMembers = new SentenceMember[split.length];

        for (int i = 0; i < split.length; i++) {
            String punctuationSymbols = ",.!?;:";
            if (punctuationSymbols.contains(split[i])) {
                sentenceMembers[i] = new PunctuationMark(split[i]);
            } else {
                sentenceMembers[i] = new Word(split[i]);
            }
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < sentenceMembers.length; i++) {
            result.append(sentenceMembers[i] instanceof Word && i != 0 ?
                    " " : "").append(sentenceMembers[i].toString());
        }
        return result.toString();
    }

    public Word[] toWords() {
        Word[] words = new Word[this.sentenceMembers.length];
        int i = 0;

        for (SentenceMember member : this.sentenceMembers) {
            if (member instanceof Word) {
                words[i] = (Word) member;
                ++i;
            }
        }
        return words;
    }

    public int getLength() { return this.sentenceMembers.length; }
}


class Text {
    private Sentence[] sentences;

    public Text(String s) {
        String[] split = s.split("(?<=[?!.]) ");
        this.sentences = new Sentence[split.length];
        for (int i = 0; i < split.length; i++) {
            this.sentences[i] = new Sentence(split[i]);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Sentence sentence : this.sentences) {
            result.append(sentence.toString()).append(" ");
        }
        return result.toString();
    }

    public Text Lab3() {
        int length = 0;
        for (Sentence sentence : this.sentences) { length += sentence.getLength(); }

        Word remove;
        StringBuilder result = new StringBuilder(length);

        for (Sentence sentence : this.sentences) {
            for (Word word : sentence.toWords()) {
                if (word == null) continue;

                //If you don't wanna to delete special characters
                //remove = word.replaceAll("[(.,!?\n\r)]", "")
                //        .substring(word.replaceAll("[(.,!?\n\r)]", "").length()-1);
                remove = Word.subword(word, word.getLength() - 1);

                result.append(word.toString()).append(" ");

                result = new StringBuilder(result.toString()
                        .replaceAll(remove.toString(), ""));
            }
        }

        return new Text(result.toString());
    }
}

class PunctuationMark extends SentenceMember {
    private String symbols;

    public PunctuationMark(String symbols) {
        this.symbols = symbols;
    }

    @Override
    public String toString() {
        return this.symbols;
    }
}

public class lab_5 {
    public static void main(String[] args) {
        String plot = "The Original Series began in 1864 and continues to the present." +
                "In 1867 an Extra Series was started, of texts already printed " +
                "but not in satisfactory or readily obtainable editions." +
                "In 1921 the Extra Series was discontinued and all publications were subsequently " +
                "listed and numbered as part of the Original Series.";

        Text text = new Text(plot);                                                                                                                                                                                                                                                                                                                                                 
        System.out.println(text.Lab3().toString());
    }
}


