/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongquang94.scienceresearch.astar;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author dongquang94
 */
public class Maps extends JFrame implements MouseListener, MouseMotionListener, ActionListener, ChangeListener {

    private int a = 1, b = 1;
    private final int width, height;
    static JButton button, btnStart, btnClearMaps, btnDeleteMaps, btnBlogStart, btnBlogEnd, btnPauCon;
    private JComboBox cboSelect;
    private JMenuBar menuBar;
    static JLabel blog[][], lblComboBox, xx, yy, speed;
    static ImageIcon blogWall, blogDefault, blogTime, blogStart, blogEnd, blogRoad, blogOpen, blogRobo, blogOpenTime, blogRoadTime;
    private JPanel panelMaps, panelControl, panel;
    private final JLayeredPane layer;
    private JSlider slider;
    private List list;
    private MatrixBean m;
    private Thread t;

    public Maps(int w, int h) {
        width = (w - 360) / 20; // 50
        height = (h - 100) / 20; // 33
        setTitle("Find the road for robot");
        setBounds(0, 0, width * 20 + 360, height * 20 + 60);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        layer = new JLayeredPane();
        layer.setPreferredSize(new Dimension(width * 20, height * 20));

        blogDefault = new ImageIcon("picture/blogDefault.png");
        blogWall = new ImageIcon("picture/blogWall.png");
        blogTime = new ImageIcon("picture/blogTime.png");
        blogStart = new ImageIcon("picture/blogStart.png");
        blogEnd = new ImageIcon("picture/blogEnd.png");
        blogOpen = new ImageIcon("picture/blogOpen.png");
        blogRobo = new ImageIcon("picture/blogRobo.png");
        blogRoad = new ImageIcon("picture/blogRoad.png");
        blogRoadTime = new ImageIcon("picture/blogRoadTime.png");
        blogOpenTime = new ImageIcon("picture/blogOpenTime.png");

        for (int i = 0; i < width; i++) {
            list = new ArrayList<>();
            for (int j = 0; j < height; j++) {
                if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
                    m = new MatrixBean();
                    m.setStatus(false);
                    m.setX(i);
                    m.setY(j);
                } else {
                    m = new MatrixBean();
                    m.setX(i);
                    m.setY(j);
                }
                list.add(m);
            }
            Astar.matrix.add(list);
        }

        blog = new JLabel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
                    setupMatrix(i, j, 1);
                    blog[i][j] = new JLabel(blogWall);
                } else {
                    setupMatrix(i, j, 2);
                    blog[i][j] = new JLabel(blogDefault);
                }
                blog[i][j].setBounds(20 * i, j * 20, 20, 20); //10 là khoảng cách cho lọt vào frame
                layer.add(blog[i][j]);
            }
        }

        layer.addMouseListener(this);
        layer.addMouseMotionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        panelMaps = new JPanel();
        panelMaps.setPreferredSize(new Dimension(width * 20, height * 20));
        panelMaps.add(layer, gbc);

        panelControl = new JPanel(new FlowLayout());
        panelControl.setPreferredSize(new Dimension(300, height * 20));

        btnStart = new JButton("START");
        btnStart.setPreferredSize(new Dimension(200, 100));
        btnStart.addActionListener(this);
        panelControl.add(btnStart);

        btnPauCon = new JButton("PAUSE");
        btnPauCon.setEnabled(false);
        btnPauCon.setPreferredSize(new Dimension(200, 50));
        btnPauCon.addActionListener(this);
        panelControl.add(btnPauCon);

        btnClearMaps = new JButton("RESET MAPS");
        btnClearMaps.setPreferredSize(new Dimension(200, 50));
        btnClearMaps.addActionListener(this);
        panelControl.add(btnClearMaps);

        btnDeleteMaps = new JButton("DELETE MAPS");
        btnDeleteMaps.setPreferredSize(new Dimension(200, 50));
        btnDeleteMaps.addActionListener(this);
        panelControl.add(btnDeleteMaps);

        lblComboBox = new JLabel("Choose form optimization:");
        lblComboBox.setPreferredSize(new Dimension(200, 30));
        panelControl.add(lblComboBox);

        String[] textJcbo = {"Times + Way", "Times", "Way"};
        cboSelect = new JComboBox(textJcbo);
        cboSelect.setPreferredSize(new Dimension(200, 30));
        cboSelect.addActionListener(this);
        panelControl.add(cboSelect);

        btnBlogStart = new JButton("Start Point");
        btnBlogStart.setPreferredSize(new Dimension(98, 50));
        btnBlogStart.addActionListener(this);
        panelControl.add(btnBlogStart);

        btnBlogEnd = new JButton("End Point");
        btnBlogEnd.setPreferredSize(new Dimension(98, 50));
        btnBlogEnd.addActionListener(this);
        panelControl.add(btnBlogEnd);

        speed = new JLabel("Speed Control");
        speed.setPreferredSize(new Dimension(200, 30));
        panelControl.add(speed);

        slider = new JSlider(JSlider.HORIZONTAL, 10, 1000, 500);
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(10);
        slider.addChangeListener(this);
        panelControl.add(slider);

        xx = new JLabel("Coordinate of pointer now:");
        yy = new JLabel("( x,  y)");
        panelControl.add(xx, gbc);
        panelControl.add(yy, gbc);

        panel = new JPanel();
        panel.add(panelMaps, gbc);
        panel.add(panelControl, gbc);
        add(panel);
        setVisible(true);
    }

    //được gọi khi một nút chuột được nhấn và nhả trên component mà không di chuyển chuột.
    @Override
    public void mouseClicked(MouseEvent me) {
        a = (int) me.getX() / 20;
        b = (int) me.getY() / 20;
        if (a == 0 || b == 0 || a == width - 1 || b == height - 1) {
            System.out.println("You click wall!");
        } else {
            if (SwingUtilities.isLeftMouseButton(me)) { // leftMouse then set to wall - status = false
                if (Astar.matrix.get(a).get(b).isStatus() == true) {
                    setupMatrix(a, b, 1);
                    blog[a][b].setIcon(blogWall);
                } else if (Astar.matrix.get(a).get(b).isStatus() == false) {
                    setupMatrix(a, b, 2);
                    blog[a][b].setIcon(blogDefault);
                }
            } else { // rightMouse then set to time - time = 3, default = 1
                if (Astar.matrix.get(a).get(b).isStatus() == true && Astar.matrix.get(a).get(b).getTime() == 1) {
                    setupMatrix(a, b, 3);
                    blog[a][b].setIcon(blogTime);
                } else if (Astar.matrix.get(a).get(b).isStatus() == true && Astar.matrix.get(a).get(b).getTime() == 3) {
                    setupMatrix(a, b, 4);
                    blog[a][b].setIcon(blogDefault);
                }
            }
            yy.setText("(" + a + ", " + b + ")");
        }
    }

    // được gọi khi một nút chuột được nhấn và con trỏ chuột ở trên component.
    @Override
    public void mousePressed(MouseEvent me) {
    }

    //được gọi khi một nút chuột nhả sau khi kéo rê.
    @Override
    public void mouseReleased(MouseEvent me) {

    }

    //được gọi khi con trỏ chuột vào trong đường biên của một component.
    @Override
    public void mouseEntered(MouseEvent me) {
    }

    //được gọi khi con trỏ chuột ra khỏi đường biên của một component.
    @Override
    public void mouseExited(MouseEvent me) {
    }

    //phương thức này được gọi khi người dùng nhấn một nút chuột và kéo trên một component.
    @Override
    public void mouseDragged(MouseEvent me) {
        a = (int) me.getX() / 20;
        b = (int) me.getY() / 20;
        if (a == 0 || b == 0 || a == width - 1 || b == height - 1) {
        } else {
            if (SwingUtilities.isLeftMouseButton(me)) {
                try {
                    if (Astar.matrix.get(a).get(b).isStatus() == true) {
                        setupMatrix(a, b, 1);
                        blog[a][b].setIcon(blogWall);
                    }
                } catch (Exception e) {
                }
            } else if (SwingUtilities.isRightMouseButton(me)) {
                try {
                    if (Astar.matrix.get(a).get(b).isStatus() == true && Astar.matrix.get(a).get(b).getTime() == 1) {
                        setupMatrix(a, b, 3);
                        blog[a][b].setIcon(blogTime);
                    }
                } catch (Exception e) {
                }
            }
            yy.setText("(" + a + ", " + b + ")");
        }
    }

    //phương thức này được gọi khi di chuyển chuột trên component.
    @Override
    public void mouseMoved(MouseEvent me) {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            JComboBox cb = (JComboBox) ae.getSource();
            int selected = cb.getSelectedIndex();
            Astar.select = selected;
            //System.out.println(Astar.select);
        } catch (Exception e) {
            //System.out.println("Error");
        }
        String command = ae.getActionCommand();
        if (a == 0 || b == 0 || a == width - 1 || b == height - 1) {
            System.out.println("You click wall!");
        } else {
            if (btnBlogStart == ae.getSource()) {
                if (Astar.start.getX() == 0 && Astar.start.getY() == 0) {
                    Astar.start.setX(a);
                    Astar.start.setY(b);
                    Astar.start.setStatus(true);
                    setupMatrix(a, b, 2);
                    blog[a][b].setIcon(blogStart);
                } else {
                    blog[Astar.start.getX()][Astar.start.getY()].setIcon(blogDefault);
                    Astar.start.setX(a);
                    Astar.start.setY(b);
                    Astar.start.setStatus(true);
                    setupMatrix(a, b, 2);
                    blog[a][b].setIcon(blogStart);
                }
            } else if (btnBlogEnd == ae.getSource()) {
                if (Astar.end.getX() == 0 && Astar.end.getY() == 0) {
                    Astar.end.setX(a);
                    Astar.end.setY(b);
                    Astar.end.setStatus(true);
                    setupMatrix(a, b, 2);
                    blog[a][b].setIcon(blogEnd);
                } else {
                    blog[Astar.end.getX()][Astar.end.getY()].setIcon(blogDefault);
                    Astar.end.setX(a);
                    Astar.end.setY(b);
                    Astar.end.setStatus(true);
                    setupMatrix(a, b, 2);
                    blog[a][b].setIcon(blogEnd);
                }
            }
        }
        if (btnStart == ae.getSource()) {
            t = new Astar();
            t.start();
            btnPauCon.setEnabled(true);
        }
        if (btnClearMaps == ae.getSource()) {
            for (int i = 0; i < width; i++) {
                list = new ArrayList<>();
                list = Astar.matrix.get(i);
                for (int j = 0; j < height; j++) {
                    if (blog[i][j].getIcon() == blogRoad || blog[i][j].getIcon() == blogOpen || blog[i][j].getIcon() == blogRobo) {
                        blog[i][j].setIcon(blogDefault);
                    }
                    m = new MatrixBean();
                    m = (MatrixBean) list.get(j);
                    m.setF(0);
                    m.setG(0);
                    m.setH(0);
                    list.set(j, m);
                    Astar.matrix.set(i, list);
                    Astar.open.clear();
                    Astar.close.clear();
                    Astar.father.clear();
                }
            }
        }
        if (btnDeleteMaps == ae.getSource()) {
            Astar.matrix.clear();
            for (int i = 0; i < width; i++) {
                list = new ArrayList<>();
                for (int j = 0; j < height; j++) {
                    if (i == 0 || i == width - 1 || j == 0 || j == height - 1) {
                        m = new MatrixBean();
                        m.setStatus(false);
                        m.setX(i);
                        m.setY(j);
                    } else {
                        m = new MatrixBean();
                        m.setX(i);
                        m.setY(j);
                    }
                    list.add(m);
                }
                Astar.matrix.add(list);
            }
            //---------
            for (int i = 1; i < width - 1; i++) {
                for (int j = 1; j < height - 1; j++) {
                    blog[i][j].setIcon(blogDefault);
                }
            }
            Astar.open = new ArrayList<MatrixBean>();
            Astar.close = new ArrayList<MatrixBean>();
            Astar.father = new ArrayList<List<MatrixBean>>();
        }
        if (btnPauCon == ae.getSource()) {
            if (command.equals("Tạm dừng")) {
                t.suspend();
                btnPauCon.setText("Tiếp tục");
            }
            if (command.equals("Tiếp tục")) {
                t.resume();
                btnPauCon.setText("Tạm dừng");
            }
        }
    }

    static void threadStop(boolean check) {
        if (check == false) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy điểm cuối!");
        } else {
            Astar.getFather();
            JOptionPane.showMessageDialog(null, "Tìm kiếm thành công!");
        }
    }

    void setupMatrix(int x, int y, int a) {
        switch (a) {
            case 1: // set status to false
                list = new ArrayList<>();
                list = Astar.matrix.get(x);
                m = new MatrixBean();
                m = (MatrixBean) list.get(y);
                m.setStatus(false);
                Astar.matrix.set(x, list).set(y, m);
                break;
            case 2: // set status to true
                list = new ArrayList<>();
                list = Astar.matrix.get(x);
                m = new MatrixBean();
                m = (MatrixBean) list.get(y);
                m.setStatus(true);
                Astar.matrix.set(x, list).set(y, m);
                break;
            case 3: // set time = 2
                list = new ArrayList<>();
                list = Astar.matrix.get(x);
                m = new MatrixBean();
                m = (MatrixBean) list.get(y);
                m.setTime(2);
                Astar.matrix.set(x, list).set(y, m);
                break;
            case 4: // set time = 1
                list = new ArrayList<>();
                list = Astar.matrix.get(x);
                m = new MatrixBean();
                m = (MatrixBean) list.get(y);
                m.setTime(1);
                Astar.matrix.set(x, list).set(y, m);
                break;
        }
    }

    static void paintOpen(int a, int b) {
        if ((a == Astar.start.getX() && b == Astar.start.getY()) || (a == Astar.end.getX() && b == Astar.end.getY())) {

        } else {
            blog[a][b].setIcon(blogOpen);
        }
    }

    static void paintRoad(int a, int b) {
        blog[a][b].setIcon(blogRoad);
    }

    static void paintRobo(int a, int b) {
        blog[a][b].setIcon(blogRobo);
    }

    static void paintStart(int a, int b) {
        blog[a][b].setIcon(blogStart);
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        JSlider source = (JSlider) ce.getSource();
        if (!source.getValueIsAdjusting()) {
            Astar.timeThread = (int) source.getValue();
        }
    }
}
