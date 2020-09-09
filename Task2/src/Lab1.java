import java.util.Scanner;

public class Lab1 {
    public static void main(String[] args) {
        Point3d[] points = new Point3d[3];
        for (int i = 0; i < 3; i++){
            System.out.printf("Введите координаты точки %d разделяя пробелом: ", i+1);
            Scanner scanner = new Scanner(System.in);
            String point = scanner.nextLine();
            String[] coordinates =  point.split(" ");
            double x = Double.parseDouble(coordinates[0]);
            double y = Double.parseDouble(coordinates[1]);
            double z = Double.parseDouble(coordinates[2]);

            points[i] = new Point3d(x,y,z);
        }
        double area = computeArea(points[0], points[1], points[2]);
        if (area == 0.0) {
            System.out.println("Треугольника не существует");
        }
        else {
            System.out.println("Площадь теругольника: " +area);
        }

    }

    public static double computeArea(Point3d point1, Point3d point2, Point3d point3){
        if (point1.equals(point2) || point1.equals(point3) || point2.equals(point3)) {
            return 0.0;
        }

        double perimeter = (point1.distanceTo(point2) + point1.distanceTo(point3) + point2.distanceTo(point3))/2;
        //расчет площади по формуле Герона
        double area2 = perimeter * (perimeter - point1.distanceTo(point2)) * (perimeter - point1.distanceTo(point3)) * (perimeter - point2.distanceTo(point3));
        double area = Math.sqrt(area2);
        return area;
    }
}
