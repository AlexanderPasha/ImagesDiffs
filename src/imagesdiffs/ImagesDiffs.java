/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagesdiffs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author manny
 */
public class ImagesDiffs {

    static int columns, rows;
    static int[][] massive;
    static ArrayList<Region> regions = null;
    static Set<Point> points;
    static BufferedImage image3;
    static File file3;

    public static void main(String[] args) {
        try {
            //Считываем файлы
            File file1 = new File("image1.png");
            File file2 = new File("image2.png");
            regions = new ArrayList<>();
            BufferedImage image1 = ImageIO.read(file1);
            BufferedImage image2 = ImageIO.read(file2);
            //Получаем данные для массива
            columns = image1.getWidth();
            rows = image1.getHeight();
            //Инициализируем массив
            massive = new int[rows][columns];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    massive[row][col] = 0;
                }
            }
            //Строим третье изображение и указываем в массиве отличающиеся пиксели
            image3 = new BufferedImage(columns, rows, BufferedImage.TYPE_INT_RGB);
            int count = 0;
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    int rgb = image1.getRGB(col, row);
                    int rgb2 = image2.getRGB(col, row);

                    if (rgb == rgb2) {

                        image3.setRGB(col, row, rgb2);
                    } else {
                        image3.setRGB(col, row, rgb2);
                        count++;
                        massive[row][col] = -1;
                    }
                }
            }
            //Создаем файл
            file3 = new File("image3.png");

            //Делим отличающиеся области на отдельные регионы в массиве
            recursPoisk(massive);

            for (Region r : regions) {
                //Получаем координаты прямоугольников для обвода отличающихся регионов
                getRegionRectangle(r);
                //Рисуем на новом изображении обводные прямоугольники
                drowRegions(r);

            }
            //Сохраняем картинку в новый файл
            ImageIO.write(image3, "PNG", file3);

        } catch (IOException ex) {
            Logger.getLogger(ImagesDiffs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Метод рисующий обводной прямоугольник для региона
    public static void drowRegions(Region region) {
        for (int i = region.getLeftCol(); i <= region.getRightCol(); i++) {
            image3.setRGB(i, region.getUpRow(), 16711680);
        }
        for (int i = region.getUpRow(); i <= region.getDownRow(); i++) {
            image3.setRGB(region.getLeftCol(), i, 16711680);
        }
        for (int i = region.getUpRow(); i <= region.getDownRow(); i++) {
            image3.setRGB(region.getRightCol(), i, 16711680);
        }
        for (int i = region.getLeftCol(); i <= region.getRightCol(); i++) {
            image3.setRGB(i, region.getDownRow(), 16711680);
        }
    }

    //Метод разделяющий все отличающиеся пиксели на отдельные регионы
    static public void recursPoisk(int[][] massive) {
        int count = 1;
        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col < columns - 1; col++) {
                if (massive[row][col] == -1) {
                    points = new HashSet<>();
                    Region region = new Region(count);
                    region.setId(count);
                    NearDots(row, col, count);
                    region.setPixels(points);
                    regions.add(region);
                    count++;
                }
            }
        }
    }

    //Метод группирующий все отличающиеся значения находящиеся рядом в один регион
    static public void NearDots(int row, int col, int count) {

        if (massive[row - 1][col - 1] == -1) {
            Point point = new Point(row, col);
            points.add(point);
            massive[row - 1][col - 1] = count;
            NearDots(row - 1, col - 1, count);
        }

        if (massive[row][col - 1] == -1) {
            Point point = new Point(row, col - 1);
            points.add(point);
            massive[row][col - 1] = count;
            NearDots(row, col - 1, count);
        }
        if (massive[row + 1][col - 1] == -1) {
            Point point = new Point(row + 1, col - 1);
            points.add(point);
            massive[row + 1][col - 1] = count;
            NearDots(row + 1, col - 1, count);
        }
        if (massive[row - 1][col] == -1) {
            Point point = new Point(row - 1, col);
            points.add(point);
            massive[row - 1][col] = count;
            NearDots(row - 1, col, count);
        }
        if (massive[row + 1][col] == -1) {
            Point point = new Point(row + 1, col);
            points.add(point);
            massive[row + 1][col] = count;
            NearDots(row + 1, col, count);
        }
        if (massive[row - 1][col + 1] == -1) {
            Point point = new Point(row - 1, col + 1);
            points.add(point);
            massive[row - 1][col + 1] = count;
            NearDots(row - 1, col + 1, count);
        }
        if (massive[row][col + 1] == -1) {
            Point point = new Point(row, col + 1);
            points.add(point);
            massive[row][col + 1] = count;
            NearDots(row, col + 1, count);
        }
        if (massive[row + 1][col + 1] == -1) {
            Point point = new Point(row + 1, col + 1);
            points.add(point);
            massive[row + 1][col + 1] = count;
            NearDots(row + 1, col + 1, count);
        }

    }

    //Метод находит крайние точки региона (для отрисовки прямоугольников)
    public static void getRegionRectangle(Region region) {
        int upRow = massive.length, downRow = 0, rightCol = 0, leftCol = massive.length;
        for (Point point : region.getPoints()) {

            if (point.x < upRow) {
                upRow = point.x;
            }
            if (point.x > downRow) {
                downRow = point.x;
            }
            if (point.y > rightCol) {
                rightCol = point.y;
            }
            if (point.y < leftCol) {
                leftCol = point.y;
            }
        }
        region.setDownRow(downRow);
        region.setUpRow(upRow);
        region.setLeftCol(leftCol);
        region.setRightCol(rightCol);
    }

}
