import javax.swing.*;

public class Screen extends JPanel{
    private static final int ALTURA_BARRA_TITULO = 20;
    private JFrame frame;

    public Screen(String nome) {
        frame = new JFrame(nome); // cria um frame
        frame.add(this); // insere o território no frame
        frame.setSize(400, 300 + ALTURA_BARRA_TITULO ); // define as dimensões do frame
        frame.setVisible(true); // torna o frame visível
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // define como o frame é fechado
    }
}
