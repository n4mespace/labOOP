/**
 * C3  = 8210 % 3  = 2  = > String
 * C17 = 8210 % 17 = 16
 */

public class lab_3 {
    public static void main(String[] args) {

        //Obviously, you can change your text via adding some input
        //system or reading from a file
        String text = "The Original Series began in 1864 and continues to the present.\n " +
                "In 1867 an Extra Series was started, of texts already printed " +
                "but not in satisfactory or readily obtainable editions.\n " +
                "In 1921 the Extra Series was discontinued and all publications were subsequently " +
                "listed and numbered as part of the Original Series.\n In 1970 the Supplementary " +
                "Series was initiated: volumes in this series are issued only occasionally and " +
                "as suitable texts become available.\n";

        StringBuilder newText = new StringBuilder(text.length());
        String remove;

        for (String word : text.split(" ")) {
            //If you don't wanna to delete special characters
            //remove = word.replaceAll("[(.,!?\n\r)]", "")
            //        .substring(word.replaceAll("[(.,!?\n\r)]", "").length()-1);

            remove = word.substring(word.length()-1);

            newText.append(word + " ");
            newText = new StringBuilder(newText.toString()
                    .replaceAll(remove, ""));
        }

        System.out.println(newText.toString());
    }
}
