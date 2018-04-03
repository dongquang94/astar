/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongquang94.astar;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author DongQuang
 */
public class NghienCuuKhoaHoc {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dim = toolkit.getScreenSize();
        Maps bando = new Maps(dim.width, dim.height);
    }

}
