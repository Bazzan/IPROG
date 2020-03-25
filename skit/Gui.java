
import javax.swing.*;


public class Gui extends JFrame{
    public TextArea ta = new TextArea();


    static class TextArea extends JPanel {
        protected JTextField txtfield;
        protected JTextArea textArea;
        private final static String newline = "\n";

        public TextArea() {
            // super( new GridBagLayout());
            txtfield = new JTextField(20);

            textArea = new JTextArea(30,30);
            textArea.setSize(600, 800);

            add(txtfield);
            add(textArea);

            // setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            textArea.setColumns(20);
            textArea.setLineWrap(true);
            textArea.setRows(5);
            textArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(textArea);

        }

        public static void mgui() {
            TextArea ta = new TextArea();

            JFrame frame = new JFrame("Databas koppling");
            frame.setSize(600,800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new TextArea());
            frame.pack();
            frame.setVisible(true);

        }
    }

    public static void main(String[] args) {


        TextArea.mgui();
    }
}