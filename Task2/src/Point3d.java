public class Point3d {

    private double X_coord;
    private double Y_coord;
    private double Z_coord;

    public Point3d() {

        //this.X_coord = 0.0;
        //this.Y_coord = 0.0;
        //this.Z_coord = 0.0;
        this(0,0,0);
    }

    public Point3d(double x, double y, double z){

        this.X_coord = x;
        this.Y_coord = y;
        this.Z_coord = z;
    }

    public void setX_coord(double x){
        this.X_coord = x;
    }
    public void setY_coord(double y){
        this.Y_coord = y;
    }
    public void setZ_coord(double z){
        this.Z_coord = z;
    }


    public double getX_cord(){
        return this.X_coord;
    }
    public double getY_cord(){
        return this.Y_coord;
    }
    public double getZ_cord(){
        return this.Z_coord;
    }


    public boolean equals(Point3d Object){
        return this.X_coord == Object.X_coord && this.Y_coord == Object.Y_coord && this.Z_coord == Object.Z_coord;
    }


        public double distanceTo(Point3d Object){
        double distance = Math.sqrt(
                Math.pow(Math.abs(this.X_coord - Object.X_coord), 2)
                        + Math.pow(Math.abs(this.Y_coord - Object.Y_coord), 2)
                        + Math.pow(Math.abs(this.Z_coord - Object.Z_coord), 2)
        );
        return distance;
    }

}
