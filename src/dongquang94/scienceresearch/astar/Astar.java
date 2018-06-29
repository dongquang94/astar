/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongquang94.scienceresearch.astar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dongquang94
 */
public class Astar extends Thread implements Runnable {

    static List<List<MatrixBean>> matrix = new ArrayList<List<MatrixBean>>();
    static List<MatrixBean> open = new ArrayList<>();
    static List<MatrixBean> close = new ArrayList<>();
    static List<List<MatrixBean>> father = new ArrayList<List<MatrixBean>>();
    static MatrixBean start = new MatrixBean();
    static MatrixBean end = new MatrixBean();
    //-------------
    static int indexOp;
    static MatrixBean vOld, vOldCl;
    static boolean check;
    static int select = 0;
    static int timeThread = 500;

    @Override
    public void run() {
        try {
            check = false;
            MatrixBean u = new MatrixBean();
            MatrixBean v = new MatrixBean();
            start.setH(setHT(start.getX(), start.getY()));
            start.setF(start.getH());
            MatrixBean uOld = start;
            open.add(start);
            while (!open.isEmpty()) {
                u = open.get(0);
                open.remove(0);
                close.add(u);
                if (u.getX() == end.getX() && u.getY() == end.getY()) {
                    check = true;
                    Maps.paintOpen(uOld.getX(), uOld.getY());
                    break;
                }

                v = matrix.get(u.getX()).get(u.getY() - 1); // Ä�iá»ƒm náº±m bÃªn trÃªn
                setHeu(v.getX(), v.getY());
                setChildHeu(u, v, 2);

                v = matrix.get(u.getX()).get(u.getY() + 1); // Ä�iá»ƒm náº±m bÃªn dÆ°á»›i
                setHeu(v.getX(), v.getY());
                setChildHeu(u, v, 2);

                v = matrix.get(u.getX() - 1).get(u.getY()); // Ä�iá»ƒm náº±m bÃªn trÃ¡i
                setHeu(v.getX(), v.getY());
                setChildHeu(u, v, 2);

                v = matrix.get(u.getX() + 1).get(u.getY()); // Ä�iá»ƒm náº±m bÃªn pháº£i
                setHeu(v.getX(), v.getY());
                setChildHeu(u, v, 2);

                v = matrix.get(u.getX() - 1).get(u.getY() - 1); // Ä�iá»ƒm náº±m chÃ©o trÃªn trÃ¡i
                setHeu(v.getX(), v.getY());
                setChildHeu(u, v, 4);

                v = matrix.get(u.getX() - 1).get(u.getY() + 1); // Ä�iá»ƒm náº±m chÃ©o dÆ°á»›i trÃ¡i
                setHeu(v.getX(), v.getY());
                setChildHeu(u, v, 4);

                v = matrix.get(u.getX() + 1).get(u.getY() - 1); // Ä�iá»ƒm náº±m chÃ©o trÃªn pháº£i
                setHeu(v.getX(), v.getY());
                setChildHeu(u, v, 4);

                v = matrix.get(u.getX() + 1).get(u.getY() + 1); // Ä�iá»ƒm náº±m chÃ©o dÆ°á»›i pháº£i
                setHeu(v.getX(), v.getY());
                setChildHeu(u, v, 4);

                sortList();
                if (uOld.getX() == start.getX() && uOld.getY() == start.getY()) {
                    Maps.paintStart(start.getX(), start.getY());
                    Maps.paintRobo(u.getX(), u.getY());
                } else {
                    Maps.paintOpen(uOld.getX(), uOld.getY());
                    Maps.paintRobo(u.getX(), u.getY());
                }
                Thread.sleep(1000 - timeThread);
                uOld = u;
            }
        } catch (InterruptedException ex) {

        } finally {
            Maps.threadStop(check);
            Maps.btnPauCon.setEnabled(false);
        }
    }

    ;

    // Kiá»ƒm tra xem v cÃ³ trong open vÃ  close chÆ°a?
    // 0 - KhÃ´ng cÃ³ trong open vÃ  close
    // 1 - Ä�Ã£ cÃ³ trong open
    // 2 - Ä�Ã£ cÃ³ trong close
    static int checkExist(MatrixBean v) {
        boolean check1 = true, check2 = true;
        MatrixBean op = new MatrixBean();
        MatrixBean cl = new MatrixBean();
        for (int i = 0; i < open.size(); i++) {
            op = open.get(i);
            if (v.getX() == op.getX() && v.getY() == op.getY()) {
                check1 = false;
            }
        }
        for (int i = 0; i < close.size(); i++) {
            cl = close.get(i);
            if (v.getX() == cl.getX() && v.getY() == cl.getY()) {
                check2 = false;
            }
        }
        if (check1 == true && check2 == true) {
            return 0;
        } else if (check1 == false && check2 == true) {
            return 1;
        } else if (check1 == true && check2 == false) {
            return 2;
        } else {
            return 3;
        }
    }

    static void setChildHeu(MatrixBean u, MatrixBean v, int cost) {
        if (v.isStatus() == true) {
            //-------------------------------
            MatrixBean op;
            for (int i = 0; i < open.size(); i++) {
                op = open.get(i);
                if (v.getX() == op.getX() && v.getY() == op.getY()) {
                    vOld = op;
                    indexOp = i;
                }
            }
            for (MatrixBean cl : close) {
                if (v.getX() == cl.getX() && v.getY() == cl.getY()) {
                    vOldCl = cl;
                }
            }
            //------------------------------
            switch (select) {
                case 0:
                    v.setG(u.getG() + cost); // G(v) = G(u) + Cost(u,v)
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
            switch (checkExist(v)) {
                case 0:
                    v.setF(v.getH() + v.getG());
                    open.add(v);
                    setFather(u, v);
                    break;
                case 1:
                    try {
                        if (v.getG() < vOld.getG()) {
                            v.setF(v.getH() + v.getG());
                            open.set(indexOp, v);
                            delItemFather(v);
                            setFather(u, v);
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 2:
                    if (v.getG() < vOldCl.getG()) {
                        v.setF(v.getH() + v.getG());
                        close.remove(vOldCl);
                        open.add(v);
                        delItemFather(v);
                        setFather(u, v);
                    }
                    break;
                default:
                    break;
            }
            Maps.paintOpen(v.getX(), v.getY());
        }
    }

    static void sortList() {
        MatrixBean m;
        for (int i = 0; i < open.size() - 1; i++) {
            for (int j = i + 1; j < open.size(); j++) {
                if (open.get(i).getF() > open.get(j).getF()) {
                    m = open.get(i);
                    open.set(i, open.get(j));
                    open.set(j, m);
                }
            }
        }
    }

    static void setFather(MatrixBean u, MatrixBean v) {
        List<MatrixBean> list = new ArrayList<>();
        list.add(u);
        list.add(v);
        father.add(list);
    }

    static void delItemFather(MatrixBean v) {
        List<MatrixBean> list;
        for (int i = 0; i < father.size(); i++) {
            list = new ArrayList<>();
            list = father.get(i);
            if (list.get(1) == v) {
                father.remove(i);
            }
        }
    }

    static void getFather() {
        List<MatrixBean> list;
        MatrixBean bo = new MatrixBean(), con = end;
        boolean check = false;
        do {
            for (int i = 0; i < father.size(); i++) {
                list = new ArrayList<>();
                list = father.get(i);
                if (con.getX() == list.get(1).getX() && con.getY() == list.get(1).getY()) {
                    bo = list.get(0);
                }
            }
            con = bo;
            if (bo.getX() == start.getX() && bo.getY() == start.getY()) {
                check = true;
                break;
            }
            Maps.paintRoad(bo.getX(), bo.getY());
        } while (check == false);
    }
    /*
     static int setHC(int x, int y) {
     int dx = Math.abs(end.getX() - x);
     int dy = Math.abs(end.getY() - y);
     if (dx > dy) {
     return ((dx + dy) + ((3 - 2) * dy));
     } else {
     return ((dx + dy) + ((3 - 2) * dx));
     }
     }*/

    static int setHT(int x, int y) {
        int dx = Math.abs(end.getX() - x);
        int dy = Math.abs(end.getY() - y);
        return (dx + dy);
    }

    static void setHeu(int x, int y) {
        if (end.getX() == x && end.getY() == y) {

        } else {
            List<MatrixBean> list = matrix.get(x);
            MatrixBean m = list.get(y);
            if (m.getH() == 0) {
                m.setH(setHT(m.getX(), m.getY()));
                list.set(y, m);
                matrix.set(x, list);
            }
        }
    }
}
