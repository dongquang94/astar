/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongquang94.scienceresearch.astar;

/**
 *
 * @author dongquang94
 */
public class MatrixBean {

    public int x;
    public int y;
    public int h;
    public int g;
    public int f;
    public boolean status;
    public int time;

    public MatrixBean() {
        this.x = 0;
        this.y = 0;
        this.h = 0;
        this.g = 0;
        this.f = 0;
        this.status = true;
        this.time = 1;
    }

    public MatrixBean(int x, int y) {
        this.x = x;
        this.y = y;
        this.h = 0;
        this.g = 0;
        this.f = 0;
        this.status = true;
        this.time = 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getH() {
        return h;
    }

    public int getG() {
        return g;
    }

    public int getF() {
        return f;
    }

    public boolean isStatus() {
        return status;
    }

    public int getTime() {
        return time;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
