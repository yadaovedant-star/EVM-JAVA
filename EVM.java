import java.awt.*;
import java.awt.event.*;

public class EVM extends Frame implements ActionListener {

    int votes[] = new int[4];
    String names[] = {"INC", "BJP", "SHIVSENA", "NOTA"};

    Button vote[] = new Button[4];
    Button close, reset, result;

    boolean closed = false, show = false;

    Image img[] = new Image[4];

    int w, h;

    public EVM() {

        setLayout(null);

        
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        w = d.width;
        h = d.height;
        setSize(w, h);

        
        Toolkit t = Toolkit.getDefaultToolkit();
        img[0] = t.getImage("candA.jpeg");
        img[1] = t.getImage("candB.jpeg");
        img[2] = t.getImage("candC.jpeg");
        img[3] = t.getImage("candD.jpeg");

        int y = h / 6;

        for (int i = 0; i < 4; i++) {
            vote[i] = new Button("Vote");
            vote[i].setBounds(200, y + i * (h / 6), 100, 50);
            vote[i].addActionListener(this);
            add(vote[i]);
        }

        
        close = new Button("Close Voting");
        close.setBounds(w - 500, 50, 150, 50);
        add(close);

        reset = new Button("Reset");
        reset.setBounds(w - 330, 50, 150, 50);
        add(reset);

        result = new Button("Show Result");
        result.setBounds(w - 160, 50, 150, 50);
        add(result);

        close.addActionListener(this);
        reset.addActionListener(this);
        result.addActionListener(this);

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {

        Object s = e.getSource();

        
        for (int i = 0; i < 4; i++) {
            if (s == vote[i] && !closed) {
                votes[i]++;
                repaint();
            }
        }

        if (s == close) {
            closed = true;
            for (int i = 0; i < 4; i++) vote[i].setEnabled(false);
        }

        if (s == result && closed) {
            show = true;
            repaint();
        }

        if (s == reset) {
            for (int i = 0; i < 4; i++) {
                votes[i] = 0;
                vote[i].setEnabled(true);
            }
            closed = false;
            show = false;
            repaint();
        }
    }

    public void paint(Graphics g) {

        int y = h / 6;
        int size = h / 10;

       
        for (int i = 0; i < 4; i++) {

            if (img[i] != null)
                g.drawImage(img[i], 50, y + i * (h / 6), size, size, this);
            else {
                g.setColor(Color.ORANGE);
                g.fillOval(50, y + i * (h / 6), size, size);
            }

            g.setColor(Color.BLACK);
            g.drawString(names[i], 50, y + i * (h / 6) + size + 20);
        }

        
        if (show) {

            int total = 0;
            for (int i = 0; i < 4; i++) total += votes[i];

            int x = w / 3;
            int y2 = h / 3;

            int maxH = h / 3;

            int win = 0;

            for (int i = 0; i < 4; i++) {

                int p = (total == 0) ? 0 : (votes[i] * 100) / total;
                int bar = (p * maxH) / 100;

                g.setColor(Color.BLUE);
                g.fillRect(x + i * 140, y2 + maxH - bar, 100, bar);

                g.setColor(Color.BLACK);
                g.drawString("C" + (i + 1) + ": " + p + "%", x + i * 140, y2 + maxH + 20);

                if (votes[i] > votes[win]) win = i;
            }

            g.setColor(Color.RED);
            g.drawString(names[win] + " won", x, y2 + maxH + 60);
        }
    }

    public static void main(String args[]) {
        new EVM();
    }
}