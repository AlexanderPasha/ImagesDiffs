/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagesdiffs;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author manny
 */
public class Region {

    private Set<Point> pixels;
    private int id;
    private int upRow;
    private int downRow;
    private int leftCol;
    private int rightCol;

    public Region(int id) {
        this.id = id;
        pixels = new HashSet<>();

    }

    /**
     * @return the pixels
     */
    public Set<Point> getPoints() {
        return pixels;
    }

    /**
     * @param pixels the pixels to set
     */
    public void setPixels(Set<Point> pixels) {
        this.pixels = pixels;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the upRow
     */
    public int getUpRow() {
        return upRow;
    }

    /**
     * @param upRow the upRow to set
     */
    public void setUpRow(int upRow) {
        this.upRow = upRow;
    }

    /**
     * @return the downRow
     */
    public int getDownRow() {
        return downRow;
    }

    /**
     * @param downRow the downRow to set
     */
    public void setDownRow(int downRow) {
        this.downRow = downRow;
    }

    /**
     * @return the leftCol
     */
    public int getLeftCol() {
        return leftCol;
    }

    /**
     * @param leftCol the leftCol to set
     */
    public void setLeftCol(int leftCol) {
        this.leftCol = leftCol;
    }

    /**
     * @return the rightCol
     */
    public int getRightCol() {
        return rightCol;
    }

    /**
     * @param rightCol the rightCol to set
     */
    public void setRightCol(int rightCol) {
        this.rightCol = rightCol;
    }

}
